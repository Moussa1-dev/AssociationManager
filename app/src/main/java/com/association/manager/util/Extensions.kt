package com.association.manager.util

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toFormattedDate(): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)
    return sdf.format(Date(this))
}

fun Long.toFormattedDateTime(): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRANCE)
    return sdf.format(Date(this))
}

fun Double.toFormattedCurrency(): String {
    val format = NumberFormat.getCurrencyInstance(Locale.FRANCE)
    return format.format(this)
}

fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}
