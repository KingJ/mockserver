package org.mockserver.client.serialization;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.mockserver.client.serialization.deserializers.BodyDTODeserializer;
import org.mockserver.client.serialization.model.*;
import org.mockserver.client.serialization.serializers.*;
import org.mockserver.model.*;

/**
 * @author jamesdbloom
 */
public class ObjectMapperFactory {

    public static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // ignore failures
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        // relax parsing
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

        // use arrays
        objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);

        // remove empty values from JSON
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        // register our own module with our serializers and deserializers
        Module gameServerModule = new Module();
        objectMapper.registerModule(gameServerModule);

        return objectMapper;
    }

    private static class Module extends SimpleModule {

        public Module() {
            addDeserializer(BodyDTO.class, new BodyDTODeserializer());
            addSerializer(StringBodyDTO.class, new StringBodyDTOSerializer());
            addSerializer(StringBody.class, new StringBodySerializer());
            addSerializer(RegexBodyDTO.class, new RegexBodyDTOSerializer());
            addSerializer(RegexBody.class, new RegexBodySerializer());
            addSerializer(JsonBodyDTO.class, new JsonBodyDTOSerializer());
            addSerializer(JsonBody.class, new JsonBodySerializer());
            addSerializer(JsonSchemaBodyDTO.class, new JsonSchemaBodyDTOSerializer());
            addSerializer(JsonSchemaBody.class, new JsonSchemaBodySerializer());
            addSerializer(XPathBodyDTO.class, new XPathBodyDTOSerializer());
            addSerializer(XPathBody.class, new XPathBodySerializer());
        }

    }

}