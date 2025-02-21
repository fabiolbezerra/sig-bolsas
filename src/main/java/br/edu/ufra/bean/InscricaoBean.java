package br.edu.ufra.bean;

import br.edu.ufra.entidade.Edital;
import br.edu.ufra.entidade.Inscricao;
import br.edu.ufra.entidade.Pessoa;
import br.edu.ufra.rn.InscricaoRN;
import br.edu.ufra.util.UtilBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.List;

@ManagedBean
@RequestScoped
public class InscricaoBean {
    private final InscricaoRN RN = new InscricaoRN();

    private Inscricao inscricao = new Inscricao();
    private Pessoa pesquisador;
    private Edital edital;

    private List<Inscricao> inscricoes;

    public InscricaoBean() {
        System.out.println("Criando InscricaoBean ...");
    }

    @PostConstruct
    public void init() {
        System.out.println("Inicializando InscricaoBean ...");
        inscricoes = RN.listarTodos();
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

    public Pessoa getUsuarioLogado() {
        return UtilBean.obterUsuarioLogado();
    }

    public String iniciarInscricao() {
        inscricao = RN.obter(pesquisador, edital);
        if (inscricao != null) {
            return "/pesquisador/inscricao/detalhes.xhtml?faces-redirect=true";
        } else {
            return "/pesquisador/inscricao/formulario.xhtml?faces-redirect=true";
        }
    }
}
