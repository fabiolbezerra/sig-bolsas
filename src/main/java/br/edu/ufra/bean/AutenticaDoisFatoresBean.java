package br.edu.ufra.bean;

import br.edu.ufra.entidade.Pessoa;
import br.edu.ufra.entidade.tipo.EnumAcaoAuditoria;
import br.edu.ufra.rn.LogAuditoriaRN;
import br.edu.ufra.rn.PessoaRN;
import br.edu.ufra.util.UtilBean;
import br.edu.ufra.util.UtilEmail;
import br.edu.ufra.util.UtilTexto;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Named
@RequestScoped  // Bug 3 corrigido: ViewScoped causava conflito com redirect em @PostConstruct
public class AutenticaDoisFatoresBean {

    private final PessoaRN RN = new PessoaRN();

    private String codigoDigitado;
    private String emailPendente;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context == null) return;

        Object val = context.getExternalContext().getSessionMap().get("pendente2FA");
        emailPendente = val != null ? val.toString() : null;

        if (emailPendente == null) {
            try {
                context.getExternalContext().redirect(
                        context.getExternalContext().getRequestContextPath() + "/index.xhtml");
                context.responseComplete();
            } catch (Exception ignored) {}
        }
    }

    public String validar() {
        if (emailPendente == null) {
            UtilBean.criarMensagemDeErro("Sessão inválida. Faça login novamente.");
            return "/index.xhtml?faces-redirect=true";
        }

        if (codigoDigitado == null || codigoDigitado.trim().isEmpty()) {
            UtilBean.criarMensagemDeErro("Informe o código recebido por e-mail.");
            return null;
        }

        Pessoa pessoa = RN.obterPorEmail(emailPendente);
        if (pessoa == null) {
            UtilBean.criarMensagemDeErro("Usuário não encontrado.");
            return "/index.xhtml?faces-redirect=true";
        }

        if (pessoa.getdoisFatoresCodigo() == null
                || pessoa.getdoisFatoresExpiracao() == null
                || LocalDateTime.now().isAfter(pessoa.getdoisFatoresExpiracao())) {
            UtilBean.criarMensagemDeErro("Código expirado. Faça login novamente para receber um novo código.");
            limparSessao2FA();
            return "/index.xhtml?faces-redirect=true";
        }

        if (!codigoDigitado.trim().equals(pessoa.getdoisFatoresCodigo())) {
            String ip = obterIpDoCliente();
            LogAuditoriaRN.registrar(emailPendente, EnumAcaoAuditoria.FALHA_2FA, "Tentativa de código 2FA inválida", ip);
            UtilBean.criarMensagemDeErro("Código inválido. Tente novamente.");
            return null;
        }

        pessoa.setdoisFatoresCodigo(null);
        pessoa.setdoisFatoresExpiracao(null);
        RN.salvar(pessoa);

        limparSessao2FA();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("pessoa", pessoa);

        String ip = obterIpDoCliente();
        LogAuditoriaRN.registrar(emailPendente, EnumAcaoAuditoria.LOGIN_2FA, "Autenticação de dois fatores concluída com sucesso", ip);

        // Bug 2 corrigido: irParaPaginaPrincipal já inclui ?faces-redirect=true
        return UtilBean.irParaPaginaPrincipal(pessoa);
    }

    public void reenviarCodigo() {
        if (emailPendente == null) return;
        Pessoa pessoa = RN.obterPorEmail(emailPendente);
        if (pessoa == null) return;

        String novoCodigo = UtilTexto.gerarNumeros(6);
        pessoa.setdoisFatoresCodigo(novoCodigo);
        pessoa.setdoisFatoresExpiracao(LocalDateTime.now().plusMinutes(10));
        RN.salvar(pessoa);

        UtilEmail.enviarEmail(emailPendente,
            "Novo código de verificação — SIG Bolsas",
            "Seu novo código de verificação é: " + novoCodigo + "\nEle expira em 10 minutos.");

        UtilBean.criarMensagemDeInformacao("Novo código enviado para " + emailPendente);
    }

    // Bug 3 corrigido: remove diretamente sem put(null)
    private void limparSessao2FA() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null) {
            context.getExternalContext().getSessionMap().remove("pendente2FA");
        }
    }

    private String obterIpDoCliente() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context == null) return null;

        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }

    public String getCodigoDigitado() { return codigoDigitado; }
    public void setCodigoDigitado(String codigoDigitado) { this.codigoDigitado = codigoDigitado; }
    public String getEmailPendente() { return emailPendente; }
}
