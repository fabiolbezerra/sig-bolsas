/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufra.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

import java.math.BigDecimal;
import java.text.*;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Fabio
 */
public class UtilTexto {

    private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] pesoCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    public static String criptografarSHA256(String texto) {
        ShaPasswordEncoder sha = new ShaPasswordEncoder(256);
        String textoCriptografado = sha.encodePassword(texto, null);
        return textoCriptografado;
    }

    public static String gerarNumerosDataHora() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        String resposta = sdf.format(new Date());
        return resposta;
    }

    public static String gerarLetras(int numeroDeLetras) {
        return gerarPalavra(true, false, numeroDeLetras);
    }

    public static String gerarNumeros(int numeroDeNumeros) {
        return gerarPalavra(false, true, numeroDeNumeros);
    }

    public static String gerarLetrasENumeros(int numeroDeLetras) {
        return gerarPalavra(true, true, numeroDeLetras);
    }

    private static String gerarPalavra(boolean comLetras, boolean comNumeros, int numeroDeLetras) {
        String palavra = RandomStringUtils.random(numeroDeLetras, comLetras, comNumeros).toUpperCase();
        return palavra;
    }

    public static boolean isCPFouCNPJ(String CPFouCNPJ) {
        CPFouCNPJ = removeSimbolosPontuacoes(CPFouCNPJ);

        if ((CPFouCNPJ == null) || (CPFouCNPJ.length() < 11) || (CPFouCNPJ.length() > 14)) {
            return false;
        } else if (CPFouCNPJ.length() == 11) {

            Integer digito1 = calcularDigito(CPFouCNPJ.substring(0, 9), pesoCPF);
            Integer digito2 = calcularDigito(CPFouCNPJ.substring(0, 9) + digito1, pesoCPF);
            return CPFouCNPJ.equals(CPFouCNPJ.substring(0, 9) + digito1.toString() + digito2.toString());

        } else if (CPFouCNPJ.length() == 14) {

            Integer digito1 = calcularDigito(CPFouCNPJ.substring(0, 12), pesoCNPJ);
            Integer digito2 = calcularDigito(CPFouCNPJ.substring(0, 12) + digito1, pesoCNPJ);
            return CPFouCNPJ.equals(CPFouCNPJ.substring(0, 12) + digito1.toString() + digito2.toString());

        }

        return false;
    }

    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
            digito = Integer.parseInt(str.substring(indice, indice + 1));
            soma += digito * peso[peso.length - str.length() + indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

    public static String formatarCpfouCnpj(String valor) {
        if (valor == null) {
            return null;
        } else {
            valor = removeSimbolosPontuacoes(valor);
            if (valor.length() == 14) {
                return valor.substring(0, 2) + "." + valor.substring(2, 5) + "." + valor.substring(5, 8) + "/" + valor.substring(8, 12) + "-" + valor.substring(12, 14);
            } else if (valor.length() == 11) {
                return valor.substring(0, 3) + "." + valor.substring(3, 6) + "." + valor.substring(6, 9) + "-" + valor.substring(9, 11);
            } else {
                return null;
            }
        }
    }

    public static String formatarTelefone(String valor) {
        if (valor == null) {
            return null;
        } else {
            valor = removeSimbolosPontuacoes(valor);
            if (valor.length() == 11) {
                return "(" + valor.substring(0, 2) + ") " + valor.substring(2, 7) + "-" + valor.substring(7, 11);
            } else if (valor.length() == 10) {
                return "(" + valor.substring(0, 2) + ") " + valor.substring(2, 6) + "-" + valor.substring(6, 10);
            } else {
                return null;
            }
        }
    }

    public static boolean isNomeCompleto(final String nomeCompleto) {
        if (nomeCompleto == null) {
            return false;
        }
        String nomeCompletoSemPontos = removeSimbolosPontuacoes(nomeCompleto);
        StringTokenizer stringTokenizer = new StringTokenizer(nomeCompletoSemPontos);
        return stringTokenizer.countTokens() > 1;
    }

    /**
     * Teste se um nome de login é válido. No caso, o padrão é semelhante ao nome de e-mail, mas não considera sinais
     * como o _, - e +. Apenas o sinal de ponto pode ser usado. Login deve ser iniciado com letra.
     *
     * @param login
     * @return
     */
    public static boolean isLogin(final String login) {
        final String LOGIN_PATTERN = "^[A-Za-z]+([\\._A-Za-z0-9]+)*$";
        Pattern pattern = Pattern.compile(LOGIN_PATTERN);
        Matcher matcher = pattern.matcher(login);
        return matcher.matches();
    }

    public static boolean isEmail(final String enderecoEmail) {
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        ;
        Matcher matcher = pattern.matcher(enderecoEmail);
        return matcher.matches();
    }

    public static boolean isCEP(final String cep) {
        if (cep == null || cep.trim().isEmpty()) {
            return false;
        } else {
            final String CEP_PATTERN = "[0-9]{2}\\.?[0-9]{3}\\-?[0-9]{3}";
            Pattern pattern = Pattern.compile(CEP_PATTERN);
            Matcher matcher = pattern.matcher(cep);
            return matcher.matches();
        }
    }

    public static String formatarCEP(String valor) {
        if (valor == null) {
            return null;
        } else {
            valor = removeSimbolosPontuacoes(valor);
            if (valor.length() == 8) {
                return valor.substring(0, 2) + "." + valor.substring(2, 5) + "-" + valor.substring(5);
            } else {
                return null;
            }
        }
    }

    public static String formatarData(Date data) {
        return formatarData(data, "ddMMyyyy");
    }

    public static String formatarData(Date data, String padrao) {
        if (data == null) {
            return null;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(padrao);
            String resposta = sdf.format(data);
            return resposta;
        }
    }

    public static Date criarData(String data, String padrao) {
        if (data == null || padrao == null) {
            return null;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(padrao);
            Date resposta = null;
            try {
                resposta = sdf.parse(data);
            } catch (ParseException e) {
                System.err.println(e);
            }
            return resposta;
        }
    }

    public static Date criarData(String data) {
        String[] padroes = {
                "dd/MM/yy",
                "dd/MM/yyyy",
                "yyyy-MM-dd",
                "yyyy-MM-dd HH:mm:ss"
        };

        Date resposta = null;
        for (int i = 0; i < padroes.length; i++) {
            resposta = criarData(data, padroes[i]);
            if (resposta != null) {
                return resposta;
            }
        }
        return resposta;
    }

    public static String formatarMoeda(BigDecimal valor) {
        if (valor == null) {
            return "";
        } else {
            return formatarMoeda(valor.doubleValue());
        }
    }

    public static String formatarMoeda(double valor) {
        String resposta = null;
        try {
            NumberFormat nf = DecimalFormat.getCurrencyInstance();
            resposta = nf.format(valor);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return resposta;
    }

    public static String formatarNumero(long numero, String padrao) {
        String resposta = null;
        try {
            NumberFormat nf = new DecimalFormat(padrao);
            resposta = nf.format(numero);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return resposta;
    }

    public static String formatarNumero(double numero, String padrao) {
        String resposta = null;
        try {
            NumberFormat nf = new DecimalFormat(padrao);
            resposta = nf.format(numero);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return resposta;
    }

    public static String removeAcento(String texto) {
        if (texto == null) {
            return null;
        } else {
            String txt = Normalizer.normalize(texto, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            String resposta = pattern.matcher(txt).replaceAll("");
            return resposta;
        }
    }

    public static String removeSimbolosPontuacoes(String texto) {
        if (texto == null) {
            return null;
        } else {
            String txt = Normalizer.normalize(texto, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{Punct}+");
            String resposta = pattern.matcher(txt).replaceAll("");
            return resposta;
        }
    }

    public static String removeCharacterPhone(String telefone) {
        Pattern pattern;
        String resposta, txt;

        txt = Normalizer.normalize(telefone, Normalizer.Form.NFD);

        pattern = Pattern.compile("\\p{Punct}+");
        resposta = pattern.matcher(txt).replaceAll("");

        txt = Normalizer.normalize(resposta, Normalizer.Form.NFD);

        pattern = Pattern.compile("\\p{Space}+");
        resposta = pattern.matcher(txt).replaceAll("");

        return resposta;
    }
}
