package br.edu.ufra.entidade;

import br.edu.ufra.entidade.tipo.EnumTipoUnidadeAdministrativa;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "unidade_administrativa")
@NamedQueries({
        @NamedQuery(name = "UnidadeAdministrativa.findAll", query = "SELECT o FROM UnidadeAdministrativa o"),
        @NamedQuery(name = "UnidadeAdministrativa.findById", query = "SELECT o FROM UnidadeAdministrativa o WHERE o.id = :id")})
public class UnidadeAdministrativa {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @NotNull
    @Column(name = "sigla", nullable = false, length = 20)
    private String sigla;

    @Column(name = "tipo", nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumTipoUnidadeAdministrativa tipo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unidadeAdministrativa")
    private List<Pessoa> membros = new ArrayList<>();

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

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public EnumTipoUnidadeAdministrativa getTipo() {
        return tipo;
    }

    public void setTipo(EnumTipoUnidadeAdministrativa tipo) {
        this.tipo = tipo;
    }

    public List<Pessoa> getMembros() {
        return membros;
    }

    public void setMembros(List<Pessoa> membros) {
        this.membros = membros;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        UnidadeAdministrativa that = (UnidadeAdministrativa) object;
        if (this.id == null || that.id == null) return false;
        return this.id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return (id != null) ? id.hashCode() : 0;
    }
}