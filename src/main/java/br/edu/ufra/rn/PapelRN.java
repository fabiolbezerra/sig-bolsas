package br.edu.ufra.rn;
import br.edu.ufra.dao.PapelDAO;
import br.edu.ufra.entidade.Papel;
import java.util.List;

public class PapelRN {
    private final PapelDAO DAO = new PapelDAO();
    public boolean salvar(Papel Papel) {
        if (Papel == null) {
            return false;            
        } else {
            if (Papel.getId() == null || Papel.getId() == 0) {
                return DAO.criar(Papel);
            } else {
                return DAO.alterar(Papel);
            }
        }
    }
    
    public List<Papel> listarTodas() {
        return DAO.obterTodos(Papel.class);
    }
    
    public Papel obter(Integer id) {
        return DAO.obter(Papel.class, id);
    }

    public List<Papel> listarPerfil() {
        return DAO.listarPerfil();
    }
}
