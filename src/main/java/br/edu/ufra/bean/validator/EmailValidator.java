package br.edu.ufra.bean.validator;

import br.edu.ufra.util.UtilTexto;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

@FacesValidator(value = "emailValidator")
public class EmailValidator implements Validator {
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object object) throws ValidatorException {
        if (!(object instanceof String)
                || !UtilTexto.isEmail(object.toString())) {
            FacesMessage msg = new FacesMessage("E-mail", "Um e-mail válido contém nome e domínio separados por @");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
