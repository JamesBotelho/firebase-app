package br.com.jmsdevel.firebaseexample.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import br.com.jmsdevel.firebaseexample.databinding.FragmentFireStoreBinding
import br.com.jmsdevel.firebaseexample.model.Usuario
import br.com.jmsdevel.firebaseexample.ui.adapter.RealTimeAdapter
import br.com.jmsdevel.firebaseexample.viewmodel.FireStoreViewModel
import kotlinx.android.synthetic.main.fragment_fire_store.*

class FireStoreFragment : Fragment(), Observer<List<Usuario>>, View.OnClickListener {
    private val fireStoreViewModel by lazy {
        ViewModelProvider(this).get(FireStoreViewModel::class.java)
    }

    private val controlador by lazy {
        findNavController()
    }

    private lateinit var adaptador: RealTimeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewBinding = FragmentFireStoreBinding.inflate(inflater, container, false)

        viewBinding.listener = this

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(rv_usr_firestore){
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        fireStoreViewModel.getUsers().observe(this, this)
    }

    override fun onClick(view: View?) {
        FireStoreFragmentDirections.actionFireStoreFragmentToCadastroFragment(false).apply {
            controlador.navigate(this)
        }
    }

    override fun onChanged(usuarios: List<Usuario>?) {
        usuarios?.let {lista ->
            adaptador = RealTimeAdapter(lista, context) {callback ->
                fireStoreViewModel.manageUser(lista[callback.posicao], callback.evento)
            }
            rv_usr_firestore.adapter = adaptador
        }
    }
}
