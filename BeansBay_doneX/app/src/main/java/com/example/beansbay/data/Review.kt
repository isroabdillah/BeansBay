package com.example.beansbay.data

data class Review(
    var id: String,
    var idProduk : String,
    var name : String,
    var rating : Int,
    var desc : String,
    var date : String,
) {}

fun getDummyReview() : List<Review>{
    return dataDummyReview.dummyReview
}

object dataDummyReview{
    val dummyReview = listOf(
        Review("1",
            "1",
            "Fikri",
            4,
            "Kopinya kuat, sangat membekas di mulut",
            "21 Feb 2023"
        ),
        Review("2",
            "1",
            "Visa",
            5,
            "Enak for general coffee lovers",
            "21 Jan 2023"
        ),
        Review("3",
            "1",
            "Fahira",
            5,
            "My Favorites bgttt",
            "25 Jun 2023"
        ),
        Review("4",
            "1",
            "Isro",
            4,
            "rasanya enak tapi aku pribadi kurang cocok",
            "11 Aug 2023"
        ),
        Review("5",
            "1",
            "Rejar",
            3,
            "Not my type bgt coffee nya",
            "15 Sept 2023"
        ),
        Review("6",
            "1",
            "Tenzilla",
            5,
            "njir enak bgt asli",
            "25 April 2023"
        ),
    )

}

