package br.edu.ufra.bean.validator;

import br.edu.ufra.util.UtilTexto;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

@FacesValidator(value = "cpfCnpjValidator")
public class CpfCnpjValidator implements Validator {
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object object) throws ValidatorException {
        if (object == null || !(object instanceof String) || ! UtilTexto.isCPFouCNPJ(object.toString())) {
            FacesMessage msg = new FacesMessage("CPF/CNPJ", "Não é um número de CPF/CNPJ válido");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
