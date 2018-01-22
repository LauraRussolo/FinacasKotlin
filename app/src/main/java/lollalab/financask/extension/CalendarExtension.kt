package lollalab.financask.extension

import java.text.SimpleDateFormat
import java.util.Calendar

fun Calendar.formataParaBr() : String{
    val formatoDataBr = "dd/MM/yyyy"
    val dataFormat = SimpleDateFormat(formatoDataBr)
    return dataFormat.format(this.time)
}
