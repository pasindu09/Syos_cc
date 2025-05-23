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

        <script>
            let cartItems = []; // Declare cartItems globally to showw
            let isUpdatingCart = false; // Lockiing to prevent multiple cart updates
            let isAddingToCart = false; // Locking to prevent multiple simultaneous add-to-cart actions
        
            document.addEventListener('DOMContentLoaded', function () {
                const productCodeForm = document.querySelector('.product-code-form');
                const productCodeInput = document.querySelector('#product-code-input');
        
                if (productCodeForm) {
                    productCodeForm.addEventListener('submit', debounce(async function (event) {
                        event.preventDefault();
        
                        if (isAddingToCart) return; // Preventing concurrent submissions
                        isAddingToCart = true;
        
                        const productId = productCodeInput.value.trim();
                        const quantity = 1;
        
                        try {
                            await addToCart(productId, quantity);
                            await updateCartDetails(); // Updating cart afteer adding product
                        } catch (error) {
                            console.error('Error adding product:', error);
                        } finally {
                            isAddingToCart = false; // Release lock
                        }
                    }, 300)); // 300ms debounce delay
                }
        
                document.querySelectorAll('form.add-to-cart-form').forEach(form => {
                    form.addEventListener('submit', debounce(async function (event) {
                        event.preventDefault();
        
                        if (isAddingToCart) return; // Prevent concurrent submissions
                        isAddingToCart = true;
        
                        const productId = form.getAttribute('data-product-id');
                        const quantity = form.querySelector('input[name="quantity"]').value;
        
                        try {
                            await addToCart(productId, quantity);
                            await updateCartDetails(); // Update cart after adding product
                        } catch (error) {
                            console.error('Error adding product:', error);
                        } finally {
                            isAddingToCart = false; // Release lock
                        }
                    }, 300)); // 300ms debounce delay
                });
        
                async function addToCart(productId, quantity) {
                    const formData = new URLSearchParams();
                    formData.append('productId', productId);
                    formData.append('quantity', quantity);
        
                    const response = await fetch('addtocart', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        },
                        body: formData.toString()
                    });
        
                    const data = await response.text();
                    console.log('Product added to cart:', data);
                }
        
                async function updateCartDetails() {
                    if (isUpdatingCart) return; // Prevent multiple concurrent updates
                    isUpdatingCart = true;
        
                    try {
                        const response = await fetch('cartdetails', { method: 'GET' });
                        const cartData = await response.json();
                        renderCartItems(cartData);
                    } catch (error) {
                        console.error('Error fetching cart details:', error);
                    } finally {
                        isUpdatingCart = false; // Release lock after update
                    }
                }
        
                function renderCartItems(cartData) {
                    const cartItemsContainer = document.querySelector('#cart-items');
                    cartItemsContainer.innerHTML = '';
        
                    cartItems = cartData.items || [];
        
                    if (cartItems.length > 0) {
                        cartItems.forEach(item => {
                            const cartItem = document.createElement('div');
                            cartItem.className = 'cart-item flex justify-between items-center';
                            cartItem.innerHTML = `
                                <div>
                                    <h2 class="text-lg text-black">${item.productName}</h2>
                                    <p class="text-sm text-gray-500">${Number(item.price).toFixed(2)} x ${Number(item.quantity)}</p>
                                </div>
                                <span class="text-lg font-semibold text-black">${(Number(item.price) * Number(item.quantity)).toFixed(2)}</span>
                            `;
                            cartItemsContainer.appendChild(cartItem);
                        });
                        document.querySelector('#cart-subtotal').textContent = '$' + cartData.subTotalDue.toFixed(2);
                        document.querySelector('#cart-discount').textContent = '$' + cartData.discount.toFixed(2);
                        document.querySelector('#cart-total').textContent = '$' + cartData.totalDue.toFixed(2);
                    } else {
                        cartItemsContainer.innerHTML = '<p class="text-gray-500">No items in the cart.</p>';
                        document.querySelector('#cart-subtotal').textContent = '$0.00';
                        document.querySelector('#cart-discount').textContent = '$0.00';
                        document.querySelector('#cart-total').textContent = '$0.00';
                    }
                }
        
                function debounce(func, delay) {
                    let debounceTimer;
                    return function() {
                        const context = this;
                        const args = arguments;
                        clearTimeout(debounceTimer);
                        debounceTimer = setTimeout(() => func.apply(context, args), delay);
                    };
                }
        
                // Call updateCartDetails to load the cart data initially
                updateCartDetails();
            });
        
            // Example of using Promise.all() to perform multiple concurrent actions
            async function refreshCartData() {
                try {
                    const [cartResponse, discountResponse] = await Promise.all([
                        fetch('cartdetails', { method: 'GET' }),
                        fetch('discountdetails', { method: 'GET' })
                    ]);
        
                    const cartData = await cartResponse.json();
                    const discountData = await discountResponse.json();
        
                    renderCartItems(cartData);
                    applyDiscounts(discountData);
                } catch (error) {
                    console.error('Error fetching cart or discount details:', error);
                }
            }
        
            function applyDiscounts(discountData) {
                console.log('Discount data:', discountData);
            }
        </script>
        
        
    </body>
</html>

