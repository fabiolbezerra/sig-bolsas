package br.edu.ufra.bean;

import br.edu.ufra.entidade.Comite;
import br.edu.ufra.rn.ComiteRN;

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
        } else {
            if (RN.salvar(comite)) {
                //Mensagem de sucesso
            } else {
                //Mensagem de erro: problemas ao salvar
            }
        }
    }
}
