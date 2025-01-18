package br.edu.ufra.rn;

import br.edu.ufra.dao.GenericDAO;
import br.edu.ufra.entidade.Edital;

import java.util.List;

public class EditalRN {
    private final GenericDAO<Edital> DAO = new GenericDAO<>();

    public boolean salvar(Edital edital) {
        if (edital == null) {
            return false;
        } else {
            if (edital.getId() == null || edital.getId() == 0) {
                return DAO.criar(edital);
            } else {
                return DAO.alterar(edital);
            }
        }
    }

    public List<Edital> listarTodas() {
        return DAO.obterTodos(Edital.class);
    }

    public Edital obter(Integer id) {
        return DAO.obter(Edital.class, id);
    }
}
