package br.edu.ufra.dao;

import br.edu.ufra.entidade.Papel;
import br.edu.ufra.entidade.Pessoa;
import br.edu.ufra.entidade.PessoaPapel;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
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

    public boolean alterarPermissoes(Pessoa pessoa, List<Papel> papeis) {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = null;
        if (em != null) {
            transaction = em.getTransaction();
        } else {
            return false;
        }
        try {
            transaction.begin();

            //Recuperar o objeto pessoa
            String query = Pessoa.class.getSimpleName() + ".findById";
            final TypedQuery<Pessoa> q = em.createNamedQuery(query, Pessoa.class);
            Pessoa resposta = null;
            List<Pessoa> objs = null;
            objs = q.setParameter("id", pessoa.getId()).getResultList();
            if (objs != null && !objs.isEmpty()) {
                resposta = objs.get(0);
                em.refresh(resposta);
            }

            //Remover os papéis que não estão na lista de papéis
            for (PessoaPapel pp : resposta.getPessoaPapels()) {
                if (!papeis.contains(pp.getPapel())) {
                    em.remove(pp);
                } else {
                    papeis.remove(pp.getPapel());
                }
            }

            //Adicionar os papéis que estão na lista de papéis
            for (Papel papel : papeis) {
                PessoaPapel pp = new PessoaPapel();
                pp.setPapel(papel);
                pp.setPessoa(pessoa);
                em.persist(pp);
            }

            transaction.commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            if (transaction != null
                    && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    public List<Pessoa> buscarPessoa(String termoPesquisa, String campoTabela) {
        EntityManager em = getEntityManager();
        String sql = "select p from Pessoa p "
                + " where p." + campoTabela
                + " like '%" + termoPesquisa + "%'";
        TypedQuery<Pessoa> query = em.createQuery(sql, Pessoa.class);

        List<Pessoa> resposta = null;
        try {
            resposta = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            em.close();
        }
        return resposta;
    }

}
