package com.stormcode.everfood

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
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
import com.stormcode.everfood.firstMain.StoreViewModel



private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {

    @Suppress("UNRESOLVED_REFERENCE")
    private lateinit var viewModel: StoreViewModel
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        viewModel = ViewModelProvider(this).get(StoreViewModel::class.java)

        val btnNavigate: Button = root.findViewById(R.id.pedido_button)
        btnNavigate.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_pedidoFragment)
        }

        val logoStore: ImageView = root.findViewById(R.id.logo_store)

        root.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            root.getWindowVisibleDisplayFrame(rect)
            val screenHeight = root.rootView.height
            val keypadHeight = screenHeight - rect.bottom

            if (keypadHeight > screenHeight * 0.15) {
                logoStore.visibility = View.GONE
                btnNavigate.visibility = View.GONE
            } else {
                logoStore.visibility = View.VISIBLE
                btnNavigate.visibility = View.VISIBLE
            }
        }

        val recyclerViewComidas: RecyclerView = root.findViewById(R.id.recyclerViewComidas)
        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        recyclerViewComidas.addItemDecoration(dividerItemDecoration)






        recyclerViewComidas.layoutManager = LinearLayoutManager(context)
        recyclerViewComidas.adapter = viewModel.storesAdapter

        viewModel.loadStores(50, 0)

        recyclerViewComidas.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                    loadMoreStores()
                }
            }
        })

        return root
    }


    private var isLoading = false
    private var offset = 0
    private val limit = 50

    private fun loadMoreStores() {
        if (!isLoading) {
            isLoading = true
            offset += limit
            viewModel.loadStores(limit, offset)
            isLoading = false
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
