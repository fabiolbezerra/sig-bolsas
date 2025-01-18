package br.edu.ufra.rn;

import br.edu.ufra.dao.GenericDAO;
import br.edu.ufra.entidade.Comite;

import java.util.List;

public class ComiteRN {
    private final GenericDAO<Comite> DAO = new GenericDAO<>();

    public boolean salvar(Comite Comite) {
        if (Comite == null) {
            return false;
        } else {
            if (Comite.getId() == null || Comite.getId() == 0) {
                return DAO.criar(Comite);
            } else {
                return DAO.alterar(Comite);
            }
        }
    }

    public List<Comite> listarTodas() {
        return DAO.obterTodos(Comite.class);
    }

    public Comite obter(Integer id) {
        return DAO.obter(Comite.class, id);
    }
}
