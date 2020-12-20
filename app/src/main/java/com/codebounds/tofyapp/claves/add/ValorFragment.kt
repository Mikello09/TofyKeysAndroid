package com.codebounds.tofyapp.claves.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.codebounds.tofyapp.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ValorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
interface ValorFragmentInterface{
    fun onGuardarValorClicked(valor: String)
}

class ValorFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var valorInterface: ValorFragmentInterface
    private lateinit var valorEditText: EditText
    private lateinit var guardarBoton: Button



    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        *//*arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }*//*

    }*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        container?.removeAllViews()
        val view = inflater.inflate(R.layout.fragment_valor, container, false)

        valorEditText = view.findViewById(R.id.valor_editText)
        guardarBoton = view.findViewById(R.id.guardar_valor_boton)

        guardarBoton.setOnClickListener { valorInterface.onGuardarValorClicked(valorEditText.text.toString())}

        return view
    }

    companion object {
        fun newInstance(valorInterface: ValorFragmentInterface) = ValorFragment().apply {
            this.valorInterface = valorInterface
        }
    }
}