package ru.egordubina.hotels.utils

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.chip.Chip
import ru.egordubina.hotels.R

fun addChipToPeculiarities(context: Context, feature: String): Chip {
    val chip = Chip(context)
    chip.text = feature
    chip.isEnabled = false
    chip.setEnsureMinTouchTargetSize(false)
    chip.typeface = Typeface.create(
        ResourcesCompat.getFont(context, R.font.sf_pro_display_medium),
        Typeface.NORMAL
    )
    chip.setTextColor(Color.parseColor("#FF828796"))
    chip.chipStrokeWidth = 0f
//    chip.styl = context.resources.getColor(R.color.blue, context.theme)
    return chip
}