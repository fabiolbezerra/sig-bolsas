package br.edu.ufra.bean;

import br.edu.ufra.entidade.Pessoa;
import br.edu.ufra.rn.PessoaRN;
import br.edu.ufra.util.UtilBean;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

@ViewScoped
@ManagedBean

public class AutoCadastroBean {

    private final PessoaRN RN = new PessoaRN();

    private Pessoa pessoa = new Pessoa();

    private String email;
    private String codigoGerado;
    private String codigoDigitado;

    @PostConstruct
    public void init() {
        System.out.println("Iniciando AutoCadastroBean");
        FacesContext currentInstance = FacesContext.getCurrentInstance();
        String codigoRequisicao = currentInstance.getExternalContext().getRequestParameterMap().get("CODIGO");
        this.codigoGerado = codigoRequisicao;
    }

    public PessoaRN getRN() {
        return RN;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCodigoGerado() {
        return codigoGerado;
    }

    public void setCodigoGerado(String codigoGerado) {
        this.codigoGerado = codigoGerado;
    }

    public String getCodigoDigitado() {
        return codigoDigitado;
    }

    public void setCodigoDigitado(String codigoDigitado) {
        this.codigoDigitado = codigoDigitado;
    }

    public void gerarCodigo() {
        if (this.pessoa.getEmail() == null || this.pessoa.getEmail().trim().isEmpty()) {
            UtilBean.criarMensagemDeAviso("Informe um e-mail");
        } else {
            String resultadoEnvio = RN.iniciarValidacaoDoEmail(this.pessoa.getEmail());
            if (resultadoEnvio != null && resultadoEnvio.equals(this.codigoGerado)) {
                UtilBean.criarMensagemDeInformacao("Código gerado e enviado para o seu e-mail.");
            } else {
                UtilBean.criarMensagemDeErro("Erro ao enviar o código para o e-mail.");
            }
        }
    }

    public String validarCodigo() {
        if (this.codigoDigitado != null && this.codigoDigitado.equals(this.codigoGerado)) {
            return ""; //<--- inserir o endereço da página de criaçao do usuário
        } else {
            UtilBean.criarMensagemDeErro("Código Inválido");
            pessoa = new Pessoa();
            return null;
        }
    }
}


