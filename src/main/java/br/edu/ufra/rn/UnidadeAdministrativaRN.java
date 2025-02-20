package br.edu.ufra.rn;

import br.edu.ufra.dao.GenericDAO;
import br.edu.ufra.entidade.Comite;
import br.edu.ufra.entidade.Papel;
import br.edu.ufra.entidade.UnidadeAdministrativa;

import java.util.List;

public class UnidadeAdministrativaRN {
    private final GenericDAO<UnidadeAdministrativa> DAO = new GenericDAO<>();

    public boolean salvar(UnidadeAdministrativa UnidadeAdministrativa) {
        if (UnidadeAdministrativa == null) {
            return false;
        } else {
            if (UnidadeAdministrativa.getId() == null || UnidadeAdministrativa.getId() == 0) {
                return DAO.criar(UnidadeAdministrativa);
            } else {
                return DAO.alterar(UnidadeAdministrativa);
            }
        }
    }

    public List<UnidadeAdministrativa> listarTodos() {
        return DAO.obterTodos(UnidadeAdministrativa.class);
    }

    public UnidadeAdministrativa obter(Integer id) {
        return DAO.obter(UnidadeAdministrativa.class, id);
    }
}