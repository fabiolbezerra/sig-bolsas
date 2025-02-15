package br.edu.ufra.bean.validator;

import br.edu.ufra.util.UtilTexto;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

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
