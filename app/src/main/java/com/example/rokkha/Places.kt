package com.example.rokkha

object Places {
    var places = mutableListOf<String>()
    fun addPlace(place: Place) {
        places.add(place.toJson())
    }
}