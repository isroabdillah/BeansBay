## "Jual Beli Kopi" Application API Documentation

This repository contains the code for the "Jual Beli Kopi" application's backend API. The API is developed using Node.js with the Express framework and is responsible for handling various functionalities related to product management, user interactions, and cart management. Below is a detailed documentation of the available API endpoints and their usage:

### API Endpoints

#### 1. Search for Products

- *Endpoint:* `GET /api/products/search`
- *Description:* Search for products based on a provided name query parameter.
- *Parameters:*
  - `name` (required): The name of the product to search for.
- *Example:*
  javascript

  app.get("/api/products/search", (req, res) => {
    const { name } = req.query;

    if (!name) {
      res.status(400).json({ error: "Parameter name harus diberikan" });
      return;
    }

    const filteredProducts = products.filter((product) =>
      product.name.toLowerCase().includAes(name.toLowerCase())
    );
    res.json(filteredProducts);
  });
  

#### 2. Get All Products

- *Endpoint:* `GET /api/products`
- *Description:* Get the list of all available products.
- *Example:*
  javascript

  app.get("/api/products", (req, res) => {
    res.json(products);
  });
  

#### 3. Get Product Details

- *Endpoint:* `GET /api/products/:id`
- *Description:* Get the details of a specific product based on its ID.
- *Parameters:*
  - `id` (required): The ID of the product to retrieve.
- *Example:*
  javascript

  app.get("/api/products/:id", (req, res) => {
  const id = parseInt(req.params.id);
  const product = products.find((p) => p.id === id);

  if (product) {
    res.json(product);
  } else {
    res.status(404).json({ error: "Produk tidak ditemukan" });
  }
});
  

#### 4. Add Product to Cart

- *Endpoint:* `POST /api/cart`
- *Description:* Add a product to the user's cart.
- *Request Body:*
  - `productId` (required): The ID of the product to add to the cart.
  - `quantity` (optional): The quantity of the product to add (default: 1).
- *Example:*
  javascript

  app.post("/api/cart", (req, res) => {
  const { productId, quantity } = req.body;
  const product = products.find((p) => p.id === productId);

  if (!product) {
    res.status(404).json({ error: "Produk tidak ditemukan" });
    return;
  }

  const cartItem = {
    productId: product.id,
    name: product.name,
    price: product.price,
    quantity: quantity || 1,
  };

  cartItems.push(cartItem);
  res.status(201).json(cartItem);
});
  

#### 5. Get Cart Items

- *Endpoint:* `GET /api/cart`
- *Description:* Get the list of products in the user's cart.
- *Example:*
  javascript

  app.get("/api/cart", (req, res) => {
  res.json(cartItems);
});
  

#### 6. Remove Product from Cart

- *Endpoint:* `DELETE /api/cart/:productId`
- *Description:* Remove a product from the user's cart based on its ID.
- *Parameters:*
  - `productId` (required): The ID of the product to remove from the cart.
- *Example:*
  javascript

  app.delete("/api/cart/:productId", (req, res) => {
  const productId = parseInt(req.params.productId);
  const index = cartItems.findIndex((item) => item.productId === productId);

  if (index !== -1) {
    cartItems.splice(index, 1);
    res.status(204).json({ message: "Produk telah terhapus dari keranjang" });
  } else {
    res.status(404).json({ error: "Produk tidak ditemukan dalam keranjang" });
  }
});
  

#### 7. Calculate Cart Total

- *Endpoint:* `GET /api/cart/total`
- *Description:* Calculate the total price of all products in the user's cart.
- *Example:*
  javascript

  app.get("/api/cart/total", (req, res) => {
  const total = cartItems.reduce(
    (acc, item) => acc + item.price * item.quantity,
    0
  );
  res.json({ total });
});
  

#### 8. Checkout Cart

- *Endpoint:* `POST /api/cart/checkout`
- *Description:* Complete the purchase by emptying the user's cart.
- *Example:*
  javascript

  app.post("/api/cart/checkout", (req, res) => {
  if (cartItems.length === 0) {
    const response = {
      success: false,
      message: "Tidak ada produk di keranjang",
    };
    return res.status(400).json(response);
  }

  try {
    // Proses pembayaran dan lainnya...
    // Setelah pembelian berhasil, Anda dapat mengosongkan keranjang:
    cartItems = [];
    const response = {
      success: true,
      message: "Pembelian Berhasil",
    };
    res.status(200).json(response);
  } catch (error) {
    console.error("Terjadi kesalahan saat checkout:", error);
    const response = {
      success: false,
      message: "Terjadi kesalahan saat checkout",
    };
    res.status(500).json(response);
  }
});
  

### How to Use the APIs

1. Search for Products: Send a GET request to `/api/products/search` with the `name` query parameter to search for products with a specific name.

2. Get All Products: Send a GET request to `/api/products` to retrieve the list of all available products.

3. Get Product Details: Send a GET request to `/api/products/:id`, where `:id` is the ID of the product, to get the details of a specific product.

4. Add Product to Cart: Send a POST request to `/api/cart` with the `productId` and `quantity` (optional) in the request body to add a product to the user's cart.

5. Get Cart Items: Send a GET request to `/api/cart` to get the list of products in the user's cart.

6. Remove Product from Cart: Send a DELETE request to `/api/cart/:productId`, where `:productId` is the ID of the product, to remove a product from the user's cart.

7. Calculate Cart Total: Send a GET request to `/api/cart/total` to calculate the total price of all products in the user's cart.

8. Checkout Cart: Send a POST request to `/api/cart/checkout` to complete the purchase by emptying the user's cart.
