package com.example.contactapp.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.contactapp.data.database.ContactEntity
import com.example.contactapp.databinding.ActivityContactBinding
import com.example.contactapp.view.contactDetail.ContactDetailFragment
import com.example.contactapp.view.contactList.ContactListFragment
import com.example.contactapp.viewmodel.ContactViewModel

class ContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactBinding
    private val contactListFragment: ContactListFragment = ContactListFragment()
    private val contactDetailFragment: ContactDetailFragment = ContactDetailFragment()

    private val contactViewModel: ContactViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setCurrentFragment(contactListFragment)
        //setCurrentFragment(contactDetailFragment)
        initUI()
    }

    // Estado inicial de la Activity
    private fun initUI() {
        // Se inicializa los estados iniciales de la Activity
        initUIState()
        // Y va a iniciar los Listener (observerables que estarán escuchando los eventos de la app)
        // Ej: onCLick (botones)
        // Y esto hará cambiar el estado inicial
        initUIListener()
    }

    private fun initUIState() {
        // Lo primero es hacer un llamado a los contactos de la base de datos interna con Room
        // Si no hay hace un fetch y los guarda en caché
        contactViewModel.getContacts()
        // Como fragment inicial sera el de la lista de contacto
        startContactListFragment()
    }

    private fun initUIListener() {
        // 1. Nuestro primer Listener será el de apretar la lupa, o en otra palabras
        // Nuestro imagenView con el vector de Search
        binding.ivSearch.setOnClickListener {
            binding.tvTitle.isVisible = false
            binding.etSearch.isVisible = true
            binding.btnArrow.isVisible = false
        }

        // 2. Nuestro segundo observanble va a dar cuenta de los cambios del currentContact
        contactViewModel.currentContact.observe(this, Observer { currentContact ->
            //2.1 Checkear que se imprime dede el Contact Activity
            println("Current Contact from Contact Activity - contactViewModel.currentContact")
            println(currentContact)
            // 2.2 Cada vez que cambie el currentContact y que no sea nulo que se envie el DetailContactFragment
            if (currentContact != null) {
                startDetailContactFragment(currentContact)
            }
        })

        //3. Nuestro tercer Listener sera el retroceder página
        binding.btnArrow.setOnClickListener {
            startContactListFragment()
            contactViewModel.resetCurrentContact()
            println("Current Contact from Contact Activity - btnArrow.setOnClickListener ")
            println(contactViewModel.currentContact.value)
            println("ContactList from Contact Activity - btnArrow.setOnClickListener ")
            contactViewModel.resetContactList()
            println(contactViewModel.contactsListFromDB)
        }

        //4. Nuestro cuarto Listener sera el de buscar un contacto
        binding.etSearch.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(char: CharSequence?, start: Int, before: Int, count: Int) {
                if (char != null) {
                    contactViewModel.filterContactsList(char)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(binding.frameLayoutFragment.id, fragment)
            commit()
        }
    }

    private fun startDetailContactFragment(currentContact: ContactEntity) {
        setCurrentFragment(contactDetailFragment)

        val fullName = currentContact.firstName + " " + currentContact.lastName
        binding.tvTitle.text = fullName

        //1. Se muestra el boton de volver
        binding.btnArrow.isVisible = true
        //2. Se esconde el boton de busqueda
        binding.ivSearch.isVisible = false
        //3. Se esconde la casilla de busqueda
        binding.etSearch.isVisible = false
    }

    private fun startContactListFragment() {
        // 1. Inicializa el fragment de la lista de Contacto
        setCurrentFragment(contactListFragment)
        // 2. Mostar la barra del title
        binding.tvTitle.isVisible = true
        // 3. Esconde la barra de búsqueda
        binding.etSearch.isVisible = false
        // 4. Inicializar la barra del titulo con el nombre de contact
        binding.tvTitle.text = "Contacts"
        // 5. Se esconde el boton de retroceder
        binding.btnArrow.isVisible = false
        // 6. Se muestra el boton de busqueda
        binding.ivSearch.isVisible = true
    }
}

//02-Agosto-2024
//1. ContactDetailFragment sea el fragment que se muestre inicialmente
//2. Replicar el UI que se muestra en el DetailContact de la prueba
//3. Pintar el layout con el primer contacto
//4. Implementar Picasso la imagen y siguiendo las buenas prácticas de las versiones de la libreria

//06-Agosto-2024
// Tarea1: 1-6 Super importante hacerlo a conciencia
// 1. Crear un nuevo imagenview
// 2. Si ContactListFragment -> Invisible
// 3. Si ContactDetailFragment -> Visible
// 4. Agregar un Listener de tipo click que devulva al currentContact al estado null
// 5. Observar el cambio desde el ContactDetailFragment a ContactListFragment
// 6. Asegurar el estado original del toolbar

// Tarea 2
// Filtrar los contactos
// 1. Crear en el viewmodel una nueva lista de contactos que va a ser nuestra lista de contacto inicial
// 2. Crear una funcion en ViewModel que permita filtar la Query que se esta haciendo en le text view
// 3. Actualizar la lista de contacto con los contactos filtrados
// 4. Crear una funcion que resete y vuelva en su estado original la lista de contacto
// 4. Ya sea el EditText quede vacio o que se vuelva desde el ContactListFragment o desde el ContactDetailFragment que se resete la lista