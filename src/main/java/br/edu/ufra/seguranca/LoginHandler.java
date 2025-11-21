/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufra.seguranca;

import br.edu.ufra.entidade.Pessoa;
import br.edu.ufra.entidade.PessoaPapel;
import br.edu.ufra.rn.PessoaRN;
import br.edu.ufra.util.UtilBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Fabio
 */
public class LoginHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final PessoaRN RN = new PessoaRN();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication a)
            throws IOException, ServletException {
        String username = a.getName();
        Pessoa usuario = RN.obterPorEmail(username);

        // LOG: mostrar papeis e página de destino
        if (usuario != null && usuario.getPessoaPapels() != null) {
            System.out.println("Usuário autenticado: " + username);
            System.out.println("Papéis do usuário:");
            for (PessoaPapel pp : usuario.getPessoaPapels()) {
                System.out.println(" - codigo=" + pp.getPapel().getCodigo());
            }
        }

        String pagina = UtilBean.irParaPaginaPrincipal(usuario);
        System.out.println("Redirecionando para: " + pagina);
        setDefaultTargetUrl(pagina);
        super.onAuthenticationSuccess(request, response, a);
    }
}