package br.edu.ufra.bean;

import br.edu.ufra.entidade.UnidadeAdministrativa;
import br.edu.ufra.rn.UnidadeAdministrativaRN;
import br.edu.ufra.util.UtilBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.List;

@ManagedBean
@RequestScoped
public class UnidadeAdministrativaBean {
    private final UnidadeAdministrativaRN RN = new UnidadeAdministrativaRN();
    private UnidadeAdministrativa unidade = new UnidadeAdministrativa();
    private List<UnidadeAdministrativa> unidades;

    public UnidadeAdministrativaBean() {
        System.out.println("Criando UnidadeAdministrativaBean ...");
    }
    @PostConstruct
    public void init() {
        System.out.println("Inicializando UnidadeAdministrativaBean ...");
        unidades = RN.listarTodos();
    }

    public List<UnidadeAdministrativa> getUnidades() {
        return unidades;
    }

    public UnidadeAdministrativa getUnidade() {
        return unidade;
    }

    public void setUnidade(UnidadeAdministrativa unidade) {
        this.unidade = unidade;
    }

    public void salvar() {
        if (unidade == null) {
            //Mensagem de erro: objeto nulo
            UtilBean.criarMensagemDeErro("Objeto nulo");
        } else {
            if (unidade.getSigla() == null || unidade.getSigla().isEmpty()) {
                //Mensagem de erro: sigla vazia
                UtilBean.criarMensagemDeErro("Sigla vazia");
            } else {
                if (RN.salvar(unidade)) {
                    //Mensagem de sucesso
                    UtilBean.criarMensagemDeInformacao("Unidade Administrativa gravada com sucesso");
                } else {
                    //Mensagem de erro: problemas ao salvar
                    UtilBean.criarMensagemDeErro("Problema ao gravar a Unidade Administrativa");
                }
            }
        }
    }
}