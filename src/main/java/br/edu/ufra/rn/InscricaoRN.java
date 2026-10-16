package br.edu.ufra.rn;

import br.edu.ufra.dao.InscricaoDAO;
import br.edu.ufra.entidade.Edital;
import br.edu.ufra.entidade.Inscricao;
import br.edu.ufra.entidade.Pessoa;
import br.edu.ufra.entidade.tipo.EnumAcaoAuditoria;
import br.edu.ufra.rn.LogAuditoriaRN;
import br.edu.ufra.util.UtilEmail;
import br.edu.ufra.util.UtilTexto;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class InscricaoRN {
    private final static InscricaoDAO DAO = new InscricaoDAO();

    private PessoaRN pessoaRN = new PessoaRN();

    public boolean salvar(Inscricao inscricao) {
        if (inscricao == null) {
            return false;
        } else {
            if (inscricao.getId() == null || inscricao.getId() == 0) {
                return DAO.criar(inscricao);
            } else {
                return DAO.alterar(inscricao);
            }
        }
    }

    public Inscricao obter(Integer id) {
        if (id == null) {
            return null;
        } else {
            return DAO.obter(Inscricao.class, id);
        }
    }

    public boolean novaInscricao(Edital edital, Pessoa pesquisador, String senha, String email) {
        if (edital == null || pesquisador == null) {
            return false;
        } else {
            String senhaAtualCriptografada = UtilTexto.criptografarSHA256(senha);
            Pessoa pesquisadorLogado = pessoaRN.login(email, senhaAtualCriptografada);
            if (pesquisadorLogado != null) {
                Inscricao inscricao = obter(pesquisador, edital);
                if (inscricao != null) {
                    return false;
                }
                Inscricao novaInscricao = new Inscricao();
                novaInscricao.setEdital(edital);
                novaInscricao.setPessoa(pesquisador);
                novaInscricao.setDataInscricao(new Date());
                novaInscricao.setProtocolo(gerarProtocolo(edital.getId(), pesquisador.getId()));
                boolean salvou = salvar(novaInscricao);
                if (salvou){
                    notificarNovaInscricao(pesquisadorLogado, novaInscricao);
                }
                return salvou;
            }
            return false;
        }
    }

    private void notificarNovaInscricao(Pessoa pessoa, Inscricao inscricao) {
        String destinatario = pessoa.getEmail();
        String titulo = "Confirmação da inscrição no edital";
        StringBuilder mensagem = new StringBuilder();
        mensagem.append("Prezado(a) ");
        mensagem.append(pessoa.getNomeCompleto());
        mensagem.append("\n\n");
        mensagem.append("Confirmamos que a sua inscrição foi um sucesso no edital " + inscricao.getEdital().getTitulo() + ".\n\n");
        mensagem.append("Atenciosamente \n");
        mensagem.append("SIG-BOLSAS");
        UtilEmail.enviarEmail(destinatario, titulo, mensagem.toString());
    }

    public String gerarProtocolo(int editalId, int pessoaId) {
        int ano = LocalDate.now().getYear();

        // Formata editalId 3 dígitos, preenchendo com zeros
        String editalFormatado = String.format("%03d", editalId);
        // Formata pessoaId 5 dígitos, preenchendo com zeros
        String pessoaFormatada = String.format("%05d", pessoaId);

        // Concatena tudo no formato AAAAEEEEPPPP
        String protocoloGerado = String.format("%d%s%s", ano, editalFormatado, pessoaFormatada);
        return protocoloGerado;
    }

    public List<Inscricao> obter(Pessoa pesquisador) {
        if (pesquisador == null || pesquisador.getId() == null) {
            return null;
        } else {
            return DAO.obter(pesquisador);
        }
    }

    public List<Inscricao> obter(Pessoa pesquisador, boolean vigentes) {
        if (pesquisador == null || pesquisador.getId() == null) {
            return null;
        } else {
            return DAO.obter(pesquisador, vigentes);
        }
    }

    public List<Inscricao> obter(Edital edital) {
        if (edital == null || edital.getId() == null) {
            return null;
        } else {
            return DAO.obter(edital);
        }
    }

    public Inscricao obter(Pessoa pesquisador, Edital edital) {
        if (pesquisador == null || edital == null) {
            return null;
        } else {
            Inscricao resposta = DAO.obter(pesquisador, edital);
            return resposta;
        }
    }

    public List<Inscricao> obter(Edital edital, Date inicio, Date fim) {
        if (edital == null || inicio == null || fim == null) {
            return null;
        } else {
            return DAO.obter(edital, inicio, fim);
        }
    }

    public boolean homologar(Inscricao inscricao, String usuarioResponsavel) {
        if (inscricao == null) return false;
        inscricao.setSituacao('H');
        boolean ok = DAO.alterar(inscricao);
        if (ok) {
            LogAuditoriaRN.registrar(usuarioResponsavel, EnumAcaoAuditoria.HOMOLOGAR, "Inscricao",
                inscricao.getId(), "Inscrição homologada: protocolo " + inscricao.getProtocolo(), null);
            notificarMudancaSituacao(inscricao);
        }
        return ok;
    }

    public boolean aprovar(Inscricao inscricao, String usuarioResponsavel) {
        if (inscricao == null) return false;
        inscricao.setSituacao('A');
        boolean ok = DAO.alterar(inscricao);
        if (ok) {
            LogAuditoriaRN.registrar(usuarioResponsavel, EnumAcaoAuditoria.APROVAR, "Inscricao",
                inscricao.getId(), "Inscrição aprovada: protocolo " + inscricao.getProtocolo(), null);
            notificarMudancaSituacao(inscricao);
        }
        return ok;
    }

    public boolean indeferir(Inscricao inscricao, String motivo, String usuarioResponsavel) {
        if (inscricao == null) return false;
        inscricao.setSituacao('I');
        inscricao.setMotivoIndeferimento(motivo);
        boolean ok = DAO.alterar(inscricao);
        if (ok) {
            LogAuditoriaRN.registrar(usuarioResponsavel, EnumAcaoAuditoria.INDEFERIR, "Inscricao",
                inscricao.getId(), "Inscrição indeferida. Motivo: " + motivo, null);
            notificarMudancaSituacao(inscricao);
        }
        return ok;
    }

    public boolean classificar(Edital edital, String usuarioResponsavel) {
        List<Inscricao> inscricoes = DAO.obter(edital);
        if (inscricoes == null || inscricoes.isEmpty()) return false;

        inscricoes.sort(Comparator.comparingDouble(
            (Inscricao i) -> i.getPontuacaoPesquisador() + i.getPontuacaoComite()
        ).reversed());

        for (int i = 0; i < inscricoes.size(); i++) {
            inscricoes.get(i).setClassificacao(i + 1);
            DAO.alterar(inscricoes.get(i));
        }
        LogAuditoriaRN.registrar(usuarioResponsavel, EnumAcaoAuditoria.CLASSIFICAR, "Edital",
            edital.getId(), "Ranking de pesquisadores recalculado para o edital: " + edital.getTitulo(), null);
        return true;
    }

    private void notificarMudancaSituacao(Inscricao inscricao) {
        Pessoa pesquisador = inscricao.getPessoa();
        if (pesquisador == null || pesquisador.getEmail() == null) return;

        String assunto = "Atualização da sua inscrição — SIG Bolsas";
        StringBuilder msg = new StringBuilder();
        msg.append("Prezado(a) ").append(pesquisador.getNomeCompleto()).append(",\n\n");
        msg.append("O status da sua inscrição no edital \"")
           .append(inscricao.getEdital().getTitulo()).append("\" foi atualizado.\n\n");

        Character sit = inscricao.getSituacao();
        if (sit == null) sit = 'P';
        switch (sit) {
            case 'H':
                msg.append("Status: HOMOLOGADA — sua inscrição está em análise pelo comitê.\n");
                break;
            case 'A':
                msg.append("Status: APROVADA — parabéns!\n");
                msg.append("Classificação: ").append(inscricao.getClassificacao()).append("\n");
                break;
            case 'I':
                msg.append("Status: INDEFERIDA.\n");
                if (inscricao.getMotivoIndeferimento() != null) {
                    msg.append("Motivo: ").append(inscricao.getMotivoIndeferimento()).append("\n");
                }
                break;
            default:
                msg.append("Status: Em análise.\n");
        }
        msg.append("\nAcesse o sistema para mais detalhes.\nAtenciosamente,\nSIG-BOLSAS");
        UtilEmail.enviarEmail(pesquisador.getEmail(), assunto, msg.toString());
    }
}