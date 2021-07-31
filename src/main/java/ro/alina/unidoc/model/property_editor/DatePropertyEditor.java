package ro.alina.unidoc.model.property_editor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatePropertyEditor extends PropertyEditorSupport {

    private static final Logger LOG = LoggerFactory.getLogger(DatePropertyEditor.class);
    private static final String DATE_FORMAT = "dd/MM/yyyy";

    @Override
    public void setAsText(String value) {
        try {
            setValue(new SimpleDateFormat(DATE_FORMAT).parse(value));
        } catch (ParseException e) {
            setValue(null);
            LOG.error("Error on mapping [{}] - {}", value, e);
        }
    }

    @Override
    public String getAsText() {
        return new SimpleDateFormat(DATE_FORMAT).format((Date) getValue());
    }
}