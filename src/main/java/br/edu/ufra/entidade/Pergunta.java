package br.edu.ufra.entidade;

import br.edu.ufra.entidade.tipo.EnumTipoPergunta;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.List;

@Entity
@Table(name = "pergunta")
@NamedQueries({
        @NamedQuery(name = "Pergunta.findAll", query = "SELECT o FROM Pergunta o"),
        @NamedQuery(name = "Pergunta.findById", query = "SELECT o FROM Pergunta o WHERE o.id = :id")})
public class Pergunta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "texto", nullable = false, length = 250)
    private String texto;

    @Column(name = "pontuacao")
    private Double pontuacao;

    @Column(name = "tipo", nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumTipoPergunta tipo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "formulario", nullable = false)
    private Formulario formulario;

    @OneToMany(mappedBy = "pergunta")
    private List<OpcaoPergunta> opcaoPerguntas = new ArrayList<>();

    @OneToMany(mappedBy = "pergunta")
    private List<Resposta> respostas = new ArrayList<>();

    @Column(name = "ordem", nullable = false)
    private Integer ordem;

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Double getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Double pontuacao) {
        this.pontuacao = pontuacao;
    }

    public EnumTipoPergunta getTipo() {
        return tipo;
    }

    public void setTipo(EnumTipoPergunta tipo) {
        this.tipo = tipo;
    }

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    public List<OpcaoPergunta> getOpcaoPerguntas() {
        return opcaoPerguntas;
    }

    public void setOpcaoPerguntas(List<OpcaoPergunta> opcaoPerguntas) {
        this.opcaoPerguntas = opcaoPerguntas;
    }

    public List<Resposta> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<Resposta> respostas) {
        this.respostas = respostas;
    }

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Pergunta pergunta = (Pergunta) object;
        return getId() != null && Objects.equals(getId(), pergunta.getId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }
}