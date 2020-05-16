package br.com.jmsdevel.firebaseexample


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.jmsdevel.firebaseexample.databinding.FragmentRealTimeBinding

/**
 * A simple [Fragment] subclass.
 */
class RealTimeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewBinding = FragmentRealTimeBinding.inflate(inflater, container, false)

        return viewBinding.root
    }
}
