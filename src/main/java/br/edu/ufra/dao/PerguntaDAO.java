package br.edu.ufra.dao;

import br.edu.ufra.entidade.Formulario;
import br.edu.ufra.entidade.OpcaoPergunta;
import br.edu.ufra.entidade.Pergunta;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class PerguntaDAO extends GenericDAO<Pergunta> {

    public boolean salvar(List<Pergunta> perguntas) {
        EntityManager em = getEntityManager();

        EntityTransaction transaction = null;
        if (em != null) {
            transaction = em.getTransaction();
        } else {
            return false;
        }

        try {
            transaction.begin();

            for (Pergunta pergunta : perguntas) {
                em.merge(pergunta);
            }
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
    }

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
                List<OpcaoPergunta> opcoes = pergunta.getOpcaoPerguntas();
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

    public boolean excluir(Pergunta pergunta, List<Pergunta> perguntas) {
        EntityManager em = getEntityManager();

        EntityTransaction transaction = null;
        if (em != null) {
            transaction = em.getTransaction();
        }else{
            return false;
        }

        try {
            int ordem = pergunta.getOrdem();
            transaction.begin();
            for (Pergunta value : perguntas) {
                if (value.getOrdem() == ordem + 1) {
                    value.setOrdem(ordem);
                    ordem++;
                    em.merge(value);
                }
            }
            em.remove(em.merge(pergunta));
            transaction.commit();
            return true;
        }catch (Exception e) {
            e.printStackTrace(System.err);
            if (transaction != null
                    && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        }finally {
            em.close();
        }
    }

}
