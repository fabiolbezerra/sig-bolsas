package br.edu.ufra.bean;

import br.edu.ufra.entidade.Formulario;
import br.edu.ufra.entidade.OpcaoPergunta;
import br.edu.ufra.entidade.Pergunta;
import br.edu.ufra.entidade.tipo.EnumTipoPergunta;
import br.edu.ufra.rn.FormularioRN;
import br.edu.ufra.rn.PerguntaRN;
import br.edu.ufra.util.UtilBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@ManagedBean
@ViewScoped
public class PerguntaBean implements Serializable {
    private final FormularioRN FORMULARIO_RN = new FormularioRN();
    private final PerguntaRN PERGUNTA_RN = new PerguntaRN();

    private Formulario formulario = new Formulario();
    private Pergunta pergunta = new Pergunta();
    private OpcaoPergunta opcaoPergunta = new OpcaoPergunta();

    private int ordem = 0;

    private List<Pergunta> perguntas;

    public PerguntaBean() {
        System.out.println("Criando PerguntaBean ...");
    }

    @PostConstruct
    public void init() {
        System.out.println("Inicializando PerguntaBean ...");
        String formularioID = UtilBean.obterValor("formularioID");
        try {
            int id = Integer.parseInt(formularioID);
            formulario = FORMULARIO_RN.obter(id);
            if (formulario == null) {
                formulario = new Formulario();
            }
            pergunta.setFormulario(formulario);
            pergunta.setOpcaoPerguntas(new ArrayList<>());
            perguntas = PERGUNTA_RN.obter(formulario);
            if (perguntas != null) {
                ordem = perguntas.size();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    public List<Pergunta> getPerguntas() {
        return perguntas;
    }

    public void setPerguntas(List<Pergunta> perguntas) {
        this.perguntas = perguntas;
    }

    public OpcaoPergunta getOpcaoPergunta() {
        return opcaoPergunta;
    }

    public void setOpcaoPergunta(OpcaoPergunta opcaoPergunta) {
        this.opcaoPergunta = opcaoPergunta;
    }

    public Pergunta getPergunta() {
        return pergunta;
    }

    public void setPergunta(Pergunta pergunta) {
        this.pergunta = pergunta;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public void moverParaCima(Pergunta pergunta) {
        int index = perguntas.indexOf(pergunta);
        if (index == -1) {
            //Não achou a pergunta
            UtilBean.criarMensagemDeAviso("Pergunta não encontrada"); //esse caso não deve ocorrer, mas ...
        } else {
            if (index == 0) {
                UtilBean.criarMensagemDeAviso("Pergunta não pode ser movida para cima");
            } else {
                if (PERGUNTA_RN.trocarDeOrdem(perguntas.get(index), perguntas.get(index - 1), true)) {
                    UtilBean.criarMensagemDeInformacao("Pergunta movida para cima");
                }
            }
            atualizar();
        }
    }

    public void moverParaBaixo(Pergunta pergunta) {
        int index = perguntas.indexOf(pergunta);
        if (index == -1) {
            //Não achou a pergunta
            UtilBean.criarMensagemDeAviso("Pergunta não encontrada"); //esse caso não deve ocorrer, mas ...
        } else {
            if (index == perguntas.size() - 1) {
                UtilBean.criarMensagemDeAviso("Pergunta não pode ser movida para baixo");
            } else {
                if (PERGUNTA_RN.trocarDeOrdem(perguntas.get(index), perguntas.get(index + 1), false)) {
                    UtilBean.criarMensagemDeInformacao("Pergunta movida para baixo");
                }
            }
            atualizar();
        }
    }

    public void salvar() {
        if (pergunta == null) {
            //Mensagem de erro: objeto nulo
            UtilBean.criarMensagemDeErro("Objeto nulo");
        } else {
            if (pergunta.getId() == null) {
                pergunta.setOrdem(++ordem);
            }
            if (PERGUNTA_RN.salvar(pergunta)) {
                //Mensagem de sucesso
                UtilBean.criarMensagemDeInformacao("Pergunta gravada com sucesso");
            } else {
                //Mensagem de erro: problemas ao salvar
                UtilBean.criarMensagemDeErro("Problema ao gravar a pergunta");
            }

            atualizar();
        }
    }

    public void excluir(Pergunta pergunta) {
        if (pergunta == null) {
            //Mensagem de erro: objeto nulo
            UtilBean.criarMensagemDeErro("Objeto nulo");
        } else {
            if (PERGUNTA_RN.excluir(pergunta)) {
                //Mensagem de sucesso
                UtilBean.criarMensagemDeInformacao("Pergunta excluída com sucesso");
            } else {
                //Mensagem de erro: problemas ao salvar
                UtilBean.criarMensagemDeErro("Problema ao gravar a pergunta");
            }

            atualizar();
        }
    }

    public void atualizar() {
        //Atualizando os objetos
        pergunta = new Pergunta();
        pergunta.setFormulario(formulario);
        pergunta.setOpcaoPerguntas(new ArrayList<>());
        opcaoPergunta = new OpcaoPergunta();
        perguntas = PERGUNTA_RN.obter(formulario);
    }

    public void selecionarTipo(){
        System.out.println("Tipo de pergunta: " + pergunta.getTipo());
        if (pergunta.getOpcaoPerguntas() == null) {
            pergunta.setOpcaoPerguntas(new ArrayList<>());
        }
    }

    public boolean isUnicaEscolha(){
        return pergunta.getTipo() == EnumTipoPergunta.UNICA_ESCOLHA;
    }

    public void adicionarOpcao() {
        if (opcaoPergunta != null && opcaoPergunta.getResposta() != null && !opcaoPergunta.getResposta().isEmpty()) {
            if (pergunta.getOpcaoPerguntas() == null) {
                pergunta.setOpcaoPerguntas(new ArrayList<>());
            }
            pergunta.getOpcaoPerguntas().add(opcaoPergunta);
            opcaoPergunta = new OpcaoPergunta();
            UtilBean.criarMensagemDeInformacao("Opção adicionada com sucesso!");
        } else {
            UtilBean.criarMensagemDeAviso("Por favor, insira o texto da opção.");
        }
    }

    public void removerOpcao(OpcaoPergunta opcao) {
        if (pergunta.getOpcaoPerguntas() != null) {
            pergunta.getOpcaoPerguntas().remove(opcao);
        }
    }

}