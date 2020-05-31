package br.com.jmsdevel.firebaseexample.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import br.com.jmsdevel.firebaseexample.database.FireStoreDatabase
import br.com.jmsdevel.firebaseexample.model.Evento
import br.com.jmsdevel.firebaseexample.model.Usuario
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class FireStoreViewModel: ViewModel() {
    private val firestoreInstance = FirebaseFirestore.getInstance()
    private val cardsInstance = FirebaseFirestore.getInstance().collection("cards")

    private val live = FireStoreDatabase(cardsInstance)

    private val liveData = MediatorLiveData<List<Usuario>>()

    init {
        liveData.addSource(live) { querySnapshot ->
            val usuarioLista = mutableListOf<Usuario>()

            querySnapshot?.let {snapshot ->
                CoroutineScope(Dispatchers.IO).run {
                    snapshot.documents.forEach {documento ->
                        val usr = documento.toObject(Usuario::class.java)
                        usr?.let { u ->
                            u.id = documento.id
                            usuarioLista.add(u)
                        }
                    }
                    liveData.value = usuarioLista
                }
            }
        }
    }

    fun getUsers(): LiveData<List<Usuario>> {
        return liveData
    }

    fun saveUser(usuario: Usuario) {
        cardsInstance.add(usuario).addOnSuccessListener {
            Log.i("SUCESSO", "SUCESSO")
        }.addOnFailureListener{
            Log.i("DEU RUIM", it.toString())
        }
    }

    private fun likeOrNot(user: Usuario, event: String) {
        val document = cardsInstance.document(user.id)

        firestoreInstance.runTransaction {transaction ->
            val snap = transaction.get(document)

            snap.getLong("curtidas")?.let { curtidas ->
                if (event == Evento.CURTIR.name)
                    transaction.update(document, "curtidas", curtidas+1)
                else
                    transaction.update(document, "curtidas", curtidas-1)
            }
        }.addOnSuccessListener {
            Log.i("TRANSACTION", "sucesso")
        }.addOnFailureListener {
            Log.e("TRANSACTION", it.toString())
        }
    }

    fun manageUser(user: Usuario, event: String) {
        when(event){
            Evento.CURTIR.name -> likeOrNot(user, event)
            Evento.DESCURTIR.name -> likeOrNot(user, event)
            //Evento.REMOVER.name -> "TEST"
        }
    }
}