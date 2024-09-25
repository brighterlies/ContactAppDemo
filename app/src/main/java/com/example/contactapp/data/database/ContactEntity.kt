package com.example.contactapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_entity")
data class ContactEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    @ColumnInfo(name="firstName")
    val firstName: String,
    @ColumnInfo(name="lastName")
    val lastName: String,
    @ColumnInfo(name="email")
    val email: String,
    @ColumnInfo(name="phone")
    val phone: String,
    @ColumnInfo(name="streetName")
    val streetName: String,
    @ColumnInfo(name="streetNumber")
    val streetNumber: String,
    @ColumnInfo(name="city")
    val city: String,
    @ColumnInfo(name="state")
    val state: String,
    @ColumnInfo(name="country")
    val country: String,
    @ColumnInfo(name="picture")
    val picture: String,
    @ColumnInfo(name="isFavorite")
    var isFavorite: Boolean = false
)