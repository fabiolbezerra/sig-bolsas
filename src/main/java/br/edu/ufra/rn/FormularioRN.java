package br.edu.ufra.rn;

import br.edu.ufra.dao.GenericDAO;
import br.edu.ufra.entidade.Formulario;

import java.util.List;

public class FormularioRN {
    private final GenericDAO<Formulario> DAO = new GenericDAO<>();

    public boolean salvar(Formulario formulario) {
        if (formulario == null) {
            return false;            
        } else {
            if (formulario.getId() == null || formulario.getId() == 0) {
                return DAO.criar(formulario);
            } else {
                return DAO.alterar(formulario);
            }
        }
    }
    
    public List<Formulario> listarTodos() {
        return DAO.obterTodos(Formulario.class);
    }
    
    public Formulario obter(Integer id) {
        return DAO.obter(Formulario.class, id);
    }

}
