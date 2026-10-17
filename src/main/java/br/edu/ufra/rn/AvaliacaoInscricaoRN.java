package br.edu.ufra.rn;

import br.edu.ufra.dao.AvaliacaoInscricaoDAO;
import br.edu.ufra.dao.InscricaoDAO;
import br.edu.ufra.entidade.AvaliacaoInscricao;
import br.edu.ufra.entidade.Inscricao;
import br.edu.ufra.entidade.Pessoa;
import br.edu.ufra.util.UtilEmail;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class AvaliacaoInscricaoRN {

    private final AvaliacaoInscricaoDAO DAO = new AvaliacaoInscricaoDAO();
    private final InscricaoDAO inscricaoDAO = new InscricaoDAO();

    public boolean salvar(AvaliacaoInscricao avaliacao) {
        if (avaliacao == null || avaliacao.getInscricao() == null || avaliacao.getPessoa() == null) {
            return false;
        }
        avaliacao.setDataAvaliacao(LocalDate.now());

        AvaliacaoInscricao existente = DAO.obterPorPessoaEInscricao(avaliacao.getPessoa(), avaliacao.getInscricao());
        boolean salvo;
        if (existente == null) {
            salvo = DAO.criar(avaliacao);
        } else {
            avaliacao.setId(existente.getId());
            salvo = DAO.alterar(avaliacao);
        }

        if (salvo) {
            atualizarPontuacaoComite(avaliacao.getInscricao());
        }
        return salvo;
    }

    public List<AvaliacaoInscricao> obterPorInscricao(Inscricao inscricao) {
        if (inscricao == null) return null;
        return DAO.obterPorInscricao(inscricao);
    }

    public AvaliacaoInscricao obterPorPessoaEInscricao(Pessoa pessoa, Inscricao inscricao) {
        if (pessoa == null || inscricao == null) return null;
        return DAO.obterPorPessoaEInscricao(pessoa, inscricao);
    }

    private void atualizarPontuacaoComite(Inscricao inscricao) {
        double media = DAO.calcularMediaPorInscricao(inscricao);
        inscricao.setPontuacaoComite(media);
        inscricaoDAO.alterar(inscricao);
    }

    public boolean calcularPontuacaoPesquisador(Inscricao inscricao) {
        if (inscricao == null || inscricao.getRespostas() == null) return false;
        double total = inscricao.getRespostas().stream()
            .mapToDouble(r -> r.getPontuacaoResposta())
            .sum();
        inscricao.setPontuacaoPesquisador(total);
        return inscricaoDAO.alterar(inscricao);
    }

    public boolean classificar(List<Inscricao> inscricoes) {
        if (inscricoes == null || inscricoes.isEmpty()) return false;
        inscricoes.sort(Comparator.comparingDouble(
            (Inscricao i) -> i.getPontuacaoPesquisador() + i.getPontuacaoComite()
        ).reversed());

        for (int i = 0; i < inscricoes.size(); i++) {
            inscricoes.get(i).setClassificacao(i + 1);
            inscricaoDAO.alterar(inscricoes.get(i));
        }
        return true;
    }

    public void notificarResultado(Pessoa pesquisador, Inscricao inscricao) {
        String situacao = inscricao.getSituacao() != null ? inscricao.getSituacao().toString() : "P";
        String assunto = "Resultado da sua inscrição — SIG Bolsas";
        StringBuilder msg = new StringBuilder();
        msg.append("Prezado(a) ").append(pesquisador.getNomeCompleto()).append(",\n\n");
        msg.append("O resultado da sua inscrição no edital \"")
           .append(inscricao.getEdital().getTitulo()).append("\" foi definido.\n\n");

        if ("A".equals(situacao)) {
            msg.append("Parabéns! Sua inscrição foi APROVADA.\n");
            msg.append("Classificação: ").append(inscricao.getClassificacao()).append("\n");
            msg.append("Pontuação total: ")
               .append(inscricao.getPontuacaoPesquisador() + inscricao.getPontuacaoComite()).append("\n");
        } else if ("I".equals(situacao)) {
            msg.append("Informamos que sua inscrição foi INDEFERIDA.\n");
            if (inscricao.getMotivoIndeferimento() != null && !inscricao.getMotivoIndeferimento().isEmpty()) {
                msg.append("Motivo: ").append(inscricao.getMotivoIndeferimento()).append("\n");
            }
        } else if ("H".equals(situacao)) {
            msg.append("Sua inscrição foi HOMOLOGADA e está em análise pelo comitê.\n");
        }

        msg.append("\nAtenciosamente,\nSIG-BOLSAS");
        UtilEmail.enviarEmail(pesquisador.getEmail(), assunto, msg.toString());
    }
}