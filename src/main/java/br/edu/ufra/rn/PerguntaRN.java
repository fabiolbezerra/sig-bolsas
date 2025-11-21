package br.edu.ufra.rn;

import br.edu.ufra.dao.PerguntaDAO;
import br.edu.ufra.entidade.Formulario;
import br.edu.ufra.entidade.Pergunta;
import br.edu.ufra.entidade.tipo.EnumTipoPergunta;

import java.util.Arrays;
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
                        && ! Objects.equals(EnumTipoPergunta.UNICA_ESCOLHA, pergunta.getId())) {
                    //Excluir opções de resposta da pergunta
                    pergunta.setOpcaoPerguntas(null);

                }
                return DAO.alterar(pergunta);
            }
        }
    }

    public boolean excluir(Pergunta pergunta, List<Pergunta> perguntas) {
        if (pergunta == null) {
            return false;
        } else {
            //TODO: caso a pergunta tenha resposta preenchida, não permitir a exclusão
            //if (pergunta.getRespostas() == null) {
                if (perguntas.indexOf(pergunta) == perguntas.size() -1){
                    return DAO.excluir(pergunta);
                }else{
                    return DAO.excluir(pergunta, perguntas);
                }
            //} else {
            //    return false;
            //}
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

    public boolean trocarDeOrdem(Pergunta pergunta1, Pergunta pergunta2, boolean paraCima) {
        if (paraCima) {
            pergunta1.setOrdem(pergunta1.getOrdem() - 1);
            pergunta2.setOrdem(pergunta2.getOrdem() + 1);
        } else {
            pergunta1.setOrdem(pergunta1.getOrdem() + 1);
            pergunta2.setOrdem(pergunta2.getOrdem() - 1);
        }
        return DAO.salvar(Arrays.asList(pergunta1, pergunta2));
    }

}
