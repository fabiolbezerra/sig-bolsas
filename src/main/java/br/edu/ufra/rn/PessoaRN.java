package br.edu.ufra.rn;

import br.edu.ufra.dao.GenericDAO;
import br.edu.ufra.entidade.Pessoa;

import java.util.List;

public class PessoaRN {
    private final GenericDAO<Pessoa> DAO = new GenericDAO<>();

    public List<Pessoa> listarTodas() {
        return DAO.obterTodos(Pessoa.class);
    }
}
