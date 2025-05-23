<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="main.product.Product" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Products List</title>
        
        <!-- Include Tailwind CSS -->
        <script src="https://cdn.tailwindcss.com"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <style>
            /* Remove spinner (arrows) from number input fields */
            input[type=number]::-webkit-outer-spin-button,
            input[type=number]::-webkit-inner-spin-button {
                -webkit-appearance: none;
                margin: 0;
            }

            input[type=number] {
                -moz-appearance: textfield; /* Remove spinner for Firefox */
            }
        </style>
        
        <script>
          tailwind.config = {
            theme: {
              extend: {
                colors: {
                  'sidebar-blue': '#3d68ff',
                  'cta-blue': '#1947ee',
                  'hover-blue': '#0038fd',
                  customGray: '#D9D9D9',
                  clifford: '#da373d',
                },
                fontFamily: {
                  'michroma': ['Michroma', 'sans-serif'],
                  karla: ['Karla', 'sans-serif'],
                  sans: ['Poppins', 'sans-serif'],
                },
              }
            }
          }
        </script>

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
        <div class="flex-grow flex flex-wrap p-6">
            <!-- Product Grid -->
            <div class="w-full lg:w-2/3 p-4">
                <h1 class="text-3xl font-semibold text-black mb-6">Products</h1>

                <!-- Search and Barcode Entry -->
                <div class="flex mb-6">
                    <form class="product-code-form flex w-full">
                        <input type="text" id="product-code-input" placeholder="Enter Product Code" class="w-1/2 p-3 bg-white border border-gray-300 rounded-l-lg focus:outline-none" />
                        <button type="submit" class="bg-red-600 text-white px-4 py-3 rounded-r-lg hover:bg-red-500 ml-2">
                            <i class="fas fa-barcode"></i>
                        </button>
                    </form>
                </div>

                <!-- Product Grid -->
                <div class="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-4 gap-6">
                    <%
                    List<Product> products = (List<Product>) request.getAttribute("products");
                    if (products != null) {
                        for (Product product : products) {
                    %>
                    <div class="bg-white p-6 rounded-lg shadow-md hover:shadow-lg transition-shadow duration-300 cursor-pointer h-full flex flex-col">
                        <h2 class="text-xl font-semibold text-gray-800 mb-2"><%= product.getName() %></h2>
                        <p class="text-gray-500 text-lg mb-4">$<%= String.format("%.2f", product.getPrice()) %></p>
                        
                        <!-- Hidden fields to pass product ID and default quantity -->
                        <form class="add-to-cart-form mt-auto" data-product-id="<%= product.getId() %>">
                            <div class="flex items-center space-x-3 mb-4">
                                <button type="button" class="quantity-decrease bg-gray-200 text-gray-700 px-3 py-2 rounded-lg focus:outline-none hover:bg-gray-300">
                                    -
                                </button>
                                <input 
                                    type="number" 
                                    name="quantity" 
                                    value="1" 
                                    min="1" 
                                    class="quantity-input w-14 text-center border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:outline-none" 
                                    readonly
                                >
                                <button type="button" class="quantity-increase bg-gray-200 text-gray-700 px-3 py-2 rounded-lg focus:outline-none hover:bg-gray-300">
                                    +
                                </button>
                            </div>
                            <button type="submit" class="w-full bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 transition-colors duration-200 focus:ring-2 focus:ring-blue-500 focus:outline-none">
                                Add to Cart
                            </button>
                        </form>
                    </div>
                    
                    <%
                        }
                    } else {
                    %>
                    <p>No products available.</p>
                    <%
                    }
                    %>
                </div>
            </div>

            <!-- Order Summary -->
            <div class="w-full lg:w-1/3 bg-white p-6 shadow-lg rounded-lg">
                <h1 class="text-3xl font-semibold text-black mb-6">Order Summary</h1>
                <div class="space-y-4">
                    <div id="cart-items" class="space-y-4">
                        <!-- Cart items will be dynamically inserted here -->
                    </div>

                    <!-- Cart Totals -->
                    <div class="mt-6">
                        <div class="flex justify-between items-center">
                            <span class="text-lg font-semibold">Subtotal</span>
                            <span id="cart-subtotal" class="text-lg font-semibold">$0.00</span>
                        </div>
                        <div class="flex justify-between items-center">
                            <span class="text-lg font-semibold">Discount</span>
                            <span id="cart-discount" class="text-lg font-semibold">$0.00</span>
                        </div>
                        <div class="flex justify-between items-center">
                            <span class="text-lg font-semibold">Total</span>
                            <span id="cart-total" class="text-lg font-semibold">$0.00</span>
                        </div>
                    </div>

                    <!-- Checkout Button -->
                    <button id="checkout-button" class="w-full mt-6 bg-red-600 text-white py-3 rounded-lg text-lg hover:bg-red-500 transition duration-200">
                        Checkout
                    </button>
                </div>
            </div>
        </div>

        <!-- JavaScript Logic -->
        <script>
            let cartItems = []; // Declare cartItems globally to be used in both updateCartDetails and checkout

            document.addEventListener('DOMContentLoaded', function () {
                



                const productCodeForm = document.querySelector('.product-code-form');
                const productCodeInput = document.querySelector('#product-code-input');
            
                if (productCodeForm) {
                    productCodeForm.addEventListener('submit', function (event) {
                        event.preventDefault(); // Prevent the default form submission
                        console.log('Form submission prevented for product code:', productCodeInput.value.trim());
            
                        // Retrieve the product ID from the input field
                        const productId = productCodeInput.value.trim();
                        const quantity = 1; // Default quantity for this input, since there's no quantity field in this form
            
                        // Encode form data properly
                        const formData = new URLSearchParams();
                        formData.append('productId', productId);
                        formData.append('quantity', quantity);
            
                        // Send the data to the AddToCartServlet via AJAX
                        fetch('addtocart', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/x-www-form-urlencoded'
                            },
                            body: formData.toString() // Ensure URL-encoded form data
                        })
                        .then(response => response.text())
                        .then(data => {
                            console.log('Product added to cart:', data);
                            updateCartDetails(); // Fetch updated cart details after adding a product
                        })
                        .catch(error => console.error('Error:', error));
                    });
                } else {
                    console.error('Form not found. Make sure the class name is correct.');
                }





                document.querySelectorAll('form.add-to-cart-form').forEach(form => {
                    form.addEventListener('submit', function (event) {
                        event.preventDefault(); // Prevent the default form submission

                        // Get productId from data attribute
                        const productId = form.getAttribute('data-product-id');

                        
                        // Get quantity from hidden input field
                        const quantity = form.querySelector('input[name="quantity"]').value;

                        // Encode form data properly
                        const formData = new URLSearchParams();
                        formData.append('productId', productId);
                        formData.append('quantity', quantity);

                        // Send the data to the AddToCartServlet via AJAX
                        fetch('addtocart', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/x-www-form-urlencoded'
                            },
                            body: formData.toString() // Ensure URL-encoded form data
                        })
                        .then(response => response.text())
                        .then(data => {
                            console.log('Product added to cart:', data);
                            updateCartDetails(); // Fetch updated cart details after adding a product
                        })
                        .catch(error => console.error('Error:', error));
                    });
                });

                // Function to fetch updated cart details from the cartdetails servlet and update the cart panel
                function updateCartDetails() {
                    fetch('cartdetails', { method: 'GET' })
                        .then(response => response.json())
                        .then(cartData => {
                            const cartItemsContainer = document.querySelector('#cart-items');
                            cartItemsContainer.innerHTML = ''; // Clear existing cart details

                            cartItems = cartData.items || []; // Update global cartItems array

                            // Check if there are items in the cart
                            if (cartItems.length > 0) {
                                cartItems.forEach(item => {
                                    const cartItem = document.createElement('div');
                                    cartItem.className = 'cart-item flex justify-between items-center';
                                    cartItem.innerHTML = `
                                        <div>
                                            <h2 class="text-lg text-black">\${item.productName}</h2>
                                            <p class="text-sm text-gray-500">\${Number(item.price).toFixed(2)} x \${Number(item.quantity)}</p>
                                        </div>
                                        <span class="text-lg font-semibold text-black">\${(Number(item.price) * Number(item.quantity)).toFixed(2)}</span>
                                    `;
                                    cartItemsContainer.appendChild(cartItem);
                                });
                                // Update subtotal, total, and discount
                                document.querySelector('#cart-subtotal').textContent = '$' + cartData.subTotalDue.toFixed(2);
                                document.querySelector('#cart-discount').textContent = '$' + cartData.discount.toFixed(2);
                                document.querySelector('#cart-total').textContent = '$' + cartData.totalDue.toFixed(2);
                                
                            } else {
                                // If there are no items in the cart, show an empty message
                                cartItemsContainer.innerHTML = '<p class="text-gray-500">No items in the cart.</p>';
                                document.querySelector('#cart-subtotal').textContent = '$0.00';
                                document.querySelector('#cart-discount').textContent = '$0.00';
                                document.querySelector('#cart-total').textContent = '$0.00';
                            }
                        })
                        .catch(error => console.error('Error fetching cart details:', error));
                }

                // SweetAlert Checkout Logic
                const checkoutButton = document.querySelector('#checkout-button');
                if (checkoutButton) {
                    // Add event listener for checkout button
checkoutButton.addEventListener('click', function () {
    const totalAmount = parseFloat(document.querySelector('#cart-total').textContent.replace('$', ''));

    Swal.fire({
        title: 'Enter Amount Paid',
        input: 'number',
        inputPlaceholder: 'Enter amount',
        showCancelButton: true,
        confirmButtonText: 'Proceed',
        cancelButtonText: 'Cancel',
        customClass: {
            confirmButton: 'bg-blue-500 hover:bg-blue-700 text-white',
            cancelButton: 'bg-gray-300 text-black'
        },
        preConfirm: (amountPaid) => {
            if (amountPaid < totalAmount) {
                Swal.showValidationMessage(`The amount must be at least $` + totalAmount);
                return false;
            }
            return amountPaid;
        }
    }).then((result) => {
        if (result.isConfirmed) {
            Swal.fire({
                title: 'Are you sure you want to checkout?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: 'Checkout',
                cancelButtonText: 'Cancel',
                customClass: {
                    confirmButton: 'bg-blue-500 hover:bg-blue-700 text-white',
                    cancelButton: 'bg-gray-300 text-black'
                },
                reverseButtons: true
            }).then((checkoutResult) => {
                if (checkoutResult.isConfirmed) {
                    fetch('bill', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({
                            cartItems: cartItems,
                            totalPaid: result.value,
                            change: result.value - totalAmount
                        })
                    })
                    .then(response => response.json())
                    .then(billData => {
                        showBillModal(billData);  // Call function to display modal
                    })
                    .catch(error => console.error('Error during checkout:', error));
                } else if (checkoutResult.isDismissed) {
                    Swal.fire('Cancelled', 'Your checkout has been cancelled', 'info');
                }
            });
        }
    });
});


