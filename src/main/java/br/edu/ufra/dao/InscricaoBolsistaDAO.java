package br.edu.ufra.dao;
import br.edu.ufra.entidade.*;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

public class InscricaoBolsistaDAO extends GenericDAO<InscricaoBolsista> {

    public InscricaoBolsista obterDetalhado(Object id) {
        if (id == null) {
            return null;
        }

        EntityManager em = getEntityManager();
        InscricaoBolsista resposta = null;
        try {
            String jpql = "select i from InscricaoBolsista i " +
                          "left join fetch i.pessoa p " +
                          "left join fetch i.planoDeTrabalho plt " +
                          "left join fetch plt.inscricao ins " +
                          "left join fetch ins.edital ed " +
                          "where i.id = :id";
            TypedQuery<InscricaoBolsista> q = em.createQuery(jpql, InscricaoBolsista.class);
            List<InscricaoBolsista> resultados = q.setParameter("id", id).getResultList();
            if (resultados != null && !resultados.isEmpty()) {
                resposta = resultados.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            em.close();
        }
        return resposta;
    }

    public List<InscricaoBolsista> obter(Pessoa pessoa) {
        EntityManager em = getEntityManager();

        final String SQL = "select i from InscricaoBolsista i where i.pessoa = :pessoa";
        TypedQuery<InscricaoBolsista> query = em.createQuery(SQL, InscricaoBolsista.class);

        List<InscricaoBolsista> resposta = null;
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
    public List<Inscricao> obter(Pessoa pessoa, boolean vigentes) {
        EntityManager em = getEntityManager();

        final String JPQL = "select i from Inscricao i " +
                "where i.pessoa = :pessoa " +
                (vigentes ? " and i.edital.dataFinal >= :hoje " : " and i.edital.dataFinal < :hoje ");
        TypedQuery<Inscricao> query = em.createQuery(JPQL, Inscricao.class);

        List<Inscricao> resposta = null;
        try {
            resposta = query
                    .setParameter("pessoa", pessoa)
                    .setParameter("hoje", new Date())
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            em.close();
        }

        return resposta;
    }

    public List<PlanoDeTrabalho> obterVigentes() {
        EntityManager em = getEntityManager();
        String sql = "select p from PlanoDeTrabalho p where p.inscricao.edital.dataInicio <= :hoje and p.inscricao.edital.dataFinal >= :hoje order by p.inscricao.edital.dataFinal";
        TypedQuery<PlanoDeTrabalho> q = em.createQuery(sql, PlanoDeTrabalho.class);
        final Date HOJE = new Date();
        List<PlanoDeTrabalho> resposta = null;
        try {
            resposta = q
                    .setParameter("hoje", HOJE)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            em.close();
        }
        return resposta;
    }
    public InscricaoBolsista obter(Pessoa bolsista, PlanoDeTrabalho planoDeTrabalho) {
        InscricaoBolsista resposta = null;

        EntityManager em = getEntityManager();
        final String JPQL = "SELECT i FROM InscricaoBolsista i " +
                "WHERE i.pessoa = :bolsista AND i.planoDeTrabalho = :planoDeTrabalho " +
                "ORDER BY i.dataInscricao DESC";
        TypedQuery<InscricaoBolsista> query = em.createQuery(JPQL, InscricaoBolsista.class);

        List<InscricaoBolsista> inscricoes = null;
        try {
            inscricoes = query
                    .setParameter("bolsista", bolsista)
                    .setParameter("planoDeTrabalho", planoDeTrabalho)
                    .getResultList();
            
            // Verificar se a lista não está vazia e retornar o primeiro resultado
            if (inscricoes != null && !inscricoes.isEmpty()) {
                resposta = inscricoes.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            em.close();
        }

        return resposta;
    }
}
