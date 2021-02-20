package org.mychat.backend.service.utility;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.bson.types.ObjectId;
import org.mychat.backend.config.security.ObjectIdJSONSerializer;

public class ObjectUtility {
    private static ObjectMapper mapper=new ObjectMapper();
    
    static {
        SimpleModule module=new SimpleModule();
        module.addSerializer(ObjectId.class, ObjectIdJSONSerializer.INSTANCE);
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.findAndRegisterModules();
        mapper.registerModule(module);
    }

    private ObjectUtility(){}
    
    public static <T> T getMappedObject(Object object, Class<T> clas){
        return mapper.convertValue(object, clas);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getMappedObject(Object object,TypeReference<?> toValueTypeReference){
        return (T) mapper.convertValue(object,toValueTypeReference);
    }

    public static String writeValueAsString(Object object) throws JsonProcessingException{
        return mapper.writeValueAsString(object);
    }

}
