package br.edu.ufra.bean;

import br.edu.ufra.entidade.Comite;
import br.edu.ufra.entidade.ComitePessoa;
import br.edu.ufra.entidade.Edital;
import br.edu.ufra.entidade.Pessoa;
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
    private static String formulario;

    public ComiteBean() {
        System.out.println("Criando ComiteBean ...");
    }

    @PostConstruct
    public void init() {
        System.out.println("Inicializando ComiteBean ...");

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
        Pessoa usuarioLogado = UtilBean.obterUsuarioLogado();
        if (comite.getId() == null && UtilBean.possuiPapel(usuarioLogado, 'C')) {
            comite.setCoordenador(usuarioLogado);
        }
    }

    public static String getFormulario() {
        return formulario;
    }

    public static void setFormulario(String formulario) {
        ComiteBean.formulario = formulario;
    }

    public List<Comite> getComites() {
        setFormulario("detalhe/lista.xhtml");
        setFormulario("editar/lista.xhtml");
        return comites = RN.listarTodos();
    }

    public List<Comite> listarVigentes() {
        setFormulario("detalhe/lista-vigentes.xhtml");
        setFormulario("editar/lista-vigentes.xhtml");
        return comites = RN.listarVigentes();

    }

    public List<Comite> listarEncerrados() {
        setFormulario("detalhe/lista-encerrados.xhtml");
        return comites = RN.listarEncerrados();
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
        } else if (comite.getInicio().after(comite.getFim())) {
            UtilBean.criarMensagemDeErro("Data inicial não pode ser maior que a data final.");
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

    public List<Comite> obterComites(Character tipo) {
        switch (tipo) {
            case 'V': {
                return comites = RN.listarVigentes();
            }
            case 'E': {
                return comites = RN.listarEncerrados();
            }
            default: {
                return comites = RN.listarTodos();
            }
        }
    }

    public String voltar() {
        if (formulario.equals("detalhe/lista-vigentes.xhtml")) {
            return "/pesquisador/comite/lista-vigentes.xhtml?faces-redirect=true";
        } else if (formulario.equals("editar/lista-vigentes.xhtml")) {
            return "/pesquisador/comite/lista-vigentes.xhtml?faces-redirect=true";
        } else if (formulario.equals("detalhe/lista-encerrados.xhtml")) {
            return "/pesquisador/comite/lista-encerrados.xhtml?faces-redirect=true";
        } else if (formulario.equals("detalhe/lista.xhtml")) {
            return "/pesquisador/comite/lista.xhtml?faces-redirect=true";
        } else if (formulario.equals("editar/lista.xhtml")) {
            return "/pesquisador/comite/lista.xhtml?faces-redirect=true";
        } else {
            return null;
        }
    }
}

