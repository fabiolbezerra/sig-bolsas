package br.edu.ufra.seguranca;

import br.edu.ufra.entidade.Pessoa;
import br.edu.ufra.entidade.tipo.EnumAcaoAuditoria;
import br.edu.ufra.rn.LogAuditoriaRN;
import br.edu.ufra.rn.PessoaRN;
import br.edu.ufra.util.UtilBean;
import br.edu.ufra.util.UtilEmail;
import br.edu.ufra.util.UtilTexto;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

public class LoginHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final PessoaRN RN = new PessoaRN();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication a)
            throws IOException, ServletException {
        String username = a.getName();
        Pessoa usuario = RN.obterPorEmail(username);

        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/index.xhtml?e=-1");
            return;
        }

        String ip = obterIpDoCliente(request);
        LogAuditoriaRN.registrar(username, EnumAcaoAuditoria.LOGIN, "Autenticação realizada com sucesso", ip);

        if (Boolean.TRUE.equals(usuario.getdoisFatoresHabilitado())) {
            String codigo = UtilTexto.gerarNumeros(6);
            usuario.setdoisFatoresCodigo(codigo);
            usuario.setdoisFatoresExpiracao(LocalDateTime.now().plusMinutes(10));
            RN.salvar(usuario);

            HttpSession session = request.getSession(true);
            session.setAttribute("pendente2FA", username);

            UtilEmail.enviarEmail(username,
                "Código de verificação — SIG Bolsas",
                "Seu código de verificação é: " + codigo + "\nEle expira em 10 minutos.");

            response.sendRedirect(request.getContextPath() + "/formulario-autentica-dois-fatores.xhtml");
            return;
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("pessoa", usuario);

        String pagina = UtilBean.irParaPaginaPrincipal(usuario);
        setDefaultTargetUrl(pagina);
        super.onAuthenticationSuccess(request, response, a);
    }

    private String obterIpDoCliente(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // Pega o primeiro IP se houver múltiplos (proxy chain)
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}