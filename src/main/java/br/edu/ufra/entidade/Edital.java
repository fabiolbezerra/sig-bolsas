package br.edu.ufra.entidade;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "edital")
public class Edital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "titulo", nullable = false, length = 45)
    private String titulo;

    @Column(name = "ano", nullable = false)
    private Integer ano;

    @Column(name = "numero", nullable = false, length = 5)
    private String numero;

    @Column(name = "descricao", length = 100)
    private String descricao;

    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_final", nullable = false)
    private LocalDate dataFinal;

    @Column(name = "situacao", nullable = false)
    private Character situacao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "coordenador", nullable = false)
    private Pessoa coordenador;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "comite", nullable = false)
    private Comite comite;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDate dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Character getSituacao() {
        return situacao;
    }

    public void setSituacao(Character situacao) {
        this.situacao = situacao;
    }

    public Pessoa getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(Pessoa coordenador) {
        this.coordenador = coordenador;
    }

    public Comite getComite() {
        return comite;
    }

    public void setComite(Comite comite) {
        this.comite = comite;
    }

}