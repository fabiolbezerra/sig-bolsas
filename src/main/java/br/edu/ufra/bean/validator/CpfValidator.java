package br.edu.ufra.bean.validator;

import br.edu.ufra.util.UtilTexto;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("cpfValidator")
public class CpfValidator implements Validator {
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object object) throws ValidatorException {
        if (object == null || !(object instanceof String) || ! UtilTexto.isCPFouCNPJ(object.toString())) {
            FacesMessage msg = new FacesMessage("CPF", "Não é um número de CPF válido");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
