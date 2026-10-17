package br.edu.ufra.entidade;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "log_auditoria")
@NamedQueries({
    @NamedQuery(name = "LogAuditoria.findAll",
        query = "SELECT l FROM LogAuditoria l ORDER BY l.dataHora DESC"),
    @NamedQuery(name = "LogAuditoria.findById",
        query = "SELECT l FROM LogAuditoria l WHERE l.id = :id")
})
public class LogAuditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "usuario", length = 100)
    private String usuario;

    @Column(name = "acao", length = 50)
    private String acao;

    @Column(name = "entidade", length = 100)
    private String entidade;

    @Column(name = "entidade_id")
    private Integer entidadeId;

    @Column(name = "descricao", length = 500)
    private String descricao;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getAcao() { return acao; }
    public void setAcao(String acao) { this.acao = acao; }

    public String getEntidade() { return entidade; }
    public void setEntidade(String entidade) { this.entidade = entidade; }

    public Integer getEntidadeId() { return entidadeId; }
    public void setEntidadeId(Integer entidadeId) { this.entidadeId = entidadeId; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
}