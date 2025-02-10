package br.edu.ufra.dao;

import br.edu.ufra.entidade.Inscricao;
import br.edu.ufra.entidade.Pessoa;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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
}
