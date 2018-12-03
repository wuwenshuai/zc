import org.activiti.engine.ProcessEngine;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestActivit {


    @Test
    public void test1(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-*.xml");
        ProcessEngine processEngine = (ProcessEngine)applicationContext.getBean("processEngine");

    }
}
