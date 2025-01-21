/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufra.seguranca;

import br.edu.ufra.entidade.Pessoa;
import br.edu.ufra.rn.PessoaRN;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Fabio
 */
public class LoginHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final PessoaRN RN = new PessoaRN();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication a) throws IOException, ServletException {
        String username = a.getName();
        Pessoa usuario = RN.obterPorEmail(username);
        String pagina = "/index.xhtml";
        if (usuario != null) {
            pagina = "/pesquisador/principal.xhtml";
        }
        setDefaultTargetUrl(pagina);
        super.onAuthenticationSuccess(request, response, a);
    }

}
