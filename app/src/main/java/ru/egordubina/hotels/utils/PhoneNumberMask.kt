package ru.egordubina.hotels.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class PhoneNumberMask(private val editText: EditText) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(s: Editable?) {
        val digits = editText.text.toString().replace("""\+7|\D""".toRegex(), "")
        val formattedNumber = buildString {
            append("+7 (")
            for (i in digits.indices) {
                append(digits[i])
                when (i) {
                    2 -> append(") ")
                    5, 7 -> append("-")
                }
            }
        }
        editText.removeTextChangedListener(this)
        editText.setText(formattedNumber)
        editText.setSelection(formattedNumber.length)
        editText.addTextChangedListener(this)
    }
}