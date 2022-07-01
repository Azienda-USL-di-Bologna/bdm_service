package it.bologna.ausl.bdm_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.bologna.ausl.bdm.utilities.Dumpable;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@Component
public class JacksonFix {
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
//    private CustomObjectMapper objectMapper;
//    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        List<HttpMessageConverter<?>> messageConverters = requestMappingHandlerAdapter.getMessageConverters();
        ObjectMapper customObjectMapper = Dumpable.buildCustomObjectMapper();
        for (HttpMessageConverter<?> messageConverter : messageConverters) {
            if (messageConverter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter m = (MappingJackson2HttpMessageConverter) messageConverter;
                m.setObjectMapper(customObjectMapper);
            }
        }
    }

    // this will exist due to the <mvc:annotation-driven/> bean
    @Autowired
    public void setAnnotationMethodHandlerAdapter(RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
        this.requestMappingHandlerAdapter  = requestMappingHandlerAdapter;
    }
    
//    @Autowired
//    public void setObjectMapper(CustomObjectMapper objectMapper) {
//        this.objectMapper = objectMapper;
//    }
    
//    @Autowired
//    public void setObjectMapper() {
//        this.objectMapper = Dumpable.buildCustomObjectMapper();
//    }
}