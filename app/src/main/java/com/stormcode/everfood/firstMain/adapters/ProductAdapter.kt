package com.stormcode.everfood.firstMain.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.stormcode.everfood.firstMain.Producto
import com.stormcode.everfood.R

class ProductAdapter(
    private val carrito: Carrito,
    private val productosList: MutableList<Producto>,
    private val onAddToCart: (Producto, Int) -> Unit // Callback para agregar al carrito
) : RecyclerView.Adapter<ProductAdapter.ProductoViewHolder>() {

    class ProductoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productoName: TextView = view.findViewById(R.id.productoName)
        val productoDescrt: TextView = view.findViewById(R.id.descripcionProduct)
        val precioProduct: TextView = view.findViewById(R.id.precioProduct)
        val tvContador: TextView = view.findViewById(R.id.tvContador)
        val btnIncrease: Button = view.findViewById(R.id.btnIncrease)
        val btnDecrease: Button = view.findViewById(R.id.btnDecrease)
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

        var cantidad = 1
        holder.tvContador.text = cantidad.toString()

        holder.btnIncrease.setOnClickListener {
            cantidad++
            holder.tvContador.text = cantidad.toString()
        }

        holder.btnDecrease.setOnClickListener {
            if (cantidad > 1) {
                cantidad--
                holder.tvContador.text = cantidad.toString()
            }
        }

        holder.itemView.findViewById<Button>(R.id.btnAgregar).setOnClickListener {
            val carritoItem = CarritoItem(
                productId = producto._id,
                name = producto.name,
                price = producto.precio.toDouble(),
                quantity = cantidad
            )
            onAddToCart(producto, cantidad)
            Toast.makeText(holder.itemView.context, "${producto.name} agregado al carrito", Toast.LENGTH_SHORT).show()
        }
    }


    override fun getItemCount(): Int {
        return productosList.size
    }
}
