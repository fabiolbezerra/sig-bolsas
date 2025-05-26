package br.edu.ufra.rn;

import br.edu.ufra.dao.PessoaDAO;
import br.edu.ufra.entidade.Papel;
import br.edu.ufra.entidade.Pessoa;
import br.edu.ufra.util.UtilEmail;
import br.edu.ufra.util.UtilTexto;

import java.util.List;

public class PessoaRN {
    public static final PessoaDAO DAO = new PessoaDAO();

    public boolean salvar(Pessoa pessoa) {
        if (pessoa == null) {
            return false;
        } else {
            if (pessoa.getId() == null || pessoa.getId() == 0) {
                pessoa.setSenha(UtilTexto.criptografarSHA256(pessoa.getSenha()));
                return DAO.criar(pessoa);
            } else {
                return DAO.alterar(pessoa);
            }
        }
    }

    public List<Pessoa> listarTodas() {
        return DAO.obterTodos(Pessoa.class);
    }

    public Pessoa obter(Integer id) {
        return DAO.obter(Pessoa.class, id);
    }

    public List<Pessoa> obterCoordenadores() {
        return DAO.obterPessoasComPerfil('C');
    }

    public List<Pessoa> obterGerentes() {
        return DAO.obterPessoasComPerfil('G');
    }

    public List<Pessoa> obterPesquisadores() {
        return DAO.obterPessoasComPerfil('P');
    }

    public Pessoa obterPorEmail(String email) {
        return DAO.obterPorEmail(email);
    }

    public Pessoa login(String email, String senhaCripto) {
        return DAO.login(email, senhaCripto);
    }

    public static List<Pessoa> obter(String pista, char criterio) {
        if (pista == null) {
            return null;
        } else {
            switch (criterio) {
                case 'N':
                    return DAO.obterPorNome(pista);
                case 'E':
                    return DAO.obterPessoasPorEmail(pista);
                case 'D':
                    return DAO.obterPorDataDeNascimento(pista);
                default:
                    return DAO.obterPorNome(pista);
            }
        }
    }

    public Pessoa reinicializarSenha(String email) {
        Pessoa pessoaLogada = obterPorEmail(email);
        if (pessoaLogada != null) {
            String novaSenha = UtilTexto.gerarLetrasENumeros(6);

            String novaSenhaCriptografada = UtilTexto.criptografarSHA256(novaSenha);
            pessoaLogada.setSenha(novaSenhaCriptografada);
            if (!DAO.alterar(pessoaLogada)) {
                return null;
            }
            String assunto = "Reinicialização de senha";
            String mensagem = "Sua nova senha é: " + novaSenha;

            UtilEmail.enviarEmail(email, assunto, mensagem);


        }
        return pessoaLogada;
    }

    public String iniciarValidacaoDoEmail(String email) {
        final String CODIGO = UtilTexto.gerarNumeros(6);

        String titulo = "Confirmação da alteração da senha";
        StringBuilder mensagem = new StringBuilder();
        mensagem.append("Prezado usuário");
        mensagem.append("\n\n");
        mensagem.append("Para continuar a criação de uma conta no sistema, ");
        mensagem.append("precisamos que confirme seu e-mail digitando o código abaixo:\n\n");
        mensagem.append(CODIGO);
        mensagem.append("\n\n");
        mensagem.append("Atenciosamente \n");
        mensagem.append("SIG-BOLSAS");
        UtilEmail.enviarEmail(email, titulo, mensagem.toString());

        return CODIGO;
    }

    public Pessoa alterarSenha(String email, String senhaAtual, String novaSenha) {
        String senhaAtualCriptografada = UtilTexto.criptografarSHA256(senhaAtual);
        Pessoa pessoaLogada = login(email, senhaAtualCriptografada);
        if (pessoaLogada != null) {
            String novaSenhaCriptografada = UtilTexto.criptografarSHA256(novaSenha);
            pessoaLogada.setSenha(novaSenhaCriptografada);
            if (!DAO.alterar(pessoaLogada)) {
                pessoaLogada = null;
            } else {
                notificarAlteracaoDaSenha(pessoaLogada);
            }
        }
        return pessoaLogada;
    }

    private void notificarAlteracaoDaSenha(Pessoa pessoa) {
        String destinatario = pessoa.getEmail();
        String titulo = "Confirmação da alteração da senha";
        StringBuilder mensagem = new StringBuilder();
        mensagem.append("Prezado(a) ");
        mensagem.append(pessoa.getNomeCompleto());
        mensagem.append("\n\n");
        mensagem.append("Confirmamos que sua senha foi alterada com sucesso.\n\n");
        mensagem.append("Atenciosamente \n");
        mensagem.append("SIG-BOLSAS");
        UtilEmail.enviarEmail(destinatario, titulo, mensagem.toString());
    }

    public Pessoa alterarEmail(String email, String senhaAtual, String novoEmail) {
        String senhaAtualCriptografada = UtilTexto.criptografarSHA256(senhaAtual);
        Pessoa pessoaLogada = login(email, senhaAtualCriptografada);
        if (pessoaLogada != null) {
            pessoaLogada.setEmail(novoEmail);
            if (!DAO.alterar(pessoaLogada)) {
                pessoaLogada = null;
            } else {
                notificarAlteracaoDoEmail(pessoaLogada, email);
            }
        }
        return pessoaLogada;
    }

    private void notificarAlteracaoDoEmail(Pessoa pessoa, String emailAntigo) {
        String destinatario = pessoa.getEmail();
        String titulo = "Confirmação da alteração do email";
        StringBuilder mensagem = new StringBuilder();
        mensagem.append("Prezado(a) ");
        mensagem.append(pessoa.getNomeCompleto());
        mensagem.append("\n\n");
        mensagem.append("Confirmamos que seu email foi alterado com sucesso.\n\n");
        mensagem.append("Atenciosamente \n");
        mensagem.append("SIG-BOLSAS");
        UtilEmail.enviarEmail(destinatario, titulo, mensagem.toString());
        UtilEmail.enviarEmail(emailAntigo, titulo, mensagem.toString());
    }

    public Pessoa alterarPermissoes(String emailLogado, String senhaLogado, Pessoa pessoa, List<Papel> papeis) {
        if (pessoa == null || papeis == null || papeis.isEmpty()) {
            return null;
        } else {
            String senhaAtualCriptografada = UtilTexto.criptografarSHA256(senhaLogado);
            Pessoa pessoaLogada = login(emailLogado, senhaAtualCriptografada);
            if (pessoaLogada == null) {
                return null;
            }
            if (!DAO.alterarPermissoes(pessoa, papeis)) {
                pessoa = null;
            } else {
                pessoa = obter(pessoa.getId());
            }
            return pessoa;
        }
    }
}
