package br.edu.ufra.seguranca;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/administrador/*", "/gerente/*", "/coordenador/*", "/pesquisador/*", "/bolsista/*"})
public class AutenticaDoisFatoresFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("pendente2FA") != null) {
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/formulario-autentica-dois-fatores.xhtml");
            return;
        }
        chain.doFilter(req, res);
    }
}