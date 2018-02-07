package com.kingdee.hrp.sms.system.menu.controller;

import com.kingdee.hrp.sms.AbstractControllerTests;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author yadda<silenceisok@163.com>
 * @since 2018/2/7
 */
public class MenuControllerTest extends AbstractControllerTests {

    private MockMvc mockMvc;

    //该方法在每个方法执行之前都会执行一遍
    @Before
    public void setUp() throws Exception {
        // mockMvc = MockMvcBuilders.standaloneSetup(new MenuController()).build();
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        ;
    }


    /**
     * perform：执行一个RequestBuilder请求，会自动执行SpringMVC的流程并映射到相应的控制器执行处理；
     * get:声明发送一个get请求的方法。MockHttpServletRequestBuilder get(String urlTemplate, Object... urlVariables)：根据uri模板和uri变量值得到一个GET请求方式的。另外提供了其他的请求的方法，如：post、put、delete等。
     * param：添加request的参数，如上面发送请求的时候带上了了pcode = root的参数。假如使用需要发送json数据格式的时将不能使用这种方式，可见后面被@ResponseBody注解参数的解决方法
     * andExpect：添加ResultMatcher验证规则，验证控制器执行完成后结果是否正确（对返回的数据进行的判断）；
     * andDo：添加ResultHandler结果处理器，比如调试时打印结果到控制台（对返回的数据进行的判断）；
     * andReturn：最后返回相应的MvcResult；然后进行自定义验证/进行下一步的异步处理（对返回的数据进行的判断）
     *
     * @throws Exception
     */
    @Test
    public void getMenu() throws Exception {
        //请求的url,请求的方法是get
        String responseString = mockMvc.perform(get("/menu/getMenu")
                //数据的格式
                .contentType(MediaType.APPLICATION_JSON)
                //添加参数
                .param("parentId", "-1")

        ).andExpect(status().isOk())     //返回的状态是200
                //打印出请求和相应的内容
                .andDo(print())
                //将相应的数据转换为字符串
                .andReturn().getResponse().getContentAsString();

        System.out.println("===========返回的json数据=========> " + responseString);
    }

}