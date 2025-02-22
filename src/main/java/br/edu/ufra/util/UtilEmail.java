package br.edu.ufra.util;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

public class UtilEmail {

    private static final String HOST = "smtp.gmail.com";
    private static final int PORTA_SMTP = 587;
    private static final boolean TLS = true;
    private static final String USUARIO = "noreply.tukio@gmail.com";
    private static final String SENHA = "vdqmqmsntsmgrhuu";

    public UtilEmail() {

    }

    /**
     * Método de execução assíncrona, pois seu comportamento é executado no escopo de uma thread
     *
     * @param destinatario destinatário
     * @param assunto      título ou assunto do e-mail
     * @param mensagem     corpo da mensagem
     */
    public static void enviarEmail(String destinatario, String assunto, String mensagem) {
        Thread thread = new Thread(() -> {
            try {
                Email email = new SimpleEmail();
                email.setHostName(HOST);
                email.setSmtpPort(PORTA_SMTP);
                email.setSslSmtpPort(Integer.toString(PORTA_SMTP));
                email.setAuthenticator(new DefaultAuthenticator(USUARIO, SENHA));
                email.setStartTLSEnabled(TLS);
                email.setFrom(USUARIO);
                email.setSubject(assunto);
                email.setMsg(mensagem);
                email.addTo(destinatario);
                email.send();
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        });

        thread.start(); //Inicia a thread ... método run() é chamado
    }

}
