package com.example.contactapp.data.network

import com.example.contactapp.data.model.ContactResponse
import retrofit2.Response
import retrofit2.http.GET

interface ContactApiClient {
    @GET("/api/?results=20&exc=login,registered,dob,id&noinfo")
    suspend fun getContacts(): Response<ContactResponse>
}