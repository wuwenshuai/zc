import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.manager.service.UserService;
import com.atguigu.atcrowdfunding.util.MD5Util;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Auther: wuwenshuai
 * @Date: 2018/9/19 15:27
 * @Description:
 */
public class Test1 {

    public static void main(String[] args) throws Exception {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("spring/spring*.xml");

        UserService userService = ioc.getBean(UserService.class);


        for (int i = 1; i <= 100; i++) {
            User user = new User();
            user.setLoginacct("test"+i);
            user.setUserpswd(MD5Util.digest("123"));
            user.setUsername("test"+i);
            user.setEmail("test"+i+"@atguigu.com");
            user.setCreatetime("2017-09-23 14:17:00");
            userService.saveUser(user);
        }

    }
}
