package br.edu.ufra.dao;

import br.edu.ufra.entidade.AvaliacaoInscricao;
import br.edu.ufra.entidade.Inscricao;
import br.edu.ufra.entidade.Pessoa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class AvaliacaoInscricaoDAO extends GenericDAO<AvaliacaoInscricao> {

    public List<AvaliacaoInscricao> obterPorInscricao(Inscricao inscricao) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<AvaliacaoInscricao> q = em.createQuery(
                "SELECT a FROM AvaliacaoInscricao a WHERE a.inscricao = :inscricao ORDER BY a.dataAvaliacao DESC",
                AvaliacaoInscricao.class);
            q.setParameter("inscricao", inscricao);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return null;
        } finally {
            em.close();
        }
    }

    public AvaliacaoInscricao obterPorPessoaEInscricao(Pessoa pessoa, Inscricao inscricao) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<AvaliacaoInscricao> q = em.createQuery(
                "SELECT a FROM AvaliacaoInscricao a WHERE a.pessoa = :pessoa AND a.inscricao = :inscricao",
                AvaliacaoInscricao.class);
            q.setParameter("pessoa", pessoa);
            q.setParameter("inscricao", inscricao);
            List<AvaliacaoInscricao> result = q.getResultList();
            return result.isEmpty() ? null : result.get(0);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return null;
        } finally {
            em.close();
        }
    }

    public double calcularMediaPorInscricao(Inscricao inscricao) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Double> q = em.createQuery(
                "SELECT AVG(a.pontuacaoAvaliacao) FROM AvaliacaoInscricao a WHERE a.inscricao = :inscricao",
                Double.class);
            q.setParameter("inscricao", inscricao);
            Double resultado = q.getSingleResult();
            return resultado != null ? resultado : 0.0;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return 0.0;
        } finally {
            em.close();
        }
    }
}