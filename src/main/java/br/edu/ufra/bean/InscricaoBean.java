package br.edu.ufra.bean;

import br.edu.ufra.entidade.Edital;
import br.edu.ufra.entidade.Inscricao;
import br.edu.ufra.entidade.Pessoa;
import br.edu.ufra.rn.EditalRN;
import br.edu.ufra.rn.InscricaoRN;
import br.edu.ufra.rn.PessoaRN;
import br.edu.ufra.util.UtilBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.util.List;

@ManagedBean
@RequestScoped
public class InscricaoBean {

    private EditalRN editalRN = new EditalRN();

    // Correção 1: Inicializar o pessoaRN
    private PessoaRN pessoaRN = new PessoaRN();

    private InscricaoRN inscricaoRN = new InscricaoRN();

    private Inscricao inscricao;
    private Edital edital;
    private Pessoa pesquisador;
    private String senha;

    private List<Inscricao> inscricoes;

    public InscricaoBean() {
        System.out.println("Criando InscricaoBean ...");
    }

    @PostConstruct
    public void init() {
        System.out.println("Inicializando InscricaoBean ...");

        try {

                String idParam = UtilBean.obterValor("inscricaoID");
                if (idParam != null && !idParam.isEmpty()) {
                    int inscricaoID = Integer.parseInt(idParam);
                    inscricao = inscricaoRN.obter(inscricaoID);
                }

                if (inscricao == null) {
                    inscricao = new Inscricao();
                }

                // Carrega o edital se tiver ID na URL
                String editalIdParam = UtilBean.obterValor("editalID");
                if (editalIdParam != null && !editalIdParam.isEmpty()) {
                    int editalID = Integer.parseInt(editalIdParam);
                    edital = editalRN.obter(editalID);
                    System.out.println("Carregando edital ID: " + editalID);
                }

                // Carrega o pesquisador se tiver ID na URL
                String pesquisadorIdParam = UtilBean.obterValor("pesquisadorID");
                if (pesquisadorIdParam != null && !pesquisadorIdParam.isEmpty()) {
                    int pesquisadorID = Integer.parseInt(pesquisadorIdParam);
                    pesquisador = pessoaRN.obter(pesquisadorID);
                    System.out.println("Carregando pesquisador ID: " + pesquisadorID);
                }

        } catch (Exception e) {
            System.out.println("Erro ao inicializar InscricaoBean: " + e.getMessage());
            UtilBean.criarMensagemDeErro("Erro ao carregar inscrição");
            inscricao = new Inscricao();
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
        if (pesquisador == null || edital == null) {
            UtilBean.criarMensagemDeErro("Dados incompletos para iniciar inscrição");
            return null;
        }

        inscricao = inscricaoRN.obter(pesquisador, edital);
        if (inscricao != null) {
            return "/pesquisador/inscricao/detalhes.xhtml?faces-redirect=true&inscricaoID=" + inscricao.getId();
        } else {
            return "/pesquisador/inscricao/formulario-perguntas.xhtml?faces-redirect=true&editalID=" + edital.getId()+"&pesquisadorID=" + pesquisador.getId();
        }

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

            if (inscricaoRN.salvar(inscricao)) {
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
                edital = editalRN.obter(edital.getId());
            }

            if (pesquisador == null && pesquisador.getId() != null) {
                pesquisador = pessoaRN.obter(pesquisador.getId());
            }

            if (edital == null || pesquisador == null) {
                UtilBean.criarMensagemDeErro("Dados do edital ou pesquisador inválidos");
                return;
            }

            boolean inscricaoCriada = inscricaoRN.novaInscricao(edital, pesquisador, senha, pesquisador.getEmail());

            if (inscricaoCriada) {
                UtilBean.criarMensagemDeInformacao("Inscrição gravada com sucesso");

                // Buscar a inscrição recém-criada para obter o ID
                inscricao = inscricaoRN.obter(pesquisador, edital);

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