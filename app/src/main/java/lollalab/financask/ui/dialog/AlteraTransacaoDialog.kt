package lollalab.financask.ui.dialog

import android.content.Context
import android.view.ViewGroup
import lollalab.financask.R
import lollalab.financask.extension.formataParaBr
import lollalab.financask.model.Tipo
import lollalab.financask.model.Transacao


class AlteraTransacaoDialog(private val contexto: Context,
                            viewGroup: ViewGroup) : FormularioTransacaoDialog(contexto, viewGroup) {
    override val textoDoBotaoPositivo: String
        get() = "Alterar"

    override fun tituloPorTipo(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA) {
            return R.string.altera_receita
        }
        return R.string.altera_despesa
    }


    fun configuraDialog(transacao: Transacao,
                        delegate: (transacao: Transacao) -> Unit) {
        val tipo = transacao.tipo
        super.configuraDialog(tipo, delegate)
        inicializaCampos(transacao)
    }

    private fun inicializaCampos(transacao: Transacao) {
        configuraCampoValor(transacao)
        configuraCampodata(transacao)
        configuraCampoCategorias(transacao)
    }

    private fun configuraCampoCategorias(transacao: Transacao) {
        val tipo = transacao.tipo
        val categoriasRetornadas = contexto.resources.getStringArray(categoriasPorTipo(tipo))
        val posicaoCategoria = categoriasRetornadas.indexOf(transacao.categoria)
        campoCategorias.setSelection(posicaoCategoria, true)
    }

    private fun configuraCampodata(transacao: Transacao) {
        campoData.setText(transacao.data.formataParaBr())
    }

    private fun configuraCampoValor(transacao: Transacao) {
        campoValor.setText(transacao.valor.toString())
    }
}
