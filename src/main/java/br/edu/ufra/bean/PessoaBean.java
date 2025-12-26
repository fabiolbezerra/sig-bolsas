package br.edu.ufra.bean;

import br.edu.ufra.entidade.Papel;
import br.edu.ufra.entidade.Pessoa;
import br.edu.ufra.entidade.PessoaPapel;
import br.edu.ufra.rn.PessoaRN;
import br.edu.ufra.util.UtilBean;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class PessoaBean implements Serializable {
    private final PessoaRN RN = new PessoaRN();

    private Pessoa pessoa = new Pessoa();

    private String senha;
    private String novaSenha;
    private String novaSenhaRedigitada;

    private String novoEmail;
    private char criterio;
    private String chaveDeBusca;
    private List<Papel> papeis;

    private List<Pessoa> pessoas;

    private String returnTo; // Armazena a página de retorno

    public PessoaBean() {
        System.out.println("Criando PessoaBean ...");
    }

    @PostConstruct
    public void init() {
        System.out.println("Inicializando PessoaBean ...");
        FacesContext currentInstance = FacesContext.getCurrentInstance();

        // Captura o parâmetro pessoa ou pessoaID
        String pessoaID = currentInstance.getExternalContext().getRequestParameterMap().get("pessoa");
        if (pessoaID == null) {
            pessoaID = currentInstance.getExternalContext().getRequestParameterMap().get("pessoaID");
        }

        if (pessoaID != null) {
            try {
                int id = Integer.parseInt(pessoaID);
                pessoa = RN.obter(id);
                if (pessoa == null) {
                    pessoa = new Pessoa();
                    UtilBean.criarMensagemDeAviso("Não foi recuperar o pessoa. Código inválido.");
                }
            } catch (Exception e) {
                pessoa = new Pessoa();
                UtilBean.criarMensagemDeErro("Não foi recuperar o pessoa");
            }
        }

        // Captura o parâmetro returnTo se existir
        returnTo = currentInstance.getExternalContext().getRequestParameterMap().get("returnTo");
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getNovaSenhaRedigitada() {
        return novaSenhaRedigitada;
    }

    public void setNovaSenhaRedigitada(String novaSenhaRedigitada) {
        this.novaSenhaRedigitada = novaSenhaRedigitada;
    }

    public String getNovoEmail() {
        return novoEmail;
    }

    public void setNovoEmail(String novoEmail) {
        this.novoEmail = novoEmail;
    }

    public char getCriterio() {
        return criterio;
    }

    public void setCriterio(char criterio) {
        this.criterio = criterio;
    }

    public String getChaveDeBusca() {
        return chaveDeBusca;
    }

    public void setChaveDeBusca(String chaveDeBusca) {
        this.chaveDeBusca = chaveDeBusca;
    }

    public List<Papel> getPapeis() {
        return papeis;
    }

    public void setPapeis(List<Papel> papeis) {
        this.papeis = papeis;
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public List<Pessoa> getCoordenadores() {
        return RN.obterCoordenadores();
    }

    public List<Pessoa> getGerentes() {
        return RN.obterGerentes();
    }

    public List<Pessoa> getPesquisadores() {
        return RN.obterPesquisadores();
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        if (pessoa != null && pessoa.getPessoaPapels() != null) {
            papeis = new ArrayList<>();
            for (PessoaPapel p : pessoa.getPessoaPapels()) {
                papeis.add(p.getPapel());
            }
        }
    }

    public void salvar() {
        if (pessoa == null) {
            //Mensagem de erro: objeto nulo
            UtilBean.criarMensagemDeErro("Objeto nulo");
        } else {
            if (RN.salvar(pessoa)) {
                //Mensagem de sucesso
                UtilBean.criarMensagemDeInformacao("Pessoa gravada com sucesso");
            } else {
                //Mensagem de erro: problemas ao salvar
                UtilBean.criarMensagemDeErro("Problema ao gravar a pessoa");
            }
        }
    }

    public void alterarSenha() {
        if (pessoa == null) {
            //Mensagem de erro: objeto nulo
            UtilBean.criarMensagemDeErro("Objeto nulo");
        } else {
            Pessoa pessoaTemp = RN.alterarSenha(pessoa.getEmail(), senha, novaSenha);
            if (pessoaTemp != null) {
                //Mensagem de sucesso
                UtilBean.criarMensagemDeInformacao("Senha alterada com sucesso");
                pessoa = pessoaTemp;
            } else {
                //Mensagem de erro: problemas ao salvar
                UtilBean.criarMensagemDeErro("Problema ao alterar a senha");
            }
            senha = "";
            novaSenha = "";
            novaSenhaRedigitada = "";
        }
    }
    public void enviarEmailRenicializacaoDeSenha() {
        if (pessoa == null) {
            //Mensagem de erro: objeto nulo
            UtilBean.criarMensagemDeErro("Objeto nulo");
        } else {
            pessoa = RN.reinicializarSenha(novoEmail);
            if (pessoa != null) {
                //Mensagem de sucesso
                UtilBean.criarMensagemDeInformacao("Um e-mail com nova senha foi enviado");
            } else {
                //Mensagem de erro: problemas ao salvar
                UtilBean.criarMensagemDeErro("Nenhum usuário com o e-mail informado");
                pessoa = new Pessoa();
            }
        }
    }

    public void alterarEmail() {
        if (pessoa == null) {
            //Mensagem de erro: objeto nulo
            UtilBean.criarMensagemDeErro("Objeto nulo");
        } else {
            Pessoa pessoaTemp = RN.alterarEmail(pessoa.getEmail(), senha, novoEmail); //senha e e-mail digitados no form
            if (pessoaTemp != null) {
                //Mensagem de sucesso
                UtilBean.criarMensagemDeInformacao("E-mail alterado com sucesso");
                pessoa = pessoaTemp;
            } else {
                //Mensagem de erro: problemas ao salvar
                UtilBean.criarMensagemDeErro("Problema ao alterar o e-mail");
            }
            novoEmail = "";
            senha = "";
        }
    }

    public void alterarPermissao() {
        if (pessoa == null) {
            //Mensagem de erro: objeto nulo
            UtilBean.criarMensagemDeErro("Objeto nulo");
        } else {
            String emailLogado = UtilBean.obterUsuarioLogado().getEmail();
            pessoa = RN.obter(pessoa.getId());

            //atualizando a lista de papéis após atualiza a pessoa
            setPessoa(RN.alterarPermissoes(emailLogado, senha, pessoa, papeis));

            if (pessoa != null) {
                //Mensagem de sucesso
                UtilBean.criarMensagemDeInformacao("Permissões alteradas com sucesso");
                senha = "";
            } else {
                //Mensagem de erro: problemas ao salvar
                UtilBean.criarMensagemDeErro("Problema ao alterar as permissões");
                pessoa = new Pessoa();
            }
        }
    }

    public void pesquisar() {
        if (chaveDeBusca == null || chaveDeBusca.trim().length() == 0) {
            UtilBean.criarMensagemDeAviso("Nenhuma chave de busca ou pista informada");
        } else {
            pessoas = PessoaRN.obter(chaveDeBusca, criterio);
            if (pessoas == null || pessoas.isEmpty()) {
                UtilBean.criarMensagemDeAviso("Nenhuma pessoa encontrada.");
                UtilBean.criarMensagemDeInformacao("Tente um outro criterio/pista de pesquisa");
            } else {
                UtilBean.criarMensagemDeInformacao("Pessoa encontrada com sucesso");
            }
        }
    }

    /**
     * Método para cancelar e voltar para a página apropriada
     * Se returnTo foi definido, usa ele. Caso contrário, detecta pelo papel do usuário.
     * Prioridade de papéis: Administrador > Gerente > Coordenador > Pesquisador/Bolsista
     */
    public String cancelar() {
        if (returnTo != null && !returnTo.trim().isEmpty()) {
            // Se veio com parâmetro returnTo, usa ele
            return returnTo + "?faces-redirect=true";
        }

        // Detecta o papel do usuário logado com prioridade
        Pessoa usuarioLogado = UtilBean.obterUsuarioLogado();
        if (usuarioLogado != null && usuarioLogado.getPessoaPapels() != null) {

            // Prioridade 1: Administrador
            for (PessoaPapel pp : usuarioLogado.getPessoaPapels()) {
                if ("Administrador".equalsIgnoreCase(pp.getPapel().getNome())) {
                    return "/administrador/principal.xhtml?faces-redirect=true";
                }
            }

            // Prioridade 2: Gerente
            for (PessoaPapel pp : usuarioLogado.getPessoaPapels()) {
                if ("Gerente".equalsIgnoreCase(pp.getPapel().getNome())) {
                    return "/gerente/pessoa/lista.xhtml?faces-redirect=true";
                }
            }

            // Prioridade 3: Coordenador
            for (PessoaPapel pp : usuarioLogado.getPessoaPapels()) {
                if ("Coordenador".equalsIgnoreCase(pp.getPapel().getNome())) {
                    return "/gerente/pessoa/lista.xhtml?faces-redirect=true";
                }
            }

            // Prioridade 4: Bolsista
            for (PessoaPapel pp : usuarioLogado.getPessoaPapels()) {
                if ("Bolsista".equalsIgnoreCase(pp.getPapel().getNome())) {
                    return "/bolsista/principal.xhtml?faces-redirect=true";
                }
            }
        }

        // Caso padrão: volta para o dashboard do pesquisador
        return "/pesquisador/principal.xhtml?faces-redirect=true";
    }

    public String getReturnTo() {
        return returnTo;
    }

    public void setReturnTo(String returnTo) {
        this.returnTo = returnTo;
    }
}
