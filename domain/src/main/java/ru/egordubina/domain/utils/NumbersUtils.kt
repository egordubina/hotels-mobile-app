package ru.egordubina.domain.utils

/**
 * Возвращает строку, приведённую к формату стоиости с валютным знаком
 * */
fun Int.toRubInt(): String {
    val result = StringBuilder()
    val reversNumber = this.toString().reversed()
    reversNumber.forEachIndexed { index, char ->
        result.append(char)
        if ((index + 1) % 3 == 0 && (index + 1) != reversNumber.length )
            result.append(" ")
    }
    result.reverse()
    result.append(" ₽")
    return result.toString()
}