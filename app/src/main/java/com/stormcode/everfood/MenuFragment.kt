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
    private var storeId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            storeId = it.getString("tienda_id")
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


        storeId?.let { viewModel.loadMenu(storeId!!) }

        return root
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
