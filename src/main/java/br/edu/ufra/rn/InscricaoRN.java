package br.edu.ufra.rn;

import br.edu.ufra.dao.InscricaoDAO;
import br.edu.ufra.entidade.Edital;
import br.edu.ufra.entidade.Inscricao;
import br.edu.ufra.entidade.Pessoa;
import br.edu.ufra.util.UtilTexto;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class InscricaoRN {
    private final static InscricaoDAO DAO = new InscricaoDAO();

    private PessoaRN pessoaRN = new PessoaRN();

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
                List<Inscricao> inscricoes = obter(pesquisador);
                for (Inscricao inscricao : inscricoes) {
                    if (edital.equals(inscricao.getEdital())) {
                        return false;
                    }
                }
                Inscricao novaInscricao = new Inscricao();
                novaInscricao.setEdital(edital);
                novaInscricao.setPessoa(pesquisador);
                novaInscricao.setDataInscricao(new Date());
                novaInscricao.setProtocolo(gerarProtocolo(edital.getId(), pesquisador.getId()));
                return salvar(novaInscricao);
            }
            return false;
        }
    }

    public String gerarProtocolo(int editalId, int pessoaId) {
        int ano = LocalDate.now().getYear();

        // Formata editalId 3 dígitos, preenchendo com zeros
        String editalFormatado = String.format("%03d", editalId);
        // Formata pessoaId 5 dígitos, preenchendo com zeros
        String pessoaFormatada = String.format("%05d", pessoaId);

        // Concatena tudo no formato AAAAEEEEPPPP
        String protocoloGerado = String.format("%d%s%s", ano, editalFormatado, pessoaFormatada);
        return protocoloGerado;
    }

    public List<Inscricao> obter(Pessoa pesquisador) {
        if (pesquisador == null || pesquisador.getId() == null) {
            return null;
        } else {
            return DAO.obter(pesquisador);
        }
    }

    public List<Inscricao> obter(Pessoa pesquisador, boolean vigentes) {
        if (pesquisador == null || pesquisador.getId() == null) {
            return null;
        } else {
            return DAO.obter(pesquisador, vigentes);
        }
    }

    public List<Inscricao> obter(Edital edital) {
        if (edital == null || edital.getId() == null) {
            return null;
        } else {
            return DAO.obter(edital);
        }
    }

    public Inscricao obter(Pessoa pesquisador, Edital edital) {
        if (pesquisador == null || edital == null) {
            return null;
        } else {
            Inscricao resposta = DAO.obter(pesquisador, edital);
            return resposta;
        }
    }

    public List<Inscricao> obter(Edital edital, Date inicio, Date fim) {
        if (edital == null || inicio == null || fim == null) {
            return null;
        } else {
            return DAO.obter(edital, inicio, fim);
        }
    }
}