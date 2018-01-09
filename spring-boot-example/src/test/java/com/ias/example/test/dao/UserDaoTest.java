package com.ias.example.test.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.hujz.framework.orm.util.QueryCondition;
import com.ias.example.TestApplication;
import com.ias.example.boot.dao.UserDao;
import com.ias.example.boot.entity.User;

/**
 * @author liuzh
 * @since 2017/9/2.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(TestApplication.class)
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void save() {
        for(int i=11000;i<11100;i++) {
            userDao.save(new User("name"+i,0));
        }
    }
    
    @Test
    public void query() {
        QueryCondition cond = new QueryCondition(13,1);
        cond.put("name","name");
        List<User> list = userDao.query(cond);
        System.out.println(list.size());
    }
    
    @Test
    public void get() {
        System.out.println(userDao.get(950686088177909760l).getName());
    }
}
