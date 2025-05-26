package br.edu.ufra.bean;

import br.edu.ufra.entidade.Comite;
import br.edu.ufra.rn.ComiteRN;
import br.edu.ufra.util.UtilBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@ViewScoped
public class ComiteBean implements Serializable {
    private final ComiteRN RN = new ComiteRN();

    private Comite comite = new Comite();

    private List<Comite> comites;
    private String todos;
    public ComiteBean() {
        System.out.println("Criando ComiteBean ...");
    }

    @PostConstruct
    public void init() {
        System.out.println("Inicializando ComiteBean ...");
        comites = RN.listarTodos();

        String comiteID = UtilBean.obterValor("comite");
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
        return comites = RN.listarVigentes();

    }

    public List<Comite> listarTodos() {
        return comites = RN.listarTodos();

    }

    public List<Comite> listarEncerrados() {
        return comites = RN.listarEncerrados();

    }

}
