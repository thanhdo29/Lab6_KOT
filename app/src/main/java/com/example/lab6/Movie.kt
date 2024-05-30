package com.example.lab6

data class Movie (
    val title:String,
    val year:String,
    val postURL:String
){
    companion object{
        fun getSampleMovies()= listOf(
            Movie("Movie 1", "2013", "https://i.pinimg.com/564x/50/bf/08/50bf08df9bdb82370080d7b722b22983.jpg"),
            Movie("Movie 2", "2004", "https://i.pinimg.com/564x/50/bf/08/50bf08df9bdb82370080d7b722b22983.jpg"),
            Movie("Movie 3", "2015", "https://i.pinimg.com/564x/50/bf/08/50bf08df9bdb82370080d7b722b22983.jpg"),
            Movie("Movie 4", "2016", "https://i.pinimg.com/564x/50/bf/08/50bf08df9bdb82370080d7b722b22983.jpg"),
            Movie("Movie 5", "2018", "https://i.pinimg.com/564x/50/bf/08/50bf08df9bdb82370080d7b722b22983.jpg"),
        )
    }
}
