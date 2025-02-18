package br.edu.ufra.entidade;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "pessoa")
@NamedQueries({
        @NamedQuery(name = "Pessoa.findAll", query = "SELECT r FROM Pessoa r"),
        @NamedQuery(name = "Pessoa.findById", query = "SELECT r FROM Pessoa r WHERE r.id = :id")})
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nome_completo", nullable = false, length = 50)
    private String nomeCompleto;

    @Column(name = "nascimento")
    @Temporal(TemporalType.DATE)
    private Date nascimento;

    @Column(name = "telefone", nullable = false, length = 11)
    private String telefone;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "senha", nullable = false, length = 100)
    private String senha;

    @OneToMany(mappedBy = "coordenador")
    private Set<Comite> comites = new LinkedHashSet<>();

    @OneToMany(mappedBy = "pessoa")
    private Set<ComitePessoa> comitePessoas = new LinkedHashSet<>();

    @OneToMany(mappedBy = "coordenador")
    private Set<Edital> editals = new LinkedHashSet<>();

    @OneToMany(mappedBy = "pessoa")
    private Set<PessoaPapel> pessoaPapels = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "unidade_administrativa", nullable = false)
    private UnidadeAdministrativa unidadeAdministrativa;

    @OneToMany(mappedBy = "pessoa")
    private Set<Inscricao> inscricaos = new LinkedHashSet<>();

    public Set<Inscricao> getInscricaos() {
        return inscricaos;
    }

    public void setInscricaos(Set<Inscricao> inscricaos) {
        this.inscricaos = inscricaos;
    }

    public UnidadeAdministrativa getUnidadeAdministrativa() {
        return unidadeAdministrativa;
    }

    public void setUnidadeAdministrativa(UnidadeAdministrativa unidadeAdministrativa) {
        this.unidadeAdministrativa = unidadeAdministrativa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Set<Comite> getComites() {
        return comites;
    }

    public void setComites(Set<Comite> comites) {
        this.comites = comites;
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

    public Set<PessoaPapel> getPessoaPapels() {
        return pessoaPapels;
    }

    public void setPessoaPapels(Set<PessoaPapel> pessoaPapels) {
        this.pessoaPapels = pessoaPapels;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        Pessoa pessoa = (Pessoa) object;
        return id.equals(pessoa.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}