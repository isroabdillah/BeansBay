## "BeansBay" Application API Documentation

This repository contains the code for the "Jual Beli Kopi" application's backend API. The API is developed using Node.js with the Express framework and is responsible for handling various functionalities related to product management, user interactions, and cart management. Below is a detailed documentation of the available API endpoints and their usage:

### API Endpoints

#### 1. Welcome
- Base URL: `https://capstone-4cffc.et.r.appspot.com/`
- Metode: GET
- Endpoint: `/`
- Contoh Permintaan:
  ```
  curl -X GET https://capstone-4cffc.et.r.appspot.com/
  ```
  ```
  curl -X GET -L https://capstone-4cffc.et.r.appspot.com/
  ```
- Parameter: Tidak ada parameter yang diperlukan.
- Respon:
  ```
  Selamat Datang di API Kegelapan
  ```

#### 2. Registrasi User
- Base URL: `https://capstone-4cffc.et.r.appspot.com/api/register`
- Metode: POST
- Endpoint: `/api/register`
- Tipe Konten: `application/json`
- Body Permintaan:
  ```json
  {
    "username": "kenangan",
    "email": "kenangan@gmail.com",
    "password": "kenangan",
    "jenisKelamin": "Laki-laki",
    "kategoriProduk": "Arabika",
    "skorAroma": "6",
    "aroma": "Sangat kuat",
    "skorAsam": "8",
    "asam": "Sedang"
  }
  ```
- Contoh Permintaan:
  ```
  curl -X POST -H "Content-Type: application/json" -d '{
    "username": "kenangan",
    "email": "kenangan@gmail.com",
    "password": "kenangan",
    "jenisKelamin": "Laki-laki",
    "kategoriProduk": "Arabika",
    "skorAroma": "6",
    "aroma": "Sangat kuat",
    "skorAsam": "8",
    "asam": "Sedang"
  }' https://capstone-4cffc.et.r.appspot.com/api/register
  ```
- Respon:
  ```
  {
    "message": "Pendaftaran berhasil"
  }
  ```
#### 3. Login
- Base URL: `https://capstone-4cffc.et.r.appspot.com/api/login`
- Metode: POST
- Endpoint: `/api/login`
- Tipe Konten: `application/json`
- Body Permintaan:
  ```json
  {
    "email": "kemenangan@gmail.com",
    "password": "kemenangan I'"
  }
  ```
- Contoh Permintaan:
  ```
  curl -X POST -H "Content-Type: application/json" -d '{
    "email": "kegelapan@gmail.com",
    "password": "kegelapan"
  }' https://capstone-4cffc.et.r.appspot.com/api/login
  ```
- Respon:
  ```json
  {
    "message": "Login berhasil",
    "token": "eyJhbGciOiJIUz11Ni1s1nv-'*T'T'--Vl'CJ9.eyJpYXQiOjE20DY€NjAxMTF9.xgtJOJ9N"
  }
  ```
#### 4. Logout
- Metode: POST
- Endpoint: `/api/logout`
- Contoh Permintaan:
  ```shell
  curl -X POST https://capstone-acffc.et.r.appspot.com/api/logout
  ```
  ```shell
  curl -X POST -L https://capstone-acffc.et.r.appspot.com/api/logout
  ```
- Respon:
  - Headers (8)
  - Body:
    ```
    Logout berhasil
    ```

#### 5. Memeriksa User Login/Tidak
- Metode: GET
- Endpoint: `/api/check-login`
- Contoh Permintaan:
  ```shell
  curl -X GET https://capstone-acffc.et.r.appspot.com/api/check-login
  ```
  ```shell
  curl -X GET -L https://capstone-acffc.et.r.appspot.com/api/check-login
  ```
- Respon:
  - Headers (8)
  - Body:
    ```json
    {
      "loggedln": true,
      "message": "Pengguna sudah login"
    }
    ```
  
