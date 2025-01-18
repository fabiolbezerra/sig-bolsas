/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufra.bean.converter;

import br.edu.ufra.entidade.Pessoa;
import br.edu.ufra.rn.PessoaRN;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Fábio
 */
@FacesConverter(value = "pessoaConverter")
public class PessoaConverter implements Converter {

    private PessoaRN RN = new PessoaRN();

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string == null || string.trim().isEmpty() || "null".equalsIgnoreCase(string)) {
            return null;
        }
        try {
            return RN.obter(Integer.valueOf(string));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object == null || !(object instanceof Pessoa)) {
            return null;
        }
        Pessoa pessoa = (Pessoa) object;
        if (pessoa.getId() == null) {
            return null;
        }
        return String.valueOf(pessoa.getId());
    }
}
