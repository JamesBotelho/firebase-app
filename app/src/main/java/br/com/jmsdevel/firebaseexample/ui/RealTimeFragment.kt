package br.com.jmsdevel.firebaseexample.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.jmsdevel.firebaseexample.databinding.FragmentRealTimeBinding
import br.com.jmsdevel.firebaseexample.model.Usuario
import br.com.jmsdevel.firebaseexample.ui.adapter.RealTimeAdapter
import kotlinx.android.synthetic.main.fragment_real_time.*

class RealTimeFragment : Fragment() {

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
        lista.add(Usuario("Teste", 1, 24, 0))
        val adaptador = RealTimeAdapter(lista, context) {usuario ->
            Toast.makeText(activity, "${usuario.evento} - ${usuario.posicao}", Toast.LENGTH_SHORT).show()
        }
        with(rv_cadastros){
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = adaptador
        }
    }
}