#### 6. Mendapatkan Semua Produk
   - Metode: GET
   - Endpoint: `/api/products`
   - Contoh Permintaan:
     ```shell
     curl -X GET https://capstone-acffc.et.r.appspot.com/api/products
     ```
     ```shell
     curl -X GET -L https://capstone-acffc.et.r.appspot.com/api/products
     ```
   - Respon:
     - Headers (8)
     - Body:
       ```json
       {
         "asam": "Sedang",
         "namaProduk": "Kopi Arabika Sumatera",
         "skorAroma": 8,
         "asalProduk": "Sumatera Utara",
         "skorAsam": 6,
         "hargaDalamDolar": 15.99,
         "aroma": "Sangat kuat",
         "jenisProduk": "Biji kopi"
       }
       ```

  

#### 7. Mencari Produk Berdasarkan Nama
- Metode: GET
- Endpoint: `/api/products/search`
- Contoh Permintaan:
  ```shell
  curl "https://capstone-4cffc.et.r.appspot.com/api/products/search?name=kopi luwak"
  ```
- Respon:
  - Headers (8)
  - Body:
    ```json
    {
      "asam": "Sedang",
      "namaProduk": "Kopi Luwak",
      "skorAroma": 8,
      "asalProduk": "Indonesia",
      "hargaRupiah": 149985,
      "skorAsam": 6,
      "hargaDolar": 200.0,
      "aroma": "Sangat kuat"
    }
    ```
  

#### 8. Melihat Detail Produk
- Metode: GET
- Endpoint: `/api/products/:id`
- Contoh Permintaan:
  ```shell
  curl https://capstone-4cffc.et.r.appspot.com/api/products/:id
  ```
  atau
  ```shell
  curl -L https://capstone-4cffc.et.r.appspot.com/api/products/produkl
  ```
- PATH VARIABLES:
  - id
- Respon:
  - Headers (8)
  - Body:
    ```json
    {
      "id": "produkl",
      "asam": "Sedang",
      "namaProduk": "Kopi Arabika Sumatera",
      "skorAroma": 8,
      "asalProduk": "Sumatera Utara",
      "skorAsam": 6,
      "hargaDolar": 15.99,
      "aroma": "Sangat kuat",
      "deskripsiProduk": "Biji kopi dengan cita rasa tinggi dan aroma yang kaya"
    }
    ```

9. Menambahkan Produk Ke Keranjang
- Metode: POST
- Endpoint: `/api/cart`
- Contoh Permintaan:
  ```shell
  curl -X POST -H "Content-Type: application/json" -d '{"quantity": "2"}' https://capstone-4cffc.et.r.appspot.com/api/cart
  ```
- Body:
  ```json
  {
    "quantity": "2"
  }
  ```
- Respon:
  - Headers (8)
  - Body:
    ```json
    {
      "message": "Produk telah ditambahkan ke keranjang"
    }
    ```
    atau
    ```json
    {
      "status": 201,
      "message": "Produk telah ditambahkan ke keranjang"
    }
    ```
10. Mendapatkan Produk di Keranjang
- Metode: GET
- Endpoint: `/api/cart`
- Contoh Permintaan:
  ```shell
  curl -X GET https://capstone-4cffc.et.r.appspot.com/api/cart
  ```
  atau
  ```shell
  curl -L https://capstone-4cffc.et.r.appspot.com/api/cart
  ```
- Respon:
  - Headers (8)
  - Body:
    ```json
    {
      "cartItems": [
        {
          "quantity": 2,
          "productld": "produk7",
          "userld": "kegelapan@gmail.com",
          "totalHarga": 14997,
          "url": "https://storage.googleapis.com/bucket-gambar/Produk1.png"
        }
      ]
    }
    ```
    atau
    ```json
    {
      "status": 200,
      "cartItems": [
        {
          "quantity": 2,
          "productld": "produk7",
          "userld": "kegelapan@gmail.com",
          "totalHarga": 14997,
          "url": "https://storage.googleapis.com/bucket-gambar/Produk1.png"
        }
      ]
    }
    ```
11. Kurangi Jumlah, Hapus Produk Pada Keranjang
- Metode: DELETE
- Endpoint: `/api/cart/:productld`
- Contoh Permintaan:
  ```shell
  curl -X DELETE https://capstone-4cffc.et.r.appspot.com/api/cart/:productld
  ```
- PATH VARIABLES:
  - productld
- Respon:
  - Headers (8)
  - Body:
    ```json
    {
      "message": "Quantity produk telah dikurangi"
    }
    ```
    atau
    ```json
    {
      "status": 200,
      "message": "Quantity produk telah dikurangi"
    }
    ```
