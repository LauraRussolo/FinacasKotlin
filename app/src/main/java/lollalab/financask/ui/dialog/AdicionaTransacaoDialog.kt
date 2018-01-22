package lollalab.financask.ui.dialog

import android.content.Context
import android.view.ViewGroup
import lollalab.financask.R
import lollalab.financask.model.Tipo


class AdicionaTransacaoDialog(contexto: Context,
                              viewGroup: ViewGroup) : FormularioTransacaoDialog(contexto, viewGroup) {
    override val textoDoBotaoPositivo: String
        get() = "Adicionar"

    override fun tituloPorTipo(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA) {
            return R.string.adiciona_receita
        }
        return R.string.adiciona_despesa
    }
}
