package com.example.contactapp.view.contactList

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactapp.databinding.FragmentContactListBinding
import com.example.contactapp.viewmodel.ContactViewModel

class ContactListFragment : Fragment() {

    private lateinit var binding: FragmentContactListBinding

    private val contactListViewModel: ContactViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactListBinding.inflate(layoutInflater)
        initUI()
        return binding.root
    }

    private fun initUI() {
        initUIState()
        initUIListener()
    }

    private fun initUIState() {
        // 1. Primer Listener es cuando se clickea un contacto
        contactListViewModel.contacts.observe(viewLifecycleOwner, Observer { contacts->
            println("Contact from ContactListFragment")
            binding.rvContact.layoutManager = LinearLayoutManager(context)
            binding.rvContact.adapter = ContactListAdapter(contacts) { contact ->
                println(contact)
                contactListViewModel.setCurrentContact(contact)
            }
        })
    }

    private fun initUIListener() {
    }
}