package com.codebounds.tofyapp.claves

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codebounds.tofyapp.R
import com.codebounds.tofyapp.api.data.Clave
import kotlinx.android.synthetic.main.clave_item.view.*

class ClavesListaAdapter(val clickListener: (Clave) -> Unit) : RecyclerView.Adapter<ClavesListaAdapter.ClavesHolder>(){

    var lista: List<Clave> = ArrayList<Clave>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClavesHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.clave_item, parent, false)
        return ClavesHolder(inflatedView)
    }

    fun update(claves:List<Clave>){
        lista = claves
        notifyDataSetChanged()
    }

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: ClavesListaAdapter.ClavesHolder, position: Int) {
        val itemHistorial = lista[position]
        holder.bindHistorial(itemHistorial, clickListener)
    }

    class ClavesHolder(v: View) : RecyclerView.ViewHolder(v){
        private var view: View = v
        private var clave: Clave? = null

        fun bindHistorial(clave: Clave, clickListener: (Clave) -> Unit) {
            view.clave_item_titulo.text = clave.titulo
            if (clave.sincronizado)  view.clave_item_cloud.setBackgroundResource(R.drawable.oncloud) else view.clave_item_cloud.setBackgroundResource(R.drawable.offcloud)
            view.clave_layout.setOnClickListener { clickListener(clave) }
        }
    }

}
