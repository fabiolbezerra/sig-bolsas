package br.edu.ufra.rn;

import br.edu.ufra.dao.PlanoDeTrabalhoDAO;
import br.edu.ufra.entidade.Inscricao;
import br.edu.ufra.entidade.PlanoDeTrabalho;
import java.util.List;

public class PlanoDeTrabalhoRN {

    public static final PlanoDeTrabalhoDAO DAO = new PlanoDeTrabalhoDAO();

    public static List<PlanoDeTrabalho> obter(String pista, char criterio) {
        if (pista == null || pista.trim().isEmpty()) {
            return null;
        } else {
            switch (criterio) {
                case 'R':
                    return DAO.obterPorResumo(pista);
                case 'T':
                    return DAO.obterPorTitulo(pista);
                case 'P':
                    return DAO.obterPorPesquisador(pista);
                default:
                    return DAO.obterPorTitulo(pista);
            }
        }
    }

    public static List<PlanoDeTrabalho> listarTodos(){
        return DAO.obterTodos(PlanoDeTrabalho.class);
    }

    public static List<PlanoDeTrabalho> obterVigentes(){
        return DAO.obterVigentes();
    }

    public static PlanoDeTrabalho obter(Integer id){
        return DAO.obterPlanoDeTrabalho(PlanoDeTrabalho.class, id);
    }

    public static List<PlanoDeTrabalho> obter(Inscricao inscriao){
        return DAO.obter(inscriao);
    }

    public List<PlanoDeTrabalho> obterPorBolsista(Long bolsistaId) {
        if (bolsistaId == null) {
            return null;
        }
        return DAO.obterPorBolsista(bolsistaId);
    }

    public boolean salvar(PlanoDeTrabalho planoDeTrabalho) {
        if (planoDeTrabalho == null) {
            return false;
        } else {
            if (planoDeTrabalho.getId() == null || planoDeTrabalho.getId() == 0) {
                return DAO.criar(planoDeTrabalho);
            } else {
                return DAO.alterar(planoDeTrabalho);
            }
        }
    }

    public boolean excluir(PlanoDeTrabalho planoDeTrabalho) {
        if (planoDeTrabalho == null) {
            return false;
        } else {
            return DAO.excluir(planoDeTrabalho);
        }
    }

}