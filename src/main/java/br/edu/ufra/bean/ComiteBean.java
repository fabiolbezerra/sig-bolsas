package br.edu.ufra.bean;

import br.edu.ufra.entidade.Comite;
import br.edu.ufra.rn.ComiteRN;
import br.edu.ufra.util.UtilBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.List;

@ManagedBean
@RequestScoped
public class ComiteBean {
    private final ComiteRN RN = new ComiteRN();

    private Comite comite = new Comite();

    private List<Comite> comites;

    public ComiteBean() {
        System.out.println("Criando ComiteBean ...");
    }

    @PostConstruct
    public void init() {
        System.out.println("Inicializando ComiteBean ...");
        comites = RN.listarTodas();
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
}
