package com.example.contactapp.view.contactDetail

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.contactapp.databinding.FragmentContactDetailBinding
import com.example.contactapp.viewmodel.ContactViewModel
import com.squareup.picasso.Picasso

class ContactDetailFragment : Fragment() {

    private lateinit var binding: FragmentContactDetailBinding

    private val contactDetailViewModel: ContactViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactDetailBinding.inflate(inflater, container, false)
        initUI()
        return binding.root
    }

    private fun initUI() {
        initUIState()
        initUIListener()
    }

    private fun initUIState() {
        //Desde Api
        /*
        contactDetailViewModel.contact.observe(viewLifecycleOwner) { contacts->
            //println("Contacts from view - detail : $contacts")
            val fullName = contacts[0].name.first +" "+ contacts[0].name.last
            val fullLocation = contacts[0].location.city +", "+ contacts[0].location.state +", "+ contacts[0].location.country
            Picasso.get().load(contacts[0].picture.large).into(binding.imgContact)
            binding.tvNameContact.text = fullName
            binding.tvSubtitle.text = "Subtitle"
            binding.tvEmailContact.text = contacts[0].email
            binding.tvPhoneContact.text = contacts[0].phone
            binding.tvAddressContact.text = fullLocation
        }
         */

        //Desde database
        /*
        contactDetailViewModel.contact.observe(viewLifecycleOwner) {contacts->
            val fullName = contacts[0].firstName +" "+ contacts[0].lastName
            val fullLocation =
                contacts[0].streetName +" "+ contacts[0].streetNumber +", "+ contacts[0].city +", "+ contacts[0].state +", "+ contacts[0].country
            binding.tvNameContact.text = fullName
            binding.tvEmailContact.text = contacts[0].email
            binding.tvPhoneContact.text = contacts[0].phone
            binding.tvAddressContact.text = fullLocation
            Picasso.get().load(contacts[0].picture).into(binding.imgContact)
        }
        */

        //El estado inicial del currentDetails es nulo, ya que el currentContact tiene como estado inicial nulo
    }

    private fun initUIListener() {

        //0. Listener que está escuchando el cambio de estado del currentContact
        contactDetailViewModel.currentContact.observe(viewLifecycleOwner){contact->
            println("Current Contact from ContactDetailFragment")
            println(contact)
            val fullName = contact?.firstName +" "+ contact?.lastName
            val fullLocation =
                contact?.streetName +" "+ contact?.streetNumber +", "+ contact?.city +", "+ contact?.state +", "+ contact?.country
            binding.tvNameContact.text = fullName
            binding.tvNameContact2.text = fullName
            binding.tvEmailContact.text = contact?.email
            binding.tvPhoneContact.text = contact?.phone
            binding.tvAddressContact.text = fullLocation
            Picasso.get().load(contact?.picture).into(binding.imgContact)

            if (contact?.isFavorite == true) {
                binding.ivUnFavorite.isVisible = false
                binding.ivFavorite.isVisible = true
            } else {
                binding.ivUnFavorite.isVisible = true
                binding.ivFavorite.isVisible = false
            }

            binding.ivUnFavorite.setOnClickListener {
                println("ivUnFavorite 1: " + contact?.isFavorite)
                binding.ivUnFavorite.isVisible = false
                binding.ivFavorite.isVisible = true
                if (contact != null) {
                    contact.isFavorite = true
                    contactDetailViewModel.saveFavorite(contact)
                }
                println("ivUnFavorite 2: " + contact?.isFavorite)
            }

            binding.ivFavorite.setOnClickListener {
                println("ivFavorite 1: " + contact?.isFavorite)
                binding.ivUnFavorite.isVisible = true
                binding.ivFavorite.isVisible = false
                if (contact != null) {
                    contact.isFavorite = false
                    contactDetailViewModel.saveFavorite(contact)
                }
                println("ivFavorite 2: " + contact?.isFavorite)
            }
        }
    }
}