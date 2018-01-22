package lollalab.financask.ui.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.transacao_item.view.*
import lollalab.financask.R
import lollalab.financask.extension.formataParaBr
import lollalab.financask.extension.limitaemAte
import lollalab.financask.model.Tipo
import lollalab.financask.model.Transacao



class ListaTransacoesAdapter(private val transacoes: List<Transacao>,
                             private val contexto: Context) : BaseAdapter() {

    private val limiteDaCategoria = 14

    override fun getView(posicao: Int, view: View?, parent: ViewGroup?): View {
        val viewCriada: View = LayoutInflater.from(contexto)
                .inflate(R.layout.transacao_item, parent, false)

        val transacao = transacoes[posicao]


        adicionaValor(transacao, viewCriada)

        adicionaIcone(transacao, viewCriada)

        adicionaCategoria(viewCriada, transacao)

        adicionaData(viewCriada, transacao)

        return viewCriada
    }

    private fun adicionaValor(transacao: Transacao, viewCriada: View) {
        viewCriada.transacao_valor
                .setTextColor(corPorTipo(transacao.tipo))

        viewCriada.transacao_valor.text = transacao.valor.formataParaBr()
    }

    private fun corPorTipo(tipo: Tipo): Int {
         if (tipo == Tipo.RECEITA) {
            return ContextCompat.getColor(contexto, R.color.receita)
        }
           return ContextCompat.getColor(contexto, R.color.despesa)
    }

    private fun adicionaIcone(transacao: Transacao, viewCriada: View) {
        viewCriada.transacao_icone
                .setBackgroundResource(iconePorTipo(transacao.tipo))
    }

    private fun iconePorTipo(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA) {
            return R.drawable.icone_transacao_item_receita
        }
            return R.drawable.icone_transacao_item_despesa
    }

    private fun adicionaCategoria(viewCriada: View, transacao: Transacao) {
        viewCriada.transacao_categoria.text = transacao.categoria.limitaemAte(limiteDaCategoria)
    }

    private fun adicionaData(viewCriada: View, transacao: Transacao) {
        viewCriada.transacao_data.text = transacao.data.formataParaBr()
    }

    override fun getItem(posicao: Int): Transacao {
        return transacoes[posicao]
    }

    override fun getItemId(posicao: Int): Long {
        return  0 //os itens da lista não possuem ID
    }

    override fun getCount(): Int {
        return transacoes.size
   }
}