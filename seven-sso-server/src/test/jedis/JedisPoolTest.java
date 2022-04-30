package jedis;

import com.appleyk.auth.common.excep.SeException;
import com.appleyk.auth.core.container.SeRedisInstanceContainer;
import com.appleyk.auth.core.model.SeAuthUser;
import com.appleyk.sso.server.SsoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <p>jedis功能测试</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on  下午11:43 2022/4/2
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SsoApplication.class)
public class JedisPoolTest {

    @Autowired
    private SeRedisInstanceContainer container;

    String key = "name";
    String value = "appleyk";
    int ttl = 120;

    @Test
    public void put() throws SeException {
        String result = container.jedisPool().setex(key, ttl, value);
        System.out.println(result);
    }

    @Test
    public void get() throws SeException {
        String result = container.jedisPool().get(key);
        System.out.println(result);
    }

    @Test
    public void setObject() throws SeException{
        /**一定要保证SeAuthUser类实现了Serializable接口*/
        SeAuthUser authUser = new SeAuthUser();
        authUser.setId(1001L);
        authUser.setName("appleyk");
        authUser.setPassword("11dsada1wdsada");
        String user = container.jedisPool().setObject("user", 60, authUser);
        System.out.println(user);
    }
    @Test
    public void getObject() throws SeException{
        Object object = container.jedisPool().getObject("user");
        SeAuthUser authUser = (SeAuthUser)object;
        System.out.println(authUser.getPassword());
    }


}
