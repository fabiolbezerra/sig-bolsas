package br.edu.ufra.bean;

import br.edu.ufra.entidade.Edital;
import br.edu.ufra.entidade.Inscricao;
import br.edu.ufra.entidade.Pessoa;
import br.edu.ufra.rn.EditalRN;
import br.edu.ufra.rn.InscricaoRN;
import br.edu.ufra.rn.PessoaRN;
import br.edu.ufra.util.UtilBean;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import java.util.List;

@Named
@RequestScoped
public class InscricaoBean {

    private final EditalRN EDITAL_RN = new EditalRN();
    private final PessoaRN PESSOA_RN = new PessoaRN();
    private final InscricaoRN INSCRICAO_RN = new InscricaoRN();

    private String senha;

    private Inscricao inscricao = new Inscricao();
    private Edital edital;
    private Pessoa pesquisador;

    private List<Inscricao> inscricoes;

    public InscricaoBean() {
        System.out.println("Criando InscricaoBean ...");
    }

    @PostConstruct
    public void init() {
        System.out.println("Inicializando InscricaoBean ...");

        try {
            String idInscricao = UtilBean.obterValor("inscricaoID");
            if (idInscricao != null && !idInscricao.isEmpty()) {
                int inscricaoID = Integer.parseInt(idInscricao);
                inscricao = INSCRICAO_RN.obter(inscricaoID);
                if (inscricao == null) {
                    inscricao = new Inscricao();
                }
            }

            // Carrega o edital se tiver ID na URL
            String idEdital = UtilBean.obterValor("editalID");
            if (idEdital != null && !idEdital.isEmpty()) {
                int editalID = Integer.parseInt(idEdital);
                System.out.println("Carregando edital ID: " + editalID);
                edital = EDITAL_RN.obter(editalID);
                if (edital != null) {
                    inscricoes = INSCRICAO_RN.obter(edital);
                }
            }

            // Carrega o pesquisador se tiver ID na URL
            String idPesquisador = UtilBean.obterValor("pesquisadorID");
            if (idPesquisador != null && !idPesquisador.isEmpty()) {
                int pesquisadorID = Integer.parseInt(idPesquisador);
                pesquisador = PESSOA_RN.obter(pesquisadorID);
                System.out.println("Carregando pesquisador ID: " + pesquisadorID);
            }
        } catch (Exception e) {
            System.out.println("Erro ao inicializar InscricaoBean: " + e.getMessage());
            UtilBean.criarMensagemDeErro("Erro ao carregar inscrição");
        }
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Inscricao> getInscricoes() {
        return inscricoes;
    }

    public Inscricao getInscricao() {
        return inscricao;
    }

    public void setInscricao(Inscricao inscricao) {
        this.inscricao = inscricao;
    }

    public Edital getEdital() {
        return edital;
    }

    public void setEdital(Edital edital) {
        this.edital = edital;
    }

    public Pessoa getPesquisador() {
        return pesquisador;
    }

    public void setPesquisador(Pessoa pesquisador) {
        this.pesquisador = pesquisador;
    }


    public String iniciarInscricao() {
        if (!UtilBean.possuiPapel(UtilBean.obterUsuarioLogado(), 'P')) {
            UtilBean.criarMensagemDeErro("Apenas pesquisadores podem se inscrever.");
            return null;
        }
        if (pesquisador == null || edital == null) {
            UtilBean.criarMensagemDeErro("Dados incompletos para iniciar inscrição");
            return null;
        }

        inscricao = INSCRICAO_RN.obter(pesquisador, edital);
        if (inscricao != null) {
            return "/pesquisador/inscricao/detalhes.xhtml?faces-redirect=true&inscricaoID=" + inscricao.getId();
        } else {
            return "/pesquisador/inscricao/formulario.xhtml?faces-redirect=true&editalID=" + edital.getId() + "&pesquisadorID=" + pesquisador.getId();
        }

    }

    public List<Inscricao> obterPorPesquisador() {
        return INSCRICAO_RN.obter(UtilBean.obterUsuarioLogado()) ;
    }

    public String obterSituacao(Character situacao) {
        if (situacao == null) {
            return null;
        }
        switch (situacao) {
            case 'C':
                return "Cadastrado";
            case 'P':
                return "Publicado";
            case 'N':
                return "Não publicado";
            case 'E':
                return "Encerrado";
            default:
                return "-";
        }
    }

    public String obterTipo(Character tipo) {
        if (tipo == null) {
            return null;
        }
        switch (tipo) {
            case 'P':
                return "Pesquisa";
            case 'D':
                return "Desenvolvimento Tecnológico";
            case 'E':
                return "Extensão";
            default:
                return "-";
        }
    }

    public void salvar() {
        try {
            if (inscricao == null) {
                UtilBean.criarMensagemDeErro("Inscrição inválida");
                return;
            }

            if (INSCRICAO_RN.salvar(inscricao)) {
                UtilBean.criarMensagemDeInformacao("Inscrição salva com sucesso");
            } else {
                UtilBean.criarMensagemDeErro("Falha ao salvar inscrição");
            }
        } catch (Exception e) {
            UtilBean.criarMensagemDeErro("Erro ao salvar inscrição: " + e.getMessage());
        }
    }

    public void novaInscricao() {
        try {
            // Se algum dos objetos for nulo, tenta carregá-los novamente usando seus IDs
            if (edital == null && edital.getId() != null) {
                edital = EDITAL_RN.obter(edital.getId());
            }

            if (pesquisador == null && pesquisador.getId() != null) {
                pesquisador = PESSOA_RN.obter(pesquisador.getId());
            }

            if (edital == null || pesquisador == null) {
                UtilBean.criarMensagemDeErro("Dados do edital ou pesquisador inválidos");
                return;
            }

            boolean inscricaoCriada = INSCRICAO_RN.novaInscricao(edital, pesquisador, senha, pesquisador.getEmail());

            if (inscricaoCriada) {
                UtilBean.criarMensagemDeInformacao("Inscrição gravada com sucesso");

                // Buscar a inscrição recém-criada para obter o ID
                inscricao = INSCRICAO_RN.obter(pesquisador, edital);

                if (inscricao != null) {
                    FacesContext.getCurrentInstance().getExternalContext()
                            .redirect("detalhes.xhtml?faces-redirect=true&inscricaoID=" + inscricao.getId());
                } else {
                    UtilBean.criarMensagemDeErro("Inscrição criada mas não foi possível obter os detalhes");
                }
            } else {
                UtilBean.criarMensagemDeErro("Problema ao gravar a Inscricao");
            }
        } catch (Exception e) {
            System.out.println("Erro em novaInscricao: " + e.getMessage());
            UtilBean.criarMensagemDeErro("Erro ao salvar inscrição: " + e.getMessage());
        }
    }
}
