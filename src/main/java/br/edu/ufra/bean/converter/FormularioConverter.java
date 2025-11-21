/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufra.bean.converter;

import br.edu.ufra.entidade.Formulario;
import br.edu.ufra.rn.FormularioRN;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

/**
 *
 * @author Fábio
 */
@FacesConverter(value = "formularioConverter")
public class FormularioConverter implements Converter {

    private FormularioRN RN = new FormularioRN();

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string == null || string.trim().isEmpty() || "null".equalsIgnoreCase(string)) {
            return null;
        }
        try {
            Formulario resposta = RN.obter(Integer.valueOf(string));
            return resposta;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (!(object instanceof Formulario)) {
            return null;
        }
        Formulario formulario = (Formulario) object;
        if (formulario.getId() == null) {
            return null;
        }
        return String.valueOf(formulario.getId());
    }
}
