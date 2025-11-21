/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufra.bean.converter;

import br.edu.ufra.entidade.Papel;
import br.edu.ufra.rn.PapelRN;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

/**
 *
 * @author Fábio
 */
@FacesConverter(value = "papelConverter")
public class PapelConverter implements Converter {

    private PapelRN RN = new PapelRN();

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string == null || string.trim().isEmpty() || "null".equalsIgnoreCase(string)) {
            return null;
        }
        try {
            Papel resposta = RN.obter(Integer.valueOf(string));
            return resposta;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object == null || !(object instanceof Papel)) {
            return null;
        }
        Papel papel = (Papel) object;
        if (papel.getId() == null) {
            return null;
        }
        return String.valueOf(papel.getId());
    }
}
