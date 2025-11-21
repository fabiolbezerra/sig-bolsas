package br.edu.ufra.bean.converter;

import br.edu.ufra.entidade.tipo.EnumTipoPergunta;
import br.edu.ufra.util.UtilTexto;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(value = "tipoPerguntaConverter")
public class TipoPerguntaConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String string) {
        if (string == null || string.trim().equals("") || string.equals("null")) {
            return null;
        } else {
            return EnumTipoPergunta.valueOf(string);
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
        if (object == null || !(object instanceof EnumTipoPergunta)) {
            return null;
        } else {
            return object.toString();
        }
    }
}
