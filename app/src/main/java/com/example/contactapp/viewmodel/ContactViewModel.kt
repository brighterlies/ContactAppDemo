package com.example.contactapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactapp.data.database.ContactEntity
import com.example.contactapp.data.model.ContactModel
import com.example.contactapp.data.service.ContactService
import kotlinx.coroutines.launch

class ContactViewModel: ViewModel() {
    private val contactService: ContactService = ContactService()

    //Contacto Actual
    val currentContact = MutableLiveData<ContactEntity?>(null)

    //Desde Api
    //val contact = MutableLiveData<List<ContactModel>>()

    //Desde database
    val contacts = MutableLiveData<List<ContactEntity>>()

    var contactsListFromDB: List<ContactEntity> = emptyList()

    fun getContacts() {
        viewModelScope.launch {
            //val result = contactService.getContacts()
            val result = contactService.getContactsDataBase()
            if (result.isNotEmpty()) {
                //println(result)
                println("Contacts from viewModel: $result")
                contacts.postValue(result)
                contactsListFromDB = result
            }
        }
    }

    fun setCurrentContact(contactCurrent: ContactEntity) {
        currentContact.postValue(contactCurrent)
    }

    fun resetCurrentContact() {
        currentContact.postValue(null)
    }

    fun resetContactList() {
        contacts.postValue(contactsListFromDB)
    }

    fun saveFavorite(contact: ContactEntity) {
        viewModelScope.launch {
            contactService.updateContact(contact)
        }
    }

    fun filterContactsList(charSequence: CharSequence) {
        println("Secuencia de letra, mostrada desde el viewModel")
        println(charSequence)

        val contactsFiltered = contactsListFromDB.filter { contact->
            contact.firstName.contains(charSequence, ignoreCase = true)
                || contact.email.contains(charSequence, ignoreCase = true)
        }

        contacts.postValue(contactsFiltered)
    }

    fun updateFavorite() {
        viewModelScope.launch {
            val contact = currentContact.value?.copy(isFavorite = !currentContact.value!!.isFavorite)
            if (contact != null) {
                contactService.updateContact(contact)
                currentContact.postValue(contact)
            }
        }
    }
}