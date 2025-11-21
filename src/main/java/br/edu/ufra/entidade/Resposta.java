package br.edu.ufra.entidade;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "resposta")
@NamedQueries({
        @NamedQuery(name = "Resposta.findAll", query = "SELECT o FROM Resposta o"),
        @NamedQuery(name = "Resposta.findById", query = "SELECT o FROM Resposta o WHERE o.id = :id")})
public class Resposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "valor", length = 100)
    private String valor;

    @Column(name = "pontuacao_resposta", nullable = false)
    private double pontuacaoResposta;

    public double getPontuacaoResposta() {
        return pontuacaoResposta;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "inscricao", nullable = false)
    private Inscricao inscricao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pergunta", nullable = false)
    private Pergunta pergunta;

    public void setPontuacaoResposta(double pontuacaoResposta) {
        this.pontuacaoResposta = pontuacaoResposta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Inscricao getInscricao() {
        return inscricao;
    }

    public void setInscricao(Inscricao inscricao) {
        this.inscricao = inscricao;
    }

    public Pergunta getPergunta() {
        return pergunta;
    }

    public void setPergunta(Pergunta pergunta) {
        this.pergunta = pergunta;
    }

    public void calcularPontuacaoResposta() {
        //TODO: Calcular a partir do valor e da pergunta
        this.pontuacaoResposta = 0;
    }

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Resposta resposta = (Resposta) object;
        return getId() != null && Objects.equals(getId(), resposta.getId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }
}