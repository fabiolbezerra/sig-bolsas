package br.edu.ufra.dao;

import br.edu.ufra.entidade.LogAuditoria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class LogAuditoriaDAO extends GenericDAO<LogAuditoria> {

    public List<LogAuditoria> listarRecentes(int limite) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<LogAuditoria> q = em.createQuery(
                "SELECT l FROM LogAuditoria l ORDER BY l.dataHora DESC", LogAuditoria.class);
            q.setMaxResults(limite);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return null;
        } finally {
            em.close();
        }
    }

    public List<LogAuditoria> listarPorUsuario(String usuario) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<LogAuditoria> q = em.createQuery(
                "SELECT l FROM LogAuditoria l WHERE l.usuario = :usuario ORDER BY l.dataHora DESC",
                LogAuditoria.class);
            q.setParameter("usuario", usuario);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return null;
        } finally {
            em.close();
        }
    }

    public List<LogAuditoria> listarPorPeriodo(LocalDate inicio, LocalDate fim) {
        EntityManager em = getEntityManager();
        try {
            LocalDateTime ini = inicio.atStartOfDay();
            LocalDateTime end = fim.atTime(23, 59, 59);
            TypedQuery<LogAuditoria> q = em.createQuery(
                "SELECT l FROM LogAuditoria l WHERE l.dataHora BETWEEN :ini AND :end ORDER BY l.dataHora DESC",
                LogAuditoria.class);
            q.setParameter("ini", ini);
            q.setParameter("end", end);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return null;
        } finally {
            em.close();
        }
    }

    public List<LogAuditoria> listarPorAcao(String acao) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<LogAuditoria> q = em.createQuery(
                "SELECT l FROM LogAuditoria l WHERE l.acao = :acao ORDER BY l.dataHora DESC",
                LogAuditoria.class);
            q.setParameter("acao", acao);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return null;
        } finally {
            em.close();
        }
    }

    public List<LogAuditoria> listarComFiltros(String usuario, String acao, LocalDate dataInicio, LocalDate dataFim) {
        EntityManager em = getEntityManager();
        try {
            StringBuilder jpql = new StringBuilder("SELECT l FROM LogAuditoria l WHERE 1=1");

            if (usuario != null && !usuario.trim().isEmpty()) {
                jpql.append(" AND LOWER(l.usuario) LIKE LOWER(:usuario)");
            }
            if (acao != null && !acao.trim().isEmpty()) {
                jpql.append(" AND l.acao = :acao");
            }
            if (dataInicio != null && dataFim != null) {
                jpql.append(" AND l.dataHora BETWEEN :ini AND :end");
            }
            jpql.append(" ORDER BY l.dataHora DESC");

            TypedQuery<LogAuditoria> q = em.createQuery(jpql.toString(), LogAuditoria.class);

            if (usuario != null && !usuario.trim().isEmpty()) {
                q.setParameter("usuario", "%" + usuario.trim() + "%");
            }
            if (acao != null && !acao.trim().isEmpty()) {
                q.setParameter("acao", acao);
            }
            if (dataInicio != null && dataFim != null) {
                LocalDateTime ini = dataInicio.atStartOfDay();
                LocalDateTime end = dataFim.atTime(23, 59, 59);
                q.setParameter("ini", ini);
                q.setParameter("end", end);
            }

            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return null;
        } finally {
            em.close();
        }
    }
}