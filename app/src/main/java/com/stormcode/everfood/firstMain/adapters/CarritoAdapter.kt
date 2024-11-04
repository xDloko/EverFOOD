package com.stormcode.everfood.firstMain.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stormcode.everfood.R


class CarritoAdapter(private val items: List<CarritoItem>) : RecyclerView.Adapter<CarritoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView = view.findViewById(R.id.productoName)
        val itemQuantity: TextView = view.findViewById(R.id.quiantityProduct)
        val itemPrice: TextView = view.findViewById(R.id.precioProduct)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_carrito, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.itemName.text = item.name
        holder.itemQuantity.text = item.quantity.toString()
        holder.itemPrice.text = item.price.toString()

    }

    override fun getItemCount() = items.size
}