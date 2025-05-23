package main.billgeneration.bill;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import java.util.Map;

public class FetchedBill {
    private int id;
    private String serialNumber;
    private String billInformation;
    private String createdAt;

    public FetchedBill(int id, String serialNumber, String billInformation, String createdAt) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.billInformation = billInformation;
        this.createdAt = createdAt;
    }

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private String compactCartContents(String jsonStr) {
        Map<String, Object> jsonMap = gson.fromJson(jsonStr, Map.class);
        if (jsonMap.containsKey("cartContents")) {
            List<List<Object>> cartContents = (List<List<Object>>) jsonMap.get("cartContents");
            StringBuilder compactContents = new StringBuilder("[");
            for (List<Object> item : cartContents) {
                if (item.size() >= 4) {
                    compactContents
                            .append(String.format("(%s, %s items, $%s), ", item.get(1), item.get(2), item.get(3)));
                }
            }
            if (!cartContents.isEmpty()) {
                compactContents.setLength(compactContents.length() - 2);
            }
            compactContents.append("]");
            jsonMap.put("cartContents", compactContents.toString());
        }
        return gson.toJson(jsonMap);
    }

    public int getId() {
        return id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getBillInformation() {
        return billInformation;
    }

    public String getCreatedAt() {
        return createdAt;
    }

   

    @Override
    public String toString() {
        return String.format("Bill ID: %-6d | Serial Number: %-10s | Created At: %-20s | Bill Info: %s",
                id, serialNumber, createdAt, compactCartContents(billInformation));
    }
}
