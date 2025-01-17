package br.edu.ufra.entidade;

import javax.persistence.*;

@Entity
@Table(name = "pessoa_papel")
public class PessoaPapel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pessoa", nullable = false)
    private Pessoa pessoa;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "papel", nullable = false)
    private Papel papel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Papel getPapel() {
        return papel;
    }

    public void setPapel(Papel papel) {
        this.papel = papel;
    }

}