package br.edu.ufra.dao;

import br.edu.ufra.entidade.Papel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class PapelDAO extends GenericDAO<Papel> {

    public List<Papel> listarPerfil() {

        EntityManager em = getEntityManager();
        String sql = "select e from Papel e where e.codigo = 'B' or e.codigo = 'P' ";
        TypedQuery<Papel> query = em.createQuery(sql, Papel.class);

        List<Papel> resposta = null;
        try {
            resposta = query
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            em.close();
        }
        return resposta;
    }

}
