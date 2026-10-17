package br.edu.ufra.bean;

import br.edu.ufra.entidade.AvaliacaoInscricao;
import br.edu.ufra.entidade.Edital;
import br.edu.ufra.entidade.Inscricao;
import br.edu.ufra.entidade.Pessoa;
import br.edu.ufra.entidade.tipo.EnumAcaoAuditoria;
import br.edu.ufra.rn.AvaliacaoInscricaoRN;
import br.edu.ufra.rn.EditalRN;
import br.edu.ufra.rn.InscricaoRN;
import br.edu.ufra.rn.LogAuditoriaRN;
import br.edu.ufra.util.UtilBean;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import java.util.List;

@Named
@RequestScoped
public class AvaliacaoBean {

    private final AvaliacaoInscricaoRN RN = new AvaliacaoInscricaoRN();
    private final InscricaoRN INSCRICAO_RN = new InscricaoRN();
    private final EditalRN EDITAL_RN = new EditalRN();

    private Edital edital;
    private Inscricao inscricao;
    private AvaliacaoInscricao avaliacao = new AvaliacaoInscricao();
    private List<Inscricao> inscricoes;
    private List<AvaliacaoInscricao> avaliacoes;
    private String motivoIndeferimento;

    @PostConstruct
    public void init() {
        try {
            String idEdital = UtilBean.obterValor("editalID");
            if (idEdital != null && !idEdital.isEmpty()) {
                edital = EDITAL_RN.obter(Integer.parseInt(idEdital));
                if (edital != null) {
                    inscricoes = INSCRICAO_RN.obter(edital);
                }
            }

            String idInscricao = UtilBean.obterValor("inscricaoID");
            if (idInscricao != null && !idInscricao.isEmpty()) {
                inscricao = INSCRICAO_RN.obter(Integer.parseInt(idInscricao));
                if (inscricao != null) {
                    avaliacoes = RN.obterPorInscricao(inscricao);
                    Pessoa avaliador = UtilBean.obterUsuarioLogado();
                    AvaliacaoInscricao existente = RN.obterPorPessoaEInscricao(avaliador, inscricao);
                    if (existente != null) {
                        avaliacao = existente;
                    } else {
                        avaliacao.setInscricao(inscricao);
                        avaliacao.setPessoa(avaliador);
                    }
                }
            }
        } catch (Exception e) {
            UtilBean.criarMensagemDeErro("Erro ao carregar dados de avaliação: " + e.getMessage());
        }
    }

    public void salvarAvaliacao() {
        Pessoa avaliador = UtilBean.obterUsuarioLogado();
        if (avaliador == null) {
            UtilBean.criarMensagemDeErro("Usuário não identificado.");
            return;
        }
        if (avaliacao.getPontuacaoAvaliacao() < 0 || avaliacao.getPontuacaoAvaliacao() > 10) {
            UtilBean.criarMensagemDeErro("A pontuação deve ser entre 0 e 10.");
            return;
        }
        avaliacao.setPessoa(avaliador);
        avaliacao.setInscricao(inscricao);

        if (RN.salvar(avaliacao)) {
            LogAuditoriaRN.registrar(avaliador.getEmail(), EnumAcaoAuditoria.AVALIAR, "Inscricao",
                inscricao.getId(), "Avaliação registrada: " + avaliacao.getPontuacaoAvaliacao() + " pts", null);
            UtilBean.criarMensagemDeInformacao("Avaliação salva com sucesso.");
            avaliacoes = RN.obterPorInscricao(inscricao);
        } else {
            UtilBean.criarMensagemDeErro("Erro ao salvar avaliação.");
        }
    }

    public void homologar() {
        Pessoa responsavel = UtilBean.obterUsuarioLogado();
        if (inscricao == null) {
            UtilBean.criarMensagemDeErro("Nenhuma inscrição selecionada.");
            return;
        }
        if (INSCRICAO_RN.homologar(inscricao, responsavel.getEmail())) {
            UtilBean.criarMensagemDeInformacao("Inscrição homologada e pesquisador notificado.");
        } else {
            UtilBean.criarMensagemDeErro("Erro ao homologar inscrição.");
        }
    }

    public void indeferir() {
        Pessoa responsavel = UtilBean.obterUsuarioLogado();
        if (inscricao == null) {
            UtilBean.criarMensagemDeErro("Nenhuma inscrição selecionada.");
            return;
        }
        if (motivoIndeferimento == null || motivoIndeferimento.trim().isEmpty()) {
            UtilBean.criarMensagemDeErro("Informe o motivo do indeferimento.");
            return;
        }
        if (INSCRICAO_RN.indeferir(inscricao, motivoIndeferimento, responsavel.getEmail())) {
            UtilBean.criarMensagemDeInformacao("Inscrição indeferida e pesquisador notificado.");
        } else {
            UtilBean.criarMensagemDeErro("Erro ao indeferir inscrição.");
        }
    }

    public void classificarTodas() {
        if (edital == null) {
            UtilBean.criarMensagemDeErro("Nenhum edital selecionado.");
            return;
        }
        Pessoa responsavel = UtilBean.obterUsuarioLogado();
        if (INSCRICAO_RN.classificar(edital, responsavel.getEmail())) {
            inscricoes = INSCRICAO_RN.obter(edital);
            UtilBean.criarMensagemDeInformacao("Pesquisadores classificados com sucesso.");
        } else {
            UtilBean.criarMensagemDeErro("Erro ao classificar pesquisadores.");
        }
    }

    public String obterDescricaoSituacao(Character situacao) {
        if (situacao == null) return "Pendente";
        switch (situacao) {
            case 'P': return "Pendente";
            case 'H': return "Homologada";
            case 'A': return "Aprovada";
            case 'I': return "Indeferida";
            default: return "-";
        }
    }

    public Edital getEdital() { return edital; }
    public void setEdital(Edital edital) { this.edital = edital; }
    public Inscricao getInscricao() { return inscricao; }
    public void setInscricao(Inscricao inscricao) { this.inscricao = inscricao; }
    public AvaliacaoInscricao getAvaliacao() { return avaliacao; }
    public void setAvaliacao(AvaliacaoInscricao avaliacao) { this.avaliacao = avaliacao; }
    public List<Inscricao> getInscricoes() { return inscricoes; }
    public List<AvaliacaoInscricao> getAvaliacoes() { return avaliacoes; }
    public String getMotivoIndeferimento() { return motivoIndeferimento; }
    public void setMotivoIndeferimento(String motivoIndeferimento) { this.motivoIndeferimento = motivoIndeferimento; }
}