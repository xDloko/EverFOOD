package com.stormcode.everfood
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Comida(
    val nombre: String,
    val precio: Double
)

class ComidaAdapter(private val listaComida: List<Comida>) : RecyclerView.Adapter<ComidaAdapter.ComidaViewHolder>() {

    class ComidaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombreComida: TextView = itemView.findViewById(R.id.tvNombreComida)
        val tvPrecioComida: TextView = itemView.findViewById(R.id.tvPrecioComida)
        val tvContador: TextView = itemView.findViewById(R.id.tvContador)
        val btnIncrease: Button = itemView.findViewById(R.id.btnIncrease)
        val btnDecrease: Button = itemView.findViewById(R.id.btnDecrease)
    }

    private val contadores = MutableList(listaComida.size) { 0 }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComidaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comida, parent, false)
        return ComidaViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComidaViewHolder, position: Int) {
        val comida = listaComida[position]
        holder.tvNombreComida.text = comida.nombre
        holder.tvPrecioComida.text = comida.precio.toString()

        holder.tvContador.text = contadores[position].toString()

        holder.btnIncrease.setOnClickListener {
            contadores[position]++
            holder.tvContador.text = contadores[position].toString()
        }

        holder.btnDecrease.setOnClickListener {
            if (contadores[position] > 0) {
                contadores[position]--
                holder.tvContador.text = contadores[position].toString()
            }
        }
    }

    override fun getItemCount(): Int {
        return listaComida.size
    }
}