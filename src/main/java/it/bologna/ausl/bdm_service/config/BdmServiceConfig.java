package it.bologna.ausl.bdm_service.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author gdm
 */
@Configuration
public class BdmServiceConfig {

    @Bean
    public TomcatEmbeddedServletContainerFactory containerFactory(@Value("${server.context-parameters.maxSwallowSize}") int value) {
        return new TomcatEmbeddedServletContainerFactory() {
            @Override
            protected void customizeConnector(Connector connector) {
                super.customizeConnector(connector);
                if ((connector.getProtocolHandler() instanceof AbstractHttp11Protocol)) {
                    ((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(value);
                }
            }
        };

    }
}
