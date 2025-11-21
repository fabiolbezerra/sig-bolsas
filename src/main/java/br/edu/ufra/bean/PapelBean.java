package br.edu.ufra.bean;

import br.edu.ufra.entidade.*;
import br.edu.ufra.rn.PapelRN;
import br.edu.ufra.util.UtilBean;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import java.util.List;

@Named
@RequestScoped
public class PapelBean {
    private final PapelRN RN = new PapelRN();

    private Papel papel = new Papel();

    private List<Papel> papeis;

    public List<Papel> getlistarPerfil() {
        return RN.listarPerfil();
    }

    public PapelBean() {
        System.out.println("Criando PapelBean ...");
    }

    @PostConstruct
    public void init() {
        System.out.println("Inicializando PapelBean ...");
        papeis = RN.listarTodas();
    }

    public List<Papel> getPapeis() {
        return papeis;
    }

    public Papel getPapel() {
        return papel;
    }

    public void setPapel(Papel papel) {
        this.papel = papel;
    }

    public Pessoa getUsuarioLogado() {
        return UtilBean.obterUsuarioLogado();
    }

    public boolean isUsuarioLogadoAdministrador() {
        boolean resposta = false;
        for (PessoaPapel permissao : getUsuarioLogado().getPessoaPapels()) {
            if (permissao.getPapel().getCodigo() == 'A') {
                resposta = true;
                break;
            }
        }
        return resposta;
    }

    public boolean isUsuarioLogadoCoordenador() {
        boolean resposta = false;
        for (PessoaPapel permissao : getUsuarioLogado().getPessoaPapels()) {
            if (permissao.getPapel().getCodigo() == 'C') {
                resposta = true;
                break;
            }
        }
        return resposta;
    }

    public boolean isUsuarioLogadoGerente() {
        boolean resposta = false;
        for (PessoaPapel permissao : getUsuarioLogado().getPessoaPapels()) {
            if (permissao.getPapel().getCodigo() == 'G') {
                resposta = true;
                break;
            }
        }
        return resposta;
    }

    public boolean isUsuarioLogadoPesquisador() {
        boolean resposta = false;
        for (PessoaPapel permissao : getUsuarioLogado().getPessoaPapels()) {
            if (permissao.getPapel().getCodigo() == 'P') {
                resposta = true;
                break;
            }
        }
        return resposta;
    }
    
    public boolean isUsuarioLogadoBolsista() {
        boolean resposta = false;
        for (PessoaPapel permissao : getUsuarioLogado().getPessoaPapels()) {
            if (permissao.getPapel().getCodigo() == 'B') {
                resposta = true;
                break;
            }
        }
        return resposta;
    }

    public String irParaPaginaPrincipal() {
        Pessoa usuario = UtilBean.obterUsuarioLogado();
        return UtilBean.irParaPaginaPrincipal(usuario);
    }

    public boolean isMembroDoComite(Edital edital) {
        Pessoa usuario = UtilBean.obterUsuarioLogado();
        if (usuario == null
                || !UtilBean.possuiPapel(usuario, 'P')
                || edital == null
                || edital.getComite() == null
                || edital.getComite().getComitePessoas() == null) {
            return false;
        }
        for (ComitePessoa membro : edital.getComite().getComitePessoas()) {
            if (usuario.equals(membro.getPessoa())) {
                return true;
            }
        }
        return false;
    }
}


