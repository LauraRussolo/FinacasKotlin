package lollalab.financask.model

import java.math.BigDecimal

class Resumo(private val transacoes: List<Transacao>) {

    val receita get() = somaPorTipo(Tipo.RECEITA)

    val despesa get() = somaPorTipo(Tipo.DESPESA)

    val total get() = receita - despesa

    fun somaPorTipo(tipo: Tipo): BigDecimal{
        val soma = transacoes
                .filter { it.tipo == tipo }
                .sumByDouble { it.valor.toDouble() }
        return BigDecimal(soma)
    }
}