// Function to display the bill modal
function showBillModal(billData) {
    // Create the modal HTML structure with placeholders
    const modalHtml = `
        <div id="bill-modal" style="display:none; position:fixed; top:0; left:0; width:100%; height:100%; background-color: rgba(0, 0, 0, 0.5); z-index: 9999; display: flex; justify-content: center; align-items: center;">
            <div style="background: white; padding: 20px; border-radius: 10px; font-family: monospace; width: 450px; box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);">
                <h2 style="text-align: center;">Synex Outlet Store (SYOS)</h2>
                <p style="text-align: center;">Colombo 05</p>
                <p style="text-align: center;">Phone: 011-1234567</p>
                <p style="text-align: center;">Email: SYOS@gmail.com</p>
                <hr/>
                <p>Serial No: <span id="bill-serial-number"></span></p>
                <p>Date: <span id="bill-date"></span></p>
                <hr/>
                <table style="width: 100%; text-align: left; border-collapse: collapse;">
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>DESC</th>
                            <th>QTY</th>
                            <th>PRICE</th>
                        </tr>
                    </thead>
                    <tbody id="bill-items">
                    </tbody>
                </table>
                <hr/>
                <div style="display: flex; flex-direction: column; gap: 5px; padding: 10px;">
                    <div style="display: flex; justify-content: space-between;">
                        <span>Subtotal:</span>
                        <span><span class="currency">Rs</span> <span id="bill-subtotal"></span></span>
                    </div>
                    <div style="display: flex; justify-content: space-between;">
                        <span>Discount:</span>
                        <span><span class="currency">Rs</span> <span id="bill-discount"></span></span>
                    </div>
                    <div style="display: flex; justify-content: space-between;">
                        <span>Total:</span>
                        <span><span class="currency">Rs</span> <span id="bill-total"></span></span>
                    </div>
                    <div style="display: flex; justify-content: space-between;">
                        <span>Total Paid:</span>
                        <span><span class="currency">Rs</span> <span id="bill-total-paid"></span></span>
                    </div>
                    <div style="display: flex; justify-content: space-between;">
                        <span>Change:</span>
                        <span><span class="currency">Rs</span> <span id="bill-change"></span></span>
                    </div>
                </div>
                <hr/>
                <p style="text-align: center;">***** THANK YOU *****</p>
                <div style="display: flex; justify-content: space-between; margin-top: 10px;">
                    <button onclick="window.print()" style="padding: 10px 20px; background-color: blue; color: white; border-radius: 5px;">Print</button>
                    <button onclick="document.querySelector('#bill-modal').remove()" style="padding: 10px 20px; background-color: red; color: white; border-radius: 5px;">Close</button>
                </div>
            </div>
        </div>
    `;

    // Insert the modal into the body
    document.body.insertAdjacentHTML('beforeend', modalHtml);

    // Populate the modal with data
    document.querySelector('#bill-serial-number').textContent = billData.serialNumber;
    document.querySelector('#bill-date').textContent = billData.date;
    document.querySelector('#bill-subtotal').textContent = billData.subTotal.toFixed(2);
    document.querySelector('#bill-discount').textContent = billData.discount.toFixed(2);
    document.querySelector('#bill-total').textContent = billData.total.toFixed(2);
    document.querySelector('#bill-total-paid').textContent = billData.totalPaid.toFixed(2);
    document.querySelector('#bill-change').textContent = billData.change.toFixed(2);

    // Populate the items list
    const billItemsContainer = document.querySelector('#bill-items');
    billData.cartContents.forEach((item, index) => {
        const row = document.createElement('tr');
        const cellIndex = document.createElement('td');
        const cellDescription = document.createElement('td');
        const cellQuantity = document.createElement('td');
        const cellPrice = document.createElement('td');

        cellIndex.textContent = index + 1;
        cellDescription.textContent = item.description;
        cellQuantity.textContent = item.quantity;
        cellPrice.textContent = item.price.toFixed(2);

        row.appendChild(cellIndex);
        row.appendChild(cellDescription);
        row.appendChild(cellQuantity);
        row.appendChild(cellPrice);
        billItemsContainer.appendChild(row);
    });

    // Show the modal
    document.querySelector('#bill-modal').style.display = 'flex';
}




document.querySelectorAll('.add-to-cart-form').forEach(form => {
    const decreaseButton = form.querySelector('.quantity-decrease');
    const increaseButton = form.querySelector('.quantity-increase');
    const quantityInput = form.querySelector('.quantity-input');

    decreaseButton.addEventListener('click', () => {
        let currentQuantity = parseInt(quantityInput.value);
        if (currentQuantity > 1) {
            quantityInput.value = currentQuantity - 1;
        }
    });

    increaseButton.addEventListener('click', () => {
        let currentQuantity = parseInt(quantityInput.value);
        quantityInput.value = currentQuantity + 1;
    });
});




                }
                

                // Call updateCartDetails to load the cart data initially
                updateCartDetails();
            });
        </script>
    </body>
</html>

