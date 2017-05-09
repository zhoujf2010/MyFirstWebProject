import java.io.IOException;
import java.lang.reflect.Proxy;

import com.test.CommFactory;
import com.test.IUserService;
import com.test.MyHandler;

public class test
{

    public static void main(String[] args) throws IOException {

        IUserService service = new CommFactory().Create("userservice");
        
        //Handle示例，也可以移入到Factory中
        service = (IUserService) Proxy.newProxyInstance(test.class.getClassLoader(), new Class[] {IUserService.class },
                new MyHandler(service));
        
        
        service.getDisplayName("");

    }

}
