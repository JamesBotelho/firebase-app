package br.com.jmsdevel.firebaseexample.ui


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import br.com.jmsdevel.firebaseexample.databinding.FragmentCadastroBinding
import br.com.jmsdevel.firebaseexample.model.Usuario
import br.com.jmsdevel.firebaseexample.viewmodel.FireStoreViewModel
import br.com.jmsdevel.firebaseexample.viewmodel.RealTimeViewModel

class CadastroFragment : Fragment(), View.OnClickListener {

    private val argumentos by navArgs<CadastroFragmentArgs>()

    private val realTime by lazy {
        argumentos.realtime
    }

    private val realTimeViewModel by lazy {
        ViewModelProvider(this).get(RealTimeViewModel::class.java)
    }

    private val fireStoreViewModel by lazy {
        ViewModelProvider(this).get(FireStoreViewModel::class.java)
    }

    private val controlador by lazy {
        findNavController()
    }

    private val usuario by lazy {
        Usuario()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val dataBinding = FragmentCadastroBinding.inflate(inflater, container, false)

        dataBinding.listener = this
        dataBinding.usuario = usuario

        return dataBinding.root
    }

    override fun onClick(view: View?) {
        if (realTime) {
            realTimeViewModel.salva(usuario)
        } else {
            fireStoreViewModel.saveUser(this.usuario)
        }

        controlador.popBackStack()
    }

    override fun onDetach() {
        super.onDetach()
        activity?.let {
            val inputMethodManager = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            it.currentFocus?.let { view ->
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }
}
