package br.edu.ufra.rn;

import br.edu.ufra.entidade.Comite;
import br.edu.ufra.dao.ComiteDAO;
import java.util.List;

public class ComiteRN {
   private final ComiteDAO DAO = new ComiteDAO();

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

    public List<Comite> listarTodos() {
        return DAO.obterTodos(Comite.class);
    }

    public Comite obter(Integer id) {
        return DAO.obter(Comite.class, id);
    }

    public List<Comite> listarVigentes() {
        return DAO.listarVigentes();
    }
    public List<Comite> listarEncerrados() {
        return DAO.listarEncerrados();
    }
}
