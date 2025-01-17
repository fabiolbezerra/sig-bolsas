package br.edu.ufra.entidade;

import javax.persistence.*;

@Entity
@Table(name = "comite_pessoa")
public class ComitePessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "comite", nullable = false)
    private Comite comite;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pessoa", nullable = false)
    private Pessoa pessoa;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Comite getComite() {
        return comite;
    }

    public void setComite(Comite comite) {
        this.comite = comite;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

}