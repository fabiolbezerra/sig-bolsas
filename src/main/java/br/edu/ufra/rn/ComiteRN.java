package br.edu.ufra.rn;

import br.edu.ufra.entidade.Comite;
import br.edu.ufra.dao.ComiteDAO;
import java.util.Date;
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

    public List<Comite> listarTodas() {
        return DAO.obterTodos(Comite.class);
    }

    public Comite obter(Integer id) {
        return DAO.obter(Comite.class, id);
    }

    public List<Comite> listarVigentes(Date dataIndicada) {
        return DAO.listarVigentesEm(dataIndicada);
    }

}
