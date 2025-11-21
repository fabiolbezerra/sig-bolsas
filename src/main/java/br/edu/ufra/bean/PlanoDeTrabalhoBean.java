package br.edu.ufra.bean;

import br.edu.ufra.entidade.*;
import br.edu.ufra.rn.EditalRN;
import br.edu.ufra.rn.InscricaoRN;
import br.edu.ufra.rn.PessoaRN;
import br.edu.ufra.rn.PlanoDeTrabalhoRN;
import br.edu.ufra.util.UtilBean;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Collections;

@Named
@ViewScoped
public class PlanoDeTrabalhoBean implements Serializable {

    private final PlanoDeTrabalhoRN RN = new PlanoDeTrabalhoRN();
    private EditalRN editalRN = new EditalRN();
    private PessoaRN pessoaRN = new PessoaRN();
    private InscricaoRN inscricaoRN = new InscricaoRN();

    private PlanoDeTrabalho planoDeTrabalho = new PlanoDeTrabalho();
    private List<PlanoDeTrabalho> planosDeTrabalho;
    private String titulo;
    private String resumo;
    private Inscricao inscricao;
    private Edital edital;
    private Pessoa pesquisador;
    private String chaveDeBusca;
    private char criterio;
    private static String formulario;

    public PlanoDeTrabalhoBean() {
        System.out.println("Criando PlanoDeTrabalhoBean ...");
    }

    @PostConstruct
    public void init() {
        System.out.println("Inicializando PlanoDeTrabalhoBean ...");

        try {

            String idParam = UtilBean.obterValor("inscricaoID");
            if (idParam != null && !idParam.isEmpty()) {
                int inscricaoID = Integer.parseInt(idParam);
                inscricao = inscricaoRN.obter(inscricaoID);
                System.out.println("Carregando inscricao ID: " + inscricaoID);
            }

            String editalIdParam = UtilBean.obterValor("editalID");
            if (editalIdParam != null && !editalIdParam.isEmpty()) {
                int editalID = Integer.parseInt(editalIdParam);
                edital = editalRN.obter(editalID);
                System.out.println("Carregando edital ID: " + editalID);
            }

            // Carrega o edital se tiver ID na URL
            String idEdital = UtilBean.obterValor("editalID");
            if (idEdital != null && !idEdital.isEmpty()) {
                int editalID = Integer.parseInt(idEdital);
                System.out.println("Carregando edital ID: " + editalID);
                edital = editalRN.obter(editalID);
            }

            String pesquisadorIdParam = UtilBean.obterValor("pesquisadorID");
            if (pesquisadorIdParam != null && !pesquisadorIdParam.isEmpty()) {
                int pesquisadorID = Integer.parseInt(pesquisadorIdParam);
                pesquisador = pessoaRN.obter(pesquisadorID);
                System.out.println("Carregando pesquisador ID: " + pesquisadorID);
            }
            String planoIdParam = UtilBean.obterValor("planoID");
            if (planoIdParam != null && !planoIdParam.isEmpty()) {
                int planoID = Integer.parseInt(planoIdParam);
                planoDeTrabalho = RN.obter(planoID);
                System.out.println("Carregando plano ID: " + planoID);
            }
            planosDeTrabalho = obterInscricaoPlanos();

        } catch (Exception e) {
            System.out.println("Erro ao inicializar InscricaoBean: " + e.getMessage());
            UtilBean.criarMensagemDeErro("Erro ao carregar planoDeTrabalhoBean");
            planoDeTrabalho = new PlanoDeTrabalho();
        }
    }


    public String getChaveDeBusca() {
        return chaveDeBusca;
    }

    public void setChaveDeBusca(String chaveDeBusca) {
        this.chaveDeBusca = chaveDeBusca;
    }

    public char getCriterio() {
        return criterio;
    }

    public void setCriterio(char criterio) {
        this.criterio = criterio;
    }

    public static String getFormulario() {
        return formulario;
    }

    public static void setFormulario(String formulario) {
        PlanoDeTrabalhoBean.formulario = formulario;
    }

    public List<PlanoDeTrabalho> getPlanosDeTrabalho() {
        setFormulario("lista.xhtml");
        return planosDeTrabalho;
    }

    public void setPlanosDeTrabalho(List<PlanoDeTrabalho> planosDeTrabalho) {
        this.planosDeTrabalho = planosDeTrabalho;
    }

