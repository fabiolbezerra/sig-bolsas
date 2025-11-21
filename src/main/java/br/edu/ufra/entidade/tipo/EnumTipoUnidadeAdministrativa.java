package br.edu.ufra.entidade.tipo;

public enum EnumTipoUnidadeAdministrativa {

    CAMPUS("Campus"),
    INSTITUTO("Instituto"),
    FACULDADE("Faculdade"),
    CURSO("Curso");

    private String descricao;

    EnumTipoUnidadeAdministrativa(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
