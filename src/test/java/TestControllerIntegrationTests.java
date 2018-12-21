import com.dlut.config.MyMvcConfig;
import com.dlut.service.DemoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MyMvcConfig.class})
/**
 * 它的属性指向了Web资源位置,默认为webapp下,但是本实例不同
 */
@WebAppConfiguration("src/main/resources")
public class TestControllerIntegrationTests
{
    /**
     * 模拟MVC对象,通过MockMvcBuilders.webAppContextSetup(this.war).build()初始化
     */
    private MockMvc mockMvc;

    /**
     * 可以在测试用例中注入Spring的Bean
     */
    @Autowired
    private DemoService demoService;

    /**
     * 可注入WebApplicationContext
     */
    @Autowired
    WebApplicationContext wac;

    /**
     * 可注入模拟的HttpSession,此处只做展示,没有实际使用
     */
    @Autowired
    MockHttpSession session;

    /**
     * 可注入模拟的HttpRequest,此处只做展示,没有实际使用
     */
    @Autowired
    MockHttpServletRequest request;

    @Before
    public void setup()
    {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testNormalController() throws  Exception
    {
        //模拟向/normal发起一个get请求
        mockMvc.perform(get("/normal"))
                //预期控制返回状态为200
                .andExpect(status().isOk())
                //预期view的名称为page
                .andExpect(view().name("page"))
                //预期页面转向的真正路径
                .andExpect(forwardedUrl("/WEB-INF/classes/views/page.jsp"))
                //预期返回的值是demoService.saySomething()
                .andExpect(model().attribute("msg",demoService.saySomething()));
    }

    @Test
    public void testRestController() throws  Exception
    {
        //模拟向/testRestController发起get请求
        mockMvc.perform(get("/testRest"))
                //预期控制返回状态为200
                .andExpect(status().isOk())
                //预期返回的媒体类型
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                //预期返回值的内容
                .andExpect(content().string(demoService.saySomething()));
    }

}
