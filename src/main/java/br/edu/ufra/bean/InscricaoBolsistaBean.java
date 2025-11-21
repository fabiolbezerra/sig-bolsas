package br.edu.ufra.bean;

import br.edu.ufra.entidade.*;
import br.edu.ufra.rn.*;
import br.edu.ufra.util.UtilBean;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import java.util.List;

@Named
@RequestScoped
public class InscricaoBolsistaBean {
    private final EditalRN EDITAL_RN = new EditalRN();
    private final PlanoDeTrabalhoRN planoRN = new PlanoDeTrabalhoRN();
    private final PessoaRN PESSOA_RN = new PessoaRN();
    private final InscricaoRN INSCRICAO_RN = new InscricaoRN();
    private final InscricaoBolsistaRN inscricaoBolsistaRN = new InscricaoBolsistaRN();

    private InscricaoBolsista inscricaoBolsista = new InscricaoBolsista();
    private Edital edital;
    private PlanoDeTrabalho planoDeTrabalho;
    private List<PlanoDeTrabalho> planosDeTrabalho;
    private Pessoa bolsista;
    private Inscricao inscricao;
    private String senha;
    private static String formulario;

    private List<InscricaoBolsista> inscricoes;

    public InscricaoBolsistaBean() {
        System.out.println("Criando InscricaoBolsitaBean ...");
    }

