package com.stormcode.everfood

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stormcode.everfood.firstMain.MenuViewModel
import com.stormcode.everfood.firstMain.CarritoViewModel
import com.stormcode.everfood.firstMain.adapters.Carrito
import com.stormcode.everfood.firstMain.adapters.CarritoAdapter
import com.stormcode.everfood.firstMain.adapters.CarritoViewModelFactory

class MenuFragment : Fragment() {

    private lateinit var viewModel: MenuViewModel
    private lateinit var carritoViewModel: CarritoViewModel
    private var storeId: String? = null
    private var menusCargados = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            storeId = it.getString("tienda_id")
        }

        carritoViewModel = ViewModelProvider(requireActivity(), CarritoViewModelFactory(storeId!!)).get(CarritoViewModel::class.java)

        Log.d("MenuFragment", "CarritoViewModel inicializado para la tienda ID: $storeId")

        val items = carritoViewModel.carrito.obtenerItems()
        Log.d("CarritoMenu1", "Items en el carrito: ${items.size}")



    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_menu, container, false)

        viewModel = ViewModelProvider(this).get(MenuViewModel::class.java)

        val logoStore: ImageView = root.findViewById(R.id.logo_store)

        root.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            root.getWindowVisibleDisplayFrame(rect)
            val screenHeight = root.rootView.height
            val keypadHeight = screenHeight - rect.bottom

            logoStore.visibility = if (keypadHeight > screenHeight * 0.15) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }

        val recyclerViewMenus: RecyclerView = root.findViewById(R.id.recyclerViewMenus)
        recyclerViewMenus.layoutManager = LinearLayoutManager(context)
        recyclerViewMenus.adapter = viewModel.MenuAdapter

        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        recyclerViewMenus.addItemDecoration(dividerItemDecoration)




        Log.d("MenuFragment", "ID de la tienda seleccionada: $storeId")

        if (!menusCargados) {
            storeId?.let { viewModel.loadMenu(storeId!!) }
            menusCargados = true
        }

        storeId?.let {
            carritoViewModel.seleccionarTienda(it)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val carritoButton: Button = view.findViewById(R.id.carritoButton)
        carritoButton.setOnClickListener {

            val carritoFragment = CarritoFragment.newInstance(storeId!!)
            findNavController().navigate(R.id.action_menuFragment_to_carritofragment, carritoFragment.arguments)
        }

        val items = carritoViewModel.carrito.obtenerItems()
        Log.d("CarritoMenu", "Items en el carrito al hacer clic en el botón: ${items.size}")
        items.forEach { producto ->
            Log.d("CarritoMenu", "Producto en carrito: ${producto.name}, Cantidad: ${producto.quantity}")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(idStore: String) =
            MenuFragment().apply {
                arguments = Bundle().apply {
                    putString("tienda_id", idStore)
                }
            }
    }
}
