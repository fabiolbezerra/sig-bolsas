package br.edu.ufra.entidade;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "comite")
@NamedQueries({
        @NamedQuery(name = "Comite.findAll", query = "SELECT r FROM Comite r"),
        @NamedQuery(name = "Comite.findById", query = "SELECT r FROM Comite r WHERE r.id = :id")})
public class Comite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "inicio", nullable = false)
    private LocalDate inicio;

    @Column(name = "fim", nullable = false)
    private LocalDate fim;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "coordenador", nullable = false)
    private Pessoa coordenador;

    @OneToMany(mappedBy = "comite")
    private Set<ComitePessoa> comitePessoas = new LinkedHashSet<>();

    @OneToMany(mappedBy = "comite")
    private Set<Edital> editals = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFim() {
        return fim;
    }

    public void setFim(LocalDate fim) {
        this.fim = fim;
    }

    public Pessoa getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(Pessoa coordenador) {
        this.coordenador = coordenador;
    }

    public Set<ComitePessoa> getComitePessoas() {
        return comitePessoas;
    }

    public void setComitePessoas(Set<ComitePessoa> comitePessoas) {
        this.comitePessoas = comitePessoas;
    }

    public Set<Edital> getEditals() {
        return editals;
    }

    public void setEditals(Set<Edital> editals) {
        this.editals = editals;
    }

}