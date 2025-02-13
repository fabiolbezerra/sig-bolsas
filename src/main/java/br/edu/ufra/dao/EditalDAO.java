package br.edu.ufra.dao;

import br.edu.ufra.entidade.Edital;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Date;

public class EditalDAO extends GenericDAO<Edital> {

    public List<Edital> obterVigentes() {

        EntityManager em = getEntityManager();
        String sql = "select e from Edital e where e.dataInicio <= :hoje and e.dataFinal >= :hoje ";
        TypedQuery<Edital> query = em.createQuery(sql, Edital.class);

        final Date HOJE = new Date();
        List<Edital> resposta = null;
        try {
            resposta = query
                    .setParameter("hoje", HOJE)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            em.close();
        }
        return resposta;
    }

    public List<Edital> obterNaoVigentes() {

        EntityManager em = getEntityManager();
        String sql = "select e from Edital e where e.dataInicio > :hoje or e.dataFinal < :hoje ";
        TypedQuery<Edital> query = em.createQuery(sql, Edital.class);

        final Date HOJE = new Date();
        List<Edital> resposta = null;
        try {
            resposta = query
                    .setParameter("hoje", HOJE)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            em.close();
        }
        return resposta;
    }

}
