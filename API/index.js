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

// Mengakses kunci rahasia
const secretKey = process.env.SECRET_KEY;
const REFRESH_TOKEN_SECRET = process.env.REFRESH_TOKEN_SECRET;
const ACCESS_TOKEN_SECRET = process.env.ACCESS_TOKEN_SECRET;
const db = admin.firestore();

// Middleware untuk mengizinkan CORS
app.use((req, res, next) => {
  res.setHeader("Access-Control-Allow-Origin", "*");
  res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
  res.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
  next();
});
// Middleware untuk menginisialisasi sesi
app.use(
  session({
    secret: "rahasia", // Ganti dengan secret key yang lebih aman
    resave: false,
    saveUninitialized: true,
  })
);

// Middleware untuk memeriksa token
function checkToken(req, res, next) {
  const token = req.cookies.token; // Mengambil token dari cookies

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

// Menggunakan middleware checkToken pada rute yang memerlukan otentikas

app.use(express.json());
let users = [];
// Fungsi untuk menghasilkan refresh token
function generateRefreshToken(userId) {
  const refreshToken = jwt.sign({ userId }, process.env.REFRESH_TOKEN_SECRET);
  return refreshToken;
}

// Fungsi untuk memverifikasi refresh token
function verifyRefreshToken(refreshToken) {
  try {
    const decoded = jwt.verify(refreshToken, process.env.REFRESH_TOKEN_SECRET);
    return decoded.userId;
  } catch (error) {
    throw new Error("Refresh token tidak valid");
  }
}

// Fungsi untuk menghasilkan access token
function generateAccessToken(userId) {
  const accessToken = jwt.sign({ userId }, process.env.ACCESS_TOKEN_SECRET, {
    expiresIn: "120m",
  });
  return accessToken;
}

// Fungsi untuk memverifikasi access token
function verifyAccessToken(accessToken) {
  try {
    const decoded = jwt.verify(accessToken, process.env.ACCESS_TOKEN_SECRET);
    return decoded.userId;
  } catch (error) {
    throw new Error("Access token tidak valid");
  }
}

// Middleware untuk mengatur sesi
app.use(
  session({
    secret: "rahasia", // Ganti dengan kunci rahasia yang lebih kuat
    resave: true,
    saveUninitialized: true,
  })
);

// Middleware untuk memeriksa status login pengguna
function checkLogin(req, res, next) {
  if (req.session.user && req.session.user.loggedIn) {
    // Lanjutkan ke handler berikutnya jika pengguna sudah login
    next();
  } else {
    res.status(401).json({ message: "Silakan login terlebih dahulu" });
  }
}

const generateRandomInvoice = () => {
  const randomInvoice = Math.floor(Math.random() * 1000000) + 1;
  return `INV-${randomInvoice}`;
};

// Endpoint yang memerlukan autentikasi
app.get("/api/user", checkToken, (req, res) => {
  // Pengguna berhasil melewati autentikasi, lakukan operasi yang diperlukan
  // contoh: mengambil data pengguna dari database
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

// Endpoint untuk refresh token
app.post("/api/refresh-token", (req, res) => {
  const refreshToken = req.body.refreshToken;

  // Verifikasi refresh token
  jwt.verify(refreshToken, process.env.REFRESH_TOKEN_SECRET, (err, decoded) => {
    if (err) {
      return res.status(403).json({ error: "Refresh token tidak valid" });
    }

    // Buat token JWT baru
    const userId = decoded.userId;
    const newToken = jwt.sign({ userId }, process.env.ACCESS_TOKEN_SECRET, {
      expiresIn: process.env.ACCESS_TOKEN_EXPIRY,
    });

    res.json({ token: newToken });
  });
});

// Handler untuk memeriksa status login pengguna
app.get("/api/check-login", checkLogin, (req, res) => {
  res.json({ loggedIn: true, message: "Pengguna sudah login" });
});

// Handler untuk logout pengguna
app.post("/api/logout", (req, res) => {
  // Hapus data login pengguna dari sesi
  req.session.destroy();

  // Hapus cookie dengan nama "token" dan "user"
  res.clearCookie("token");
  res.clearCookie("user");

  res.json({ message: "Logout berhasil" });
});

// Pencarian produk berdasarkan nama
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
          product.id = doc.id; // Menambahkan properti "id" dari dokumen
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

// Mendapatkan daftar produk
app.get("/api/products", (req, res) => {
  db.collection("products")
    .get()
    .then((snapshot) => {
      const products = [];
      snapshot.forEach((doc) => {
        const product = doc.data();
        product.id = doc.id; // Menambahkan properti "id" dari dokumen
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

// Menambahkan produk ke keranjang
let counter = 0;
app.post("/api/cart", checkToken, checkLogin, (req, res) => {
  const { productId, quantity } = req.body;
  const userId = req.cookies.user.userDocId; // Mendapatkan userID dari sesi

  if (!userId) {
    return res.status(401).json({ error: "Unauthorized" });
  }

  const cartId = "keranjang_" + userId + "_" + counter;
  counter++;

  const cartData = {
    productId,
    quantity: parseInt(quantity, 10), // Mengubah quantity menjadi tipe data number
    userId,
  };

  db.collection("cart")
    .where("userId", "==", userId)
    .where("productId", "==", productId)
    .get()
    .then((snapshot) => {
      if (snapshot.empty) {
        // Jika dokumen belum ada, buat dokumen baru
        return db.collection("cart").doc(cartId).set(cartData);
      } else {
        // Jika dokumen sudah ada, update quantity
        const existingCartData = snapshot.docs[0].data();
        const existingCartId = snapshot.docs[0].id;
        const existingQuantity = existingCartData.quantity || 0;
        const newQuantity = existingQuantity + parseInt(quantity, 10); // Menambahkan quantity yang baru

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

// Mendapatkan daftar produk dalam keranjang
app.get("/api/cart", checkToken, checkLogin, (req, res) => {
  const userId = req.cookies.user.userDocId;
  db.collection("cart")
    .where("userId", "==", userId)
    .get()
    .then((snapshot) => {
      if (snapshot.empty) {
        // Tidak ada dokumen yang cocok dengan kueri
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

// Menghapus semua produk dari keranjang
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

// Menghapus produk dari keranjang
app.delete("/api/cart/:productId", checkToken, checkLogin, (req, res) => {
  const productId = req.params.productId;
  const userId = req.session.user.userDocId;

  db.collection("cart")
    .where("productId", "==", productId)
    .where("userId", "==", userId) // Ganti dengan ID pengguna yang sedang login
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
        // Tidak ada produk dalam keranjang
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

          // Cek apakah dokumen transaksi dengan nama yang sama sudah ada
          const checkTransaction = () => {
            return db
              .collection("transaksi")
              .doc(transactionDocName)
              .get()
              .then((doc) => {
                if (doc.exists) {
                  // Dokumen transaksi dengan nama yang sama sudah ada
                  // Tambahkan angka sebagai sufiks dan cek kembali
                  counter++;
                  transactionDocName = `transaksi_${userId}_${counter}`;
                  return checkTransaction(); // Lakukan pengecekan ulang
                } else {
                  // Dokumen transaksi dengan nama yang unik
                  // Simpan data transaksi ke Firestore
                  const transactionData = {
                    userId: userId, // Tambahkan userId ke data transaksi
                    cartItems,
                    hargaKeranjang: hargaKeranjang,
                    tanggalWaktuCheckout: waktuJakarta, // Menggunakan waktu Jakarta
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
                        tanggalWaktuCheckout: waktuJakarta, // Menggunakan waktu Jakarta
                        invoice,
                      });
                    })
                    .then(() => {
                      // Hapus semua produk dalam keranjang setelah checkout
                      const deletePromises = snapshot.docs.map((doc) =>
                        doc.ref.delete()
                      );
                      return Promise.all(deletePromises);
                    });
                }
              });
          };

          // Panggil fungsi untuk memeriksa dan menyimpan data transaksi
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

// Transaksi terbaru user
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

//history
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

// Menghitung total harga pembelian
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

// Menyelesaikan pembelian produk di keranjang

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

  // Mengubah skor aroma dan asam menjadi number
  const parsedSkorAroma = parseInt(skorAroma);
  const parsedSkorAsam = parseInt(skorAsam);

  // Periksa keberadaan email dalam database
  db.collection("users")
    .where("email", "==", email)
    .get()
    .then((snapshot) => {
      if (!snapshot.empty) {
        return res.status(400).json({ error: "Email sudah terdaftar" });
      }

      // Enkripsi kata sandi menggunakan bcrypt
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

        // Tambahkan data pengguna ke Firestore
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

  // Cari pengguna dengan email yang sesuai
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

      // Periksa kecocokan kata sandi menggunakan bcrypt
      bcrypt.compare(password, user.password, (err, result) => {
        if (err || !result) {
          return res.status(401).json({ error: "Email atau kata sandi salah" });
        }

        // Buat token otentikasi menggunakan JSON Web Token (JWT)
        const token = jwt.sign({ userId: user.id }, process.env.SECRET_KEY);

        // Simpan data login pengguna dalam sesi
        req.session.user = {
          loggedIn: true,
          userId: user.id,
          userDocId: snapshot.docs[0].id,
          token: token,
        };

        // Simpan token dalam cookie
        res.cookie("token", token, { httpOnly: true });
        res.cookie("user", req.session.user);
        res.setHeader("Authorization", `Bearer ${token}`);
        // Kirim permintaan ke API Flask dengan menyertakan cookie dalam header
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
            // Tangani respons dari API Flask
            const data = response.data; // Data respons yang ingin Anda simpan

            // Simpan data respons dalam dokumen pengguna yang bersangkutan
            admin
              .firestore()
              .collection("user_responses")
              .doc(email)
              .set(data)
              .then(() => {
                console.log(
                  "Data respons berhasil disimpan untuk pengguna dengan ID:"
                );
                // Lanjutkan dengan respons ke klien atau lakukan tindakan lainnya
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

// Recomendation Home
app.get("/api/recomendation", (req, res) => {
  const userId = req.cookies.user.userDocId;
  console.log(userId);

  // Mengambil data dari koleksi "user_responses" dengan nama dokumen yang sesuai dengan ID pengguna
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

      // Mengambil data produk dari koleksi "products" dengan ID dokumen yang sesuai dengan nilai predicted_idProduk
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
              const id = productDoc.id; // Mendapatkan ID dokumen
              const productWithId = { id, ...productData };
              products.push(productWithId);
            }
          });

          // Menggabungkan data pengguna dan detail produk menjadi satu objek respons
          const responseData = {
            results: results,
            products: products,
          };

          // Kirim data sebagai respons
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

  // Mengambil data dari koleksi "user_responses" dengan nama dokumen yang sesuai dengan ID pengguna
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

      // Mengambil data produk dari koleksi "products" dengan ID dokumen yang sesuai dengan nilai predicted_idProduk
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
              const id = productDoc.id; // Mendapatkan ID dokumen
              const productWithId = { id, ...productData };
              products.push(productWithId);
            }
          });

          // Menggabungkan data pengguna dan detail produk menjadi satu objek respons
          const responseData = {
            similar: results1,
            products: products,
          };

          // Kirim data sebagai respons
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

// Menambahkan review produk
app.post(
  "/api/products/:productId/reviews",
  checkLogin,
  checkToken,
  (req, res) => {
    const { productId } = req.params;
    const { rating, comment } = req.body;
    const userId = req.session.user.userDocId; // Ambil ID pengguna yang sedang login

    // Lakukan validasi data
    if (!rating || !comment) {
      res.status(400).json({ error: "Rating dan komentar diperlukan" });
      return;
    }

    // Buat objek review
    const review = {
      userId,
      rating: parseInt(rating, 10),
      comment,
      createdAt: new Date(),
    };

    // Simpan review ke database
    db.collection("reviews")
      .add(review)
      .then((docRef) => {
        const reviewId = docRef.id;

        // Update product's reviews property
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

// Melihat Detail Produk
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
