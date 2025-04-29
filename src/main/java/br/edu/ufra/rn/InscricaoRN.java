package br.edu.ufra.rn;

import br.edu.ufra.dao.InscricaoDAO;
import br.edu.ufra.entidade.Edital;
import br.edu.ufra.entidade.Inscricao;
import br.edu.ufra.entidade.Pessoa;
import br.edu.ufra.util.UtilTexto;

import java.util.Date;
import java.util.List;

public class InscricaoRN {
    private final static InscricaoDAO DAO = new InscricaoDAO();
    private PessoaRN pessoaRN;

    public boolean salvar(Inscricao inscricao) {
        if (inscricao == null) {
            return false;
        } else {
            if (inscricao.getId() == null || inscricao.getId() == 0) {
                return DAO.criar(inscricao);
            } else {
                return DAO.alterar(inscricao);
            }
        }
    }

    public List<Inscricao> listarTodos() {
        return DAO.obterTodos(Inscricao.class);
    }

    public Inscricao obter(Integer id) {
        if (id == null) {
            return null;
        } else {
            return DAO.obter(Inscricao.class, id);
        }
    }

    public boolean novaInscricao(Edital edital, Pessoa pesquisador, String senha, String email) {
        if (edital == null || pesquisador == null) {
            return false;
        } else {
            String senhaAtualCriptografada = UtilTexto.criptografarSHA256(senha);
            Pessoa pesquisadorLogado = pessoaRN.login(email, senhaAtualCriptografada);
            if (pesquisadorLogado != null) {
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
                return salvar(novaInscricao);
            }
            return false;
        }
    }

    public List<Inscricao> obterPorPesquisador(Pessoa pesquisador) {
        if (pesquisador == null || pesquisador.getId() == null) {
            return null;
        } else {
            return DAO.obter(pesquisador);
        }
    }

    public List<Inscricao> obterPorEdital(Edital edital) {
        if (edital == null || edital.getId() == null) {
            return null;
        } else {
            return DAO.obter(edital);
        }
    }

    public Inscricao obter(Pessoa pesquisador, Edital edital) {
        if (pesquisador == null || pesquisador.getId() == null || edital == null || edital.getId() == 0) {
            return null;
        } else {
            Inscricao resposta = null;
            List<Inscricao> inscricoes = obterPorPesquisador(pesquisador);

            for (Inscricao inscricao : inscricoes) {
                if (edital.equals(inscricao.getEdital())) {
                    resposta = inscricao;
                    break;
                } else {

                    resposta = inscricao;
                }
            }
            return resposta;
        }
    }

    public List<Inscricao> obterPorEdital(Edital edital, Date inicio, Date fim) {
        if (edital == null || inicio == null || fim == null) {
            return null;
        } else {
            return obterPorEdital(edital, inicio, fim);
        }
    }
}
