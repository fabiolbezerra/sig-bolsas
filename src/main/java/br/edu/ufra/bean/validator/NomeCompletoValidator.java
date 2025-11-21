package br.edu.ufra.bean.validator;

import br.edu.ufra.util.UtilTexto;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

@FacesValidator(value = "nomeCompletoValidator")
public class NomeCompletoValidator implements Validator {
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object object) throws ValidatorException {
        if (!(object instanceof String)
                || !UtilTexto.isNomeCompleto(object.toString())) {
            FacesMessage msg = new FacesMessage("Nome Completo", "Um nome completo válido deve ter ao menos dois nomes");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
