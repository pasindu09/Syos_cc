<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="main.billgeneration.bill.FetchedBill" %>
<!DOCTYPE html>
<html>
<head>
    <title>Bill Report</title>
    <!-- Include Tailwind CSS -->
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        .hidden { display: none; }
        .table-header {
            background-color: #1f2937; /* Tailwind's cool gray */
            color: white;
        }
        .table-row:hover {
            background-color: #f3f4f6; /* Tailwind's gray-100 for hover effect */
        }
    </style>
    <!-- Include Font Awesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body class="flex bg-gray-100 h-screen">
    <!-- Sidebar -->
    <aside class="w-20 lg:w-44 bg-black text-white flex flex-col items-center lg:items-start py-6">
        <div class="mb-8">
            <h1 class="text-2xl font-bold lg:pl-4">SYOS</h1>
        </div>
        <nav class="space-y-6">
            <a href="http://localhost:8080/Clean_Coding/products" class="flex flex-col lg:flex-row items-center lg:items-start text-white hover:text-red-600 px-4 py-2 rounded-lg transition-colors duration-200">
                <i class="fas fa-home"></i>
                <span class="text-xs lg:text-sm lg:ml-3">Home</span>
            </a>
            <a href="http://localhost:8080/Clean_Coding/billreport" class="flex flex-col lg:flex-row items-center lg:items-start text-white hover:text-red-600 px-4 py-2 rounded-lg transition-colors duration-200">
                <i class="fas fa-file-alt"></i>
                <span class="text-xs lg:text-sm lg:ml-3">Reports</span>
            </a>
        </nav>
    </aside>

    <!-- Main Content -->
    <div class="flex-grow p-6">
        <div class="container mx-auto">
            <h1 class="text-3xl font-bold text-gray-800 mb-6">Bill Report</h1>

            <table class="min-w-full bg-white rounded-lg shadow-lg">
                <thead class="table-header">
                    <tr>
                        <th class="px-6 py-4 text-left">Serial No</th>
                        <th class="px-6 py-4 text-left">Date</th>
                        <th class="px-6 py-4 text-left">Items</th>
                        <th class="px-6 py-4 text-left">Subtotal (Rs)</th>
                        <th class="px-6 py-4 text-left">Discount (Rs)</th>
                        <th class="px-6 py-4 text-left">Total (Rs)</th>
                        <th class="px-6 py-4 text-left">Total Paid (Rs)</th>
                        <th class="px-6 py-4 text-left">Change (Rs)</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<FetchedBill> fetchedBills = (List<FetchedBill>) request.getAttribute("fetchedBills");
                        if (fetchedBills != null && !fetchedBills.isEmpty()) {
                            for (FetchedBill bill : fetchedBills) {
                                String escapedBillInfo = bill.getBillInformation();
                                try {
                                    org.json.JSONObject billInfo = new org.json.JSONObject(escapedBillInfo);
                                    org.json.JSONArray cartContents = billInfo.getJSONArray("cartContents");
                    %>
                    <tr class="table-row">
                        <td class="px-6 py-4 border-t border-gray-200"><%= bill.getSerialNumber() %></td>
                        <td class="px-6 py-4 border-t border-gray-200"><%= bill.getCreatedAt() %></td>
                        <td class="px-6 py-4 border-t border-gray-200">
                            <ul class="list-disc pl-4 text-sm">
                                <%
                                    for (int i = 0; i < cartContents.length(); i++) {
                                        org.json.JSONArray item = cartContents.getJSONArray(i);
                                        String itemName = item.getString(1);
                                        int quantity = item.getInt(2);
                                        double price = item.getDouble(3);
                                %>
                                <li><%= itemName %> - QTY: <%= quantity %>, PRICE: Rs <%= String.format("%.2f", price) %></li>
                                <%
                                    }
                                %>
                            </ul>
                        </td>
                        <td class="px-6 py-4 border-t border-gray-200"><%= String.format("%.2f", billInfo.getDouble("subTotal")) %></td>
                        <td class="px-6 py-4 border-t border-gray-200"><%= String.format("%.2f", billInfo.getDouble("discount")) %></td>
                        <td class="px-6 py-4 border-t border-gray-200"><%= String.format("%.2f", billInfo.getDouble("total")) %></td>
                        <td class="px-6 py-4 border-t border-gray-200"><%= String.format("%.2f", billInfo.getDouble("totalPaid")) %></td>
                        <td class="px-6 py-4 border-t border-gray-200"><%= String.format("%.2f", billInfo.getDouble("change")) %></td>
                    </tr>
                    <%
                                } catch (Exception e) {
                                    e.printStackTrace();
                    %>
                    <tr class="table-row">
                        <td colspan="8" class="px-6 py-4 text-red-500 border-t border-gray-200">Error parsing bill information for Serial No <%= bill.getSerialNumber() %></td>
                    </tr>
                    <%
                                }
                            }
                        } else {
                    %>
                    <tr class="table-row">
                        <td colspan="8" class="px-6 py-4 text-center text-gray-500">No bills found.</td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>

            <!-- Pagination Controls -->
            <div class="flex justify-between items-center mt-4">
                <button id="prev-btn" class="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-700" onclick="prevPage()">Previous</button>
                <span id="page-info" class="text-gray-600">Page <span id="current-page">1</span> of <span id="total-pages">1</span></span>
                <button id="next-btn" class="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-700" onclick="nextPage()">Next</button>
            </div>
        </div>
    </div>

    <script>
        const rowsPerPage = 5;
        let currentPage = 1;
        let totalRows = document.querySelectorAll('.table-row').length;
        let totalPages = Math.ceil(totalRows / rowsPerPage);

        document.getElementById('total-pages').textContent = totalPages;

        function showPage(page) {
            const rows = document.querySelectorAll('.table-row');
            const start = (page - 1) * rowsPerPage;
            const end = start + rowsPerPage;

            rows.forEach((row, index) => {
                row.classList.add('hidden');
                if (index >= start && index < end) {
                    row.classList.remove('hidden');
                }
            });

            document.getElementById('current-page').textContent = page;
            document.getElementById('prev-btn').disabled = page === 1;
            document.getElementById('next-btn').disabled = page === totalPages;
        }

        function nextPage() {
            if (currentPage < totalPages) {
                currentPage++;
                showPage(currentPage);
            }
        }

        function prevPage() {
            if (currentPage > 1) {
                currentPage--;
                showPage(currentPage);
            }
        }

        showPage(currentPage);
    </script>
</body>
</html>
