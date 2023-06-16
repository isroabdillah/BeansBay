const express = require("express");
const session = require("express-session");
const cookieParser = require("cookie-parser");
const axios = require("axios");
const app = express();
const port = 8080;

const dotenv = require("dotenv");
dotenv.config();

const bcrypt = require("bcrypt");
const jwt = require("jsonwebtoken");

const admin = require("firebase-admin");
const serviceAccount = require("./capstone-4cffc-firebase-adminsdk-1uyq4-394dd3d1bb.json");
app.use(cookieParser());

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: process.env.FIREBASE_DATABASE_URL,
});
require("dotenv").config();

const secretKey = process.env.SECRET_KEY;
const REFRESH_TOKEN_SECRET = process.env.REFRESH_TOKEN_SECRET;
const ACCESS_TOKEN_SECRET = process.env.ACCESS_TOKEN_SECRET;
const db = admin.firestore();

app.use((req, res, next) => {
  res.setHeader("Access-Control-Allow-Origin", "*");
  res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
  res.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
  next();
});

app.use(
  session({
    secret: "rahasia",
    resave: false,
    saveUninitialized: true,
  })
);

function checkToken(req, res, next) {
  const token = req.cookies.token;

  if (!token) {
    return res.status(401).json({ error: "Token otentikasi diperlukan" });
  }

  jwt.verify(token, process.env.SECRET_KEY, (err, decoded) => {
    if (err) {
      return res.status(403).json({ error: "Token otentikasi tidak valid" });
    }

    next();
  });
}

app.use(express.json());
let users = [];

function generateRefreshToken(userId) {
  const refreshToken = jwt.sign({ userId }, process.env.REFRESH_TOKEN_SECRET);
  return refreshToken;
}

function verifyRefreshToken(refreshToken) {
  try {
    const decoded = jwt.verify(refreshToken, process.env.REFRESH_TOKEN_SECRET);
    return decoded.userId;
  } catch (error) {
    throw new Error("Refresh token tidak valid");
  }
}

function generateAccessToken(userId) {
  const accessToken = jwt.sign({ userId }, process.env.ACCESS_TOKEN_SECRET, {
    expiresIn: "120m",
  });
  return accessToken;
}

function verifyAccessToken(accessToken) {
  try {
    const decoded = jwt.verify(accessToken, process.env.ACCESS_TOKEN_SECRET);
    return decoded.userId;
  } catch (error) {
    throw new Error("Access token tidak valid");
  }
}

app.use(
  session({
    secret: "rahasia",
    resave: true,
    saveUninitialized: true,
  })
);

function checkLogin(req, res, next) {
  if (req.session.user && req.session.user.loggedIn) {
    next();
  } else {
    res.status(401).json({ message: "Silakan login terlebih dahulu" });
  }
}

const generateRandomInvoice = () => {
  const randomInvoice = Math.floor(Math.random() * 1000000) + 1;
  return `INV-${randomInvoice}`;
};

app.get("/api/user", checkToken, (req, res) => {
  const userId = req.user.userId;

  db.collection("users")
    .doc(userId)
    .get()
    .then((doc) => {
      if (doc.exists) {
        const user = doc.data();
        res.json(user);
      } else {
        res.status(404).json({ error: "Pengguna tidak ditemukan" });
      }
    })
    .catch((error) => {
      console.error("Terjadi kesalahan saat mengambil data pengguna:", error);
      res
        .status(500)
        .json({ error: "Terjadi kesalahan saat mengambil data pengguna" });
    });
});

app.post("/api/refresh-token", (req, res) => {
  const refreshToken = req.body.refreshToken;

  jwt.verify(refreshToken, process.env.REFRESH_TOKEN_SECRET, (err, decoded) => {
    if (err) {
      return res.status(403).json({ error: "Refresh token tidak valid" });
    }
    const userId = decoded.userId;
    const newToken = jwt.sign({ userId }, process.env.ACCESS_TOKEN_SECRET, {
      expiresIn: process.env.ACCESS_TOKEN_EXPIRY,
    });

    res.json({ token: newToken });
  });
});

app.get("/api/check-login", checkLogin, (req, res) => {
  res.json({ loggedIn: true, message: "Pengguna sudah login" });
});

app.post("/api/logout", (req, res) => {
  req.session.destroy();

  res.clearCookie("token");
  res.clearCookie("user");

  res.json({ message: "Logout berhasil" });
});