12. Menambahkan Review Produk
- Metode: POST
- Endpoint: `/api/products/:productld/reviews`
- Contoh Permintaan:
  ```shell
  curl -X POST -H "Content-Type: application/json" -d '{"rating": 5, "comment": "terlalu asoy"}' https://capstone-4cffc.et.r.appspot.com/api/products/produk7/reviews
  ```
- PATH VARIABLES:
  - productld
- Body:
  ```json
  {
    "rating": 5,
    "comment": "terlalu asoy"
  }
  ```
- Respon:
  - Headers (8)
  - Body:
    ```json
    {
      "status": 200,
      "reviewld": "nsps0igDvZ8NjJibAmRF",
      "comment": "terlalu asoy"
    }
    ```
13. Rekomendasi Api Dari ML
- Metode: GET
- Endpoint: `/api/recommendation`
- Contoh Permintaan:
  ```shell
  curl -X GET https://capstone-4cffc.et.r.appspot.com/api/recommendation?idProduk=P003&dk=P021
  ```
- Query Parameters:
  - idProduk: P003
  - dk: P021
- Respon:
  - Headers (11)
  - Body:
    ```json
    {
      "results": [
        {
          "predicted_idProduk": "P003"
        },
        {
          "predicted_idProduk": "P004"
        },
        {
          "predicted_idProduk": "P021"
        },
        {
          "predicted_idProduk": "P016"
        },
        {
          "predicted_idProduk": "P038"
        }
      ],
      "products": [
        {
          "namaProduk": "Kopi Single Origin Ethiopia",
          "asam": "Kuat",
          "skorAroma": 9,
          "skorAsam": 8,
          "asalProduk": "Ethiopia",
          "hargaRupiah": 284850,
          "hargaDollar": 18.99,
          "kategoriProduk": "Arabika",
          "url": "https://storage.googleapis.com/bucket-gambar/Produk3.jpg",
          "deskripsiProduk": "Biji kopi Arabika murni dari Ethiopia yang terkenal dengan cita rasa buah-buahan yang kuat. Kopi ini memiliki aroma bunga yang menarik dan tingkat keasaman yang tinggi, memberikan pengalaman yang unik bagi para pecinta kopi.",
          "aroma": "Sangat intens",
          "toko": "toko3"
        },
        {
          "namaProduk": "Kopi Luwak",
          "asam": "Sedang",
          "skorAroma": 8,
          "skorAsam": 6,
          "asalProduk": "Indonesia",
          "hargaRupiah": 1499850,
          "hargaDollar": 99.99,
          "kategoriProduk": "Luwak",
          "url": "https://storage.googleapis.com/bucket-gambar/Produk4.jpg",
          "deskripsiProduk": "Kopi Luwak terkenal karena proses uniknya. Biji kopi yang telah dikonsumsi oleh musang luwak diproses melalui pencernaan hewan tersebut sebelum ditemukan dalam kotorannya. Kopi ini memiliki rasa yang halus, rendah keasaman, dan aroma yang kaya.",
          "aroma": "Sangat kuat",
          "toko": "toko4"
        },
        {
          "asam": "Kuat",
          "namaProduk": "Kopi Yirgacheffe",
          "skorAroma": 9,
          "hargaRupiah": 269850,
          "asalProduk": "Ethiopia",
          "skorAsam": 8,
          "hargaDollar": 17.99,
          "url": "https://storage.googleapis.com/bucket-gambar/Produk16.jpg",
          "aroma": "Sangat intens",
          "kategoriProduk": "Arabika",
          "deskripsiProduk": "Biji kopi Arabika dari wilayah Yirgacheffe di Ethiopia. Kopi Yirgacheffe terkenal dengan rasa yang berani dan kompleks, dengan keasaman yang tinggi, serta aroma yang beragam seperti buah-buahan, bunga, dan cokelat.",
          "toko": "toko16"
        },
        {
          "asam": "Cukup kuat",
          "namaProduk": "Kopi Arabika Brazil",
          "skorAroma": 7,
          "asalProduk": "Brasil",
          "hargaRupiah": 224850,
          "skorAsam": 7,
          "hargaDollar": 14.99,
          "kategoriProduk": "Arabika",
          "url": "https://storage.googleapis.com/bucket-gambar/Produk21.jpg",
          "aroma": "Kuat",
          "deskripsiProduk": "Biji kopi Arabika yang ditanam di daerah pegunungan Brasil. Kopi ini memiliki rasa yang lembut dengan sentuhan cokelat dan karamel, serta aroma yang kaya dan khas.",
          "toko": "toko21"
        },
        {
          "namaProduk": "Kopi Espresso Blend",
          "asam": "Sedang",
          "skorAsam": 6,
          "asalProduk": "Berbagai Negara",
          "toko": "toko38",
          "hargaDollar": 15.99,
          "kategoriProduk": "Campuran",
          "deskripsiProduk": "Campuran biji kopi Arabika dan Robusta dari berbagai negara seperti Brasil, Kosta Rika, dan India. Kopi Espresso Blend ini cocok untuk penyeduhan espresso dengan cita rasa yang kuat, keasaman sedang, dan aroma yang kaya.",
          "url": "https://storage.googleapis.com/bucket-gambar/Produk38.jpg",
          "hargaRupiah": 239850,
          "aroma": "Sangat intens",
          "skorAroma": 9
        }
      ]
    }
    ```
