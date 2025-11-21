package br.edu.ufra.dao;

import br.edu.ufra.entidade.Edital;
import br.edu.ufra.entidade.Inscricao;
import br.edu.ufra.entidade.PlanoDeTrabalho;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

public class PlanoDeTrabalhoDAO extends GenericDAO<PlanoDeTrabalho> {

    public PlanoDeTrabalho obterPlanoDeTrabalho(Class<PlanoDeTrabalho> classe, Object id) {
        EntityManager em = getEntityManager();
        PlanoDeTrabalho plano = null;

        try {
            // Usando JPQL para carregar o plano com a inscrição e o edital de forma eager
            String jpql = "select p from PlanoDeTrabalho p " +
                    "left join fetch p.inscricao i " +
                    "left join fetch i.edital " +
                    "where p.id = :id";

            TypedQuery<PlanoDeTrabalho> query = em.createQuery(jpql, PlanoDeTrabalho.class);
            query.setParameter("id", id);

            List<PlanoDeTrabalho> resultados = query.getResultList();
            if (!resultados.isEmpty()) {
                plano = resultados.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            em.close();
        }

        return plano;
    }

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

    public List<PlanoDeTrabalho> obterPorBolsista(Long bolsistaId) {
        EntityManager em = getEntityManager();
        List<PlanoDeTrabalho> resposta = null;

        try {
            // Query correta: buscar através da tabela inscricao_bolsista
            String jpql = "select distinct ib.planoDeTrabalho from InscricaoBolsista ib " +
                    "where ib.pessoa.id = :bolsistaId " +
                    "order by ib.dataInscricao desc";

            TypedQuery<PlanoDeTrabalho> query = em.createQuery(jpql, PlanoDeTrabalho.class);
            query.setParameter("bolsistaId", bolsistaId);

            System.out.println("Executando query para bolsista ID: " + bolsistaId);
            resposta = query.getResultList();
            System.out.println("Total de planos encontrados: " + (resposta != null ? resposta.size() : 0));

            // Se encontrou resultados, carregar as associações
            if (resposta != null && !resposta.isEmpty()) {
                for (PlanoDeTrabalho p : resposta) {
                    if (p.getInscricao() != null) {
                        // Força o carregamento lazy
                        p.getInscricao().getPessoa().getNomeCompleto();
                        if (p.getInscricao().getEdital() != null) {
                            p.getInscricao().getEdital().getTitulo();
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao buscar planos do bolsista: " + e.getMessage());
            e.printStackTrace(System.err);
        } finally {
            em.close();
        }

        return resposta;
    }

    // Método alternativo para debug - lista TODOS os planos
    public List<PlanoDeTrabalho> listarTodosComDetalhes() {
        EntityManager em = getEntityManager();
        List<PlanoDeTrabalho> resposta = null;

        try {
            String jpql = "select p from PlanoDeTrabalho p";
            TypedQuery<PlanoDeTrabalho> query = em.createQuery(jpql, PlanoDeTrabalho.class);

            resposta = query.getResultList();
            System.out.println("Total de planos no banco: " + (resposta != null ? resposta.size() : 0));

            if (resposta != null && !resposta.isEmpty()) {
                for (PlanoDeTrabalho p : resposta) {
                    System.out.println("Plano ID: " + p.getId() + " | Título: " + p.getTitulo());
                    if (p.getInscricao() != null && p.getInscricao().getPessoa() != null) {
                        System.out.println("  -> Bolsista: " + p.getInscricao().getPessoa().getNomeCompleto() +
                                " (ID: " + p.getInscricao().getPessoa().getId() + ")");
                    }
                }
            }

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

}