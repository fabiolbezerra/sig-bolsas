package br.edu.ufra.dao;

import br.edu.ufra.entidade.Edital;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Date;

public class EditalDAO extends GenericDAO<Edital> {

    public List<Edital> obterVigentes() {

        EntityManager em = getEntityManager();
        final String JPQL = "select e from Edital e where e.dataInicio <= :hoje and e.dataFinal >= :hoje order by e.dataFinal";
        TypedQuery<Edital> query = em.createQuery(JPQL, Edital.class);

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

    public List<Edital> obterEncerrados() {
        List<Edital> resposta = null;

        EntityManager em = getEntityManager();
        final String JPQL = "select e from Edital e where e.dataFinal < :hoje ";
        TypedQuery<Edital> query = em.createQuery(JPQL, Edital.class);

        final Date HOJE = new Date();
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
