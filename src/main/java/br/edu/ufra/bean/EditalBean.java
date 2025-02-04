package br.edu.ufra.bean;

import br.edu.ufra.entidade.Comite;
import br.edu.ufra.entidade.Edital;
import br.edu.ufra.rn.ComiteRN;
import br.edu.ufra.rn.EditalRN;
import br.edu.ufra.util.UtilBean;
import org.primefaces.util.LangUtils;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.util.Locale;
import java.util.List;

@ManagedBean
@RequestScoped
public class EditalBean implements Serializable {
    private final EditalRN EDITAL_RN = new EditalRN();
    private final ComiteRN COMITE_RN = new ComiteRN();

    private Edital edital = new Edital();

    private List<Edital> editais;
    private List<Comite> comites;

    private List<Edital> filteredEditais;
    private boolean globalFilterOnly;

    public EditalBean() {
        System.out.println("Criando EditalBean ...");
    }

    @PostConstruct
    public void init() {
        globalFilterOnly = false;
        System.out.println("Inicializando EditalBean ...");
        editais = EDITAL_RN.listarTodas();
        comites = COMITE_RN.listarTodas();
    }

    public void toggleGlobalFilter() {
        setGlobalFilterOnly(!isGlobalFilterOnly());
    }

    public List<Edital> getFilteredEditais() {
        return filteredEditais;
    }
    public void setFilteredEditais(List<Edital> filteredEditais) {
        this.filteredEditais = filteredEditais;
    }

    public boolean isGlobalFilterOnly() {
        return globalFilterOnly;
    }

    public void setGlobalFilterOnly(boolean globalFilterOnly) {
        this.globalFilterOnly = globalFilterOnly;
    }
    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isBlank(filterText)) {
            return true;
        }

        Edital edital = (Edital) value;
        return edital.getTitulo().toLowerCase().contains(filterText)
                || edital.getAno().toString().toLowerCase().contains(filterText)
                || edital.getNumero().toLowerCase().contains(filterText)
                || edital.getDataInicio().toString().toLowerCase().contains(filterText)
                || edital.getDataFinal().toString().toLowerCase().contains(filterText)
                || edital.getSituacao().toString().toLowerCase().contains(filterText)
                || edital.getTipo().toString().toLowerCase().contains(filterText);
    }

    public List<Edital> getEditais() {
        return editais;
    }

    public List<Comite> getComites() {
        return comites;
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
