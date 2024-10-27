package com.stormcode.everfood.firstMain.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.stormcode.everfood.ProductosFragment
import com.stormcode.everfood.firstMain.Menu
import com.stormcode.everfood.R

class MenuAdapter(private val menusList: MutableList<Menu>) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    class MenuViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val menuName: TextView = view.findViewById(R.id.menuName)
        //val productPrice: TextView = view.findViewById(R.id.tienda_id)
        val productobtn: Button = view.findViewById(R.id.productobtn)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menu = menusList[position]
        holder.menuName.text = menu.name
        holder.productobtn.setOnClickListener {
            val fragment = ProductosFragment.newInstance(menu.tienda_id)
            val fragmentManager = (holder.itemView.context as FragmentActivity).supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.navHomeFragment, fragment)
                .addToBackStack(null)
                .commit()
        }



    }

    override fun getItemCount() : Int {
        return menusList.size
    }
}
