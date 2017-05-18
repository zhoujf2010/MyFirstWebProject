import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.test.IConfigService;
import com.test.IUserService;
import com.zjf.common.SpringContextUtil;

public class test
{

    public static void main(String[] args) throws IOException {

//        IUserService service = new CommFactory().Create("userservice");
//        
//        //Handle示例，也可以移入到Factory中
//        service = (IUserService) Proxy.newProxyInstance(test.class.getClassLoader(), new Class[] {IUserService.class },
//                new MyHandler(service));
//        
//        
//        service.getDisplayName("");
        
        
        @SuppressWarnings("resource")
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        
        
        
        IConfigService svr = (IConfigService)SpringContextUtil.getContext().getBean("configservice");
        System.out.println(svr.getValue("11"));
        
        IUserService service = (IUserService)context.getBean("userservice");
        service.getDisplayName("");        
        
        

    }

}
