package br.edu.ufra.bean;

import br.edu.ufra.entidade.InscricaoBolsista;
import br.edu.ufra.entidade.Pessoa;
import br.edu.ufra.rn.InscricaoBolsistaRN;
import br.edu.ufra.rn.LogAuditoriaRN;
import br.edu.ufra.util.UtilBean;
import br.edu.ufra.util.UtilEmail;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class TriagemBolsistaBean implements Serializable {

    private final InscricaoBolsistaRN RN = new InscricaoBolsistaRN();

    private List<InscricaoBolsista> inscricoes;
    private InscricaoBolsista selecionada;
    private String motivoIndeferimento;

    @PostConstruct
    public void init() {
        inscricoes = RN.listarTodas();
    }

    public void homologar(InscricaoBolsista inscricao) {
        if (inscricao == null) {
            UtilBean.criarMensagemDeErro("Inscrição inválida.");
            return;
        }
        inscricao.setSituacao('H');
        if (RN.salvar(inscricao)) {
            Pessoa responsavel = UtilBean.obterUsuarioLogado();
            LogAuditoriaRN.registrar(responsavel.getEmail(), "HOMOLOGAR", "InscricaoBolsista",
                inscricao.getId(), "Inscrição do bolsista homologada: " + inscricao.getProtocolo());
            notificar(inscricao, "Sua inscrição foi HOMOLOGADA e está em análise.",
                "Inscrição Homologada — SIG Bolsas");
            UtilBean.criarMensagemDeInformacao("Inscrição homologada. Bolsista notificado.");
        } else {
            UtilBean.criarMensagemDeErro("Erro ao homologar inscrição.");
        }
    }

    public void aprovar(InscricaoBolsista inscricao) {
        if (inscricao == null) {
            UtilBean.criarMensagemDeErro("Inscrição inválida.");
            return;
        }
        inscricao.setSituacao('A');
        if (RN.salvar(inscricao)) {
            Pessoa responsavel = UtilBean.obterUsuarioLogado();
            LogAuditoriaRN.registrar(responsavel.getEmail(), "APROVAR", "InscricaoBolsista",
                inscricao.getId(), "Inscrição do bolsista aprovada: " + inscricao.getProtocolo());
            notificar(inscricao, "Parabéns! Sua inscrição foi APROVADA.",
                "Resultado da Inscrição — SIG Bolsas");
            UtilBean.criarMensagemDeInformacao("Inscrição aprovada. Bolsista notificado.");
        } else {
            UtilBean.criarMensagemDeErro("Erro ao aprovar inscrição.");
        }
    }

    public void indeferir() {
        if (selecionada == null) {
            UtilBean.criarMensagemDeErro("Selecione uma inscrição.");
            return;
        }
        if (motivoIndeferimento == null || motivoIndeferimento.trim().isEmpty()) {
            UtilBean.criarMensagemDeErro("Informe o motivo do indeferimento.");
            return;
        }
        selecionada.setSituacao('I');
        if (RN.salvar(selecionada)) {
            Pessoa responsavel = UtilBean.obterUsuarioLogado();
            LogAuditoriaRN.registrar(responsavel.getEmail(), "INDEFERIR", "InscricaoBolsista",
                selecionada.getId(), "Indeferido. Motivo: " + motivoIndeferimento);
            notificarIndeferimento(selecionada, motivoIndeferimento);
            UtilBean.criarMensagemDeInformacao("Inscrição indeferida. Bolsista notificado.");
            motivoIndeferimento = null;
            selecionada = null;
        } else {
            UtilBean.criarMensagemDeErro("Erro ao indeferir inscrição.");
        }
    }

    private void notificar(InscricaoBolsista inscricao, String mensagemStatus, String assunto) {
        Pessoa bolsista = inscricao.getPessoa();
        if (bolsista == null || bolsista.getEmail() == null) return;
        String plano = inscricao.getPlanoDeTrabalho() != null ? inscricao.getPlanoDeTrabalho().getTitulo() : "-";
        String msg = "Prezado(a) " + bolsista.getNomeCompleto() + ",\n\n" +
            mensagemStatus + "\n\n" +
            "Plano de trabalho: " + plano + "\n" +
            "Protocolo: " + inscricao.getProtocolo() + "\n\n" +
            "Acesse o sistema para mais informações.\n\nAtenciosamente,\nSIG-BOLSAS";
        UtilEmail.enviarEmail(bolsista.getEmail(), assunto, msg);
    }

    private void notificarIndeferimento(InscricaoBolsista inscricao, String motivo) {
        Pessoa bolsista = inscricao.getPessoa();
        if (bolsista == null || bolsista.getEmail() == null) return;
        String msg = "Prezado(a) " + bolsista.getNomeCompleto() + ",\n\n" +
            "Informamos que sua inscrição foi INDEFERIDA.\n\n" +
            "Motivo: " + motivo + "\n\n" +
            "Protocolo: " + inscricao.getProtocolo() + "\n\n" +
            "Atenciosamente,\nSIG-BOLSAS";
        UtilEmail.enviarEmail(bolsista.getEmail(), "Resultado da Inscrição — SIG Bolsas", msg);
    }

    public String obterDescricaoSituacao(Character situacao) {
        if (situacao == null) return "Pendente";
        switch (situacao) {
            case 'P': return "Pendente";
            case 'H': return "Homologada";
            case 'A': return "Aprovada";
            case 'I': return "Indeferida";
            default:  return "-";
        }
    }

    public List<InscricaoBolsista> getInscricoes() { return inscricoes; }
    public InscricaoBolsista getSelecionada() { return selecionada; }
    public void setSelecionada(InscricaoBolsista selecionada) { this.selecionada = selecionada; }
    public String getMotivoIndeferimento() { return motivoIndeferimento; }
    public void setMotivoIndeferimento(String motivoIndeferimento) { this.motivoIndeferimento = motivoIndeferimento; }
}
