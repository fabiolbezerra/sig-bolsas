/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufra.seguranca;

import br.edu.ufra.entidade.Pessoa;
import br.edu.ufra.entidade.PessoaPapel;
import br.edu.ufra.rn.PessoaRN;
import br.edu.ufra.util.UtilTexto;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Fabio
 */
public class AuthenticationHandler implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication a) throws AuthenticationException {
        String login = a.getName();
        String senha = a.getCredentials().toString();
        String senhaCripto = UtilTexto.criptografarSHA256(senha);
        PessoaRN RN = new PessoaRN();
        Pessoa user = RN.login(login, senhaCripto);
        if (user != null) {
            List<GrantedAuthority> auts = new ArrayList<GrantedAuthority>();
            Set<PessoaPapel> papeis = user.getPessoaPapels();
            for (PessoaPapel pessoaPapel : papeis) {
                GrantedAuthorityImpl ga = new GrantedAuthorityImpl("ROLE_" + pessoaPapel.getPapel().getCodigo());
                auts.add(ga);
            }
            return new UsernamePasswordAuthenticationToken(login, senha, auts);
        } else {
            throw new BadCredentialsException("Email, celular ou senha inválidos");
        }
    }

    @Override
    public boolean supports(Class<? extends Object> type) {
        return true;
    }

}
