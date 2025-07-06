package br.edu.ufra.dao;

import br.edu.ufra.entidade.Inscricao;
import br.edu.ufra.entidade.PlanoDeTrabalho;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class PlanoDeTrabalhoDAO extends GenericDAO<PlanoDeTrabalho> {
    public List<PlanoDeTrabalho> obter(Inscricao inscricao) {
        EntityManager em = getEntityManager();
        String jpql = "select p from PlanoDeTrabalho p where p.inscricao = :inscricao";
        TypedQuery<PlanoDeTrabalho> query = em.createQuery(jpql, PlanoDeTrabalho.class);
        List<PlanoDeTrabalho> resposta = null;

        try {
            resposta = query.setParameter("inscricao", inscricao).getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            em.close();
        }

        return resposta;
    }

    public List<PlanoDeTrabalho> obterPorTitulo(String titulo) {
        EntityManager em = getEntityManager();
        String sql = "select p from PlanoDeTrabalho p where p.titulo like :titulo";
        TypedQuery<PlanoDeTrabalho> q = em.createQuery(sql, PlanoDeTrabalho.class);
        List<PlanoDeTrabalho> resposta = null;

        try {
            titulo = '%' + titulo + '%';
            resposta = q.setParameter("titulo", titulo).getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            em.close();
        }
        return resposta;
    }

    public List<PlanoDeTrabalho> obterPorResumo(String resumo) {
        EntityManager em = getEntityManager();
        String sql = "select p from PlanoDeTrabalho p where p.resumo like :resumo";
        TypedQuery<PlanoDeTrabalho> q = em.createQuery(sql, PlanoDeTrabalho.class);
        List<PlanoDeTrabalho> resposta = null;

        try {
            resumo = '%' + resumo + '%';
            resposta = q.setParameter("resumo", resumo).getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            em.close();
        }
        return resposta;
    }

    public List<PlanoDeTrabalho> obterPorPesquisador(String nome) {
        EntityManager em = getEntityManager();
        String sql = "select p from PlanoDeTrabalho p where p.inscricao.pessoa.nomeCompleto like :nome";
        TypedQuery<PlanoDeTrabalho> q = em.createQuery(sql, PlanoDeTrabalho.class);
        List<PlanoDeTrabalho> resposta = null;
        try {
            nome = '%' + nome + '%';
            resposta = q.setParameter("nome", nome).getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            em.close();
        }
        return resposta;
    }
}
