package com.codebounds.tofyapp.helper

import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.DialogFragment
import kotlin.random.Random

fun DialogFragment.generadorDeToken(): String {
    val randomNumber: Int = Random.nextInt(1000000000)
    return randomNumber.toString()
}

fun String.isValidEmail() = !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()
