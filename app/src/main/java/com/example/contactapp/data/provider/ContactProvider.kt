package com.example.contactapp.data.provider

import android.content.Context
import androidx.room.Room
import com.example.contactapp.data.database.ContactDatabase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ContactProvider {
    //provider firebase
    //provider Google

    val CONTACT_DATABASE_NAME = "contact-db"
    val RANDOMUSER_ENDPOINT ="https://randomuser.me/api/"

    fun provideRetroFit(): Retrofit {
        val endpointUrl = RANDOMUSER_ENDPOINT
        return Retrofit.Builder()
            .baseUrl(endpointUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun providerRoom(context: Context): ContactDatabase {
        return Room.databaseBuilder(context, ContactDatabase::class.java, CONTACT_DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }
}