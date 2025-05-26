package br.edu.ufra.bean.converter;

import br.edu.ufra.entidade.tipo.EnumTipoPergunta;
import br.edu.ufra.util.UtilTexto;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "tipoPerguntaConverter")
public class TipoPerguntaConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String string) {
        if (string == null || string.trim().equals("")) {
            return null;
        } else {
            return EnumTipoPergunta.valueOf(string);
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
        if (object == null || !(object instanceof String)) {
            return null;
        } else {
            return object.toString();
        }
    }
}
