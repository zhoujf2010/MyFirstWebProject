import java.io.IOException;

import com.test.CommFactory;
import com.test.IUserService;
import com.test.UserServiceFactory;

public class test
{

    public static void main(String[] args) throws IOException {

        IUserService service = new CommFactory().Create("userservice");
        service.getDisplayName("");

    }

}