app.get("/api/products/search", (req, res) => {
  const { name } = req.query;

  if (!name) {
    res.status(400).json({ error: "Parameter name harus diberikan" });
    return;
  }

  db.collection("products")
    .get()
    .then((snapshot) => {
      const products = [];
      snapshot.forEach((doc) => {
        const product = doc.data();
        if (product.namaProduk.toLowerCase().includes(name.toLowerCase())) {
          product.id = doc.id;
          products.push(product);
        }
      });
      res.json(products);
    })
    .catch((error) => {
      console.error("Terjadi kesalahan saat mencari produk:", error);
      res.status(500).json({ error: "Terjadi kesalahan saat mencari produk" });
    });
});

app.get("/api/products", (req, res) => {
  db.collection("products")
    .get()
    .then((snapshot) => {
      const products = [];
      snapshot.forEach((doc) => {
        const product = doc.data();
        product.id = doc.id;
        products.push(product);
      });
      res.json(products);
    })
    .catch((error) => {
      console.error("Terjadi kesalahan saat mengambil produk:", error);
      res
        .status(500)
        .json({ error: "Terjadi kesalahan saat mengambil produk" });
    });
});

let counter = 0;
app.post("/api/cart", checkToken, checkLogin, (req, res) => {
  const { productId, quantity } = req.body;
  const userId = req.cookies.user.userDocId;

  if (!userId) {
    return res.status(401).json({ error: "Unauthorized" });
  }

  const cartId = "keranjang_" + userId + "_" + counter;
  counter++;

  const cartData = {
    productId,
    quantity: parseInt(quantity, 10),
    userId,
  };

  db.collection("cart")
    .where("userId", "==", userId)
    .where("productId", "==", productId)
    .get()
    .then((snapshot) => {
      if (snapshot.empty) {
        return db.collection("cart").doc(cartId).set(cartData);
      } else {
        const existingCartData = snapshot.docs[0].data();
        const existingCartId = snapshot.docs[0].id;
        const existingQuantity = existingCartData.quantity || 0;
        const newQuantity = existingQuantity + parseInt(quantity, 10);

        return db.collection("cart").doc(existingCartId).update({
          quantity: newQuantity,
        });
      }
    })
    .then(() => {
      res
        .status(201)
        .json({ message: "Produk telah ditambahkan ke keranjang" });
    })
    .catch((error) => {
      console.error(
        "Terjadi kesalahan saat menambahkan produk ke keranjang:",
        error
      );
      res.status(500).json({
        error: "Terjadi kesalahan saat menambahkan produk ke keranjang",
      });
    });
});

app.get("/api/cart", checkToken, checkLogin, (req, res) => {
  const userId = req.cookies.user.userDocId;
  db.collection("cart")
    .where("userId", "==", userId)
    .get()
    .then((snapshot) => {
      if (snapshot.empty) {
        res.json({ cartItems: [], hargaKeranjang: 0 });
        return;
      }

      const cartItems = [];
      const promises = [];

      snapshot.forEach((doc) => {
        const cartItem = doc.data();
        cartItems.push(cartItem);

        const promise = db
          .collection("products")
          .doc(cartItem.productId)
          .get()
          .then((productDoc) => {
            if (productDoc.exists) {
              const productData = productDoc.data();
              cartItem.namaProduk = productData.namaProduk;
              const hargaRupiah = productData.hargaRupiah;
              const quantity = parseInt(cartItem.quantity, 10);
              const totalHarga = hargaRupiah * quantity;
              cartItem.totalHarga = totalHarga;
              cartItem.url = productData.url;
            }
          })
          .catch((error) => {
            console.error(
              "Terjadi kesalahan saat mengambil data produk:",
              error
            );
          });

        promises.push(promise);
      });

      Promise.all(promises)
        .then(() => {
          const hargaKeranjang = cartItems.reduce(
            (total, item) => total + item.totalHarga,
            0
          );
          res.json({
            cartItems,
            hargaKeranjang,
          });
        })
        .catch((error) => {
          console.error("Terjadi kesalahan saat mengambil data produk:", error);
          res.status(500).json({
            error: "Terjadi kesalahan saat mengambil data produk",
          });
        });
    })
    .catch((error) => {
      console.error(
        "Terjadi kesalahan saat mengambil daftar produk dalam keranjang:",
        error
      );
      res.status(500).json({
        error: "Terjadi kesalahan saat mengambil daftar produk dalam keranjang",
      });
    });
});

