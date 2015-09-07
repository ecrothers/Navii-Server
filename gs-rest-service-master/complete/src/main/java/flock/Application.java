package navii;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
public class Application {
    public static void main(String[] args) {
        /*ClassPathXmlApplicationContext context = new
                ClassPathXmlApplicationContext("/appConfig.xml");*/
        //Integer someIntBean = (Integer) context.getBean("testBean");
        //System.out.println(someIntBean.intValue()); // Demonstrate appConfig loaded

        SpringApplication.run(Application.class, args);
    }
}
