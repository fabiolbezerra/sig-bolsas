package br.edu.ufra.bean;

import br.edu.ufra.entidade.Formulario;
import br.edu.ufra.rn.FormularioRN;
import br.edu.ufra.util.UtilBean;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import java.util.List;

@Named
@RequestScoped
public class FormularioBean {
    private final FormularioRN RN = new FormularioRN();

    private Formulario formulario = new Formulario();

    private List<Formulario> formularios;

    public FormularioBean() {
        System.out.println("Criando FormularioBean ...");
    }

    @PostConstruct
    public void init() {
        System.out.println("Inicializando FormularioBean ...");
        formularios = RN.listarTodos();
    }

    public List<Formulario> getFormularios() {
        return formularios;
    }

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    public void salvar() {
        if (formulario == null) {
            //Mensagem de erro: objeto nulo
            UtilBean.criarMensagemDeErro("Objeto nulo");
        } else {
            if (RN.salvar(formulario)) {
                //Mensagem de sucesso
                UtilBean.criarMensagemDeInformacao("Formulário gravado com sucesso");
            } else {
                //Mensagem de erro: problemas ao salvar
                UtilBean.criarMensagemDeErro("Problema ao gravar o formulário");
            }
        }
    }
}
