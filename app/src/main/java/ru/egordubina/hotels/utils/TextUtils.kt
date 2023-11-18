package ru.egordubina.hotels.utils

import android.content.Context
import ru.egordubina.hotels.R

fun getStringNumberOfNights(
    context: Context,
    numbersOfNight: Int
): String {
    val valueOfNight =
        if (numbersOfNight < 10) numbersOfNight.digitToChar() else (numbersOfNight % 10).digitToChar()
    return when (valueOfNight.digitToInt()) {
        1 -> context.getString(R.string.numbers_of_night_default, numbersOfNight)
        in 2..4 -> context.getString(R.string.numbers_of_night_plural_1, numbersOfNight)
        in 4..9, 0 -> context.getString(R.string.numbers_of_night_plural_2, numbersOfNight)
        else -> context.getString(R.string.numbers_of_night_default, numbersOfNight)
    }
}