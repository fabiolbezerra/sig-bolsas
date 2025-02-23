package br.edu.ufra.bean.converter;

import javax.faces.convert.Converter;
import br.edu.ufra.entidade.UnidadeAdministrativa;
import br.edu.ufra.rn.UnidadeAdministrativaRN;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "unidadeAdministrativaConverter")
public class UnidadeAdministrativaConverter implements Converter {
    private UnidadeAdministrativaRN RN = new UnidadeAdministrativaRN();

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string == null || string.trim().isEmpty() || "null".equalsIgnoreCase(string)){
            return null;
        }
        try {
            UnidadeAdministrativa resposta = RN.obter(Integer.valueOf(string));
            return resposta;
        }catch (Exception e){
            return null;
        }
    }
    @Override
    public String getAsString(FacesContext fc,UIComponent uic,Object object){
        if (!(object instanceof UnidadeAdministrativa)){
            return null;
        }
        UnidadeAdministrativa unidadeAdministrativa = (UnidadeAdministrativa) object;
        if (unidadeAdministrativa.getId() ==null){
            return null;
        }
        return String.valueOf(unidadeAdministrativa.getId());
    }

}