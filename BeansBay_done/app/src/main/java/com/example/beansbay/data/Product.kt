package com.example.beansbay.data

data class Product(
    var id : String,
    var nama : String,
    var toko : String,
    var harga : Int,
    var deskripsi: String,
) {}

data class ProductCart(
    var id : String,
    var nama : String,
    var qty : Int,
    var harga: Int,
)

fun getDummyProduct() : List<Product>{
    return dataProductDummy.dataDummy
}

object dataProductDummy {
    val dataDummy = listOf(
        Product(
            "1",
            "Kopi Surabaya",
            "Rumah Kopi",
            10000,
            "Kopi dengan ciri khas surabaya yang memiliki rasa yang harum dan tajam"
        ),
        Product(
            "2",
            "Produk1",
            "Toko1",
            20000,
            "Deskripsi produk 1"
        ),
        Product(
            "3",
            "Produk2",
            "Toko2",
            15000,
            "Deskripsi produk 2"
        ),
        Product(
            "4",
            "Produk3",
            "Toko3",
            30000,
            "Deskripsi produk 3"
        ),
        Product(
            "5",
            "Produk4",
            "Toko4",
            25000,
            "Deskripsi produk 4"
        ),
        Product(
            "6",
            "Produk5",
            "Toko5",
            18000,
            "Deskripsi produk 5"
        ),
        Product(
            "7",
            "Produk6",
            "Toko6",
            22000,
            "Deskripsi produk 6"
        ),
        Product(
            "8",
            "Produk7",
            "Toko7",
            27000,
            "Deskripsi produk 7"
        ),
        Product(
            "9",
            "Produk8",
            "Toko8",
            19000,
            "Deskripsi produk 8"
        ),
        Product(
            "10",
            "Produk9",
            "Toko9",
            23000,
            "Deskripsi produk 9"
        ),
        Product(
            "11",
            "Produk10",
            "Toko10",
            28000,
            "Deskripsi produk 10"
        ),
        Product(
            "12",
            "Produk11",
            "Toko11",
            21000,
            "Deskripsi produk 11"
        ),
        Product(
            "13",
            "Produk12",
            "Toko12",
            16000,
            "Deskripsi produk 12"
        ),
        Product(
            "14",
            "Produk13",
            "Toko13",
            24000,
            "Deskripsi produk 13"
        ),
        Product(
            "15",
            "Produk14",
            "Toko14",
            29000,
            "Deskripsi produk 14"
        ),
    )
}


