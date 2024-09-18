package com.stormcode.everfood
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stormcode.everfood.FirstMain.OnItemClickListener

data class Comida(
    val nombre: String,
    val precio: Float
)

class ComidaAdapter(
    private val comidaList: List<Comida>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<ComidaAdapter.ComidaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComidaViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comida, parent, false)
        return ComidaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ComidaViewHolder, position: Int) {
        val comida = comidaList[position]
        holder.bind(comida, listener)
    }

    override fun getItemCount() = comidaList.size

    class ComidaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNombreComida: TextView = itemView.findViewById(R.id.tvNombreComida)
        private val tvPrecioComida: TextView = itemView.findViewById(R.id.tvPrecioComida)
        private val tvContador: TextView = itemView.findViewById(R.id.tvContador)
        private val btnIncrease: Button = itemView.findViewById(R.id.btnIncrease)
        private val btnDecrease: Button = itemView.findViewById(R.id.btnDecrease)
        private var cantidad = 0

        fun bind(comida: Comida, listener: OnItemClickListener) {
            tvNombreComida.text = comida.nombre
            tvPrecioComida.text = "$ ${comida.precio}"

            btnIncrease.setOnClickListener {
                cantidad++
                tvContador.text = cantidad.toString()
                listener.onPriceChange(comida.precio)
            }

            btnDecrease.setOnClickListener {
                if (cantidad > 0) {
                    cantidad--
                    tvContador.text = cantidad.toString()
                    listener.onPriceChange(-comida.precio)
                }
            }
        }
    }
}