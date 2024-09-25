package com.example.contactapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ContactDao {
    @Query("SELECT * FROM contact_entity")
    suspend fun getAlLContacts():List<ContactEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveContact(contact:ContactEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateContact(contact: ContactEntity)

    //05-Agosto-2024
    //1. Actualizaar el entity con un booleando con un falso
    //2. Crear funcion en el dao que busque y actualice el booleando de true a false, o de false a true
}