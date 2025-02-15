package br.edu.ufra.bean.converter;

import br.edu.ufra.util.UtilTexto;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "cpfCnpfConverter")
public class CpfCnpfConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String string) {
        if (string == null || string.trim().equals("") || !UtilTexto.isCPFouCNPJ(string)) {
            return null;
        } else {
            return UtilTexto.removeSimbolosPontuacoes(string);
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
        if (object == null || !(object instanceof String)) {
            return null;
        } else {
            return UtilTexto.formatarCpfouCnpj(object.toString());
        }
    }
}
