package com.example.contactapp.data.model

data class ContactResponse(
    val results: List<ContactModel>,
)

data class ContactModel(
    val gender: String,
    val name: Name,
    val location: Location,
    val email: String,
    val phone: String,
    val cell: String,
    val picture: Picture,
    val nat: String,
)

data class Name(
    val title: String,
    val first: String,
    val last: String,
)

data class Location(
    val street: Street,
    val city: String,
    val state: String,
    val country: String,
    val postcode: Any?,
    val coordinates: Coordinates,
    val timezone: Timezone,
)

data class Street(
    val number: Long,
    val name: String,
)

data class Coordinates(
    val latitude: String,
    val longitude: String,
)

data class Timezone(
    val offset: String,
    val description: String,
)

data class Picture(
    val large: String,
    val medium: String,
    val thumbnail: String,
)
