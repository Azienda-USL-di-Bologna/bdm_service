//package it.bologna.ausl.bdm_service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.datatype.joda.JodaModule;
//import java.util.List;
//import java.util.TimeZone;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//@Configuration
//@EnableWebMvc
//public class WebConfiguration extends WebMvcConfigurerAdapter {
//
//    
//    @Override
//    @Primary
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        super.configureMessageConverters(converters);
//        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JodaModule());
//        mapper.setTimeZone(TimeZone.getDefault());
//        mapper.enable(SerializationFeature.INDENT_OUTPUT);
//        mapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
//        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT);
//        builder.configure(mapper);
//        converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
//    }
//}