package br.edu.ufra.entidade.tipo;

public enum EnumAcaoAuditoria {
    LOGIN("Login realizado", "sucesso"),
    LOGIN_2FA("Login com 2FA concluído", "sucesso"),
    FALHA_2FA("Falha na autenticação 2FA", "perigo"),
    HOMOLOGAR("Inscrição homologada", "informe"),
    APROVAR("Inscrição aprovada", "sucesso"),
    INDEFERIR("Inscrição indeferida", "perigo"),
    AVALIAR("Inscrição avaliada", "atenção"),
    CLASSIFICAR("Classificação realizada", "atenção"),
    CRIAR("Registro criado", "informe"),
    EDITAR("Registro editado", "atenção"),
    EXCLUIR("Registro excluído", "perigo");

    private final String descricao;
    private final String severidade;

    EnumAcaoAuditoria(String descricao, String severidade) {
        this.descricao = descricao;
        this.severidade = severidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getSeveridade() {
        return severidade;
    }
}
