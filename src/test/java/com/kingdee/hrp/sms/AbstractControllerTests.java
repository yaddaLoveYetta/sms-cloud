
package com.kingdee.hrp.sms;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

/**
 * @author yadda<silenceisok@163.com>
 * @since 2018/2/7
 */

//这个必须使用junit4.9以上才有
@RunWith(SpringJUnit4ClassRunner.class)
//单元测试的时候真实的开启一个web服务
@WebAppConfiguration
//配置事务的回滚,对数据库的增删改都会回滚,便于测试用例的循环利用
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
@ContextConfiguration(locations = {"classpath:config/applicationContext.xml", "classpath:/config/spring-mvc.xml"})
public class AbstractControllerTests {

    @Resource
    protected WebApplicationContext wac;
}
