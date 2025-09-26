package br.edu.ufra.entidade;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "comite")
@NamedQueries({
        @NamedQuery(name = "Comite.findAll", query = "SELECT c FROM Comite c ORDER BY c.fim DESC"),
        @NamedQuery(name = "Comite.findById", query = "SELECT r FROM Comite r WHERE r.id = :id")})
public class Comite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "portaria", nullable = false, length = 45)
    private String portaria;

    @Column(name = "inicio", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date inicio;

    @Column(name = "fim", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fim;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "coordenador", nullable = false)
    private Pessoa coordenador;

    @OneToMany(mappedBy = "comite", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ComitePessoa> comitePessoas = new ArrayList<>();

    @OneToMany(mappedBy = "comite", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Edital> editals  = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPortaria() {
        return portaria;
    }

    public void setPortaria(String portaria) {
        this.portaria = portaria;
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

    public List<ComitePessoa> getComitePessoas() {
        return comitePessoas;
    }
    public void setComitePessoas(List<ComitePessoa> comitePessoas) {
        this.comitePessoas = comitePessoas;
    }

    public List<Edital> getEditals() {
        return editals;
    }

    public void setEditals(List<Edital> editals) {
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