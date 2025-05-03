package br.edu.ufra.dao;

import br.edu.ufra.entidade.Edital;
import br.edu.ufra.entidade.Inscricao;
import br.edu.ufra.entidade.Pessoa;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class InscricaoDAO extends GenericDAO<Inscricao> {

    public List<Inscricao> obter(Pessoa pessoa) {
        EntityManager em = getEntityManager();

        final String SQL = "select i from Inscricao i where i.pessoa = :pessoa ";
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

    public List<Inscricao> obterPorPesquisadorEEdital(Integer pesquisadorId, Integer editalId) {
        EntityManager em = getEntityManager();

            final String SQL = "SELECT i FROM Inscricao i "
                             + "WHERE i.pessoa.id = :pesquisadorId "
                             + "AND i.edital.id = :editalId "
                             + "ORDER BY i.dataInscricao DESC";

        TypedQuery<Inscricao> query = em.createQuery(SQL, Inscricao.class);

        List<Inscricao> resposta = null;
        try {
            resposta = query
                    .setParameter("pesquisadorId", pesquisadorId)
                    .setParameter("editalId", editalId)
                    .getResultList();
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

    public String gerarProtocolo(int editalId, int pessoaId) {
        int ano = LocalDate.now().getYear();

        // Formata editalId e pessoaId com 4 dígitos cada, preenchendo com zeros
        String editalFormatado = String.format("%04d", editalId);
        String pessoaFormatada = String.format("%04d", pessoaId);

        // Concatena tudo no formato AAAAEEEEPPPP
        return String.format("%d%s%s", ano, editalFormatado, pessoaFormatada);
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
