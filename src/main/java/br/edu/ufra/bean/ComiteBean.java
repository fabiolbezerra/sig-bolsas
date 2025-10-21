package br.edu.ufra.bean;

import br.edu.ufra.entidade.*;
import br.edu.ufra.rn.ComiteRN;
import br.edu.ufra.util.UtilBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.*;

@ManagedBean
@ViewScoped
public class ComiteBean implements Serializable {
    private final ComiteRN RN = new ComiteRN();

    private Comite comite = new Comite();

    private Pessoa pesquisadorSelecionado;

    private List<Comite> comites;
    private String todos;
    private static String formulario;

    public ComiteBean() {
        System.out.println("Criando ComiteBean ...");
    }

    @PostConstruct
    public void init() {
        System.out.println("Inicializando ComiteBean ...");

        String comiteID = UtilBean.obterValor("comite");
        if (comiteID != null) {
            try {
                int id = Integer.parseInt(comiteID);
                comite = RN.obter(id);
                if (comite == null) {
                    comite = new Comite();
                    UtilBean.criarMensagemDeAviso("Não foi possivel recuperar o comite. Código inválido.");
                }
            } catch (Exception e) {
                comite = new Comite();
                UtilBean.criarMensagemDeErro("Não foi possível recuperar o comite.");
            }
        }
        Pessoa usuarioLogado = UtilBean.obterUsuarioLogado();
        if (comite.getId() == null && UtilBean.possuiPapel(usuarioLogado, 'C')) {
            comite.setCoordenador(usuarioLogado);
        }
    }

    public static String getFormulario() {
        return formulario;
    }

    public static void setFormulario(String formulario) {
        ComiteBean.formulario = formulario;
    }

    public List<Comite> getComites() {
        setFormulario("detalhe/lista.xhtml");
        setFormulario("editar/lista.xhtml");
        return comites = RN.listarTodos();
    }

    public List<Comite> listarVigentes() {
        setFormulario("detalhe/lista-vigentes.xhtml");
        setFormulario("editar/lista-vigentes.xhtml");
        return comites = RN.listarVigentes();

    }

    public List<Comite> listarEncerrados() {
        setFormulario("detalhe/lista-encerrados.xhtml");
        return comites = RN.listarEncerrados();
    }

    public Comite getComite() {
        return comite;
    }

    public void setComite(Comite comite) {
        this.comite = comite;
    }

    public String getTodos() {
        return todos;
    }

    public void setTodos(String todos) {
        this.todos = todos;
    }

    public void setComites(Comite comite) {
        this.comite = comite;
    }

    public Pessoa getPesquisadorSelecionado() {
        return pesquisadorSelecionado;
    }

    public void setPesquisadorSelecionado(Pessoa pesquisadorSelecionado) {
        this.pesquisadorSelecionado = pesquisadorSelecionado;
    }

    public void adicionarPesquisador() {
        try {

            if (pesquisadorSelecionado == null) {
                UtilBean.criarMensagemDeAviso("Selecione um pesquisador para adicionar ao comitê.");
                return;
            }

            if (comite.getId() == null) {
                UtilBean.criarMensagemDeAviso("Salve o comitê antes de adicionar pesquisadores.");
                return;
            }

            if (comite.getCoordenador().getId().equals(pesquisadorSelecionado.getId())){
                UtilBean.criarMensagemDeAviso("O coordenador não pode fazer parte do comitê.");
                return;
            }

            boolean jaExiste = false;
            for (ComitePessoa cp : comite.getComitePessoas()) {
                if (cp.getPessoa() != null && cp.getPessoa().getId().equals(pesquisadorSelecionado.getId())) {
                    jaExiste = true;
                    break;
                }
            }
            if (jaExiste) {
                UtilBean.criarMensagemDeAviso("Este pesquisador já está no comitê.");
            return;}

            ComitePessoa comitePessoa = new ComitePessoa();
            comitePessoa.setComite(comite);
            comitePessoa.setPessoa(pesquisadorSelecionado);
            comite.getComitePessoas().add(comitePessoa);

            if (RN.salvar(comite)) {
                pesquisadorSelecionado = null;
                UtilBean.criarMensagemDeInformacao("Pesquisador adicionado ao comitê com sucesso!");
            } else {
                UtilBean.criarMensagemDeErro("Erro ao salvar o comitê no banco de dados.");
                }

            pesquisadorSelecionado = null;

        } catch (Exception e) {
            UtilBean.criarMensagemDeErro("Erro ao adicionar pesquisador: " + e.getMessage());
        }
    }

    public void removerPesquisador(Pessoa pesquisador) {
        try {
            if (pesquisador != null && comite.getComitePessoas() != null) {
                ComitePessoa comitePessoaParaRemover = null;

                for (ComitePessoa cp : comite.getComitePessoas()) {
                    if (cp.getPessoa() != null && cp.getPessoa().getId().equals(pesquisador.getId())) {
                        comitePessoaParaRemover = cp;
                        break;
                    }
                }

                if (comitePessoaParaRemover != null) {
                    comite.getComitePessoas().remove(comitePessoaParaRemover);
                    if (RN.salvar(comite)) {
                        UtilBean.criarMensagemDeInformacao("Pesquisador removido do comitê com sucesso!");
                    } else {
                        UtilBean.criarMensagemDeErro("Erro ao salvar as alterações no banco de dados.");
                    }
                } else {
                    UtilBean.criarMensagemDeAviso("Pesquisador não encontrado no comitê.");
                }
            } else {
                UtilBean.criarMensagemDeErro("Erro ao tentar remover o pesquisador.");
            }
        } catch (Exception e) {
            UtilBean.criarMensagemDeErro("Erro ao remover pesquisador: " + e.getMessage());
        }
    }

    public void salvar() {
        if (comite == null) {
            //Mensagem de erro: objeto nulo
            UtilBean.criarMensagemDeErro("Objeto nulo");
        } else if (comite.getInicio().after(comite.getFim())) {
            UtilBean.criarMensagemDeErro("Data inicial não pode ser maior que a data final.");
        } else {
            if (RN.salvar(comite)) {
                UtilBean.criarMensagemDeInformacao("Comitê gravado com sucesso");
            } else {
                UtilBean.criarMensagemDeErro("Problema ao gravar o comitê");
            }
        }
    }

    public List<Comite> obterComites(Character tipo) {
        switch (tipo) {
            case 'V': {
                return comites = RN.listarVigentes();
            }
            case 'E': {
                return comites = RN.listarEncerrados();
            }
            default: {
                return comites = RN.listarTodos();
            }
        }
    }

    public String voltar() {
        if (formulario.equals("detalhe/lista-vigentes.xhtml")) {
            return "/coordenador/comite/lista-vigentes.xhtml?faces-redirect=true";
        } else if (formulario.equals("editar/lista-vigentes.xhtml")) {
            return "/coordenador/comite/lista-vigentes.xhtml?faces-redirect=true";
        } else if (formulario.equals("detalhe/lista-encerrados.xhtml")) {
            return "/coordenador/comite/lista-encerrados.xhtml?faces-redirect=true";
        } else if (formulario.equals("detalhe/lista.xhtml")) {
            return "/gerente/comite/lista.xhtml?faces-redirect=true";
        } else if (formulario.equals("editar/lista.xhtml")) {
            return "/gerente/comite/lista.xhtml?faces-redirect=true";
        } else {
            return null;
        }
    }
}