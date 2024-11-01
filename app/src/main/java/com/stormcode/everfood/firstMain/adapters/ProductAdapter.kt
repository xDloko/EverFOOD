package com.stormcode.everfood.firstMain.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stormcode.everfood.firstMain.Producto
import com.stormcode.everfood.R

class ProductAdapter(private val productosList: MutableList<Producto>) : RecyclerView.Adapter<ProductAdapter.ProductoViewHolder>() {

    class ProductoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productoName: TextView = view.findViewById(R.id.productoName)
        //val productPrice: TextView = view.findViewById(R.id.tienda_id)
        val productoDescrt: TextView = view.findViewById(R.id.descripcionProduct)
        val precioProduct: TextView = view.findViewById(R.id.precioProduct)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productosList[position]
        holder.productoName.text = producto.name
        holder.productoDescrt.text = producto.descripcion
        holder.precioProduct.text = producto.precio


    }

    override fun getItemCount() : Int {
        return productosList.size
    }
}
