package br.edu.ufra.bean;

import br.edu.ufra.entidade.tipo.EnumTipoSituacaoEdital;
import br.edu.ufra.entidade.tipo.EnumTipoEdital;
import br.edu.ufra.entidade.tipo.EnumTipoPergunta;
import br.edu.ufra.entidade.tipo.EnumTipoUnidadeAdministrativa;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import java.io.Serializable;

@Named
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

    public EnumTipoSituacaoEdital[] getTiposDeSituacaoDeEdital() {
        return EnumTipoSituacaoEdital.values();
    }

    public EnumTipoUnidadeAdministrativa[] getTiposDeUnidadeAdministrativa() {
        return EnumTipoUnidadeAdministrativa.values();
    }
}
