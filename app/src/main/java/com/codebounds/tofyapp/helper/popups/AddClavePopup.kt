package com.codebounds.tofyapp.helper.popups

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.codebounds.tofyapp.R
import com.codebounds.tofyapp.claves.add.UsuarioPassFragment
import com.codebounds.tofyapp.claves.add.UsuarioPassFragmentInterface
import com.codebounds.tofyapp.claves.add.ValorFragment
import com.codebounds.tofyapp.claves.add.ValorFragmentInterface
import com.codebounds.tofyapp.helper.generadorDeToken

enum class TipoClave{
    valor,
    usuarioPass
}

interface AddClavePopupInterface{
    fun onClaveGuardado(token: String, tipo: TipoClave, titulo: String, valor: String, usuario: String, pass: String)
}

class AddClavePopup: DialogFragment(), ValorFragmentInterface, UsuarioPassFragmentInterface{

    private lateinit var titulo: EditText
    private lateinit var valorBoton: ConstraintLayout
    private lateinit var userPassBoton: ConstraintLayout
    private lateinit var errorText: TextView

    private var tipoClave: TipoClave = TipoClave.valor
    private lateinit var addClaveInterface: AddClavePopupInterface

    companion object{
        fun newInstance(addClaveInterface: AddClavePopupInterface) = AddClavePopup().apply{
            this.addClaveInterface = addClaveInterface
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.anadir_clave_popup, container, false)
        dialog?.setCanceledOnTouchOutside(true)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        titulo = view.findViewById(R.id.add_clave_popup_titulo)
        valorBoton = view.findViewById(R.id.anadirClavesValorBoton)
        userPassBoton = view.findViewById(R.id.anadirClavesUserPassBoton)
        errorText = view.findViewById(R.id.add_clave_error)

        var valorFragment = ValorFragment.newInstance(this)
        var usuarioPassFragment = UsuarioPassFragment.newInstance(this)
        childFragmentManager.beginTransaction().add(R.id.anadir_claves_fragment, valorFragment).commit()

        valorBoton.setOnClickListener {
            childFragmentManager.beginTransaction().replace(R.id.anadir_claves_fragment, valorFragment).commit()
            tipoClave = TipoClave.valor
            valorBoton.setBackgroundResource(R.drawable.opcion_tipo_seleccionado)
            userPassBoton.setBackgroundResource(0)
        }

        userPassBoton.setOnClickListener {
            childFragmentManager.beginTransaction().replace(R.id.anadir_claves_fragment, usuarioPassFragment).commit()
            tipoClave = TipoClave.usuarioPass
            valorBoton.setBackgroundResource(0)
            userPassBoton.setBackgroundResource(R.drawable.opcion_tipo_seleccionado)
        }

        return view
    }

    fun camposRellenados(valor: String, usuario: String, pass: String) : Boolean{
        when(tipoClave){
            TipoClave.valor -> {
                return !titulo.text.toString().isNullOrEmpty() && !valor.isNullOrEmpty()
            }
            TipoClave.usuarioPass -> {
                return !titulo.text.toString().isNullOrEmpty() && !usuario.isNullOrEmpty() && !pass.isNullOrEmpty()
            }
        }
    }

    fun showError(){
        errorText.visibility = View.VISIBLE
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ConstraintLayout.LayoutParams.WRAP_CONTENT)
    }

    override fun onGuardarValorClicked(valor: String) {
        if (camposRellenados(valor, "","")){
            addClaveInterface.onClaveGuardado(
                generadorDeToken(),
                tipoClave,
                titulo.text.toString().trim(),
                valor.trim(),
                "",
                "")
            dismiss()
        } else {
            showError()
        }
    }

    override fun onGuardarUsuarioPassClicked(usuario: String, pass: String) {
        if (camposRellenados("",usuario,pass)){
            addClaveInterface.onClaveGuardado(
                generadorDeToken(),
                tipoClave,
                titulo.text.toString().trim(),
                "",
                usuario.trim(),
                pass.trim())
            dismiss()
        } else {
            showError()
        }
    }
}