package br.edu.ufra.bean;

import br.edu.ufra.entidade.Edital;
import br.edu.ufra.entidade.Inscricao;
import br.edu.ufra.entidade.Pessoa;
import br.edu.ufra.rn.EditalRN;
import br.edu.ufra.rn.InscricaoRN;
import br.edu.ufra.util.UtilBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.List;

@ManagedBean
@RequestScoped
public class InscricaoBean {

    private final EditalRN EDITAL_RN = new EditalRN();
    private final InscricaoRN RN = new InscricaoRN();

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
            int inscricaoID = Integer.parseInt(UtilBean.obterValor("inscricaoID"));
            inscricao = RN.obter(inscricaoID);
            if (inscricao == null) {
                inscricao = new Inscricao();
            }
        } catch (Exception e) {
            System.out.println("Erro ao inicializar InscricaoBean " + e.getMessage());
        }
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
            UtilBean.criarMensagemDeErro("Pesquisador e/ou Edital são nulos.");
            return null;
        }
        inscricao = RN.obter(pesquisador, edital);
        if (inscricao != null) {
            //return "/pesquisador/inscricao/detalhes.xhtml?faces-redirect=true&inscricaoID=" + inscricao.getId();
            return "/pesquisador/inscricao/formulario.xhtml?faces-redirect=true&inscricaoID=" + inscricao.getId();
        } else {
            return "/pesquisador/inscricao/formulario.xhtml?faces-redirect=true";
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
        if (inscricao == null) {
            //Mensagem de erro: objeto nulo
            UtilBean.criarMensagemDeErro("Objeto nulo");
        } else {
            if (RN.salvar(inscricao)) {
                //Mensagem de sucesso
                UtilBean.criarMensagemDeInformacao("Inscrição gravada com sucesso");
            } else {
                //Mensagem de erro: problemas ao salvar
                UtilBean.criarMensagemDeErro("Problema ao gravar a Inscricao");
            }
        }
    }
}
