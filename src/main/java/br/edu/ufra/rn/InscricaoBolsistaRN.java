package br.edu.ufra.rn;

import br.edu.ufra.dao.InscricaoBolsistaDAO;
import br.edu.ufra.entidade.*;
import br.edu.ufra.util.UtilEmail;
import br.edu.ufra.util.UtilTexto;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class InscricaoBolsistaRN {
    private final static InscricaoBolsistaDAO DAO = new InscricaoBolsistaDAO();

    private PessoaRN pessoaRN = new PessoaRN();

    public boolean salvar(InscricaoBolsista inscricaoBolsista) {
        if (inscricaoBolsista == null) {
            return false;
        } else {
            if (inscricaoBolsista.getId() == null || inscricaoBolsista.getId() == 0) {
                return DAO.criar(inscricaoBolsista);
            } else {
                return DAO.alterar(inscricaoBolsista);
            }
        }
    }

    public InscricaoBolsista obter(Integer id) {
        if (id == null) {
            return null;
        } else {
            return DAO.obterDetalhado(id);
        }
    }

    public boolean novaInscricaoBolsista(PlanoDeTrabalho planoDeTrabalho, Pessoa bolsista, String senha, String email) {
        if (planoDeTrabalho == null || bolsista == null) {
            return false;
        } else {
            String senhaAtualCriptografada = UtilTexto.criptografarSHA256(senha);
            Pessoa bolsistaLogado = pessoaRN.login(email, senhaAtualCriptografada);
            if (bolsistaLogado != null) {
                InscricaoBolsista inscricaoBolsista = obter(bolsista, planoDeTrabalho);
                if (inscricaoBolsista != null) {
                    return false;
                }
                InscricaoBolsista novaInscricao = new InscricaoBolsista();
                novaInscricao.setPlanoDeTrabalho(planoDeTrabalho);
                novaInscricao.setPessoa(bolsista);
                novaInscricao.setDataInscricao(new Date());
                novaInscricao.setProtocolo(gerarProtocolo(planoDeTrabalho.getId(), bolsista.getId()));
                boolean salvou = salvar(novaInscricao);
                if (salvou){
                    notificarNovaInscricaoBolsita(bolsistaLogado, novaInscricao);
                }
                return salvou;
            }
            return false;
        }
    }

    private void notificarNovaInscricaoBolsita(Pessoa pessoa, InscricaoBolsista inscricaoBolsista) {
        String destinatario = pessoa.getEmail();
        String titulo = "Confirmação da inscrição no plano de trabalho";
        StringBuilder mensagem = new StringBuilder();
        mensagem.append("Prezado(a) ");
        mensagem.append(pessoa.getNomeCompleto());
        mensagem.append("\n\n");
        mensagem.append("Confirmamos que a sua inscrição foi um sucesso no plano de trabalho " + inscricaoBolsista.getPlanoDeTrabalho().getTitulo() + ".\n\n");
        mensagem.append("Atenciosamente \n");
        mensagem.append("SIG-BOLSAS");
        UtilEmail.enviarEmail(destinatario, titulo, mensagem.toString());
    }

    public String gerarProtocolo(int planoID, int pessoaId) {
        int ano = LocalDate.now().getYear();

        // Formata editalId 3 dígitos, preenchendo com zeros
        String editalFormatado = String.format("%03d", planoID);
        // Formata pessoaId 5 dígitos, preenchendo com zeros
        String pessoaFormatada = String.format("%05d", pessoaId);

        // Concatena tudo no formato AAAAEEEEPPPP
        String protocoloGerado = String.format("%d%s%s", ano, editalFormatado, pessoaFormatada);
        return protocoloGerado;
    }

    public List<InscricaoBolsista> obter(Pessoa bolsista) {
        if (bolsista == null || bolsista.getId() == null) {
            return null;
        } else {
            return DAO.obter(bolsista);
        }
    }

    public InscricaoBolsista obter(Pessoa bolsista, PlanoDeTrabalho planoDeTrabalho) {
        if (bolsista == null || planoDeTrabalho == null) {
            return null;
        } else {
            InscricaoBolsista resposta = DAO.obter(bolsista, planoDeTrabalho);
            return resposta;
        }
    }
}