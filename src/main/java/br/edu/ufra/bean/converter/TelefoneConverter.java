package br.edu.ufra.bean.converter;

import br.edu.ufra.util.UtilTexto;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(value = "telefoneConverter")
public class TelefoneConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String string) {
        if (string == null || string.trim().equals("")) {
            return null;
        } else {
            return UtilTexto.removeCharacterPhone(string);
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
        if (object == null || !(object instanceof String)) {
            return null;
        } else {
            return UtilTexto.formatarTelefone(object.toString());
        }
    }
}
