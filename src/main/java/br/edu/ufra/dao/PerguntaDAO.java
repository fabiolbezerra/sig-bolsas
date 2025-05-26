package br.edu.ufra.dao;

import br.edu.ufra.entidade.Formulario;
import br.edu.ufra.entidade.OpcaoPergunta;
import br.edu.ufra.entidade.Pergunta;
import br.edu.ufra.entidade.Pergunta;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

public class PerguntaDAO extends GenericDAO<Pergunta> {

    public boolean alterar(Pergunta pergunta, boolean excluirOpcoes) {
        if (excluirOpcoes) {
            EntityManager em = getEntityManager();

            EntityTransaction transaction = null;
            if (em != null) {
                transaction = em.getTransaction();
            } else {
                return false;
            }

            try {
                transaction.begin();
                //excluindo as opções da pergunta
                Set<OpcaoPergunta> opcoes = pergunta.getOpcaoPerguntas();
                for (OpcaoPergunta opcaoPergunta : opcoes) {
                    em.remove(opcaoPergunta);
                }
                pergunta.setOpcaoPerguntas(null);

                //alterando a pergunta
                em.merge(pergunta);
                transaction.commit();
                return true;
            } catch (Exception e) {
                e.printStackTrace(System.err);
                if (transaction != null
                        && transaction.isActive()) {
                    transaction.rollback();
                }
                return false;
            } finally {
                em.close();
            }

        } else {
            return super.alterar(pergunta);
        }
    }

    public List<Pergunta> obter(Formulario formulario) {

        EntityManager em = getEntityManager();
        String sql = "select p from Pergunta p where p.formulario = :formulario order by p.ordem ";
        TypedQuery<Pergunta> query = em.createQuery(sql, Pergunta.class);

        List<Pergunta> resposta = null;
        try {
            resposta = query
                    .setParameter("formulario", formulario)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            em.close();
        }
        return resposta;
    }

}
