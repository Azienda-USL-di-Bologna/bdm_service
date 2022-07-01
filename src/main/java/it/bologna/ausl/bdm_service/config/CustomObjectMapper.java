//package it.bologna.ausl.bdm_service;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.datatype.joda.JodaModule;
//import it.bologna.ausl.bdm.utilities.Dumpable;
//import java.util.TimeZone;
//import org.springframework.context.annotation.Primary;
//import org.springframework.stereotype.Component;
//
//@Component
//@Primary
//public class CustomObjectMapper extends ObjectMapper {
//    public CustomObjectMapper() {        
//        registerModule(new JodaModule());
//        setTimeZone(TimeZone.getDefault());
//        enable(SerializationFeature.INDENT_OUTPUT);
//        enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
//        enableDefaultTyping(ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT);
//    }
//}