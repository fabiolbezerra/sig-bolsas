package br.edu.ufra.entidade;

import javax.persistence.*;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "inscricao")
//TODO: named query?
public class Inscricao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "data_inscricao", nullable = false)
    private Instant dataInscricao;

    @Column(name = "protocolo", length = 15)
    private String protocolo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "edital", nullable = false)
    private Edital edital;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pessoa", nullable = false)
    private Pessoa pessoa;

    @OneToMany(mappedBy = "inscricao")
    private Set<PlanoDeTrabalho> planoDeTrabalhos = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(Instant dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public Edital getEdital() {
        return edital;
    }

    public void setEdital(Edital edital) {
        this.edital = edital;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Set<PlanoDeTrabalho> getPlanoDeTrabalhos() {
        return planoDeTrabalhos;
    }

    public void setPlanoDeTrabalhos(Set<PlanoDeTrabalho> planoDeTrabalhos) {
        this.planoDeTrabalhos = planoDeTrabalhos;
    }

    //TODO: equals e hashCode?

}