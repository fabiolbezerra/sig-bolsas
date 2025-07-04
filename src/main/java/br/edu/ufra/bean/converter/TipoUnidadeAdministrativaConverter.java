package br.edu.ufra.bean.converter;

import br.edu.ufra.entidade.tipo.EnumTipoUnidadeAdministrativa;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "tipoUnidadeAdministrativaConverter")
public class TipoUnidadeAdministrativaConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String string) {
        if (string == null || string.trim().equals("") || string.equals("null")) {
            return null;
        } else {
            return EnumTipoUnidadeAdministrativa.valueOf(string);
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
        if (object == null || !(object instanceof EnumTipoUnidadeAdministrativa)) {
            return null;
        } else {
            return object.toString();
        }
    }
}
