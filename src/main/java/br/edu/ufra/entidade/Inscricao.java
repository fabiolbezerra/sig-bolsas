package br.edu.ufra.entidade;

import jakarta.persistence.*;
import java.util.*;

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

    @Column(name = "pontuacao_pesquisador", nullable = false)
    private double pontuacaoPesquisador;

    @Column(name = "pontuacao_comite", nullable = false)
    private double pontuacaoComite;    
    
    @Column(name = "classificacao", nullable = false)
    private int classificacao;

    @Column(name = "situacao", length = 1)
    private Character situacao = 'P';

    @Column(name = "motivo_indeferimento", length = 500)
    private String motivoIndeferimento;

    @OneToMany(mappedBy = "inscricao")
    private List<AvaliacaoInscricao> avaliacoes = new ArrayList<>();

    @OneToMany(mappedBy = "inscricao")
    private List<Resposta> respostas = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "edital", nullable = false)
    private Edital edital;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pessoa", nullable = false)
    private Pessoa pessoa;

    @OneToMany(mappedBy = "inscricao")
    private List<PlanoDeTrabalho> planoDeTrabalhos = new ArrayList<>();
    
    public List<Resposta> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<Resposta> respostas) {
        this.respostas = respostas;
    }

    public List<AvaliacaoInscricao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<AvaliacaoInscricao> avaliacaoInscricaos) {
        this.avaliacoes = avaliacaoInscricaos;
    }

    public int getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(int classificacao) {
        this.classificacao = classificacao;
    }

    public Character getSituacao() { return situacao; }
    public void setSituacao(Character situacao) { this.situacao = situacao; }

    public String getMotivoIndeferimento() { return motivoIndeferimento; }
    public void setMotivoIndeferimento(String motivoIndeferimento) { this.motivoIndeferimento = motivoIndeferimento; }

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

    public List<PlanoDeTrabalho> getPlanoDeTrabalhos() {
        return planoDeTrabalhos;
    }

    public void setPlanoDeTrabalhos(List<PlanoDeTrabalho> planoDeTrabalhos) {
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