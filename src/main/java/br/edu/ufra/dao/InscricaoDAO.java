package br.edu.ufra.dao;

import br.edu.ufra.entidade.Edital;
import br.edu.ufra.entidade.Inscricao;
import br.edu.ufra.entidade.Pessoa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class InscricaoDAO extends GenericDAO<Inscricao> {

    public List<Inscricao> obter(Pessoa pessoa) {
        EntityManager em = getEntityManager();

        final String SQL = "select i from Inscricao i where i.pessoa = :pessoa";
        TypedQuery<Inscricao> query = em.createQuery(SQL, Inscricao.class);

        List<Inscricao> resposta = null;
        try {
            resposta = query
                    .setParameter("pessoa", pessoa)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            em.close();
        }

        return resposta;
    }

    public List<Inscricao> obter(Pessoa pessoa, boolean vigentes) {
        EntityManager em = getEntityManager();

        final String JPQL = "select i from Inscricao i " +
                "where i.pessoa = :pessoa " +
                (vigentes ? " and i.edital.dataFinal >= :hoje " : " and i.edital.dataFinal < :hoje ");
        TypedQuery<Inscricao> query = em.createQuery(JPQL, Inscricao.class);

        List<Inscricao> resposta = null;
        try {
            resposta = query
                    .setParameter("pessoa", pessoa)
                    .setParameter("hoje", new Date())
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            em.close();
        }

        return resposta;
    }

    public Inscricao obter(Pessoa pesquisador, Edital edital) {
        Inscricao resposta = null;

        EntityManager em = getEntityManager();
        final String JPQL = "SELECT i FROM Inscricao i " +
                "WHERE i.pessoa = :pesquisador AND i.edital = :edital " +
                "ORDER BY i.dataInscricao DESC";
        TypedQuery<Inscricao> query = em.createQuery(JPQL, Inscricao.class);

        List<Inscricao> inscricoes = null;
        try {
            inscricoes = query
                    .setParameter("pesquisador", pesquisador)
                    .setParameter("edital", edital)
                    .getResultList();
            if (inscricoes != null && !inscricoes.isEmpty()) {
                resposta = inscricoes.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            em.close();
        }

        return resposta;
    }

    public List<Inscricao> obter(Edital edital) {
        EntityManager em = getEntityManager();

        final String SQL = "select i from Inscricao i where i.edital = :edital ";
        TypedQuery<Inscricao> query = em.createQuery(SQL, Inscricao.class);

        List<Inscricao> resposta = null;
        try {
            resposta = query
                    .setParameter("edital", edital)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            em.close();
        }

        return resposta;
    }

    public List<Inscricao> obter(Edital edital, Date inicio, Date fim) {
        EntityManager em = getEntityManager();

        final String SQL = "select i from Inscricao i where i.edital = :edital and i.dataInscricao >= :inicio and i.dataInscricao <= :fim";
        TypedQuery<Inscricao> query = em.createQuery(SQL, Inscricao.class);

        List<Inscricao> resposta = null;
        try {
            resposta = query
                    .setParameter("edital", edital)
                    .setParameter("inicio", inicio)
                    .setParameter("fim", fim)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            em.close();
        }

        return resposta;
    }
}
