package br.edu.ufra.rn;

import br.edu.ufra.dao.InscricaoDAO;
import br.edu.ufra.entidade.Edital;
import br.edu.ufra.entidade.Inscricao;
import br.edu.ufra.entidade.Pessoa;

import java.util.Date;
import java.util.List;

public class InscricaoRN {
    private final static InscricaoDAO DAO = new InscricaoDAO();

    public boolean salvar(Inscricao inscricao) {
        if (inscricao.getId() == null) {
            return false;
        } else {
            return DAO.criar(inscricao);
        }
    }

    public List<Inscricao> listarTodos() {
        return DAO.obterTodos(Inscricao.class);
    }

    public Inscricao obterPorId(Integer id) {
        if (id == null) {
            return null;
        } else {
            return DAO.obter(Inscricao.class, id);
        }
    }

    public boolean novaInscricao(Edital edital, Pessoa pesquisador) {
        if (edital == null || pesquisador == null) {
            return false;
        } else {
            List<Inscricao> inscricoes = obterPorPesquisador(pesquisador);
            for (Inscricao inscricao : inscricoes) {
                if (edital.equals(inscricao.getEdital())) {
                    return false;
                }
            }
            Inscricao novaInscricao = new Inscricao();
            novaInscricao.setEdital(edital);
            novaInscricao.setPessoa(pesquisador);
            novaInscricao.setDataInscricao(new Date());
            novaInscricao.setProtocolo("TODO");
            return DAO.criar(novaInscricao);
        }
    }

    // Caso necessário:
    public boolean cancelarInscricao (Inscricao inscricao) {
        if (inscricao == null || inscricao.getId() == null) {
            return false;
        } else {
            return DAO.excluir(inscricao);
        }
    }

    public List<Inscricao> obterPorPesquisador(Pessoa pesquisador) {
        if (pesquisador == null || pesquisador.getId() == null) {
            return null;
        } else {
            return DAO.obter(pesquisador);
        }
    }

    public List<Inscricao> obterPorEdital (Edital edital){
        if (edital == null || edital.getId() == null) {
            return null;
        } else {
            return DAO.obter(edital);
        }
    }

    public List<Inscricao> obterPorEdital (Edital edital, Date inicio, Date fim){
        if (edital == null || inicio == null || fim == null) {
            return null;
        }
        else {
           return obterPorEdital(edital, inicio, fim);
        }
    }
}
