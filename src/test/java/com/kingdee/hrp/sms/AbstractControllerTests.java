package com.kingdee.hrp.sms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.junit.*;
import org.junit.contrib.java.lang.system.EnvironmentVariables;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * junit controller base tester
 * <p>
 * 常用注解
 * -@Before：初始化方法，在任何一个测试方法执行之前，必须执行的代码。对比 JUnit 3 ，和 setUp（）方法具有相同的功能。在该注解的方法中，可以进行一些准备工作，比如初始化对象，打开网络连接等。
 * <p>
 * -@After：释放资源，在任何一个测试方法执行之后，需要进行的收尾工作。对比 JUnit 3 ，和 tearDown（）方法具有相同的功能。
 * <p>
 * -@Test：测试方法，表明这是一个测试方法。在 JUnit 中将会自动被执行。对与方法的声明也有如下要求：名字可以随便取，没有任何限制，但是返回值必须为 void ，而且不能有任何参数。如果违反这些规定，会在运行时抛出一个异常。不过，为了培养一个好的编程习惯，我们一般在测试的方法名上加 test ，比如：testAdd（）。
 * 同时，该 Annotation（@Test） 还可以测试期望异常和超时时间，如 @Test（timeout=100），我们给测试函数设定一个执行时间，超过这个时间（100毫秒），他们就会被系统强行终止，并且系统还会向你汇报该函数结束的原因是因为超时，这样你就可以发现这些 bug 了。而且，它还可以测试期望的异常，例如，我们刚刚的那个空指针异常就可以这样：@Test(expected=NullPointerException.class)。
 * <p>
 * -@Ignore：忽略的测试方法，标注的含义就是“某些方法尚未完成，咱不参与此次测试”；这样的话测试结果就会提示你有几个测试被忽略，而不是失败。一旦你完成了相应的函数，只需要把 @Ignore 注解删除即可，就可以进行正常测试了。当然，这个 @Ignore 注解对于像我这样有“强迫症”的人还是大有意义的。每当看到红色条（测试失败）的时候就会全身不舒服，感觉无法忍受（除非要测试的目的就是让它失败）。当然，对代码也是一样，无法忍受那些杂乱不堪的代码。
 * <p>
 * -@BeforeClass：针对所有测试，也就是整个测试类中，在所有测试方法执行前，都会先执行由它注解的方法，而且只执行一次。当然，需要注意的是，修饰符必须是 public static void xxxx ；此 Annotation 是 JUnit 4 新增的功能。
 * <p>
 * -@AfterClass：针对所有测试，也就是整个测试类中，在所有测试方法都执行完之后，才会执行由它注解的方法，而且只执行一次。当然，需要注意的是，修饰符也必须是 public static void xxxx ；此 Annotation 也是 JUnit 4 新增的功能，与 @BeforeClass 是一对。
 *
 * @author le.xiao
 */

//这个必须使用junit4.9以上才有
@RunWith(SpringJUnit4ClassRunner.class)
//单元测试的时候真实的开启一个web服务
@WebAppConfiguration(value = "src/main/webapp")
//配置事务的回滚,对数据库的增删改都会回滚,便于测试用例的循环利用
@Rollback(value = true)
// 事务管理器
@Transactional(transactionManager = "txManager")
@ContextConfiguration(locations = { "classpath:config/applicationContext.xml", "classpath:/config/spring-mvc.xml" })
public abstract class AbstractControllerTests {

    @Resource
    private WebApplicationContext wac;

    protected static MockMvc mockMvc;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * cookies
     */
    private static Cookie[] cookies;
    /**
     * HttpSession
     */
    private static HttpSession session;

    /**
     * 环境变量
     */
    @ClassRule
    public final static EnvironmentVariables environmentVariables = new EnvironmentVariables();

    /**
     * 默认账号
     */
    private String userName = "admin";
    /**
     * 默认密码
     */
    private String password = "202cb962ac59075b964b07152d234b70";

    /**
     * 默认验证码
     */
    private String code = "123456";

    /**
     * 账号 默认 admin
     *
     * @return 登陆账号
     */
   public abstract String getUserName();

