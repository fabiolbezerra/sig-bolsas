package br.edu.ufra.rn;

import br.edu.ufra.dao.GenericDAO;
import br.edu.ufra.entidade.Pessoa;

import java.util.List;

public class PessoaRN {
    private final GenericDAO<Pessoa> DAO = new GenericDAO<>();

    public boolean salvar(Pessoa pessoa) {
        if (pessoa == null) {
            return false;
        } else {
            if (pessoa.getId() == null || pessoa.getId() == 0) {
                return DAO.criar(pessoa);
            } else {
                return DAO.alterar(pessoa);
            }
        }
    }

    public List<Pessoa> listarTodas() {
        return DAO.obterTodos(Pessoa.class);
    }

    public Pessoa obter(Integer id) {
        return DAO.obter(Pessoa.class, id);
    }
}
