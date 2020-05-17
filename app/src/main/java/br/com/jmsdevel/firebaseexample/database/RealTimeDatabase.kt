package br.com.jmsdevel.firebaseexample.database

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class RealTimeDatabase(private val query: Query): LiveData<DataSnapshot>() {

    private val listener = MyValueEventListener()

    override fun onActive() {
        query.addValueEventListener(listener)
    }

    override fun onInactive() {
        query.removeEventListener(listener)
    }

    inner class MyValueEventListener: ValueEventListener{

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            Log.i("OK", "OK")
            value = dataSnapshot
        }

        override fun onCancelled(error: DatabaseError) {
            Log.e("Deu ruim", "Deu ruim")
        }
    }

}