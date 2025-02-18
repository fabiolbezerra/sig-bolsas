package br.edu.ufra.entidade;

import javax.persistence.*;

@Entity
@Table(name = "unidade_administrativa")
@NamedQueries({
        @NamedQuery(name = "UnidadeAdministrativa.findAll", query = "SELECT o FROM Papel o"),
        @NamedQuery(name = "UnidadeAdministrativa.findById", query = "SELECT o FROM Papel o WHERE o.id = :id")})
public class UnidadeAdministrativa {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "sigla", nullable = false, length = 20)
    private String sigla;

    @Column(name = "tipo", nullable = false)
    private Character tipo;

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