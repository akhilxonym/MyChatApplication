package org.mychat.backend.config.security;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.bson.types.ObjectId;

public class ObjectIdJSONSerializer extends JsonSerializer<ObjectId>{

    public static final ObjectIdJSONSerializer INSTANCE=new ObjectIdJSONSerializer();

    @Override
    public void serialize(ObjectId o, JsonGenerator j , SerializerProvider s) throws IOException, JsonProcessingException{
        if(o==null){
            j.writeNull();;
        }else{
            j.writeString(o.toString());
        }
    }
    
}