    /**
     * 账号登陆密码，默认admin的密码
     *
     * @return 账号登陆密码
     */
    public abstract String getPassword();

    /**
     * 验证码
     *
     * @return 验证码
     */
    public String getCode() {
        return code;
    }

    /**
     * 每个请求会设置默认的header：Accept-Encoding，Accept-Language，User-Agent
     * <p>
     * 覆盖该方法可修改默认设置或增加设置个性化的请求header
     */
    public void setCustomerHeader(MockHttpServletRequestBuilder request) {
    }

    /**
     * Cookies
     *
     * @return Cookies
     */
    public Cookie[] getCookies() {
        return cookies;
    }

    /**
     * MockHttpSession
     *
     * @return MockHttpSession
     */
    public MockHttpSession getSession() {
        return (MockHttpSession) session;
    }

    @BeforeClass
    public static void setEnvironmentVariables() {

        environmentVariables.set("spring.profiles.active", "development");

        environmentVariables.set("debug", "true");
    }

    @Before
    public void setUp() throws Exception {

        if (null == mockMvc) {
            mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        }

        if (null == session) {
            login();
        }

    }

    /**
     * 设置了参数提交方式
     * 设置了用户session
     * 设置了客户端信息
     *
     * @param apiUrl       接口url
     * @param initRequest  设置请求参数
     * @param urlVariables url中的参数，可选
     * @return MockHttpServletRequestBuilder
     */
    protected MockHttpServletRequestBuilder post(String apiUrl, InitRequest initRequest, Object... urlVariables)
            throws Exception {

        //请求的url,请求的方法是post
        MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post(apiUrl, urlVariables);

        post.contentType(initRequest.getRequestMediaType());

        setHeader(post);

        if (null != getSession()) {
            post.session(getSession());
        }

        initRequest.paramsSet(post);

        return post;

    }

    /**
     * 设置了参数提交方式
     * 设置了用户session
     * 设置了客户端信息
     *
     * @param apiUrl       接口url
     * @param urlVariables url中的参数，可选
     * @return MockHttpServletRequestBuilder
     */
    protected MockHttpServletRequestBuilder post(String apiUrl, Object... urlVariables) throws Exception {

        //请求的url,请求的方法是post
        MockHttpServletRequestBuilder post = post(apiUrl, new InitRequest() {
            @Override
            public void paramsSet(MockHttpServletRequestBuilder requestBuilder) {

            }
        }, urlVariables);

        return post;

    }

    /**
     * 设置了参数提交方式
     * 设置了用户session
     * 设置了客户端信息
     *
     * @param apiUrl       接口url
     * @param initRequest  设置请求参数
     * @param urlVariables url中的参数，可选
     * @return MockHttpServletRequestBuilder
     */
    protected MockHttpServletRequestBuilder get(String apiUrl, InitRequest initRequest, Object... urlVariables) {

        //请求的url,请求的方法是get
        MockHttpServletRequestBuilder get = MockMvcRequestBuilders.get(apiUrl, urlVariables);

        get.contentType(initRequest.getRequestMediaType());
        setHeader(get);

        if (null != getSession()) {
            get.session(getSession());
        }

        initRequest.paramsSet(get);

        return get;

    }

    /**
     * 设置了参数提交方式
     * 设置了用户session
     * 设置了客户端信息
     *
     * @param apiUrl       接口url
     * @param urlVariables url中的参数，可选
     * @return MockHttpServletRequestBuilder
     */
    protected MockHttpServletRequestBuilder get(String apiUrl, Object... urlVariables) {

        //请求的url,请求的方法是get
        MockHttpServletRequestBuilder get = get(apiUrl, new InitRequest() {
            @Override
            public void paramsSet(MockHttpServletRequestBuilder requestBuilder) {

            }
        }, urlVariables);

        return get;

    }

