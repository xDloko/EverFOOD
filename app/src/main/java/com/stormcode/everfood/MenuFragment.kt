package com.stormcode.everfood

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stormcode.everfood.firstMain.MenuViewModel

class MenuFragment : Fragment() {

    private lateinit var viewModel: MenuViewModel
    private var storeId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            storeId = it.getInt("tienda_id")
        }
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

            if (keypadHeight > screenHeight * 0.15) {
                logoStore.visibility = View.GONE

            } else {
                logoStore.visibility = View.VISIBLE

            }
        }

        val recyclerViewMenus: RecyclerView = root.findViewById(R.id.recyclerViewMenus)
        recyclerViewMenus.layoutManager = LinearLayoutManager(context)
        recyclerViewMenus.adapter = viewModel.MenuAdapter

        // Cargar el menú con el ID de la tienda
        storeId?.let { viewModel.loadMenu(it, 0) }

        recyclerViewMenus.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                    loadMoreMenus()
                }
            }
        })

        return root
    }

    private var isLoading = false
    private var offset = 0
    private val limit = 50

    private fun loadMoreMenus() {
        if (!isLoading) {
            isLoading = true
            offset += limit
            storeId?.let { viewModel.loadMenu(it, offset) }
            isLoading = false
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(idStore: Int) =
            MenuFragment().apply {
                arguments = Bundle().apply {
                    putInt("tienda_id", idStore)
                }
            }
    }
}
