import com.pal.dao.IUserDao;
import com.pal.io.Resources;
import com.pal.pojo.User;
import com.pal.sqlSession.SqlSession;
import com.pal.sqlSession.SqlSessionFactory;
import com.pal.sqlSession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;
import org.junit.Test;

import javax.annotation.Resource;
import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;

/**
 * @author pal
 * @date 2020/8/28 10:23 上午
 */
public class IPersistenceTest {

    @Test
    public  void test() throws Exception {

        //1.Resources工具类，配置文件的加载，把配置文件加载为字节输入流
        InputStream resourceAsSteam = Resources.getResourceAsSteam("sqlMapConfig.xml");
        //2.解析配置文件，并创建SqlSessionFactory工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);
        //3.生产SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //调用
        User user = new User();
        user.setId(1);
        user.setUsername("Tim");


        //获取代理对象                                                                                   
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        //代理对象调用接口任一方法都会调用invoke
//        User oneRes = userDao.findByCondition(user);
//        System.out.println(oneRes);

        List<User> all = userDao.findAll();
        for (User user1 : all) {
            System.out.println(user1);
        }


    }
}
