package jedis;

import com.appleyk.auth.common.excep.SeException;
import com.appleyk.auth.core.container.SeRedisInstanceContainer;
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

}
