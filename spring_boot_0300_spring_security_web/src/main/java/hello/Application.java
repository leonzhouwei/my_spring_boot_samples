package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@EnableAutoConfiguration
@Configuration
@ComponentScan
public class Application {

    public static void main(String[] args) throws Throwable {
        ApplicationContext ac = SpringApplication.run(Application.class, args);
        Demo demo = ac.getBean(Demo.class);
        demo.hi();
    }

}

@Configuration
@ImportResource("/appCtx.xml")
class XmlImportingConfiguration {
}
