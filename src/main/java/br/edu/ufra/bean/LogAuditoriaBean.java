package br.edu.ufra.bean;

import br.edu.ufra.entidade.LogAuditoria;
import br.edu.ufra.entidade.tipo.EnumAcaoAuditoria;
import br.edu.ufra.rn.LogAuditoriaRN;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.application.FacesMessage;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Named
@ViewScoped
public class LogAuditoriaBean implements Serializable {

    private final LogAuditoriaRN RN = new LogAuditoriaRN();

    private List<LogAuditoria> logs;
    private String filtroUsuario;
    private String filtroAcao;
    private LocalDate filtroInicio;
    private LocalDate filtroFim;

    @PostConstruct
    public void init() {
        logs = RN.listarRecentes(200);
    }

    public void pesquisar() {
        // Validação de período
        if (filtroInicio != null && filtroFim != null && filtroInicio.isAfter(filtroFim)) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Data inicial não pode ser maior que data final"));
            return;
        }

        // Filtro combinado
        logs = RN.listarComFiltros(filtroUsuario, filtroAcao, filtroInicio, filtroFim);

        if (logs == null || logs.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Nenhum registro encontrado com os filtros aplicados"));
            logs = List.of();
        }
    }

    public void limparFiltros() {
        filtroUsuario = null;
        filtroAcao = null;
        filtroInicio = null;
        filtroFim = null;
        logs = RN.listarRecentes(200);
    }

    public void exportarCSV() {
        // Implementação de exportação será feita no próximo passo
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Funcionalidade de exportação será implementada"));
    }

    public EnumAcaoAuditoria[] getAcoesDisponiveis() {
        return EnumAcaoAuditoria.values();
    }

    public String getSeveridadePorAcao(String acao) {
        try {
            return EnumAcaoAuditoria.valueOf(acao).getSeveridade();
        } catch (IllegalArgumentException e) {
            return "info";
        }
    }

    public List<LogAuditoria> getLogs() { return logs; }
    public String getFiltroUsuario() { return filtroUsuario; }
    public void setFiltroUsuario(String filtroUsuario) { this.filtroUsuario = filtroUsuario; }
    public String getFiltroAcao() { return filtroAcao; }
    public void setFiltroAcao(String filtroAcao) { this.filtroAcao = filtroAcao; }
    public LocalDate getFiltroInicio() { return filtroInicio; }
    public void setFiltroInicio(LocalDate filtroInicio) { this.filtroInicio = filtroInicio; }
    public LocalDate getFiltroFim() { return filtroFim; }
    public void setFiltroFim(LocalDate filtroFim) { this.filtroFim = filtroFim; }
}