app.delete("/api/cart", checkToken, checkLogin, (req, res) => {
  const userId = req.session.user.userDocId;

  db.collection("cart")
    .where("userId", "==", userId)
    .get()
    .then((snapshot) => {
      const deletePromises = snapshot.docs.map((doc) => doc.ref.delete());
      return Promise.all(deletePromises);
    })
    .then(() => {
      res.json({ message: "Produk dalam keranjang berhasil dihapus" });
    })
    .catch((error) => {
      console.error(
        "Terjadi kesalahan saat menghapus produk dalam keranjang:",
        error
      );
      res.status(500).json({
        error: "Terjadi kesalahan saat menghapus produk dalam keranjang",
      });
    });
});

app.delete("/api/cart/:productId", checkToken, checkLogin, (req, res) => {
  const productId = req.params.productId;
  const userId = req.session.user.userDocId;

  db.collection("cart")
    .where("productId", "==", productId)
    .where("userId", "==", userId)
    .get()
    .then((snapshot) => {
      snapshot.forEach((doc) => {
        const quantity = doc.data().quantity;
        if (quantity > 1) {
          doc.ref
            .update({ quantity: quantity - 1 })
            .then(() => {
              res
                .status(200)
                .json({ message: "Quantity produk telah dikurangi" });
            })
            .catch((error) => {
              console.error(
                "Terjadi kesalahan saat mengupdate quantity:",
                error
              );
              res
                .status(500)
                .json({ error: "Terjadi kesalahan saat mengupdate quantity" });
            });
        } else {
          doc.ref
            .delete()
            .then(() => {
              res
                .status(204)
                .json({ message: "Produk telah terhapus dari keranjang" });
            })
            .catch((error) => {
              console.error(
                "Terjadi kesalahan saat menghapus produk dari keranjang:",
                error
              );
              res.status(500).json({
                error: "Terjadi kesalahan saat menghapus produk dari keranjang",
              });
            });
        }
      });
    })
    .catch((error) => {
      console.error(
        "Terjadi kesalahan saat menghapus produk dari keranjang:",
        error
      );
      res.status(500).json({
        error: "Terjadi kesalahan saat menghapus produk dari keranjang",
      });
    });
});

//checkout
app.post("/api/cart/checkout", checkToken, checkLogin, (req, res) => {
  const userId = req.cookies.user.userDocId;
  const currentTime = new Date();
  const options = { timeZone: "Asia/Jakarta" };
  const waktuJakarta = currentTime.toLocaleString("en-US", options);

  db.collection("cart")
    .where("userId", "==", userId)
    .get()
    .then((snapshot) => {
      if (snapshot.empty) {
        res.status(400).json({ error: "Keranjang kosong" });
        return;
      }

      const cartItems = [];
      const promises = [];

      snapshot.forEach((doc) => {
        const cartItem = doc.data();
        cartItems.push(cartItem);

        const promise = db
          .collection("products")
          .doc(cartItem.productId)
          .get()
          .then((productDoc) => {
            if (productDoc.exists) {
              const productData = productDoc.data();
              cartItem.namaProduk = productData.namaProduk;
              const hargaRupiah = productData.hargaRupiah;
              const quantity = parseInt(cartItem.quantity, 10);
              const totalHarga = hargaRupiah * quantity;
              cartItem.totalHarga = totalHarga;
              cartItem.url = productData.url;
            }
          })
          .catch((error) => {
            console.error(
              "Terjadi kesalahan saat mengambil data produk:",
              error
            );
          });

        promises.push(promise);
      });

      Promise.all(promises)
        .then(() => {
          const hargaKeranjang = cartItems.reduce(
            (total, item) => total + (item.totalHarga || 0),
            0
          );

          const invoice = generateRandomInvoice();

          // Nama dokumen transaksi
          let transactionDocName = `transaksi_${userId}`;
          let counter = 1;
          const checkTransaction = () => {
            return db
              .collection("transaksi")
              .doc(transactionDocName)
              .get()
              .then((doc) => {
                if (doc.exists) {
                  counter++;
                  transactionDocName = `transaksi_${userId}_${counter}`;
                  return checkTransaction();
                } else {
                  const transactionData = {
                    userId: userId,
                    cartItems,
                    hargaKeranjang: hargaKeranjang,
                    tanggalWaktuCheckout: waktuJakarta,
                    invoice,
                  };

                  return db
                    .collection("transaksi")
                    .doc(transactionDocName)
                    .set(transactionData)
                    .then(() => {
                      res.status(200).json({
                        message: "Checkout berhasil",
                        cartItems,
                        hargaKeranjang: hargaKeranjang,
                        tanggalWaktuCheckout: waktuJakarta,
                        invoice,
                      });
                    })
                    .then(() => {
                      const deletePromises = snapshot.docs.map((doc) =>
                        doc.ref.delete()
                      );
                      return Promise.all(deletePromises);
                    });
                }
              });
          };
          return checkTransaction();
        })
        .catch((error) => {
          console.error("Terjadi kesalahan saat melakukan checkout:", error);
          res
            .status(500)
            .json({ error: "Terjadi kesalahan saat melakukan checkout" });
        });
    })
    .catch((error) => {
      console.error(
        "Terjadi kesalahan saat mengambil data produk dalam keranjang:",
        error
      );
      res.status(500).json({
        error: "Terjadi kesalahan saat mengambil data produk dalam keranjang",
      });
    });
});

