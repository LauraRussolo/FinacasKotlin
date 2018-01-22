package lollalab.financask.extension

import java.text.SimpleDateFormat
import java.util.*

fun String.limitaemAte(caracteres: Int): String {
    if(this.length > caracteres){
        return "${this.substring(0, caracteres)}..."
    }
    return  this
}

fun String.converteParaCalendar(): Calendar {
    val formatoBr = SimpleDateFormat("dd/MM/yyyy")
    val dataConvertida = formatoBr.parse(this)
    val data = Calendar.getInstance()
    data.time = dataConvertida
    return data
}

