package com.example.contactapp.view.contactList

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.example.contactapp.data.database.ContactEntity
import com.example.contactapp.databinding.ItemContactListBinding
import com.squareup.picasso.Picasso

class ContactListAdapter(
    private val contacts: List<ContactEntity>,
    var itemClickListener: (ContactEntity) -> Unit
): RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>() {

    inner class ContactViewHolder(private val biding: ItemContactListBinding)
        : RecyclerView.ViewHolder(biding.root) {

            fun render(contact: ContactEntity) {
                val fullName = contact.firstName +" "+ contact.lastName
                val fullLocation = contact.streetName + " " + contact.streetNumber +" "+ contact.city +", "+ contact.state +", "+ contact.country
                biding.tvNameContact.text = fullName
                biding.tvEmailContact.text = contact.email
                biding.tvPhoneContact.text = contact.phone
                biding.tvAddressContact.text = fullLocation
                Picasso.get().load(contact.picture).into(biding.imgContact)

                biding.itemContact.setOnClickListener {
                    itemClickListener(contact)
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding =
            ItemContactListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun getItemCount() = contacts.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.render(contacts[position])
    }
}