package main.billgeneration;

import java.util.List;

import main.billgeneration.bill.ProcessData;


public class StockProcessData implements ProcessData {
 private List<StockBatch> stockBatch;

    public StockProcessData(List<StockBatch> stockBatch) {
        this.stockBatch = stockBatch;
    }    
    @Override
    public void processData() {
       System.out.println();
        System.out.println();
        System.out.println("          ----------- STOCK REPORT( ) ---------");
        System.out.println();


        int counter = 1; // Start numbering from 1
        for (StockBatch stockBatch : stockBatch) {
            System.out.println(counter + ". " + stockBatch);
            counter++; // Increment the counter for the next bill
        }
        System.out.println();
        System.out.println();
    }
}