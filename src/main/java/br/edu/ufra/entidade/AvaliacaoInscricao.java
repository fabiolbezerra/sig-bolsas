package br.edu.ufra.entidade;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "avaliacao_inscricao")
public class AvaliacaoInscricao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "data_avaliacao")
    private LocalDate dataAvaliacao;

    @Column(name = "resumo_pesquisador", length = 250)
    private String resumoPesquisador;

    @Column(name = "resumo_comite", length = 500)
    private String resumoComite;

    @Column(name = "pontuacao_avaliacao")
    private double pontuacaoAvaliacao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pessoa", nullable = false)
    private Pessoa pessoa;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "inscricao", nullable = false)
    private Inscricao inscricao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(LocalDate dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }

    public String getResumoPesquisador() {
        return resumoPesquisador;
    }

    public void setResumoPesquisador(String resumoPesquisador) {
        this.resumoPesquisador = resumoPesquisador;
    }

    public String getResumoComite() {
        return resumoComite;
    }

    public void setResumoComite(String resumoComite) {
        this.resumoComite = resumoComite;
    }

    public double getPontuacaoAvaliacao() {
        return pontuacaoAvaliacao;
    }

    public void setPontuacaoAvaliacao(double pontuacaoAvaliacao) {
        this.pontuacaoAvaliacao = pontuacaoAvaliacao;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Inscricao getInscricao() {
        return inscricao;
    }

    public void setInscricao(Inscricao inscricao) {
        this.inscricao = inscricao;
    }

}