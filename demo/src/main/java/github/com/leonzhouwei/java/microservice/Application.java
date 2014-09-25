package github.com.leonzhouwei.java.microservice;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;

@ComponentScan
@EnableAutoConfiguration
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer(){
	    return new MyCustomizer();
	}

	private static class MyCustomizer implements EmbeddedServletContainerCustomizer {

	    public void customizeTomcat(TomcatEmbeddedServletContainerFactory factory) {
	        factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
				@Override
				public void customize(Connector connector) {
					AbstractHttp11Protocol httpProtocol = (AbstractHttp11Protocol) connector.getProtocolHandler();
                    httpProtocol.setCompression("on");
                    httpProtocol.setCompressionMinSize(2048);
                    String mimeTypes = httpProtocol.getCompressableMimeTypes();
                    String mimeTypesWithJson = mimeTypes + "," + MediaType.APPLICATION_JSON_VALUE;
                    httpProtocol.setCompressableMimeTypes(mimeTypesWithJson);
                    httpProtocol.setNoCompressionUserAgents("gozilla, traviata");
				}});
	    }

		@Override
		public void customize(ConfigurableEmbeddedServletContainerFactory factory) {
	        if(factory instanceof TomcatEmbeddedServletContainerFactory) {
	            customizeTomcat((TomcatEmbeddedServletContainerFactory) factory);
	        }
		}

	}	
	
}