app.get("/api/transaksi/terbaru", checkToken, checkLogin, (req, res) => {
  const userId = req.cookies.user.userDocId;

  db.collection("transaksi")
    .where("userId", "==", userId)
    .orderBy("tanggalWaktuCheckout", "desc")
    .limit(1)
    .get()
    .then((snapshot) => {
      if (snapshot.empty) {
        res.status(404).json({ error: "Data transaksi tidak ditemukan" });
        return;
      }

      snapshot.forEach((doc) => {
        const transactionData = doc.data();
        res.status(200).json(transactionData);
      });
    })
    .catch((error) => {
      console.error("Terjadi kesalahan saat mengambil data transaksi:", error);
      res
        .status(500)
        .json({ error: "Terjadi kesalahan saat mengambil data transaksi" });
    });
});

app.get("/api/transaksi", checkToken, checkLogin, (req, res) => {
  const userId = req.cookies.user.userDocId;

  db.collection("transaksi")
    .where("userId", "==", userId)
    .get()
    .then((snapshot) => {
      if (snapshot.empty) {
        res.status(404).json({ error: "Data transaksi tidak ditemukan" });
        return;
      }

      const transaksi = [];
      snapshot.forEach((doc) => {
        const transactionData = doc.data();
        transaksi.push(transactionData);
      });

      res.status(200).json(transaksi);
    })
    .catch((error) => {
      console.error("Terjadi kesalahan saat mengambil data transaksi:", error);
      res
        .status(500)
        .json({ error: "Terjadi kesalahan saat mengambil data transaksi" });
    });
});

app.get("/api/cart/total", checkToken, checkLogin, (req, res) => {
  db.collection("cartItems")
    .get()
    .then((snapshot) => {
      let total = 0;
      snapshot.forEach((doc) => {
        const cartItem = doc.data();
        total += cartItem.price * cartItem.quantity;
      });
      res.json({ total });
    })
    .catch((error) => {
      console.error(
        "Terjadi kesalahan saat menghitung total harga pembelian:",
        error
      );
      res.status(500).json({
        error: "Terjadi kesalahan saat menghitung total harga pembelian",
      });
    });
});

app.post("/api/register", (req, res) => {
  const {
    username,
    email,
    password,
    jenisKelamin,
    kategoriProduk,
    skorAroma,
    Aroma,
    skorAsam,
    asam,
  } = req.body;

  const parsedSkorAroma = parseInt(skorAroma);
  const parsedSkorAsam = parseInt(skorAsam);

  db.collection("users")
    .where("email", "==", email)
    .get()
    .then((snapshot) => {
      if (!snapshot.empty) {
        return res.status(400).json({ error: "Email sudah terdaftar" });
      }

      bcrypt.hash(password, 10, (err, hashedPassword) => {
        if (err) {
          return res
            .status(500)
            .json({ error: "Terjadi kesalahan saat registrasi" });
        }

        const data = {
          username,
          email,
          password: hashedPassword,
          jenisKelamin,
          kategoriProduk,
          skorAroma: parsedSkorAroma,
          Aroma,
          skorAsam: parsedSkorAsam,
          asam,
        };

        db.collection("users")
          .doc(email)
          .set(data)
          .then(() => {
            res.json({ message: "Pendaftaran berhasil" });
          })
          .catch((error) => {
            console.error(
              "Terjadi kesalahan saat mendaftarkan pengguna:",
              error
            );
            res
              .status(500)
              .json({ error: "Terjadi kesalahan saat mendaftarkan pengguna" });
          });
      });
    })
    .catch((error) => {
      console.error("Terjadi kesalahan saat memeriksa email:", error);
      res.status(500).json({ error: "Terjadi kesalahan saat memeriksa email" });
    });
});