    public PlanoDeTrabalho getPlanoDeTrabalho() {
        return planoDeTrabalho;
    }

    public void setPlanoDeTrabalho(PlanoDeTrabalho planoDeTrabalho) {
        this.planoDeTrabalho = planoDeTrabalho;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public Pessoa getPesquisador() {
        return pesquisador;
    }

    public Inscricao getInscricao() {
        return inscricao;
    }

    public void setInscricao(Inscricao inscricao) {
        this.inscricao = inscricao;
    }

    public Edital getEdital() {
        return edital;
    }

    public void setEdital(Edital edital) {
        this.edital = edital;
    }

    public void setPesquisador(Pessoa pesquisador) {
        this.pesquisador = pesquisador;
    }

    public List<PlanoDeTrabalho> obterPlanoDeTrabalhoVigentes() {
        setFormulario("lista-vigentes.xhtml");
        return planosDeTrabalho = RN.obterVigentes();
    }

    public List<PlanoDeTrabalho> obterPorBolsista(Long bolsistaId) {
        if (bolsistaId == null) {
            return Collections.emptyList();
        }


        try {
            List<PlanoDeTrabalho> planos = RN.obterPorBolsista(bolsistaId);
            return planos;

        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<PlanoDeTrabalho> obterInscricaoPlanos() {
        return RN.obter(inscricao);
    }

    public String voltar() {
        if (formulario.equals("lista-vigentes.xhtml")) {
            return "/bolsista/plano/lista.xhtml?faces-redirect=true";
        } else {
            return "/gerente/plano/lista.xhtml?faces-redirect=true";
        }
    }

    public void salvar() {
        if (planoDeTrabalho == null) {
            UtilBean.criarMensagemDeErro("Plano de trabalho não encontrado!");
        }
        if (planoDeTrabalho.getInscricao() == null) {
            planoDeTrabalho.setInscricao(this.inscricao);
        }
        if (planoDeTrabalho.getDataCadastro() == null) {
            planoDeTrabalho.setDataCadastro(new Date());
        }

        if (RN.salvar(planoDeTrabalho)) {
            UtilBean.criarMensagemDeInformacao("Plano de trabalho gravado com sucesso");
        } else {
            UtilBean.criarMensagemDeErro("Problema ao gravar o plano de trabalho");
        }
    }

    public void excluir(PlanoDeTrabalho planoDeTrabalho) {
        if (planoDeTrabalho == null) {
            UtilBean.criarMensagemDeErro("Objeto nulo");
        } else {
            if (RN.excluir(planoDeTrabalho)) {
                UtilBean.criarMensagemDeInformacao("Plano excluído com sucesso");
                planosDeTrabalho.remove(planoDeTrabalho);
            } else {
                UtilBean.criarMensagemDeErro("Não se pode excluir o plano de trabalho, pois ele está vinculado a outros registros.");
            }
        }
    }

    public void pesquisar() {
        if (chaveDeBusca == null || chaveDeBusca.trim().length() == 0) {
            UtilBean.criarMensagemDeAviso("Nenhuma chave de busca ou pista informada");
        } else {
            planosDeTrabalho = PlanoDeTrabalhoRN.obter(chaveDeBusca, criterio);
            if (planosDeTrabalho == null || planosDeTrabalho.isEmpty()) {
                UtilBean.criarMensagemDeAviso("Nenhuma plano de trabalho encontrado.");
                UtilBean.criarMensagemDeInformacao("Tente um outro criterio/pista de pesquisa");
            } else {
                UtilBean.criarMensagemDeInformacao("Plano de Trabalho encontrado com sucesso");
            }
        }
    }

    public String obterSituacao(Character situacao) {
        if (situacao == null) {
            return null;
        }
        switch (situacao) {
            case 'C':
                return "Cadastrado";
            case 'P':
                return "Publicado";
            case 'N':
                return "Não publicado";
            case 'E':
                return "Encerrado";
            default:
                return "-";
        }
    }

    public String obterTipo(Character tipo) {
        if (tipo == null) {
            return null;
        }
        switch (tipo) {
            case 'P':
                return "Pesquisa";
            case 'D':
                return "Desenvolvimento Tecnológico";
            case 'E':
                return "Extensão";
            default:
                return "-";
        }
    }
}
