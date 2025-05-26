package br.edu.ufra.bean;

import br.edu.ufra.entidade.tipo.EnumTipoEdital;
import br.edu.ufra.entidade.tipo.EnumTipoPergunta;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;

@ManagedBean
@RequestScoped
public class TipoEnumBean implements Serializable {

    public TipoEnumBean() {
        System.out.println("Criando TipoEnumBean ...");
    }

    @PostConstruct
    public void init() {
        System.out.println("Inicializando TipoEnumBean ...");
    }

    public EnumTipoPergunta[] getTiposDePergunta() {
        return EnumTipoPergunta.values();
    }

    public EnumTipoEdital[] getTiposDeEdital() {
        return EnumTipoEdital.values();
    }
    
}