    /**
     * 发起请求，推荐的方法
     *
     * @param apiUrl       请求url
     * @param httpMethod   请求方式，支持 HttpMethod.GET，HttpMethod.POST ，默认HttpMethod.POST
     * @param initRequest  请求参数设置
     * @param urlVariables 赋在url上的参数
     * @return ResultActions
     * @throws Exception Exception
     */
    protected ResultActions perform(String apiUrl, HttpMethod httpMethod, InitRequest initRequest,
            Object... urlVariables) throws Exception {

        MockHttpServletRequestBuilder request;

        if (HttpMethod.GET == httpMethod) {
            //请求的url,请求的方法是post
            request = get(apiUrl, initRequest, urlVariables);
        } else {
            request = post(apiUrl, initRequest, urlVariables);
        }

        return mockMvc.perform(request);

    }

    /**
     * 发起请求，且后台响应成功(不代表业务处理成功)
     *
     * @param apiUrl       请求url
     * @param httpMethod   请求方式，支持 HttpMethod.GET，HttpMethod.POST ，默认HttpMethod.POST
     * @param initRequest  请求参数设置
     * @param urlVariables 赋在url上的参数
     * @return MvcResult MvcResult
     * @throws Exception Exception
     */
    protected MvcResult mvcResult(String apiUrl, HttpMethod httpMethod, InitRequest initRequest,
            Object... urlVariables) throws Exception {

        ResultActions perform = perform(apiUrl, httpMethod, initRequest, urlVariables);

        return perform.andExpect(status().isOk()).andDo(print()).andReturn();

    }

    /**
     * 发起请求，后台响应成功,获取后台返回的数据，格式化成JSONObject
     *
     * @param apiUrl       请求url
     * @param httpMethod   请求方式，支持 HttpMethod.GET，HttpMethod.POST ，默认HttpMethod.POST
     * @param initRequest  请求参数设置
     * @param urlVariables 赋在url上的参数
     * @return ResultActions
     * @throws Exception Exception
     */
    protected JSONObject result(String apiUrl, HttpMethod httpMethod, InitRequest initRequest,
            Object... urlVariables) throws Exception {

        MvcResult mvcResult = mvcResult(apiUrl, httpMethod, initRequest, urlVariables);

        return JSON.parseObject(mvcResult.getResponse().getContentAsString());

    }

    /**
     * 模拟登陆，获取session
     *
     * @throws Exception Exception
     */
    private void login() throws Exception {

        if (StringUtils.isNotBlank(getUserName())) {
            userName = getUserName();
        }

        if (StringUtils.isNotBlank(getPassword())) {
            password = getPassword();
        }

        if (StringUtils.isNotBlank(getCode())) {
            code = getCode();
        }

        if (StringUtils.isBlank(userName) && StringUtils.isBlank(password)) {
            Assert.fail("请设置接口请求的用户信息");
        }

        //请求的url,请求的方法是post
        MockHttpServletRequestBuilder post = post("/user/login", new InitRequest() {
            @Override
            public void paramsSet(MockHttpServletRequestBuilder requestBuilder) {
                requestBuilder.contentType(MediaType.APPLICATION_FORM_URLENCODED);
                requestBuilder.param("userName", userName);
                requestBuilder.param("password", password);
                requestBuilder.param("code", code);
            }
        });

        ResultActions perform = mockMvc.perform(post);

        //返回的状态是200
        perform.andExpect(status().isOk());
        //验证响应contentType
        //.andExpect(content().contentType(MediaType.APPLICATION_JSON))
        //打印出请求和相应的内容
        perform.andDo(print());
        //将相应的数据转换为字符串
        MvcResult mvcResult = perform.andReturn();

        cookies = mvcResult.getRequest().getCookies();
        session = mvcResult.getRequest().getSession();

        String responseString = mvcResult.getResponse().getContentAsString();

        JSONObject resp = JSON.parseObject(responseString);

        logger.info("=======接口返回的json数据======>{}", resp);

        Assert.assertEquals(resp.getString("msg"), 200, resp.getIntValue("code"));

    }

    /**
     * 设置header
     *
     * @param request MockHttpServletRequestBuilder
     */
    private void setHeader(MockHttpServletRequestBuilder request) {

        // default header
        request.header("Accept-Encoding", "gzip");
        request.header("Accept-Language", "zh-cn");
        request.header("User-Agent",
                "Mozilla/5.0 (iPad; CPU OS 6_1 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10B141 Safari/8536.25");

        // customer header
        setCustomerHeader(request);
    }
}
