package br.edu.ufra.bean;

import br.edu.ufra.entidade.Comite;
import br.edu.ufra.entidade.Edital;
import br.edu.ufra.rn.ComiteRN;
import br.edu.ufra.rn.EditalRN;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@RequestScoped
public class EditalBean implements Serializable {
    private final EditalRN EDITAL_RN = new EditalRN();
    private final ComiteRN COMITE_RN = new ComiteRN();

    private Edital edital = new Edital();

    private List<Edital> editais;
    private List<Comite> comites;

    public EditalBean() {
        System.out.println("Criando EditalBean ...");
    }

    @PostConstruct
    public void init() {
        System.out.println("Inicializando EditalBean ...");
        editais = EDITAL_RN.listarTodas();
        comites = COMITE_RN.listarTodas();
    }

    public List<Edital> getEditais() {
        return editais;
    }

    public List<Comite> getComites() {
        return comites;
    }

    public Edital getEdital() {
        return edital;
    }

    public void setEdital(Edital edital) {
        this.edital = edital;
    }

    public void salvar() {
        if (edital == null) {
            //Mensagem de erro: objeto nulo
        } else {
            if (EDITAL_RN.salvar(edital)) {
                //Mensagem de sucesso
            } else {
                //Mensagem de erro: problemas ao salvar
            }
        }
    }
}
