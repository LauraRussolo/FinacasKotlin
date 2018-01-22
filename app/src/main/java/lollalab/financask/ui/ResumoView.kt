package lollalab.financask.ui

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import kotlinx.android.synthetic.main.resumo_card.view.*
import lollalab.financask.R
import lollalab.financask.extension.formataParaBr
import lollalab.financask.model.Resumo
import lollalab.financask.model.Transacao
import java.math.BigDecimal

class ResumoView(private val view: View,
                 transacoes: List<Transacao>,
                 contexto: Context) {

    private val resumo = Resumo(transacoes)
    private val corDespesa = ContextCompat.getColor(contexto, R.color.despesa)
    private val corReceita = ContextCompat.getColor(contexto, R.color.receita)


    private fun adicionaTotalReceita() {
        with(view.resumo_card_receita){
            text = resumo.receita.formataParaBr()
            setTextColor(corReceita)
        }
    }

    private fun adicionaTotalDespesa() {
        with(view.resumo_card_despesa){
            text = resumo.despesa.formataParaBr()
            setTextColor(corDespesa)
        }
    }

    private fun adicionaTotal(){
        val total = resumo.total
        var cor = corPorTotal(total)
        with(view.resumo_card_total){
            text = total.formataParaBr()
            setTextColor(cor)
        }
    }

    fun atualizaResumo(){
        adicionaTotalReceita()
        adicionaTotalDespesa()
        adicionaTotal()
    }

    private fun corPorTotal(total: BigDecimal): Int {
        if(total >= BigDecimal.ZERO){
            return corReceita
        } else {
            return corDespesa
        }
    }

}