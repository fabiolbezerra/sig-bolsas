package br.edu.ufra.entidade;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "papel")
public class Papel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 45)
    private String nome;

    @Column(name = "codigo", nullable = false)
    private Character codigo;

    @OneToMany(mappedBy = "papel")
    private Set<PessoaPapel> pessoaPapels = new LinkedHashSet<>();

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

    public Character getCodigo() {
        return codigo;
    }

    public void setCodigo(Character codigo) {
        this.codigo = codigo;
    }

    public Set<PessoaPapel> getPessoaPapels() {
        return pessoaPapels;
    }

    public void setPessoaPapels(Set<PessoaPapel> pessoaPapels) {
        this.pessoaPapels = pessoaPapels;
    }

}