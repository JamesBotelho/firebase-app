package br.com.jmsdevel.firebaseexample.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import br.com.jmsdevel.firebaseexample.database.RealTimeDatabase
import br.com.jmsdevel.firebaseexample.model.Evento
import br.com.jmsdevel.firebaseexample.model.Usuario
import com.google.firebase.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.util.function.Function

class RealTimeViewModel: ViewModel() {
    private val firebaseInstance = FirebaseDatabase.getInstance().apply {
        try {
            this.setPersistenceEnabled(true)
        }catch (e: Exception){}
    }
    private val databaseReference = firebaseInstance.getReference("card")

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

    fun salva(usuario: Usuario) {
        databaseReference.push().setValue(usuario)
    }

    private fun atualizaBanco(id: String, usuario: Usuario?) {
        databaseReference.child(id).setValue(usuario).addOnSuccessListener {
            Log.i("DEU CERTO", "DEU CERTO")
        }.addOnCanceledListener {
            Log.e("DEU RUIM", "DEU RUIM")
        }
    }

    private fun atualizaCurtidas(id: String, add: Boolean) {
        val ref = databaseReference.child("${id}/curtidas")
        ref.runTransaction(object: Transaction.Handler {

            override fun doTransaction(mutableData: MutableData): Transaction.Result {
                val curtidasAtual = mutableData.getValue(Int::class.java)

                curtidasAtual?.let {
                    if (it == 0) {
                        mutableData.value = 1
                    } else {
                        if (add)
                            mutableData.value = curtidasAtual + 1
                        else
                            mutableData.value = curtidasAtual - 1
                    }
                }

                return Transaction.success(mutableData)
            }

            override fun onComplete(p0: DatabaseError?, p1: Boolean, p2: DataSnapshot?) {
                Log.i("ACABOU", "ACABOU")
            }
        })
    }

    fun modificaValor(acao: String, usuario: Usuario) {
        val id = usuario.id
        usuario.id = ""
        when(acao) {
            Evento.CURTIR.name -> {
                usuario.curtidas = usuario.curtidas+1
                atualizaCurtidas(id, true)
            }
            Evento.DESCURTIR.name ->{
                usuario.curtidas = usuario.curtidas-1
                atualizaCurtidas(id, false)
            }
            else -> {
                atualizaBanco(id, null)
            }
        }

    }

}