    @PostConstruct
    public void init() {
        System.out.println("Inicializando InscricaoBolsistaBean ...");

        try {
            String idInscricaoBolsista = UtilBean.obterValor("inscricaoBolsistaID");
            if (idInscricaoBolsista != null && !idInscricaoBolsista.isEmpty()) {
                int inscricaoBolsistaID = Integer.parseInt(idInscricaoBolsista);
                inscricaoBolsista = inscricaoBolsistaRN.obter(inscricaoBolsistaID);
                if (inscricaoBolsista == null) {
                    inscricaoBolsista = new InscricaoBolsista();
                } else {
                    // Popular dados derivados quando acessar pelos detalhes da inscrição
                    bolsista = inscricaoBolsista.getPessoa();
                    planoDeTrabalho = inscricaoBolsista.getPlanoDeTrabalho();
                    if (planoDeTrabalho != null && planoDeTrabalho.getInscricao() != null) {
                        inscricao = planoDeTrabalho.getInscricao();
                        if (inscricao != null && inscricao.getEdital() != null) {
                            edital = inscricao.getEdital();
                        }
                    }
                }
            }
            String idInscricao = UtilBean.obterValor("inscricaoID");
            if (idInscricao != null && !idInscricao.isEmpty()) {
                int inscricaoID = Integer.parseInt(idInscricao);
                inscricao = INSCRICAO_RN.obter(inscricaoID);
                if (inscricao == null) {
                    inscricao = new Inscricao();
                }
            }

            // Carrega o plano se tiver ID na URL
            String idPlano = UtilBean.obterValor("planoID");
            if (idPlano != null && !idPlano.isEmpty()) {
                int planoID = Integer.parseInt(idPlano);
                System.out.println("Carregando plano ID: " + planoID);
                planoDeTrabalho = planoRN.obter(planoID);

                // Carrega o edital a partir do plano de trabalho
                if (planoDeTrabalho != null && planoDeTrabalho.getInscricao() != null && 
                    planoDeTrabalho.getInscricao().getEdital() != null) {
                    edital = planoDeTrabalho.getInscricao().getEdital();
                    System.out.println("Carregando edital a partir do plano: " + edital.getId());
                }
            }

            String idbolsista = UtilBean.obterValor("bolsistaID");
            if (idbolsista != null && !idbolsista.isEmpty()) {
                int bolsistaID = Integer.parseInt(idbolsista);
                bolsista = PESSOA_RN.obter(bolsistaID);
                System.out.println("Carregando bolsista ID: " + bolsistaID);
            }
        } catch (Exception e) {
            System.out.println("Erro ao inicializar InscricaoBolsistaBean: " + e.getMessage());
            UtilBean.criarMensagemDeErro("Erro ao carregar inscrição do Bolsita");
        }
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public static String getFormulario() {
        return formulario;
    }

    public static void setFormulario(String formulario) {
        InscricaoBolsistaBean.formulario = formulario;
    }
    
    public String obterSituacao(Character situacao) {
        if (situacao == null) {
            return "";
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
                return "";
        }
    }
    
    public String obterTipo(Character tipo) {
        if (tipo == null) {
            return "";
        }
        switch (tipo) {
            case 'P':
                return "Pesquisa";
            case 'E':
                return "Extensão";
            case 'D':
                return "Desenvolvimento Tecnológico";
            default:
                return "";
        }
    }

    public List<InscricaoBolsista> getInscricoes() {
        return inscricoes;
    }

    public InscricaoBolsista getInscricaoBolsista() {
        return inscricaoBolsista;
    }

    public void setInscricaoBolsista(InscricaoBolsista inscricaoBolsista) {
        this.inscricaoBolsista = inscricaoBolsista;
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

    public List<PlanoDeTrabalho> getPlanosDeTrabalho() {
        return planosDeTrabalho;
    }

    public void setPlanosDeTrabalho(List<PlanoDeTrabalho> planosDeTrabalho) {
        this.planosDeTrabalho = planosDeTrabalho;
    }

    public PlanoDeTrabalho getPlanoDeTrabalho() {
        return planoDeTrabalho;
    }

    public void setPlanoDeTrabalho(PlanoDeTrabalho planoDeTrabalho) {
        this.planoDeTrabalho = planoDeTrabalho;
    }

    public Pessoa getBolsista() {
        return bolsista;
    }

    public void setBolsista(Pessoa bolsista) {
        this.bolsista = bolsista;
    }

    public String voltar() {
        if (formulario.equals("lista-vigentes.xhtml")) {
            return "/bolsista/plano/lista.xhtml?faces-redirect=true";
        } else {
            return "/gerente/plano/lista.xhtml?faces-redirect=true";
        }
    }
    public String iniciarInscricaoBolsista() {
        if (!UtilBean.possuiPapel(UtilBean.obterUsuarioLogado(), 'B')) {
            UtilBean.criarMensagemDeErro("Apenas bolsistas podem se inscrever em um plano de trabalho.");
            return null;
        }
        if (bolsista == null || planoDeTrabalho == null) {
            UtilBean.criarMensagemDeErro("Dados incompletos para iniciar inscrição do Bolsista");
            return null;
        }

        // Carrega o edital associado ao plano de trabalho
        if (planoDeTrabalho.getInscricao() != null && planoDeTrabalho.getInscricao().getEdital() != null) {
            edital = planoDeTrabalho.getInscricao().getEdital();
        }

        inscricaoBolsista = inscricaoBolsistaRN.obter(bolsista, planoDeTrabalho);
        if (inscricaoBolsista != null) {
            return "/bolsista/inscricao/detalhes.xhtml?faces-redirect=true&inscricaoBolsistaID=" + inscricaoBolsista.getId();
        } else {
            return "/bolsista/inscricao/formulario.xhtml?faces-redirect=true&planoID=" + planoDeTrabalho.getId() + "&bolsistaID=" + bolsista.getId();
        }
    }

    public List<InscricaoBolsista> obter() {
        return inscricaoBolsistaRN.obter(UtilBean.obterUsuarioLogado()) ;
    }

    public void salvar() {
        try {
            if (inscricaoBolsista == null) {
                UtilBean.criarMensagemDeErro("Inscrição do Bosista  inválida");
                return;
            }

            if (inscricaoBolsistaRN.salvar(inscricaoBolsista)) {
                UtilBean.criarMensagemDeInformacao("Inscrição do bolsista salva com sucesso");
            } else {
                UtilBean.criarMensagemDeErro("Falha ao salvar inscrição do bolsista");
            }
        } catch (Exception e) {
            UtilBean.criarMensagemDeErro("Erro ao salvar inscrição do bolsista: " + e.getMessage());
        }
    }

    public String novaInscricaoBolsista() {
        try {
            if (bolsista == null || bolsista.getId() == null) {
                UtilBean.criarMensagemDeErro("Bolsista não encontrado");
                return null;
            }
            
            if (planoDeTrabalho == null || planoDeTrabalho.getId() == null) {
                UtilBean.criarMensagemDeErro("Plano de trabalho não encontrado");
                return null;
            }
            
            // Recarregar o bolsista para garantir que temos todos os dados
            bolsista = PESSOA_RN.obter(bolsista.getId());
            
            // Recarregar o plano de trabalho para garantir que temos todos os dados
            planoDeTrabalho = planoRN.obter(planoDeTrabalho.getId());

            boolean inscricaoBolsistaCriada = inscricaoBolsistaRN.novaInscricaoBolsista(planoDeTrabalho, bolsista, senha, bolsista.getEmail());

            if (inscricaoBolsistaCriada) {
                UtilBean.criarMensagemDeInformacao("Inscrição gravada com sucesso");

                // Buscar a inscrição do bolsista recém-criada para obter o ID
                inscricaoBolsista = inscricaoBolsistaRN.obter(bolsista, planoDeTrabalho);

                if (inscricaoBolsista != null) {
                    try {
                        FacesContext.getCurrentInstance().getExternalContext()
                                .redirect("detalhes.xhtml?faces-redirect=true&inscricaoBolsistaID=" + inscricaoBolsista.getId());
                    } catch (Exception e) {
                        UtilBean.criarMensagemDeErro("Falha ao redirecionar para detalhes: " + e.getMessage());
                    }
                    return null;
                } else {
                    UtilBean.criarMensagemDeErro("Inscrição do bolsista criada mas não foi possível obter os detalhes");
                    return null;
                }
            } else {
                UtilBean.criarMensagemDeErro("Problema ao gravar a Inscrição");
                return null;
            }
        } catch (Exception e) {
            UtilBean.criarMensagemDeErro("Erro ao salvar inscrição do Bolsista: " + e.getMessage());
            return null;
        }
    }
}
