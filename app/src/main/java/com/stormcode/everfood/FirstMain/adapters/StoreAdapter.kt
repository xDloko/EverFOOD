package com.stormcode.everfood.FirstMain.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stormcode.everfood.FirstMain.Store
import com.stormcode.everfood.R


class StoresAdapter(private val storesList: MutableList<Store>) : RecyclerView.Adapter<StoresAdapter.StoreViewHolder>() {



    class StoreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val storeName: TextView = view.findViewById(R.id.storeName)
        val storeLocation: TextView = view.findViewById(R.id.storeLocation)
        val storeRating: TextView = view.findViewById(R.id.storeRating)
    }

    // Infla el layout item_store.xml para cada tienda
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tienda, parent, false)
        return StoreViewHolder(view)
    }

    // Enlaza los datos de la tienda con las vistas
    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val store = storesList[position]
        holder.storeName.text = store.tienda
        holder.storeLocation.text = store.direccion
        holder.storeRating.text = store.dueño
    }

    // Devuelve el número de tiendas en la lista
    override fun getItemCount(): Int {
        return storesList.size
    }
}