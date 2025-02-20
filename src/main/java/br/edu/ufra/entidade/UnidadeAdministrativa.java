package br.edu.ufra.entidade;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.Set;

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
    private Character tipo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unidadeAdministrativa")
    private Set<Pessoa> membros = new LinkedHashSet<>();

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

    public Character getTipo() {
        return tipo;
    }

    public void setTipo(Character tipo) {
        this.tipo = tipo;
    }

    public Set<Pessoa> getMembros() {
        return membros;
    }

    public void setMembros(Set<Pessoa> membros) {
        this.membros = membros;
    }

    public String obterTipo(Character tipo) {
        switch (tipo) {
            case 'I': return "Instituto";
            case 'C': return "Campus";
            default: return "-";
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        UnidadeAdministrativa that = (UnidadeAdministrativa) object;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}