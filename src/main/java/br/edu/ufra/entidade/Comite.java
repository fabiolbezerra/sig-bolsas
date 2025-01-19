package br.edu.ufra.entidade;

import javax.persistence.*;
import java.util.Date;
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
    @Temporal(TemporalType.DATE)
    private Date inicio;

    @Column(name = "fim", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fim;

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

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFim() {
        return fim;
    }

    public void setFim(Date fim) {
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

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        Comite comite = (Comite) object;
        return id.equals(comite.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}