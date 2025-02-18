package br.edu.ufra.dao;

import br.edu.ufra.entidade.Comite;
import br.edu.ufra.entidade.Pessoa;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

public class ComiteDAO extends GenericDAO<Comite> {

    public List<Comite> listarVigentes(String andSQL) {
        EntityManager em = getEntityManager();
        String sql = "select c from Comite c where c.inicio <= :hoje and c.fim >= :hoje ";
        sql += andSQL;
        TypedQuery<Comite> query = em.createQuery(sql, Comite.class);
        List<Comite> resposta = null;
        final Date HOJE = new Date();
        try {
            resposta = query.setParameter("hoje", HOJE).getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            em.close();
        }
        return resposta;
    }
    public List<Comite> listarEncerrados() {
        EntityManager em = getEntityManager();
        String sql = "select c from Comite c "
                + " where c.fim <= :hoje ";
        TypedQuery<Comite> query = em.createQuery(sql, Comite.class);
        List<Comite> resposta = null;
        final Date HOJE = new Date();
        try {
            resposta = query.setParameter("hoje", HOJE).getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            em.close();
        }
        return resposta;
    }
    public List<Comite> pesquisarPor(String termoPesquisa, String campoTabela) {
        EntityManager em = getEntityManager();
        String sql = "select c from Comite c "
                + " where c.inicio <= :hoje "
                + " and c.fim >= :hoje "
                + " and c." + campoTabela
                + " like '%" + termoPesquisa + "%'";
        TypedQuery<Comite> query = em.createQuery(sql, Comite.class);

        final Date HOJE = new Date();
        List<Comite> resposta = null;
        try {
            resposta = query.setParameter("hoje", HOJE).getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            em.close();
        }
        return resposta;
    }

}

