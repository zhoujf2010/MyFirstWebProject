package com.zjf.mybaties;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zjf.common.SpringContextUtil;

// mybaties代码测试
public class Test
{

    // @Autowired
    // private userdao userdao;

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("spring.xml");
        UserDao userdao = (UserDao) SpringContextUtil.getContext().getBean(UserDao.class);

        User u = new User();
        u.setUsername("admin");
//        List<user> lst = userdao.select(u);
//        System.out.println(lst.size());
        u = userdao.getUser(u);


        SqlSessionFactory factory = (SqlSessionFactory) SpringContextUtil.getContext().getBean("sqlSessionFactory");
        SqlSession sqlSession = factory.openSession();
        String statement = "com.zjf.mybaties.UserDao.getUser";
        User user = sqlSession.selectOne(statement, u);

        
//        String statement = "com.zjf.mybaties.userDao.getAllUsers";
//        List<user> lstUsers = sqlSession.selectList(statement);
//        System.out.println(lstUsers.size());
//        
//        int p = sqlSession.selectOne("com.zjf.mybaties.userDao.getcount");
//        System.out.println("count=" + p);
//        
        sqlSession.close();
    }

}
