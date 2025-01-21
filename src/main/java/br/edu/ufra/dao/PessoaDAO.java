package br.edu.ufra.dao;

import br.edu.ufra.entidade.Pessoa;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class PessoaDAO extends GenericDAO<Pessoa> {
    public Pessoa login(String email, String senha) {
        EntityManager em = getEntityManager();
        String sql = "select p from Pessoa p "
                + " where "
                + " p.email = :email "
                + " and p.senha = :senha ";
        TypedQuery<Pessoa> query = em.createQuery(sql, Pessoa.class);
        Pessoa resposta = null;
        try {
            //recupera o usuário
            List<Pessoa> usuarios = query
                    .setParameter("email", email)
                    .setParameter("senha", senha)
                    .getResultList();

            if (usuarios != null
                    && usuarios.size() == 1) {
                resposta = usuarios.get(0);
                em.refresh(resposta);
            }

            return resposta;
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            em.close();
        }
        return resposta;
    }

    public Pessoa obterPorEmail(String email) {
        EntityManager em = getEntityManager();
        String sql = "select p from Pessoa p where p.email = :email ";
        TypedQuery<Pessoa> query = em.createQuery(sql, Pessoa.class);
        Pessoa resposta = null;
        try {
            //recupera o usuário
            List<Pessoa> usuarios = query
                    .setParameter("email", email)
                    .getResultList();

            if (usuarios != null
                    && usuarios.size() == 1) {
                resposta = usuarios.get(0);
                em.refresh(resposta);
            }

            return resposta;
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            em.close();
        }
        return resposta;
    }
}
