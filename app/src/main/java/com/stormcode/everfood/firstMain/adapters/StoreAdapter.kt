package com.stormcode.everfood.firstMain.adapters

import android.os.Bundle
import android.provider.Settings.Global.putString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.stormcode.everfood.MenuFragment
import com.stormcode.everfood.firstMain.Store
import com.stormcode.everfood.R

class StoresAdapter(private val storesList: MutableList<Store>) : RecyclerView.Adapter<StoresAdapter.StoreViewHolder>() {

    class StoreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val menubtn: Button = view.findViewById(R.id.menubtn)
        val storeName: TextView = view.findViewById(R.id.storeName)
        val storeLocation: TextView = view.findViewById(R.id.storeLocation)
        val storeRating: TextView = view.findViewById(R.id.storeRating)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tienda, parent, false)
        return StoreViewHolder(view)
    }


    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val store = storesList[position]
        holder.storeName.text = store.name
        holder.storeLocation.text = store.direccion
        holder.storeRating.text = store.rating
        holder.menubtn.setOnClickListener {
            val navController = (holder.itemView.context as FragmentActivity).findNavController(R.id.navHomeFragment)
            navController.navigate(R.id.action_homeFragment_to_menuFragment, Bundle().apply {
                putString("tienda_id", store._id)
            })
        }
    }


    override fun getItemCount(): Int {
        return storesList.size
    }
}
