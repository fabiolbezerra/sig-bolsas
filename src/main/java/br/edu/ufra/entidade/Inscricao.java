package br.edu.ufra.entidade;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "inscricao")
@NamedQueries({
        @NamedQuery(name = "Inscricao.findAll", query = "SELECT i FROM Inscricao i"),
        @NamedQuery(name = "Inscricao.findById", query = "SELECT i FROM Inscricao i WHERE i.id = :id")})
public class Inscricao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "data_inscricao", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataInscricao;

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

    @Column(name = "pontuacaoPesquisador", nullable = false)
    private double pontuacaoPesquisador;

    @Column(name = "pontuacaoComite", nullable = false)
    private double pontuacaoComite;

    @Column(name = "classificacao", nullable = false)
    private int classificacao;

    @OneToMany(mappedBy = "inscricao")
    private Set<AvaliacaoInscricao> avaliacaoInscricaos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "inscricao")
    private Set<Resposta> respostas = new LinkedHashSet<>();

    public Set<Resposta> getRespostas() {
        return respostas;
    }

    public void setRespostas(Set<Resposta> respostas) {
        this.respostas = respostas;
    }

    public Set<AvaliacaoInscricao> getAvaliacaoInscricaos() {
        return avaliacaoInscricaos;
    }

    public void setAvaliacaoInscricaos(Set<AvaliacaoInscricao> avaliacaoInscricaos) {
        this.avaliacaoInscricaos = avaliacaoInscricaos;
    }

    public int getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(int classificacao) {
        this.classificacao = classificacao;
    }

    public double getPontuacaoComite() {
        return pontuacaoComite;
    }

    public void setPontuacaoComite(double pontuacaoComite) {
        this.pontuacaoComite = pontuacaoComite;
    }

    public double getPontuacaoPesquisador() {
        return pontuacaoPesquisador;
    }

    public void setPontuacaoPesquisador(double pontuacaoPesquisador) {
        this.pontuacaoPesquisador = pontuacaoPesquisador;
    }

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Inscricao inscricao = (Inscricao) o;
        return Objects.equals(id, inscricao.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}