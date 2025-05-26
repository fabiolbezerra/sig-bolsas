package br.edu.ufra.entidade;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "formulario")
@NamedQueries({
        @NamedQuery(name = "Formulario.findAll", query = "SELECT o FROM Formulario o"),
        @NamedQuery(name = "Formulario.findById", query = "SELECT o FROM Formulario o WHERE o.id = :id")})
public class Formulario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 45)
    private String nome;

    @Column(name = "data_criacao")
    @Temporal(TemporalType.DATE)
    private Date dataCriacao;

    @OneToMany(mappedBy = "formulario")
    private List<Edital> editals = new ArrayList<>();

    @OneToMany(mappedBy = "formulario")
    private List<Pergunta> perguntas = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public List<Edital> getEditals() {
        return editals;
    }

    public void setEditals(List<Edital> editals) {
        this.editals = editals;
    }

    public List<Pergunta> getPerguntas() {
        return perguntas;
    }

    public void setPerguntas(List<Pergunta> perguntas) {
        this.perguntas = perguntas;
    }

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Formulario that = (Formulario) object;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }
}