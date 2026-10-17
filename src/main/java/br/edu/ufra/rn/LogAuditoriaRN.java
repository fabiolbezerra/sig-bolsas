package br.edu.ufra.rn;

import br.edu.ufra.dao.LogAuditoriaDAO;
import br.edu.ufra.entidade.LogAuditoria;
import br.edu.ufra.entidade.tipo.EnumAcaoAuditoria;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogAuditoriaRN {

    private static final LogAuditoriaDAO DAO = new LogAuditoriaDAO();
    private static final Logger LOGGER = Logger.getLogger(LogAuditoriaRN.class.getName());

    public static void registrar(String usuario, EnumAcaoAuditoria acao, String entidade, Integer entidadeId, String descricao, String ipAddress) {
        try {
            LogAuditoria log = new LogAuditoria();
            log.setUsuario(usuario != null ? usuario : "sistema");
            log.setAcao(acao.name());
            log.setEntidade(entidade);
            log.setEntidadeId(entidadeId);
            log.setDescricao(descricao);
            log.setIpAddress(ipAddress);
            log.setDataHora(LocalDateTime.now());
            DAO.criar(log);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao registrar log de auditoria: " + e.getMessage(), e);
        }
    }

    public static void registrar(String usuario, EnumAcaoAuditoria acao, String descricao) {
        registrar(usuario, acao, null, null, descricao, null);
    }

    public static void registrar(String usuario, EnumAcaoAuditoria acao, String descricao, String ipAddress) {
        registrar(usuario, acao, null, null, descricao, ipAddress);
    }

    // Métodos legados para retrocompatibilidade
    @Deprecated
    public static void registrar(String usuario, String acao, String entidade, Integer entidadeId, String descricao) {
        try {
            EnumAcaoAuditoria enumAcao = EnumAcaoAuditoria.valueOf(acao);
            registrar(usuario, enumAcao, entidade, entidadeId, descricao, null);
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Ação de auditoria inválida: " + acao);
        }
    }

    @Deprecated
    public static void registrar(String usuario, String acao, String descricao) {
        registrar(usuario, acao, null, null, descricao);
    }

    public List<LogAuditoria> listarRecentes(int limite) {
        return DAO.listarRecentes(limite);
    }

    public List<LogAuditoria> listarPorUsuario(String usuario) {
        return DAO.listarPorUsuario(usuario);
    }

    public List<LogAuditoria> listarPorPeriodo(LocalDate inicio, LocalDate fim) {
        if (inicio == null || fim == null) return null;
        return DAO.listarPorPeriodo(inicio, fim);
    }

    public List<LogAuditoria> listarPorAcao(String acao) {
        return DAO.listarPorAcao(acao);
    }

    public List<LogAuditoria> listarTodos() {
        return DAO.listarRecentes(500);
    }

    public List<LogAuditoria> listarComFiltros(String usuario, String acao, LocalDate dataInicio, LocalDate dataFim) {
        return DAO.listarComFiltros(usuario, acao, dataInicio, dataFim);
    }
}