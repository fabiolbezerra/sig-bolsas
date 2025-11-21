package br.edu.ufra.entidade;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "opcao_pergunta")
@NamedQueries({
        @NamedQuery(name = "OpcaoPergunta.findAll", query = "SELECT o FROM OpcaoPergunta o"),
        @NamedQuery(name = "OpcaoPergunta.findById", query = "SELECT o FROM OpcaoPergunta o WHERE o.id = :id")})
public class OpcaoPergunta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "resposta", nullable = false, length = 100)
    private String resposta;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pergunta", nullable = false)
    private Pergunta pergunta;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public Pergunta getPergunta() {
        return pergunta;
    }

    public void setPergunta(Pergunta pergunta) {
        this.pergunta = pergunta;
    }

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        OpcaoPergunta that = (OpcaoPergunta) object;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }
}