package main;

import main.database.DatabaseConnection;
import main.database.MySqlConnection;
import main.pos.PointOfSale;
import main.pos.cart.Cart;
import main.pos.cart.CartOperations;
import main.pos.cart.CartService;
import main.pos.cart.DiscountCalculator;
import main.pos.cart.PercentageDiscountCalculator;
import main.product.ProductRepository;

import java.sql.SQLException;
import java.sql.Connection;
import main.billgeneration.*;
import main.billgeneration.bill.BillProcessData;
import main.billgeneration.bill.BillProcessDataForDailySales;
import main.billgeneration.bill.CSVReportGeneration;
import main.billgeneration.bill.FetchBillData;
import main.billgeneration.bill.FetchBillDataOperations;
import main.billgeneration.bill.FetchedBill;
import main.billgeneration.bill.PDFReportGeneration;
import main.billgeneration.bill.ProcessData;
import main.billgeneration.bill.ReportFormatter;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        DatabaseConnection connectionInstance = new MySqlConnection();

        Connection dbConnection = connectionInstance.connectDatabase();

        try {
            if (dbConnection != null && !dbConnection.isClosed()) {

                Scanner scanner = new Scanner(System.in);
                ProductRepository productRepository = new ProductRepository(connectionInstance);
                LocalDate date = LocalDate.now();
                String today = date.toString();

                System.out.println("Please choose an option:");
                System.out.println("1. Point of Sale operations");
                System.out.println("2. Generate bill report");
                System.out.println("3. Generate stock report");
                System.out.println("4. Generate stock reorder levels report");
                System.out.println("5. Generate daily sales report");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        // part 1: Point of Sale operations
                        Cart cart = new Cart();
                        DiscountCalculator discountCalculator = new PercentageDiscountCalculator(0.02);
                        CartService cartService = new CartOperations(cart, discountCalculator);
                        PointOfSale pos = new PointOfSale(productRepository, cartService, discountCalculator, cart,
                                connectionInstance);
                        pos.posTerminal();
                        break;

                    case 2:
                        // part 2: Generate bill report
                        FetchBillDataOperations fetchBillData = new FetchBillData(connectionInstance);
                        ProcessData billProcess = new BillProcessData(fetchBillData.getAllBills());
                        ReportFormatter csvFormat = new CSVReportGeneration();
                        ReportTemplate billReport = new SpecificReportTemplate(billProcess, csvFormat);
                        billReport.generateReport();
                        break;

                    case 3:
                        // part 3: Generate stock report
                        FetchStockBatchDataOperations fetchStockBatchData = new FetchStockBatchData(connectionInstance);
                        ProcessData stockBatchProcess = new StockProcessData(fetchStockBatchData.getAllStockBatches());
                        ReportFormatter pdfReportGeneration = new PDFReportGeneration();
                        ReportTemplate stockReport = new SpecificReportTemplate(stockBatchProcess, pdfReportGeneration);
                        stockReport.generateReport();
                        break;

                    case 4:
                        // part 4: Generate stock reorder levels report
                        fetchStockBatchData = new FetchStockBatchData(connectionInstance);
                        ReportFormatter pdfReportGenerationtwo = new PDFReportGeneration();
                        ProcessData stockProcessDataForReorderLevels = new StockProcessDataForReorderLevels(
                                fetchStockBatchData.getAllStockBatches(), productRepository.getAllProducts());
                                ReportTemplate stockReorderLevels = new SpecificReportTemplate(stockProcessDataForReorderLevels,
                                pdfReportGenerationtwo);
                        stockReorderLevels.generateReport();
                        break;

                    case 5:
                        // part 5: Generate daily sales report
                        fetchBillData = new FetchBillData(connectionInstance);
                        List<FetchedBill> bills = fetchBillData.getAllBillsByDate(today);
                        ProcessData processor = new BillProcessDataForDailySales(bills);
                        ReportFormatter formatter = new PDFReportGeneration();
                        SpecificReportTemplate billProcessDataForDailySales = new SpecificReportTemplate(processor,
                                formatter);
                        billProcessDataForDailySales.generateReport();
                        break;

                    default:
                        System.out.println("Invalid choice. Please select a valid option.");
                        break;
                }

                scanner.close();
            }
        } catch (SQLException e) {
            System.out.println("Error checking connection status: " + e.getMessage());
        } finally {
            connectionInstance.closeConnection(dbConnection);
        }

    }
}


