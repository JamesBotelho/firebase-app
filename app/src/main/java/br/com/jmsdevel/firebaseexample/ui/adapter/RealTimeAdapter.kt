package br.com.jmsdevel.firebaseexample.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.jmsdevel.firebaseexample.databinding.ItemPessoaBinding
import br.com.jmsdevel.firebaseexample.model.Evento
import br.com.jmsdevel.firebaseexample.model.Usuario

class RealTimeAdapter(private val mUsuarios: List<Usuario>,
                      private val mContext: Context,
                      var disparaEvento: (evento: Evento) -> Unit = {}): RecyclerView.Adapter<RealTimeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val infla = LayoutInflater.from(mContext)
        val viewDataBinding = ItemPessoaBinding.inflate(infla, parent, false)

        return ViewHolder(viewDataBinding)
    }

    override fun getItemCount(): Int {
        return mUsuarios.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val usuario = mUsuarios[position]
        holder.vincula(usuario)
    }


    inner class ViewHolder(private val viewDataBinding: ItemPessoaBinding): RecyclerView.ViewHolder(viewDataBinding.root) {
        private lateinit var usuario: Usuario

        fun vincula(usuario: Usuario) {
            this.usuario = usuario
            viewDataBinding.usuario = usuario
        }
    }
}