package br.edu.ufra.entidade;

import javax.persistence.*;
import java.util.*;

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
    private List<Comite> comites = new ArrayList<>();

    @OneToMany(mappedBy = "pessoa")
    private List<ComitePessoa> comitePessoas = new ArrayList<>();

    @OneToMany(mappedBy = "coordenador")
    private List<Edital> editals = new ArrayList<>();

    @OneToMany(mappedBy = "pessoa")
    private List<PessoaPapel> pessoaPapels = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "unidade_administrativa", nullable = false)
    private UnidadeAdministrativa unidadeAdministrativa;

    @OneToMany(mappedBy = "pessoa")
    private List<Inscricao> inscricoes = new ArrayList<>();

    @OneToMany(mappedBy = "pessoa")
    private Set<AvaliacaoInscricao> avaliacaoInscricaos = new LinkedHashSet<>();

    public Set<AvaliacaoInscricao> getAvaliacaoInscricaos() {
        return avaliacaoInscricaos;
    }

    public void setAvaliacaoInscricaos(Set<AvaliacaoInscricao> avaliacaoInscricaos) {
        this.avaliacaoInscricaos = avaliacaoInscricaos;
    }

    public List<Inscricao> getInscricoes() {
        return inscricoes;
    }

    public void setInscricoes(List<Inscricao> inscricaos) {
        this.inscricoes = inscricaos;
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

    public List<Comite> getComites() {
        return comites;
    }

    public void setComites(List<Comite> comites) {
        this.comites = comites;
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

    public List<PessoaPapel> getPessoaPapels() {
        return pessoaPapels;
    }

    public void setPessoaPapels(List<PessoaPapel> pessoaPapels) {
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