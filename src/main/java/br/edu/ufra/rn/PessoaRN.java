package br.edu.ufra.rn;

import br.edu.ufra.dao.PessoaDAO;
import br.edu.ufra.entidade.Pessoa;
import br.edu.ufra.util.UtilTexto;

import java.util.List;

public class PessoaRN {
    private final PessoaDAO DAO = new PessoaDAO();

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

    public Pessoa alterarSenha(String email, String senhaAtual, String novaSenha) {
        String senhaAtualCriptografada = UtilTexto.criptografarSHA256(senhaAtual);
        Pessoa pessoaLogada = login(email, senhaAtualCriptografada);
        if (pessoaLogada != null){
            String novaSenhaCriptografada = UtilTexto.criptografarSHA256(novaSenha);
            pessoaLogada.setSenha(novaSenhaCriptografada);
            if (!DAO.alterar(pessoaLogada)){
                pessoaLogada = null;
            }
        }
        return pessoaLogada;
    }

    public List<Pessoa> listarTodas() {
        return DAO.obterTodos(Pessoa.class);
    }

    public Pessoa obter(Integer id) {
        return DAO.obter(Pessoa.class, id);
    }

    public Pessoa obterPorEmail(String email) {
        return DAO.obterPorEmail(email);
    }

    public Pessoa login(String email, String senhaCripto) {
        return DAO.login(email, senhaCripto);
    }

}
