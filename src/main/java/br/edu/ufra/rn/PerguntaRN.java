package br.edu.ufra.rn;

import br.edu.ufra.dao.PerguntaDAO;
import br.edu.ufra.entidade.Formulario;
import br.edu.ufra.entidade.Pergunta;
import br.edu.ufra.entidade.tipo.EnumTipoPergunta;

import java.util.List;
import java.util.Objects;

public class PerguntaRN {

    private final PerguntaDAO DAO = new PerguntaDAO();

    public boolean salvar(Pergunta pergunta) {
        if (pergunta == null) {
            return false;
        } else {
            if (pergunta.getId() == null || pergunta.getId() == 0) {
                return DAO.criar(pergunta);
            } else {

                if (pergunta.getOpcaoPerguntas() != null
                        && ! Objects.equals(EnumTipoPergunta.RESPOSTA_FECHADA, pergunta.getId())) {
                    //Excluir opções de resposta da pergunta
                    pergunta.setOpcaoPerguntas(null);

                }
                return DAO.alterar(pergunta);
            }
        }
    }

    public List<Pergunta> listarTodas() {
        return DAO.obterTodos(Pergunta.class);
    }

    public Pergunta obter(Integer id) {
        return DAO.obter(Pergunta.class, id);
    }

    public List<Pergunta> obter(Formulario formulario) {
        if (formulario == null || formulario.getId() == null || formulario.getId() == 0) {
            return null;
        } else {
            return DAO.obter(formulario);
        }
    }

}
