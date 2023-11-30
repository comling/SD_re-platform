package kr.techdna.replatform2023.test;

import kr.techdna.replatform2023.mapper.AsDataMapper;
import kr.techdna.replatform2023.service.AsDataService;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class SqlTest {
    @Autowired
    private ApplicationContext context;

    @Autowired
    private SqlSessionFactory sessionFactory;

    @Autowired
    private AsDataService asDataService;

    @Autowired
    private AsDataMapper asDataMapper;

    @Test
    void contextLoads() {
    }

    @Test
    public void testByApplicationContext() {
        try {
            System.out.println("=========================");
            System.out.println(context.getBean("sqlSessionFactory"));
            System.out.println("=========================");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBySqlSessionFactory() {
        try {
            System.out.println("=========================");
            System.out.println(sessionFactory.toString());
            System.out.println("=========================");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAsDataListTest(){
        asDataMapper.selectForUserID(985);
    }


}
