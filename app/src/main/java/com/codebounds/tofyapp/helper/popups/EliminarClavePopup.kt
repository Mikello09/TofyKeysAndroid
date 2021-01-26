package com.codebounds.tofyapp.helper.popups

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.codebounds.tofyapp.R
import com.codebounds.tofyapp.api.data.Clave


interface EliminarClavePopupInterface{
    fun si(clave: Clave)
}

class EliminarClavePopup:DialogFragment(){

    private lateinit var eliminarClaveInterface: EliminarClavePopupInterface
    private lateinit var clave: Clave

    private lateinit var titulo: TextView
    private lateinit var siEliminar: Button
    private lateinit var noEliminar: Button

    companion object{
        fun newInstance(eliminarClaveInterface: EliminarClavePopupInterface, clave: Clave) = EliminarClavePopup().apply{
            this.eliminarClaveInterface = eliminarClaveInterface
            this.clave = clave
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.eliminar_clave_popup, container, false)
        dialog?.setCanceledOnTouchOutside(true)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        titulo = view.findViewById(R.id.eliminarClaveTituloLabel)
        siEliminar = view.findViewById(R.id.eliminarClaveSi)
        noEliminar = view.findViewById(R.id.eliminarClaveNo)

        titulo.text = "¿Estás seguro de eliminar la clave " + this.clave.titulo + "?"


        siEliminar.setOnClickListener {
            eliminarClaveInterface.si(this.clave)
            dismiss()
        }

        noEliminar.setOnClickListener {
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