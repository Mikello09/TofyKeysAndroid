package com.codebounds.tofyapp.helper.popups

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.codebounds.tofyapp.R

interface CerrarSesionInterface{
    fun onCerrarSesion()
}

class CerrarSesionPopup: DialogFragment(){

    private lateinit var cerrarSesionInterface: CerrarSesionInterface

    private lateinit var closeBoton: ImageView
    private lateinit var cerrarSesionBoton: Button

    companion object {
        fun newInstance(cerrarSesionInterface: CerrarSesionInterface) = CerrarSesionPopup().apply{
            this.cerrarSesionInterface = cerrarSesionInterface
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.cerrar_sesion_popup, container, false)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        closeBoton = view.findViewById(R.id.close_cerrar_sesion)
        cerrarSesionBoton = view.findViewById(R.id.cerrar_sesion_boton)

        closeBoton.setOnClickListener { dismiss() }
        cerrarSesionBoton.setOnClickListener {
            cerrarSesionInterface.onCerrarSesion()
            dismiss()
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