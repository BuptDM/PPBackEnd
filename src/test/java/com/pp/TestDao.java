package com.pp;

import com.pp.dao.IHelpDao;
import com.pp.domain.HelpArticle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TestDao {
    @Autowired
    IHelpDao helpDao;
    @Test
    public void test(){
        List<HelpArticle> list = helpDao.selectList(null);
        System.out.println(list);
    }
}
