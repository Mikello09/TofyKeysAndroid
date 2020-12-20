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
 * Use the [UsuarioPassFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

interface UsuarioPassFragmentInterface{
    fun onGuardarUsuarioPassClicked(usuario: String, pass: String)
}

class UsuarioPassFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var usuarioPassInterface: UsuarioPassFragmentInterface
    private lateinit var emailEditText: EditText
    private lateinit var passEditText: EditText
    private lateinit var guardarBoton: Button

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        container?.removeAllViews()
        var view = inflater.inflate(R.layout.fragment_usuario_pass, container, false)

        emailEditText = view.findViewById(R.id.email_clave_editText)
        passEditText = view.findViewById(R.id.pass_calve_edittext)
        guardarBoton = view.findViewById(R.id.guardar_usuario_pass_boton)

        guardarBoton.setOnClickListener { usuarioPassInterface.onGuardarUsuarioPassClicked(emailEditText.text.toString(), passEditText.text.toString()) }

        return view
    }

    companion object {
        fun newInstance(usuarioPassInterface: UsuarioPassFragmentInterface) = UsuarioPassFragment().apply {
            this.usuarioPassInterface = usuarioPassInterface
        }
    }
}