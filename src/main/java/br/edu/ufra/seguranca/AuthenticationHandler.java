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
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationHandler implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String senha = authentication.getCredentials().toString();
        String senhaCripto = UtilTexto.criptografarSHA256(senha);

        PessoaRN RN = new PessoaRN();
        Pessoa user = RN.login(login, senhaCripto);

        if (user != null) {
            List<GrantedAuthority> auts = new ArrayList<>();
            List<PessoaPapel> papeis = user.getPessoaPapels();
            for (PessoaPapel pessoaPapel : papeis) {
                GrantedAuthority ga = new SimpleGrantedAuthority("ROLE_" + pessoaPapel.getPapel().getCodigo());
                auts.add(ga);
            }
            return new UsernamePasswordAuthenticationToken(login, senha, auts);
        } else {
            throw new BadCredentialsException("Email, celular ou senha inválidos");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}