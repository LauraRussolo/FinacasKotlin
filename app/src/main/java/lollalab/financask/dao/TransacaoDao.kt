package lollalab.financask.dao

import lollalab.financask.model.Transacao

class TransacaoDao {

    val transacoes : List<Transacao> = Companion.transacoes
    companion object {
        private val transacoes : MutableList<Transacao> = mutableListOf()
    }

    fun adiciona(transacao: Transacao){
        Companion.transacoes.add(transacao)
    }

    fun remove(posicao: Int){
        Companion.transacoes.removeAt(posicao)
    }

    fun altera(transacao: Transacao, posicao: Int){
        Companion.transacoes[posicao] = transacao
    }


}