14. Produk Serupa API dari Ml 
- Metode: GET
- Endpoint: `/api/similar`
- Contoh Permintaan:
  ```shell
  curl -X GET https://capstone-4cffc.et.r.appspot.com/api/similar
  ```
- Respon:
  - Headers (11)
  - Body:
    ```json
    {
      "similar": [
        {
          "predicted_idProduk": "P003"
        },
        {
          "predicted_idProduk": "P002"
        },
        {
          "predicted_idProduk": "P001"
        }
      ],
      "products": [
        {
          "namaProduk": "Kopi Arabika Sumatera",
          "asam": "Sedang",
          "reviews": [
            "RfUX5spGjd0UETyBO8df",
            "j8fOnxfPMwg7QwS6EBDA",
            "UjS7dLwnGNR4Wo7IWrQU"
          ],
          "skorAroma": 8,
          "asalProduk": "Sumatera Utara",
          "hargaRupiah": 239850,
          "skorAsam": 6,
          "hargaDollar": 15.99,
          "url": "https://storage.googleapis.com/bucket-gambar/Produk1.png",
          "aroma": "Sangat kuat",
          "kategoriProduk": "Arabika",
          "deskripsiProduk": "Biji kopi arabika berkualitas tinggi dengan aroma yang kaya dan cita rasa yang kompleks. Ditanam di dataran tinggi Sumatera, memberikan sentuhan unik dengan karakteristik bumi yang kuat",
          "toko": "toko1"
        },
        {
          "asam": "Cukup Kuat",
          "namaProduk": "Kopi Robusta Vietnam",
          "skorAroma": 7,
          "asalProduk": "Vietnam",
          "skorAsam": 7,
          "hargaRupiah": 194850,
          "hargaDollar": 12.99,
          "deskripsiProduk": "Biji kopi Robusta yang tumbuh subur di daerah pegunungan Vietnam. Kopi ini memiliki keasaman yang rendah, kandungan kafein yang tinggi, dan cenderung memiliki rasa yang lebih pahit dibandingkan dengan biji kopi Arabika.",
          "url": "https://storage.googleapis.com/bucket-gambar/Produk2.jpg",
          "kategoriProduk": "Robusta",
          "aroma": "Kuat",
          "toko": "toko2"
        },
        {
          "namaProduk": "Kopi Single Origin Ethiopia",
          "asam": "Kuat",
          "skorAroma": 9,
          "skorAsam": 8,
          "asalProduk": "Ethiopia",
          "hargaRupiah": 284850,
          "hargaDollar": 18.99,
          "kategoriProduk": "Arabika",
          "url": "https://storage.googleapis.com/bucket-gambar/Produk3.jpg",
          "deskripsiProduk": "Biji kopi Arabika murni dari Ethiopia yang terkenal dengan cita rasa buah-buahan yang kuat. Kopi ini memiliki aroma bunga yang menarik dan tingkat keasaman yang tinggi, memberikan pengalaman yang unik bagi para pecinta kopi.",
          "aroma": "Sangat intens",
          "toko": "toko3"
        }
      ]
    }
    ```



