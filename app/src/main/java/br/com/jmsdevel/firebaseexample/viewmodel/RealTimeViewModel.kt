package br.com.jmsdevel.firebaseexample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import br.com.jmsdevel.firebaseexample.database.RealTimeDatabase
import br.com.jmsdevel.firebaseexample.model.Usuario
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.util.function.Function

class RealTimeViewModel: ViewModel() {
    private val databaseReference = FirebaseDatabase.getInstance().getReference("card")

    private val live = RealTimeDatabase(databaseReference)

    private val liveData = MediatorLiveData<List<Usuario>>()

    init {
        liveData.addSource(live) {
            val usuarioLista = mutableListOf<Usuario>()
            it?.let {
                CoroutineScope(Dispatchers.IO).run {
                    it.children.forEach { filho ->
                        val usr = filho.getValue(Usuario::class.java)
                        usr?.let { u ->
                            u.id = filho.key.toString()
                            usuarioLista.add(u)
                        }
                    }
                    liveData.value = usuarioLista
                }
            }
        }
    }

    fun getSnapshot(): LiveData<List<Usuario>> {
        return liveData
    }

}