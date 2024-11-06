package com.stormcode.everfood

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stormcode.everfood.firstMain.CarritoViewModel
import com.stormcode.everfood.firstMain.ProductoViewModel
import com.stormcode.everfood.firstMain.adapters.Carrito
import com.stormcode.everfood.firstMain.adapters.CarritoItem
import com.stormcode.everfood.firstMain.adapters.CarritoViewModelFactory
import com.stormcode.everfood.firstMain.adapters.ProductAdapter
import com.stormcode.everfood.firstMain.adapters.ProductoViewModelFactory

class ProductosFragment : Fragment() {

    private lateinit var carritoViewModel: CarritoViewModel
    private lateinit var viewModel: ProductoViewModel
    private var menuId: String? = null
    private var storeId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            menuId = it.getString("menu_id")
            storeId = it.getString("store_id")
        }

        carritoViewModel = ViewModelProvider(requireActivity(), CarritoViewModelFactory(storeId!!)).get(CarritoViewModel::class.java)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_productos, container, false)

        val carrito = carritoViewModel.carrito
        viewModel = ViewModelProvider(this, ProductoViewModelFactory(carrito)).get(ProductoViewModel::class.java)

        val recyclerViewProductos: RecyclerView = root.findViewById(R.id.recyclerViewProductos)
        recyclerViewProductos.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewProductos.adapter = viewModel.productAdapter

        menuId?.let {
            viewModel.loadProducto(it)
        }

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(menuId: String, storeId: String) =
            ProductosFragment().apply {
                arguments = Bundle().apply {
                    putString("menu_id", menuId)
                    putString("store_id", storeId)
                }
            }
    }
}



