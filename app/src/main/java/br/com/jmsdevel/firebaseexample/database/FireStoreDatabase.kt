package br.com.jmsdevel.firebaseexample.database

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.*

class FireStoreDatabase(private val query: Query): LiveData<QuerySnapshot?>() {

    private val listener = MyListener()

    private lateinit var listenerRegistration: ListenerRegistration

    override fun onActive() {
        listenerRegistration = query.addSnapshotListener(listener)
    }

    override fun onInactive() {
        if(this::listenerRegistration.isInitialized)
            listenerRegistration.remove()
    }


    inner class MyListener: EventListener<QuerySnapshot> {
        override fun onEvent(querySnapShot: QuerySnapshot?, exception: FirebaseFirestoreException?) {
            querySnapShot?.let { value = it }
        }

    }
}