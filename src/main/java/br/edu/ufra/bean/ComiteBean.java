package br.edu.ufra.bean;

import br.edu.ufra.entidade.Comite;
import br.edu.ufra.rn.ComiteRN;
import br.edu.ufra.util.UtilBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Date;

@ManagedBean
@ViewScoped
public class ComiteBean implements Serializable {
    private final ComiteRN RN = new ComiteRN();

    private Comite comite = new Comite();

    private List<Comite> comites;
    private String todos;
    private String termoPesquisa;
    private String campoTabela;


    public ComiteBean() {
        System.out.println("Criando ComiteBean ...");
    }

    @PostConstruct
    public void init() {
        System.out.println("Inicializando ComiteBean ...");
        comites = RN.pesquisarPor("","portaria");
        FacesContext currentInstance = FacesContext.getCurrentInstance();
        String comiteID = currentInstance.getExternalContext().getRequestParameterMap().get("comite");
        if (comiteID != null) {
            try {
                int id = Integer.parseInt(comiteID);
                comite = RN.obter(id);
                if (comite == null) {
                    comite = new Comite();
                    UtilBean.criarMensagemDeAviso("Não foi possivel recuperar o comite. Código inválido.");
                }
            } catch (Exception e) {
                comite = new Comite();
                UtilBean.criarMensagemDeErro("Não foi possível recuperar o comite.");
            }
        }

    }

    public String getCampoTabela() {
        return campoTabela;
    }

    public void setCampoTabela(String campoTabela) {
        this.campoTabela = campoTabela;
    }

    public String getTermoPesquisa() {
        return termoPesquisa;
    }

    public void setTermoPesquisa(String termoPesquisa) {
        this.termoPesquisa = termoPesquisa;
    }

    public List<Comite> getComites() {
        return comites;
    }

    public Comite getComite() {
        return comite;
    }

    public void setComite(Comite comite) {
        this.comite = comite;
    }

    public String getTodos() {
        return todos;
    }

    public void setTodos(String todos) {
        this.todos = todos;
    }

    public void setComites(Comite comite) {
        this.comite = comite;
    }

    public void salvar() {
        if (comite == null) {
            //Mensagem de erro: objeto nulo
            UtilBean.criarMensagemDeErro("Objeto nulo");
        } else {
            if (RN.salvar(comite)) {
                //Mensagem de sucesso
                UtilBean.criarMensagemDeInformacao("Comitê gravado com sucesso");
            } else {
                //Mensagem de erro: problemas ao salvar
                UtilBean.criarMensagemDeErro("Problema ao gravar o comitê");
            }
        }
    }

    public List<Comite> listarVigentes() {
        String andSQL = "";
        if (termoPesquisa != null && campoTabela != null && !campoTabela.equals("T")){
            if (campoTabela.equals("portaria")){
               andSQL += "and c.portaria like '%" + termoPesquisa + "%'";
            }
            if (campoTabela.equals("inicio")){
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Formato SQL
                Date dataPesquisa;
                try {
                    dataPesquisa = sdf.parse(termoPesquisa); // Converte a String para Date
                    String dataFormatadaSQL = sqlDateFormat.format(dataPesquisa);
                    andSQL += "and c.inicio = '" + dataFormatadaSQL+ "'";
                } catch (Exception e) {
                    UtilBean.criarMensagemDeErro("Formato de data inválido. Use o padrão dd/MM/yyyy.");
                    return Collections.emptyList(); // retorna uma lista vazia ou outra ação
                }

            }
            if (campoTabela.equals("fim")){
                andSQL += "and c.fim = " + termoPesquisa ;
            }

        }

        return comites = RN.listarVigentes(andSQL);

    }

    public List<Comite> listarTodos() {
        return comites = RN.listarTodos();

    }

    public List<Comite> listarEncerrados() {
        return comites = RN.listarEncerrados();

    }
    public List<Comite> pesquisarPor() {
        return comites = RN.pesquisarPor(termoPesquisa,campoTabela);
    }
}
