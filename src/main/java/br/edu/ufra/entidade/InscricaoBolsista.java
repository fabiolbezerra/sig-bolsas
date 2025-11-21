package br.edu.ufra.entidade;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "inscricao_bolsista")
public class InscricaoBolsista {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "data_inscricao", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInscricao;

    @Column(name = "protocolo", length = 15)
    private String protocolo;

    @Column(name = "classificacao")
    private Integer classificacao;

    @Column(name = "situacao")
    private Character situacao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pessoa", nullable = false)
    private Pessoa pessoa;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "plano_de_trabalho", nullable = false)
    private PlanoDeTrabalho planoDeTrabalho;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(Date dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public Integer getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(Integer classificacao) {
        this.classificacao = classificacao;
    }

    public Character getSituacao() {
        return situacao;
    }

    public void setSituacao(Character situacao) {
        this.situacao = situacao;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public PlanoDeTrabalho getPlanoDeTrabalho() {
        return planoDeTrabalho;
    }

    public void setPlanoDeTrabalho(PlanoDeTrabalho planoDeTrabalho) {
        this.planoDeTrabalho = planoDeTrabalho;
    }

}