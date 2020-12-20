package com.codebounds.tofyapp.helper.popups

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.codebounds.tofyapp.R
import com.codebounds.tofyapp.api.data.Clave
import com.google.android.material.textfield.TextInputLayout


interface ClaveDetallePopupInterface{
    fun editarClave(clave: Clave)
    fun eliminarClave(clave: Clave)
}

class ClaveDetallePopup: DialogFragment(){

    private lateinit var claveDetalleInterface: ClaveDetallePopupInterface
    private lateinit var clave: Clave

    private lateinit var titulo: TextView
    private lateinit var tituloEdit: EditText
    private lateinit var tituloEditLayout: TextInputLayout
    private lateinit var valor: TextView
    private lateinit var valorLayout: ConstraintLayout
    private lateinit var valorEdit: EditText
    private lateinit var valorEditLayout: TextInputLayout
    private lateinit var usuario: TextView
    private lateinit var usuarioLayout: ConstraintLayout
    private lateinit var usuarioEdit: EditText
    private lateinit var usuarioEditLayout: TextInputLayout
    private lateinit var usuarioSeparador: LinearLayout
    private lateinit var contrasena: TextView
    private lateinit var contrasenaLayout: ConstraintLayout
    private lateinit var contrasenaEdit: EditText
    private lateinit var contrasenaEditLayout: TextInputLayout

    private lateinit var editar_clave: LinearLayout
    private lateinit var eliminar_clave: LinearLayout
    private lateinit var editar_eliminar_layout: LinearLayout
    private lateinit var guardar_layout: LinearLayout

    companion object{
        fun newInstance(clave: Clave, claveDetalleInterface: ClaveDetallePopupInterface) = ClaveDetallePopup().apply {
            this.claveDetalleInterface = claveDetalleInterface
            this.clave = clave
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.detalle_clave_popup, container, false)
        dialog?.setCanceledOnTouchOutside(true)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        titulo = view.findViewById(R.id.clave_detalle_titulo)
        tituloEdit = view.findViewById(R.id.clave_detalle_titulo_editar)
        tituloEditLayout = view.findViewById(R.id.clave_detalle_titulo_editar_layout)
        valor = view.findViewById(R.id.clave_detalle_valor)
        valorLayout = view.findViewById(R.id.clave_detalle_valor_layout)
        valorEdit = view.findViewById(R.id.clave_detalle_valor_editar)
        valorEditLayout = view.findViewById(R.id.clave_detalle_valor_editar_layout)
        usuario = view.findViewById(R.id.clave_detalle_usuario)
        usuarioLayout = view.findViewById(R.id.clave_detalle_usuario_layout)
        usuarioEdit = view.findViewById(R.id.clave_detalle_usuario_editar)
        usuarioEditLayout = view.findViewById(R.id.clave_detalle_usuario_editar_layout)
        usuarioSeparador = view.findViewById(R.id.detalle_popup_usuario_separador)
        contrasena = view.findViewById(R.id.clave_detallet_contrasena)
        contrasenaLayout = view.findViewById(R.id.clave_detalle_contrasena_layout)
        contrasenaEdit = view.findViewById(R.id.clave_detalle_contrasena_editar)
        contrasenaEditLayout = view.findViewById(R.id.clave_detalle_contrasena_editar_layout)
        editar_eliminar_layout = view.findViewById(R.id.editar_eliminar_layout)
        guardar_layout = view.findViewById(R.id.guardar_clave_layout)
        editar_clave = view.findViewById(R.id.editar_clave)
        eliminar_clave = view.findViewById(R.id.eliminar_clave)


        editar_clave.setOnClickListener {
            setEditLayout()
        }

        eliminar_clave.setOnClickListener {
            claveDetalleInterface.eliminarClave(clave)
            dismiss()
        }

        guardar_layout.setOnClickListener {
            var nuevaClave = clave
            nuevaClave.titulo = tituloEdit.text.toString()
            if(clave.valor.isNullOrEmpty()){//USUARIO_PASS
                nuevaClave.usuario = usuarioEdit.text.toString()
                nuevaClave.contrasena = contrasenaEdit.text.toString()
            } else {//VALOR
                nuevaClave.valor = valorEdit.text.toString()
            }
            nuevaClave.sincronizado = false
            claveDetalleInterface.editarClave(nuevaClave)
            dismiss()
        }

        setUpDetalle()

        return view
    }

    fun setUpDetalle(){
        titulo.text = clave.titulo
        if(clave.valor.isNullOrEmpty()){//USUARIO_PASS
            valorLayout.visibility = View.INVISIBLE
            valorLayout.layoutParams.height = 0
            usuario.text = clave.usuario
            contrasena.text = clave.contrasena
        } else {//VALOR
            usuarioLayout.visibility = View.GONE
            contrasenaLayout.visibility = View.GONE
            valor.text = clave.valor
        }
    }

    fun setEditLayout(){
        editar_eliminar_layout.visibility = View.GONE
        guardar_layout.visibility = View.VISIBLE
        titulo.visibility = View.GONE
        tituloEditLayout.visibility = View.VISIBLE
        tituloEdit.setText(titulo.text)
        if(clave.valor.isNullOrEmpty()){//USUARIO_PASS
            usuario.visibility = View.GONE
            usuarioSeparador.visibility = View.GONE
            usuarioEditLayout.visibility = View.VISIBLE
            usuarioEdit.setText(usuario.text)
            contrasena.visibility = View.GONE
            contrasenaEditLayout.visibility = View.VISIBLE
            contrasenaEdit.setText(contrasena.text)
        } else {//VALOR
            valor.visibility = View.GONE
            valorEditLayout.visibility = View.VISIBLE
            valorEdit.setText(valor.text)
        }
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ConstraintLayout.LayoutParams.WRAP_CONTENT)
    }

}