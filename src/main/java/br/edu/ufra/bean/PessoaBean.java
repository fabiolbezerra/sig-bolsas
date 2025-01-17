package br.edu.ufra.bean;

import br.edu.ufra.entidade.Pessoa;
import br.edu.ufra.rn.PessoaRN;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.List;

@ManagedBean
@RequestScoped
public class PessoaBean {
    private final PessoaRN RN = new PessoaRN();

    private List<Pessoa> pessoas;

    public PessoaBean() {
        System.out.println("Criando PessoaBean ...");
    }

    @PostConstruct
    public void init() {
        System.out.println("Inicializando PessoaBean ...");
        pessoas = RN.listarTodas();
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }
}
