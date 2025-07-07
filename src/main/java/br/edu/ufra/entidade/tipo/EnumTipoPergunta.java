package br.edu.ufra.entidade.tipo;

public enum EnumTipoPergunta {

    DATA("Data"),
    INTEIRO("Número inteiro"),
    DECIMAL("Número decimal"),
    TEXTO("Texto curto"),
    SIM_NAO("Sim ou não"),
    UNICA_ESCOLHA("Resposta de única escolha");

    private String descricao;

    EnumTipoPergunta(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isTipoTexto() {
        return this == TEXTO;
    }

    public boolean isTipoInteiro() {
        return this == INTEIRO;
    }

    public boolean isTipoDecimal() {
        return this == DECIMAL;
    }

    public boolean isTipoData() {
        return this == DATA;
    }

    public boolean isTipoSimOuNao() {
        return this == SIM_NAO;
    }

    public boolean isTipoRespostaFechada() {
        return this == UNICA_ESCOLHA;
    }

}
