package com.example.contactapp.data.service

import com.example.contactapp.ContactApp
import com.example.contactapp.data.database.ContactEntity
import com.example.contactapp.data.model.ContactModel
import com.example.contactapp.data.network.ContactApiClient
import com.example.contactapp.data.provider.ContactProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactService {
    private val contactProvider: ContactProvider = ContactProvider()

    // Desde Api
    suspend fun getContactsApi(): List<ContactModel> {
        //1. Checkear si hay contactos guardados en nuestra db
        val contactFromDatabase = getContactsFromDatabase()

        //2. Si no hay contactos, que haga un fetch desde la Api y que guarde esos contactos
        if (contactFromDatabase.isEmpty()) {
            val contactFromApi = getContactsFromApi()
            saveAllContactOnDatabase(contactFromApi)
        }

        println("Contacts from database : $contactFromDatabase")

        return withContext(Dispatchers.IO) {
            getContactsFromApi()
        }
    }

    // Desde Database
    suspend fun getContactsDataBase(): List<ContactEntity> {
        //1. Checkear si hay contactos guardados en nuestra db
        var contactFromDatabase = getContactsFromDatabase()

        //2. Si no hay contactos, que haga un fetch desde la Api y que guarde esos contactos
        if (contactFromDatabase.isEmpty()) {
            val contactFromApi = getContactsFromApi()
            saveAllContactOnDatabase(contactFromApi)
            contactFromDatabase = getContactsFromDatabase()
        }

        println("Contacts from database : $contactFromDatabase")

        return contactFromDatabase
    }

    //suspend fun saveContact(contact:ContactModel) {
    suspend fun saveContact(contact: ContactEntity) {
        //val newContact: ContactEntity = ContactEntity(id=0, name = "pedro", email = "pedro@pedro.com")
        //val newContact: ContactEntity = ContactEntity(name = contact.name.first, email = contact.email)
        ContactApp.database.contactDao().saveContact(contact)
    }

    suspend fun saveAllContactOnDatabase(contacts: List<ContactModel>) {
        //val contacts: List<ContactModel> = getContactsFromApi()
        contacts.forEach { contact ->
            val newContact = ContactEntity(
                firstName = contact.name.first,
                lastName = contact.name.last,
                email = contact.email,
                phone = contact.phone,
                streetName = contact.location.street.name,
                streetNumber = contact.location.street.number.toString(),
                city = contact.location.city,
                state = contact.location.state,
                country = contact.location.country,
                picture = contact.picture.large
            )
            saveContact(newContact)
        }
    }

    suspend fun getContactsFromDatabase(): List<ContactEntity> {
        return ContactApp.database.contactDao().getAlLContacts()
    }

    suspend fun getContactsFromApi(): List<ContactModel> {
        val response = contactProvider.provideRetroFit().create(ContactApiClient::class.java).getContacts()
        return response.body()?.results?: emptyList()
    }

    suspend fun updateContact(contact: ContactEntity) {
        ContactApp.database.contactDao().updateContact(contact)
    }
}