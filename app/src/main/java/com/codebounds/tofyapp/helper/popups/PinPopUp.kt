package com.codebounds.tofyapp.helper.popups

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.codebounds.tofyapp.api.data.Usuario
import com.codebounds.tofyapp.R


interface PinPopUpInterface{
    fun pinCorrecto()
}

class PinPopUp: DialogFragment(){

    private lateinit var closeButton: ImageView
    private lateinit var pin: EditText
    private lateinit var errorText: TextView
    private lateinit var pinInterface: PinPopUpInterface

    private lateinit var uno: CardView
    private lateinit var dos: CardView
    private lateinit var tres: CardView
    private lateinit var cuatro: CardView
    private lateinit var cinco: CardView
    private lateinit var seis: CardView
    private lateinit var siete: CardView
    private lateinit var ocho: CardView
    private lateinit var nueve: CardView
    private lateinit var cero: CardView

    companion object {
        fun newInstance(pinInterface: PinPopUpInterface) = PinPopUp().apply {
            this.pinInterface = pinInterface
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.pin_popup_layout, container, false)
        dialog?.setCanceledOnTouchOutside(false)

        closeButton = view.findViewById(R.id.close_pin_popup_button)
        pin = view.findViewById(R.id.pin_popup_pin)
        errorText = view.findViewById(R.id.pin_popup_error)
        uno = view.findViewById(R.id.pin_popup_1)
        dos = view.findViewById(R.id.pin_popup_2)
        tres = view.findViewById(R.id.pin_popup_3)
        cuatro = view.findViewById(R.id.pin_popup_4)
        cinco = view.findViewById(R.id.pin_popup_5)
        seis = view.findViewById(R.id.pin_popup_6)
        siete = view.findViewById(R.id.pin_popup_7)
        ocho = view.findViewById(R.id.pin_popup_8)
        nueve = view.findViewById(R.id.pin_popup_9)
        cero = view.findViewById(R.id.pin_popup_0)

        closeButton.setOnClickListener(View.OnClickListener { dismiss() })

        setPinField()
        setTeclado()

        return view
    }

    fun setPinField(){
        //pin.requestFocus()
        val imm: InputMethodManager = pin.getContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        pin.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if(p0?.length == 4){
                    comprobarPin(p0?.toString())
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                errorText.visibility = View.INVISIBLE
            }
        })
    }

    fun setTeclado(){
        uno.setOnClickListener {
            pin.setText(pin.text.toString() + "1")
        }
        dos.setOnClickListener {
            pin.setText(pin.text.toString() + "2")
        }
        tres.setOnClickListener {
            pin.setText(pin.text.toString() + "3")
        }
        cuatro.setOnClickListener {
            pin.setText(pin.text.toString() + "4")
        }
        cinco.setOnClickListener {
            pin.setText(pin.text.toString() + "5")
        }
        seis.setOnClickListener {
            pin.setText(pin.text.toString() + "6")
        }
        siete.setOnClickListener {
            pin.setText(pin.text.toString() + "7")
        }
        ocho.setOnClickListener {
            pin.setText(pin.text.toString() + "8")
        }
        nueve.setOnClickListener {
            pin.setText(pin.text.toString() + "9")
        }
        cero.setOnClickListener {
            pin.setText(pin.text.toString() + "0")
        }
    }

    fun comprobarPin(pin: String){
        if(pin == Usuario.shared.pin){
            pinInterface.pinCorrecto()
            dismiss()
        } else {
            this.pin.setText("")
            errorText.visibility = View.VISIBLE
        }
    }



    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ConstraintLayout.LayoutParams.WRAP_CONTENT)
    }

    override fun onDismiss(dialog: DialogInterface) {
        val imm = pin.getContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive)
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
        super.onDismiss(dialog)
    }
}