package br.edu.ufra.bean;

import br.edu.ufra.entidade.Edital;
import br.edu.ufra.entidade.Papel;
import br.edu.ufra.entidade.Pessoa;
import br.edu.ufra.entidade.PessoaPapel;
import br.edu.ufra.rn.PapelRN;
import br.edu.ufra.util.UtilBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.List;

@ManagedBean
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
}

