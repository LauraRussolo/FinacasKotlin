package lollalab.financask.ui.dialog

import android.app.DatePickerDialog
import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.form_transacao.view.*
import lollalab.financask.R
import lollalab.financask.extension.converteParaCalendar
import lollalab.financask.extension.formataParaBr
import lollalab.financask.model.Tipo
import lollalab.financask.model.Transacao
import java.math.BigDecimal
import java.util.*


abstract class FormularioTransacaoDialog(
        private val contexto: Context,
        private val viewGroup: ViewGroup) {

    val viewCriada = criarLayout()
    protected val campoValor = viewCriada.form_transacao_valor
    protected val campoData = viewCriada.form_transacao_data
    protected val campoCategorias = viewCriada.form_transacao_categoria
    abstract protected val textoDoBotaoPositivo: String


    fun configuraDialog(tipo: Tipo,
                        delegate: (transacao: Transacao) -> Unit) {
        configuraFormulario(tipo, delegate)
        configuraData()
        configuraCategoria(tipo)
    }
    private fun configuraFormulario(tipo: Tipo, delegate: (transacao: Transacao) -> Unit) {

        val titulo = tituloPorTipo(tipo)

        AlertDialog.Builder(contexto)
                .setTitle(titulo)
                .setView(viewCriada)
                .setPositiveButton(textoDoBotaoPositivo, { _, _ ->

                    val valorEmTexto = campoValor.text.toString()
                    val dataEmTexto = campoData.text.toString()
                    val categoriaemTexto = campoCategorias.selectedItem.toString()

                    val valor = converteCampoValor(valorEmTexto)

                    val dataConvertida = dataEmTexto.converteParaCalendar()

                    val transacao = Transacao(valor,
                            categoriaemTexto, tipo, dataConvertida)

                    delegate(transacao)

                })
                .setNegativeButton("Cancelar", null)
                .show()
    }

    private fun configuraData() {

        val hoje = Calendar.getInstance()

        val ano = hoje.get(Calendar.YEAR)
        val mes = hoje.get(Calendar.MONTH)
        val dia = hoje.get(Calendar.DAY_OF_MONTH)

        campoData
                .setText(hoje.formataParaBr())

        campoData.setOnClickListener {
            DatePickerDialog(contexto,
                    DatePickerDialog.OnDateSetListener { view, ano, mes, dia ->
                        var dataSelecionada = Calendar.getInstance()
                        dataSelecionada.set(ano, mes, dia)
                        campoData
                                .setText(dataSelecionada.formataParaBr())
                    },
                    ano, mes, dia)
                    .show()
        }
    }

    private fun configuraCategoria(tipo: Tipo) {

        val categorias = categoriasPorTipo(tipo)

        val adapterCategoria = ArrayAdapter
                .createFromResource(contexto,
                        categorias,
                        android.R.layout.simple_spinner_dropdown_item)
        campoCategorias.adapter = adapterCategoria
    }

    private fun criarLayout(): View {
        return LayoutInflater.from(contexto)
                .inflate(R.layout.form_transacao,
                        viewGroup,
                        false)
    }

    private fun converteCampoValor(valorEmTexto: String): BigDecimal {
        return try {
            BigDecimal(valorEmTexto)
        } catch (exception: NumberFormatException) {
            Toast.makeText(contexto,
                    "Erro no valor",
                    Toast.LENGTH_SHORT).show()
            BigDecimal.ZERO
        }
    }

    abstract protected fun tituloPorTipo(tipo: Tipo): Int

    protected fun categoriasPorTipo(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA) {
            return R.array.categorias_de_receita
        }
        return R.array.categorias_de_despesa
    }
}