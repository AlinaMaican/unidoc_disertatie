package ro.alina.unidoc.model.property_editor;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;

public class GenericPropertyEditor<T> extends PropertyEditorSupport {

    private static final Logger LOG = LoggerFactory.getLogger(GenericPropertyEditor.class);
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private final Class<T> cls;

    public GenericPropertyEditor(Class<T> cls) {
        super();
        this.cls = cls;
    }

    @Override
    public String getAsText() {
        return null;
    }

    @Override
    public void setAsText(String text) {
        if (StringUtils.hasLength(text)) {
            ObjectMapper objectMapper = new ObjectMapper(new JsonFactory());
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
            objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, false);
            objectMapper.configure(DeserializationFeature.WRAP_EXCEPTIONS, true);
            objectMapper.configure(MapperFeature.USE_GETTERS_AS_SETTERS, true);

            objectMapper.setDateFormat(new SimpleDateFormat(DATE_FORMAT));

            ObjectReader reader = objectMapper.readerFor(cls);
            try {
                setValue(reader.readValue(text));
            } catch (Exception e) {
                LOG.error("Error on mapping [{}] - {}", text, e);
            }
        } else {
            setValue(null);
        }
    }
}