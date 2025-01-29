package br.edu.ufra.dao;

import br.edu.ufra.entidade.Comite;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

public class ComiteDAO extends GenericDAO<Comite> {

    public List<Comite> listarVigentesEm(Date dataIndicada) {
        EntityManager em = getEntityManager();
        String sql = "select c from Comite c "
                + " where c.inicio <= :dataIndicada "
                + " and c.fim >= :dataIndicada ";
        TypedQuery<Comite> query = em.createQuery(sql, Comite.class);
        List<Comite> resposta = null;
        try {
            resposta = query.setParameter("dataIndicada", dataIndicada).getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            em.close();
        }
        return resposta;
    }
}