// Endpoint untuk login
app.post("/api/login", (req, res) => {
  const { email, password } = req.body;

  admin
    .firestore()
    .collection("users")
    .where("email", "==", email)
    .get()
    .then((snapshot) => {
      if (snapshot.empty) {
        return res.status(404).json({ error: "Email atau kata sandi salah" });
      }

      const user = snapshot.docs[0].data();

      bcrypt.compare(password, user.password, (err, result) => {
        if (err || !result) {
          return res.status(401).json({ error: "Email atau kata sandi salah" });
        }
        const token = jwt.sign({ userId: user.id }, process.env.SECRET_KEY);

        req.session.user = {
          loggedIn: true,
          userId: user.id,
          userDocId: snapshot.docs[0].id,
          token: token,
        };

        res.cookie("token", token, { httpOnly: true });
        res.cookie("user", req.session.user);
        res.setHeader("Authorization", `Bearer ${token}`);
        axios
          .post(
            "https://ml-dot-capstone-4cffc.et.r.appspot.com/predict",
            {},
            {
              headers: {
                Cookie: `user=${req.session.user.userDocId}`,
              },
              withCredentials: true,
            }
          )
          .then((response) => {
            const data = response.data;
            admin
              .firestore()
              .collection("user_responses")
              .doc(email)
              .set(data)
              .then(() => {
                console.log(
                  "Data respons berhasil disimpan untuk pengguna dengan ID:"
                );
                res.json({ message: "Login berhasil", token });
              })
              .catch((error) => {
                console.error(
                  "Terjadi kesalahan saat menyimpan data respons:",
                  error
                );
                res.status(500).json({
                  error: "Terjadi kesalahan saat menyimpan data respons",
                });
              });
          })
          .catch((error) => {
            console.error(
              "Terjadi kesalahan saat mengirim permintaan ke API Flask:",
              error
            );
            res.status(500).json({
              error: "Terjadi kesalahan saat mengirim permintaan ke API Flask",
            });
          });
      });
    })
    .catch((error) => {
      console.error("Terjadi kesalahan saat login:", error);
      res.status(500).json({ error: "Terjadi kesalahan saat login" });
    });
});

app.get("/api/recomendation", (req, res) => {
  const userId = req.cookies.user.userDocId;
  console.log(userId);

  admin
    .firestore()
    .collection("user_responses")
    .doc(userId)
    .get()
    .then((doc) => {
      if (!doc.exists) {
        return res.status(404).json({ error: "Data tidak ditemukan" });
      }

      const userData = doc.data();
      const results = userData.results;
      const predictedIdProduk = results.map(
        (result) => result.predicted_idProduk
      );
      results.sort((a, b) =>
        a.predicted_idProduk.localeCompare(b.predicted_idProduk)
      );

      admin
        .firestore()
        .collection("products")
        .where(admin.firestore.FieldPath.documentId(), "in", predictedIdProduk)
        .orderBy(admin.firestore.FieldPath.documentId())
        .get()
        .then((querySnapshot) => {
          const products = [];
          querySnapshot.forEach((productDoc) => {
            if (productDoc.exists) {
              const productData = productDoc.data();
              const id = productDoc.id;
              const productWithId = { id, ...productData };
              products.push(productWithId);
            }
          });

          const responseData = {
            results: results,
            products: products,
          };

          res.json(responseData);
        })
        .catch((error) => {
          console.error("Terjadi kesalahan saat mengambil data produk:", error);
          res
            .status(500)
            .json({ error: "Terjadi kesalahan saat mengambil data produk" });
        });
    })
    .catch((error) => {
      console.error("Terjadi kesalahan saat mengambil data pengguna:", error);
      res
        .status(500)
        .json({ error: "Terjadi kesalahan saat mengambil data pengguna" });
    });
});

