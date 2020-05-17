package br.com.jmsdevel.firebaseexample.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.com.jmsdevel.firebaseexample.databinding.FragmentMainBinding
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(), View.OnClickListener {

    private val controlador by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewBinding = FragmentMainBinding.inflate(inflater, container, false)

        viewBinding.listener = this

        return viewBinding.root
    }

    private fun goToRealTime() {
        MainFragmentDirections.FromMainToReal().run {
            controlador.navigate(this)
        }
    }

    private fun goToFireStore() {

    }

    override fun onClick(view: View) {
        when(view.id){
            realTimeID.id -> goToRealTime()
        }
    }
}
