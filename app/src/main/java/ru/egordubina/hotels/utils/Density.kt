package ru.egordubina.hotels.utils

import android.content.Context
import android.util.TypedValue

/**
 * Функиця перреводит число dp в px
 * @param dp колличество DP
 * */
fun Context.toPx(dp: Int): Float = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    dp.toFloat(),
    resources.displayMetrics
)