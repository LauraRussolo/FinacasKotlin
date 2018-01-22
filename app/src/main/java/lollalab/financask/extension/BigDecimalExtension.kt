package lollalab.financask.extension

import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.Locale

fun BigDecimal.formataParaBr(): String {
    val formatadorBrasileiro = DecimalFormat
            .getCurrencyInstance(Locale("pt", "BR"))

    return formatadorBrasileiro
            .format(this)
            .replace("R$", "R$ ")
            .replace("-R$ ", "R$ -")

}
