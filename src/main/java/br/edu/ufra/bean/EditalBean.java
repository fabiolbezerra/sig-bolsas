package br.edu.ufra.bean;

import br.edu.ufra.entidade.Comite;
import br.edu.ufra.entidade.Edital;
import br.edu.ufra.rn.EditalRN;
import br.edu.ufra.util.UtilBean;
import org.primefaces.component.calendar.Calendar;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import java.io.Serializable;
import java.time.Year;
import java.util.Date;
import java.util.List;

@Named
@RequestScoped
public class EditalBean implements Serializable {
    private final EditalRN EDITAL_RN = new EditalRN();

    private Edital edital = new Edital();

    private List<Edital> editais;

    private static String formulario;

    public EditalBean() {
        System.out.println("Criando EditalBean ...");
    }

    @PostConstruct
    public void init() {
        System.out.println("Inicializando EditalBean ...");

        String editalID = UtilBean.obterValor("editalID");
        if (editalID != null) {
            try {
                int id = Integer.parseInt(editalID);
                edital = EDITAL_RN.obter(id);
                if (edital == null) {
                    edital = new Edital();
                    UtilBean.criarMensagemDeAviso("Não foi possivel recuperar o edital. Código inválido.");
                }
            } catch (Exception e) {
                edital = new Edital();
                UtilBean.criarMensagemDeErro("Não foi possível recuperar o edital.");
            }
        }
    }

    public static String getFormulario() {
        return formulario;
    }

    public static void setFormulario(String formulario) {
        EditalBean.formulario = formulario;
    }

    public List<Edital> getEditais() {
        setFormulario("lista.xhtml");
        return editais = EDITAL_RN.listarTodos();
    }
    public List<Edital> getEditaisAbertos() {
        setFormulario("lista-abertos.xhtml");
        return editais = EDITAL_RN.obterVigentes();
    }
    public List<Edital> getEditaisEncerrados() {
        setFormulario("lista-encerrados.xhtml");
        return editais = EDITAL_RN.obterEncerrados();
    }

    public Edital getEdital() {
        return edital;
    }

    public void setEdital(Edital edital) {
        this.edital = edital;
    }


    public String voltar() {
        if (formulario.equals("lista-abertos.xhtml")) {
            return "/bolsista/edital/lista-abertos.xhtml?faces-redirect=true";
        } else if (formulario.equals("lista-encerrados.xhtml")) {
            return "/gerente/edital/lista-encerrados.xhtml?faces-redirect=true";
        } else if (formulario.equals("lista.xhtml")) {
            return "/gerente/edital/lista.xhtml?faces-redirect=true";
        } else {
            return null;
        }
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
        } else if (edital.getDataInicio().after(edital.getDataFinal())) {
            UtilBean.criarMensagemDeErro("Data inicial não pode ser maior que a data final.");
        } else {
            int anoAtual = Year.now().getValue();
            if (edital.getAno() < anoAtual) {
                UtilBean.criarMensagemDeErro("Informe o ano atual.");
            } else if (EDITAL_RN.salvar(edital)) {
                //Mensagem de sucesso
                UtilBean.criarMensagemDeInformacao("Edital gravado com sucesso");
            } else {
                //Mensagem de erro: problemas ao salvar
                UtilBean.criarMensagemDeErro("Problema ao gravar o edital");
            }
        }
    }
}
