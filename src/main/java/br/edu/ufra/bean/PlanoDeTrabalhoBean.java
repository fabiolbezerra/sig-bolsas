package br.edu.ufra.bean;

import br.edu.ufra.entidade.*;
import br.edu.ufra.rn.EditalRN;
import br.edu.ufra.rn.InscricaoRN;
import br.edu.ufra.rn.PessoaRN;
import br.edu.ufra.rn.PlanoDeTrabalhoRN;
import br.edu.ufra.util.UtilBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ViewScoped
@ManagedBean
public class PlanoDeTrabalhoBean implements Serializable {

    private final PlanoDeTrabalhoRN RN = new PlanoDeTrabalhoRN();
    private EditalRN editalRN = new EditalRN();
    private PessoaRN pessoaRN = new PessoaRN();
    private InscricaoRN inscricaoRN = new InscricaoRN();
    private PlanoDeTrabalho planoDeTrabalho = new PlanoDeTrabalho();

    private List<PlanoDeTrabalho> planosDeTrabalho;
    private String titulo;
    private String resumo;
    private Inscricao inscricao;
    private Edital edital;
    private Pessoa pesquisador;

    public PlanoDeTrabalhoBean() {
        System.out.println("Criando PlanoDeTrabalhoBean ...");
    }

    @PostConstruct
    public void init() {
        System.out.println("Inicializando PlanoDeTrabalhoBean ...");

        try {

            String idParam = UtilBean.obterValor("inscricaoID");
            if (idParam != null && !idParam.isEmpty()) {
                int inscricaoID = Integer.parseInt(idParam);
                inscricao = inscricaoRN.obter(inscricaoID);
                System.out.println("Carregando inscricao ID: " + inscricaoID);
            }

            String editalIdParam = UtilBean.obterValor("editalID");
            if (editalIdParam != null && !editalIdParam.isEmpty()) {
                int editalID = Integer.parseInt(editalIdParam);
                edital = editalRN.obter(editalID);
                System.out.println("Carregando edital ID: " + editalID);
            }

            String pesquisadorIdParam = UtilBean.obterValor("pesquisadorID");
            if (pesquisadorIdParam != null && !pesquisadorIdParam.isEmpty()) {
                int pesquisadorID = Integer.parseInt(pesquisadorIdParam);
                pesquisador = pessoaRN.obter(pesquisadorID);
                System.out.println("Carregando pesquisador ID: " + pesquisadorID);
            }
            String planoIdParam = UtilBean.obterValor("planoID");
            if (planoIdParam != null && !planoIdParam.isEmpty()) {
                int planoID = Integer.parseInt(planoIdParam);
                planoDeTrabalho = RN.obter(planoID);
                System.out.println("Carregando plano ID: " + planoID);
            }
            planosDeTrabalho = obterInscricaoPlanos();

        } catch (Exception e) {
            System.out.println("Erro ao inicializar InscricaoBean: " + e.getMessage());
            UtilBean.criarMensagemDeErro("Erro ao carregar planoDeTrabalhoBean");
            planoDeTrabalho = new PlanoDeTrabalho();
        }
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public Pessoa getPesquisador() {
        return pesquisador;
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

    public void setPesquisador(Pessoa pesquisador) {
        this.pesquisador = pesquisador;
    }
    public List<PlanoDeTrabalho> obterInscricaoPlanos() {
        return RN.obter(inscricao);
    }
    public String salvar() {
        if (planoDeTrabalho == null) {
            UtilBean.criarMensagemDeErro("Plano de trabalho não encontrado!");
            return null;
        }
        if (planoDeTrabalho.getInscricao() == null) {
            planoDeTrabalho.setInscricao(this.inscricao);
        }
        if (planoDeTrabalho.getDataCadastro() == null) {
            planoDeTrabalho.setDataCadastro(new Date());
        }

        if (RN.salvar(planoDeTrabalho)) {
            UtilBean.criarMensagemDeInformacao("Plano de trabalho gravado com sucesso");
            return "formulario-plano.xhtml?faces-redirect=true";
        } else {
            UtilBean.criarMensagemDeErro("Problema ao gravar o plano de trabalho");
            return null;
        }
    }
    public void excluir(PlanoDeTrabalho planoDeTrabalho) {
        if (planoDeTrabalho == null) {
            //Mensagem de erro: objeto nulo
            UtilBean.criarMensagemDeErro("Objeto nulo");
        } else {
            if (RN.excluir(planoDeTrabalho)) {
                //Mensagem de sucesso
                UtilBean.criarMensagemDeInformacao("Plano excluído com sucesso");
            } else {
                //Mensagem de erro: problemas ao salvar
                UtilBean.criarMensagemDeErro("Problema ao excluir Plano de Trabalho");
            }
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
}
