package ro.alina.unidoc.config.advice;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import ro.alina.unidoc.model.property_editor.DatePropertyEditor;

import java.util.Date;

@ControllerAdvice
public class GlobalBinderAdvice {
    @InitBinder
    public void binder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new DatePropertyEditor());
    }
}
