package com.stormcode.everfood

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stormcode.everfood.firstMain.adapters.Comida
import com.stormcode.everfood.firstMain.adapters.ComidaAdapter
import com.stormcode.everfood.listeners.OnItemClickListener

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PedidoFragment : Fragment(), OnItemClickListener {

    private var param1: String? = null
    private var param2: String? = null
    private var total = 0.00
    private lateinit var tvTotal: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pedido, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewComidas)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        tvTotal = view.findViewById(R.id.tvTotal)

        val comidaAdapter = ComidaAdapter(getComidaList(), this)
        recyclerView.adapter = comidaAdapter

        val backbutton: ImageButton = view.findViewById(R.id.back_button)

        backbutton.setOnClickListener {
            findNavController().navigate(R.id.action_pedidoFragment_to_homeFragment2)
        }



        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val checkBox1: CheckBox = view.findViewById(R.id.checkBoxDomicilio)
        val checkBox2: CheckBox = view.findViewById(R.id.checkBoxRecolecion)
        val editTextDomicilio: EditText = view.findViewById(R.id.inputDireccion)
        val editTextTelefono: EditText = view.findViewById(R.id.inputTelefono)
        val editTextNombreRecibe: EditText = view.findViewById(R.id.inputNombreRecibe)

        val layoutImage: LinearLayout = view.findViewById(R.id.linearImage)
        val layoutDomicilio: LinearLayout = view.findViewById(R.id.linearDomicilio)
        val layoutTotal: LinearLayout = view.findViewById(R.id.linearTotal)
        val layoutViewComidas: RecyclerView = view.findViewById(R.id.recyclerViewComidas)


        view.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            // Obtener el área visible del fragmento
            view.getWindowVisibleDisplayFrame(rect)
            // Altura total del fragmento
            val screenHeight = view.rootView.height
            // Altura ocupada por el teclado
            val keypadHeight = screenHeight - rect.bottom

            if (keypadHeight > screenHeight * 0.15) {
                layoutImage.visibility = View.GONE
                layoutDomicilio.visibility = View.GONE
                layoutTotal.visibility = View.GONE
                layoutViewComidas.visibility = View.GONE
                // Si la altura del teclado es mayor al 15% de la pantalla, el teclado está visible

            } else {
                layoutImage.visibility = View.VISIBLE
                layoutDomicilio.visibility = View.VISIBLE
                layoutTotal.visibility = View.VISIBLE
                layoutViewComidas.visibility = View.VISIBLE
                // El teclado está oculto

            }
        }





        checkBox1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {

                editTextDomicilio.visibility = View.VISIBLE
                editTextTelefono.visibility = View.VISIBLE
                editTextNombreRecibe.visibility = View.VISIBLE

                checkBox2.isChecked = false
            } else {

                editTextDomicilio.visibility = View.GONE
                editTextTelefono.visibility = View.GONE
                editTextNombreRecibe.visibility = View.GONE
            }
        }

        checkBox2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {

                editTextDomicilio.visibility = View.GONE
                editTextTelefono.visibility = View.GONE
                editTextNombreRecibe.visibility = View.GONE

                checkBox1.isChecked = false
            }
        }


        return view
    }


    override fun onPriceChange(priceDifference: Float) {
        total += priceDifference
        tvTotal.text = "$ $total"
    }

    // Simula una lista de comidas
    private fun getComidaList(): List<Comida> {
        return listOf(
            Comida("Pizza", 12.99f),
            Comida("Hamburguesa", 8.99f),
            Comida("Ensalada", 6.50f),
            Comida("suchi", 12.99f),
            Comida("tamarindo", 8.99f),
            Comida("pan", 6.50f),
            Comida("awebo", 12.99f),
            Comida("huevos revueltos", 8.99f),
            Comida("carne bistec a la plancha", 6.50f),
            Comida("hamburguesa de queso fino", 12.99f),
            Comida("chingaste", 8.99f),
            Comida("cocacola light", 6.50f),
            Comida("premio", 12.99f),
            Comida("quatro", 8.99f),
            Comida("Gey el que lo lea", 6.50f)
        )
    }



    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PedidoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}