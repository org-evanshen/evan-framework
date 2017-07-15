package org.evanframework.web.serialization;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ObjectMapper extends com.fasterxml.jackson.databind.ObjectMapper {
    private static final long serialVersionUID = 568314967843082031L;

    public ObjectMapper() {
        super();
        super.setSerializationInclusion(Include.NON_EMPTY);

        //super.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        super.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
        super.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }
}
