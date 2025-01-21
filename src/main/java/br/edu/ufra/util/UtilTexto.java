/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufra.util;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

/**
 *
 * @author Fabio
 */
public class UtilTexto {

    public static String criptografarSHA256(String texto) {
        ShaPasswordEncoder sha = new ShaPasswordEncoder(256);
        String textoCriptografado = sha.encodePassword(texto, null);
        return textoCriptografado;
    }
}
