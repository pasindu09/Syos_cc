package main.billgeneration;

import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import main.database.DatabaseConnection;
import main.database.MySqlConnection;
import main.billgeneration.bill.FetchBillDataOperations;
import main.billgeneration.bill.FetchBillData;
import main.billgeneration.bill.FetchedBill;

public class BillReportServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Initialize the database connection
        DatabaseConnection dbConnection = new MySqlConnection();

        try {
            // Establish a connection to the database
            dbConnection.connectDatabase();

            // Fetch the optional date parameter from the request
            String date = request.getParameter("date");

            // Initialize FetchBillDataOperations
            FetchBillDataOperations fetchBillData = new FetchBillData(dbConnection);
            List<FetchedBill> fetchedBills;

            // Check if the date parameter is provided and use the appropriate method
            if (date != null && !date.isEmpty()) {
                fetchedBills = fetchBillData.getAllBillsByDate(date);
            } else {
                fetchedBills = fetchBillData.getAllBills();
            }

            // Store the fetched bills as a request attribute
            request.setAttribute("fetchedBills", fetchedBills);

            // Forward the request to the JSP page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/templates/billReport.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            // Log error (use a logging framework in real applications)
            e.printStackTrace();

            // Handle exceptions and send an error response
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error generating bill report: " + e.getMessage());
        } 
    }
}
