package br.edu.ufra.rn;

import br.edu.ufra.dao.EditalDAO;
import br.edu.ufra.entidade.Edital;

import java.util.Date;
import java.util.List;

public class EditalRN {
    //private final GenericDAO<Edital> DAO = new GenericDAO<>();
    private final EditalDAO DAO = new EditalDAO();

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

    public List<Edital> listarTodos() {
        return DAO.obterTodos(Edital.class);
    }

    public List<Edital> obterVigentes() {
        return DAO.obterVigentes();
    }

    public Edital obter(Integer id) {
        return DAO.obter(Edital.class, id);
    }
}
