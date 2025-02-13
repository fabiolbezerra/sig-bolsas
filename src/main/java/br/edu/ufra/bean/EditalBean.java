package br.edu.ufra.bean;

import br.edu.ufra.entidade.Edital;
import br.edu.ufra.rn.EditalRN;
import br.edu.ufra.util.UtilBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@RequestScoped
public class EditalBean implements Serializable {
    private final EditalRN EDITAL_RN = new EditalRN();

    private Edital edital = new Edital();

    private List<Edital> editais;


    public EditalBean() {
        System.out.println("Criando EditalBean ...");
    }

    @PostConstruct
    public void init() {
        System.out.println("Inicializando EditalBean ...");
        editais = EDITAL_RN.listarTodos();
    }

    public List<Edital> getEditais() {
        return editais;
    }

    public List<Edital> getEditaisAbertos() {
        return EDITAL_RN.obterVigentes();
    }

    public Edital getEdital() {
        return edital;
    }

    public void setEdital(Edital edital) {
        this.edital = edital;
    }

    public String obterSituacao(Character situacao) {
        switch (situacao) {
            case 'C': return "Cadastrado";
            case 'P': return "Publicado";
            case 'N': return "Não publicado";
            case 'E': return "Encerrado";
            default: return "-";
        }
    }

    public String obterTipo(Character tipo) {
        switch (tipo) {
            case 'P': return "Pesquisa";
            case 'D': return "Desenvolvimento Tecnológico";
            case 'E': return "Extensão";
            default: return "-";
        }
    }

    public void salvar() {
        if (edital == null) {
            //Mensagem de erro: objeto nulo
            UtilBean.criarMensagemDeErro("Objeto nulo");
        } else {
            if (EDITAL_RN.salvar(edital)) {
                //Mensagem de sucesso
                UtilBean.criarMensagemDeInformacao("Edital gravado com sucesso");
            } else {
                //Mensagem de erro: problemas ao salvar
                UtilBean.criarMensagemDeErro("Problema ao gravar o edital");
            }
        }
    }
}