app.get("/api/similar", (req, res) => {
  const userId = req.cookies.user.userDocId;
  console.log(userId);

  admin
    .firestore()
    .collection("user_responses")
    .doc(userId)
    .get()
    .then((doc) => {
      if (!doc.exists) {
        return res.status(404).json({ error: "Data tidak ditemukan" });
      }

      const userData = doc.data();
      const results1 = userData.results1;
      const predictedIdProduk = results1.map(
        (result1) => result1.predicted_idProduk
      );

      results1.sort((a, b) =>
        a.predicted_idProduk.localeCompare(b.predicted_idProduk)
      );

      admin
        .firestore()
        .collection("products")
        .where(admin.firestore.FieldPath.documentId(), "in", predictedIdProduk)
        .get()
        .then((querySnapshot) => {
          const products = [];
          querySnapshot.forEach((productDoc) => {
            if (productDoc.exists) {
              const productData = productDoc.data();
              const id = productDoc.id;
              const productWithId = { id, ...productData };
              products.push(productWithId);
            }
          });

          const responseData = {
            similar: results1,
            products: products,
          };

          res.json(responseData);
        })
        .catch((error) => {
          console.error("Terjadi kesalahan saat mengambil data produk:", error);
          res
            .status(500)
            .json({ error: "Terjadi kesalahan saat mengambil data produk" });
        });
    })
    .catch((error) => {
      console.error("Terjadi kesalahan saat mengambil data pengguna:", error);
      res
        .status(500)
        .json({ error: "Terjadi kesalahan saat mengambil data pengguna" });
    });
});

app.post(
  "/api/products/:productId/reviews",
  checkLogin,
  checkToken,
  (req, res) => {
    const { productId } = req.params;
    const { rating, comment } = req.body;
    const userId = req.session.user.userDocId;

    if (!rating || !comment) {
      res.status(400).json({ error: "Rating dan komentar diperlukan" });
      return;
    }

    const review = {
      userId,
      rating: parseInt(rating, 10),
      comment,
      createdAt: new Date(),
    };

    db.collection("reviews")
      .add(review)
      .then((docRef) => {
        const reviewId = docRef.id;

        db.collection("products")
          .doc(productId)
          .update({
            reviews: admin.firestore.FieldValue.arrayUnion(reviewId),
          })
          .then(() => {
            res.json({ reviewId });
          })
          .catch((error) => {
            console.error(
              "Terjadi kesalahan saat memperbarui reviews produk:",
              error
            );
            res.status(500).json({
              error: "Terjadi kesalahan saat memperbarui reviews produk",
            });
          });
      })
      .catch((error) => {
        console.error("Terjadi kesalahan saat menambahkan review:", error);
        res
          .status(500)
          .json({ error: "Terjadi kesalahan saat menambahkan review" });
      });
  }
);

app.get("/api/products/:id", checkToken, (req, res) => {
  const id = req.params.id;

  db.collection("products")
    .doc(id)
    .get()
    .then((productDoc) => {
      if (productDoc.exists) {
        const productData = productDoc.data();
        const product = {
          id: productDoc.id,
          ...productData,
        };

        if (Array.isArray(productData.reviews)) {
          const reviewPromises = productData.reviews.map((reviewId) =>
            db.collection("reviews").doc(reviewId).get()
          );

          Promise.all(reviewPromises)
            .then((reviewDocs) => {
              const reviews = reviewDocs.map((reviewDoc) => {
                const reviewData = reviewDoc.data();
                return {
                  id: reviewDoc.id,
                  ...reviewData,
                };
              });

              product.reviews = reviews;

              res.json(product);
            })
            .catch((error) => {
              console.error(
                "Terjadi kesalahan saat mengambil detail produk:",
                error
              );
              res.status(500).json({
                error: "Terjadi kesalahan saat mengambil detail produk",
              });
            });
        } else {
          product.reviews = [];
          res.json(product);
        }
      } else {
        res.status(404).json({
          error: "Produk tidak ditemukan",
        });
      }
    })
    .catch((error) => {
      console.error("Terjadi kesalahan saat mengambil detail produk:", error);
      res.status(500).json({
        error: "Terjadi kesalahan saat mengambil detail produk",
      });
    });
});

app.get("/", (req, res) => {
  res.send("Selamat Datang di API Kegelapan");
});

app.listen(port, () => {
  console.log(`Server berjalan di ${port}`);
});