package br.edu.ufra.rn;

import br.edu.ufra.dao.EditalDAO;
import br.edu.ufra.entidade.Edital;

import java.util.ArrayList;
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

    public List<Edital> listarTodas() {
        return DAO.obterTodos(Edital.class);
    }

    public List<Edital> obterVigentes(Date data) {
        Date hoje = new Date();
        if (data == null) {
            return DAO.obterVigentes();
        } else {
            if (data.compareTo(hoje) <= 0) {
                return DAO.obterVigentesAnterior();
            } else {
                return DAO.obterVigentesPosterior();
            }
        }
    }

    public Edital obter(Integer id) {
        return DAO.obter(Edital.class, id);
    }
}
