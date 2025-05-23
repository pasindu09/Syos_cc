package main.billgeneration.bill;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class BillProcessDataForDailySales implements ProcessData {
    private List<FetchedBill> bill;

    public BillProcessDataForDailySales(List<FetchedBill> bill) {
        this.bill = bill;
    }

    @Override
    public void processData() {
        System.out.println("          ----------- DAILY SALES REPORT ---------");
        System.out.println("Date: " + LocalDate.now().toString());
        System.out.println();

        Map<String, Integer> quantityMap = new HashMap<>();
        Map<String, Double> revenueMap = new HashMap<>();

        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, List<List<Object>>>>() {}.getType();

        for (FetchedBill bill : this.bill) {
            String billInfo = bill.getBillInformation();
            JsonObject jsonObject = JsonParser.parseString(billInfo).getAsJsonObject();
            JsonArray cartContents = jsonObject.getAsJsonArray("cartContents");

            for (JsonElement elem : cartContents) {
                JsonArray item = elem.getAsJsonArray();
                String itemName = item.get(1).getAsString();
                int quantity = item.get(2).getAsInt();
                double price = item.get(3).getAsDouble();

                quantityMap.put(itemName, quantityMap.getOrDefault(itemName, 0) + quantity);
                revenueMap.put(itemName, revenueMap.getOrDefault(itemName, 0.0) + (quantity * price));
            }
        }

        for (String key : quantityMap.keySet()) {
            System.out.println(String.format("Item: %s, Quantity Sold: %d, Total Revenue: $%.2f", key,
                    quantityMap.get(key), revenueMap.get(key)));
        }
        System.out.println();
    }
}

