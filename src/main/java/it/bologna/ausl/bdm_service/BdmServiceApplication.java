package it.bologna.ausl.bdm_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
//@Configuration
//@PropertySources(value = {
//  @PropertySource("classpath:application.properties")})
public class BdmServiceApplication {//extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BdmServiceApplication.class, args);
    }

    //  @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BdmServiceApplication.class);
    }

//    @Bean
//    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
//        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JodaModule());
//        mapper.setTimeZone(TimeZone.getDefault());
//        mapper.enable(SerializationFeature.INDENT_OUTPUT);
//        mapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
//        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT);
//        builder.configure(mapper);
//        return builder;
//    }
}
