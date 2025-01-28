package br.edu.ufra.bean;

import br.edu.ufra.entidade.Pessoa;
import br.edu.ufra.rn.PessoaRN;
import br.edu.ufra.util.UtilBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.List;

@ManagedBean
@RequestScoped
public class PessoaBean {
    private final PessoaRN RN = new PessoaRN();

    private Pessoa pessoa = new Pessoa();
    private String senha;

    private List<Pessoa> pessoas;

    public PessoaBean() {
        System.out.println("Criando PessoaBean ...");
    }

    @PostConstruct
    public void init() {
        System.out.println("Inicializando PessoaBean ...");
        pessoas = RN.listarTodas();
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public void salvar() {
        if (pessoa == null) {
            //Mensagem de erro: objeto nulo
            UtilBean.criarMensagemDeErro("Objeto nulo");
        } else {
            if (RN.salvar(pessoa)) {
                //Mensagem de sucesso
                UtilBean.criarMensagemDeInformacao("Pessoa gravada com sucesso");
            } else {
                //Mensagem de erro: problemas ao salvar
                UtilBean.criarMensagemDeErro("Problema ao gravar a pessoa");
            }
        }
    }
}
