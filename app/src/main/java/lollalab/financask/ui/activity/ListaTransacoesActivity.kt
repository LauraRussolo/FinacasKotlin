package lollalab.financask.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.AdapterView
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import lollalab.financask.R
import lollalab.financask.dao.TransacaoDao
import lollalab.financask.model.Tipo
import lollalab.financask.model.Transacao
import lollalab.financask.ui.ResumoView
import lollalab.financask.ui.adapter.ListaTransacoesAdapter
import lollalab.financask.ui.dialog.AdicionaTransacaoDialog
import lollalab.financask.ui.dialog.AlteraTransacaoDialog

class ListaTransacoesActivity : AppCompatActivity() {

    private val dao = TransacaoDao()
    private val transacoes = dao.transacoes
    private val viewDaActivity by lazy {
        window.decorView
    }
    private val viewGroupDaActivity by lazy {
        viewDaActivity as ViewGroup
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        configuraResumo()
        configuraLista()
        configuraFab() //Fab = floating action button

    }

    private fun configuraFab() {
        lista_transacoes_adiciona_receita
                .setOnClickListener {
                    chamaDialogDeAdicao(Tipo.RECEITA)
                }

        lista_transacoes_adiciona_despesa
                .setOnClickListener {
                    chamaDialogDeAdicao(Tipo.DESPESA)
                }
    }

    private fun chamaDialogDeAdicao(tipo: Tipo) {
        AdicionaTransacaoDialog(this, viewGroupDaActivity)
                .configuraDialog(tipo, { transacaoCriada ->
                        adicionaTransacao(transacaoCriada)
                        lista_transacoes_adiciona_menu.close(true)
                })
    }

    private fun adicionaTransacao(transacao: Transacao) {
        dao.adiciona(transacao)
        atualizaTransacoes()
    }

    private fun atualizaTransacoes() {
        configuraLista()
        configuraResumo()
    }


    private fun configuraResumo() {
        val resumoView = ResumoView(viewDaActivity, transacoes, this)
        resumoView.atualizaResumo()
    }

    private fun configuraLista() {
        with(lista_transacoes_listview){
            adapter = ListaTransacoesAdapter(transacoes, this@ListaTransacoesActivity)
            setOnItemClickListener { _, _, posicao, _ ->
                val transacao = transacoes[posicao]
                chamaDialogAlteracao(transacao, posicao)
            }
            setOnCreateContextMenuListener { menu, _, _ ->
                menu.add(Menu.NONE, 1, Menu.NONE, "Remover")
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        val idDoMenu = item?.itemId
        if(idDoMenu == 1){
            val adapterMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
            val posicaoDaTransacao = adapterMenuInfo.position
            removeTransacao(posicaoDaTransacao)
        }
        return super.onContextItemSelected(item)
    }

    private fun removeTransacao(posicaoDaTransacao: Int) {
        dao.remove(posicaoDaTransacao)
        atualizaTransacoes()
    }

    private fun chamaDialogAlteracao(transacao: Transacao, posicao: Int) {
        AlteraTransacaoDialog(this, viewGroupDaActivity)
                .configuraDialog(transacao, {transacaoAlterada ->
                    alteraTransacao(posicao, transacaoAlterada)
                })
    }

    private fun alteraTransacao(posicao: Int, transacao: Transacao) {
        dao.altera(transacao, posicao)
        atualizaTransacoes()
    }

}