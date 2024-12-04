package com.stormcode.everfood.firstMain.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stormcode.everfood.R
import com.stormcode.everfood.firstMain.Pedido

class PedidoAdapter(private val pedidos: List<Pedido>) : RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pedidos, parent, false)
        return PedidoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        val pedido = pedidos[position]
        holder.bind(pedido)
    }

    override fun getItemCount(): Int {
        return pedidos.size
    }

    class PedidoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tiendaName: TextView = itemView.findViewById(R.id.tiendaName)
        private val total: TextView = itemView.findViewById(R.id.total)
        private val estado: TextView = itemView.findViewById(R.id.estado)

        fun bind(pedido: Pedido) {
            tiendaName.text = pedido.tienda_id.name
            total.text = "Total: $${pedido.total}"
            estado.text = "Estado: ${pedido.estado}"
        }
    }
}
