package br.com.jmsdevel.firebaseexample.ui


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.jmsdevel.firebaseexample.databinding.FragmentRealTimeBinding
import br.com.jmsdevel.firebaseexample.model.Usuario
import br.com.jmsdevel.firebaseexample.ui.adapter.RealTimeAdapter
import br.com.jmsdevel.firebaseexample.viewmodel.RealTimeViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_real_time.*

class RealTimeFragment : Fragment(), Observer<List<Usuario>> {

    private val viewModel by lazy {
        ViewModelProvider(this).get(RealTimeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewBinding = FragmentRealTimeBinding.inflate(inflater, container, false)

        return viewBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val lista: MutableList<Usuario> = mutableListOf()
        lista.add(Usuario("Teste", 1, 24, "1"))
        viewModel.getSnapshot().observe(this, this)
        with(rv_cadastros){
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onChanged(t: List<Usuario>?) {
        t?.let {
            val adaptador = RealTimeAdapter(it, context) {click ->
                Toast.makeText(activity, "${click.posicao} - ${click.evento}", Toast.LENGTH_SHORT).show()
            }
            rv_cadastros.adapter = adaptador
        }
    }
}
