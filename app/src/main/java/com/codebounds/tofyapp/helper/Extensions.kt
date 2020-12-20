package com.codebounds.tofyapp.helper

import androidx.fragment.app.DialogFragment
import kotlin.random.Random

fun DialogFragment.generadorDeToken(): String {
    val randomNumber: Int = Random.nextInt(1000000000)
    return randomNumber.toString()
}