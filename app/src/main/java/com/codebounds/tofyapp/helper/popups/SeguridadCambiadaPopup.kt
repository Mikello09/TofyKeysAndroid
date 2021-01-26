package com.codebounds.tofyapp.helper.popups

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.codebounds.tofyapp.R
import com.codebounds.tofyapp.api.data.TipoSeguridad


interface SeguridadCambiadaPopupInterface{
    fun ok()
}

class SeguridadCambiadaPopup: DialogFragment() {

    private lateinit var seguridadCambiadaInterface: SeguridadCambiadaPopupInterface
    private lateinit var tipoSeguridad: TipoSeguridad

    private lateinit var titulo: TextView
    private lateinit var imagen: ImageView
    private lateinit var okBoton: Button

    companion object{
        fun newInstance(seguridadCambiadadPopupInterface: SeguridadCambiadaPopupInterface, tipoSeguridad: TipoSeguridad) = SeguridadCambiadaPopup().apply{
            this.seguridadCambiadaInterface = seguridadCambiadadPopupInterface
            this.tipoSeguridad = tipoSeguridad
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.seguridad_cambiada_popup, container, false)
        dialog?.setCanceledOnTouchOutside(false)

        titulo = view.findViewById(R.id.seguridad_cambiada_titulo)
        imagen = view.findViewById(R.id.seguridad_cambiada_imagen)
        okBoton = view.findViewById(R.id.seguridad_cambiada_boton_ok)

        okBoton.setOnClickListener { seguridadCambiadaInterface.ok() }


        when(tipoSeguridad){
            TipoSeguridad.ninguna -> {
                titulo.text = "Has cambiado la seguridad a Ninguna"
                imagen.visibility = View.GONE
            }
            TipoSeguridad.pin -> {
                titulo.text = "Código PIN activado"
                imagen.setImageDrawable(resources.getDrawable(R.drawable.ic_pin,null))
            }
            TipoSeguridad.biometria -> {
                titulo.text = "Biometría activada"
                imagen.setImageDrawable(resources.getDrawable(R.drawable.ic_finger, null))
            }
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ConstraintLayout.LayoutParams.WRAP_CONTENT)
    }
}