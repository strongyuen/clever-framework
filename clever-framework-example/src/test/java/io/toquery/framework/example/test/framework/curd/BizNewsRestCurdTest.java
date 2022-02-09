package io.toquery.framework.example.test.framework.curd;


import com.toquery.framework.example.bff.admin.news.info.model.constant.QueryType;
import com.toquery.framework.example.bff.admin.news.info.service.BizNewsService;
import com.toquery.framework.example.modules.news.info.constant.BizNewsShowStatus;
import com.toquery.framework.example.modules.news.info.entity.BizNews;
import com.toquery.framework.example.modules.news.info.service.BizNewsDomainService;
import io.github.toquery.framework.common.util.JacksonUtils;
import io.toquery.framework.example.test.BaseSpringTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author toquery
 * @version 1
 */
@Slf4j
//@DataJpaTest
//@BootstrapWith
//@WebMvcTest(BizNewsRest.class)
@AutoConfigureMockMvc
public class BizNewsRestCurdTest extends BaseSpringTest {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private BizNewsService bizNewsService;

    @MockBean
    private BizNewsDomainService bizNewsDomainService;

    @Test
    public void query() throws Exception {

        // List<BizNews> newsList = bizNewsService.findJpa();

        mvc.perform(get("/admin/biz-news")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .param("queryType", QueryType.APP.name()))
                .andDo(print())
                .andExpect(status().isOk());

        mvc.perform(get("/admin/biz-news")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .param("queryType", QueryType.JPA.name()))
                .andDo(print())
                .andExpect(status().isOk());

        mvc.perform(get("/admin/biz-news")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .param("queryType", QueryType.MYBATIS.name()))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    public void list() throws Exception {
        mvc.perform(get("/admin/biz-news/list")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .param("queryType", QueryType.APP.name()))
                .andDo(print())
                .andExpect(status().isOk());

        mvc.perform(get("/admin/biz-news/list")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .param("queryType", QueryType.JPA.name()))
                .andDo(print())
                .andExpect(status().isOk());

        mvc.perform(get("/admin/biz-news/list")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .param("queryType", QueryType.MYBATIS.name()))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    public void save() throws Exception {
        BizNews bizNews = new BizNews();
        bizNews.setTitle("test-rest-save");
        bizNews.setShowStatus(BizNewsShowStatus.SHOW);

        mvc.perform(post("/admin/biz-news")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("queryType", QueryType.APP.name())
                        .content(JacksonUtils.object2String(bizNews))
                )
                .andDo(print())
                .andExpect(status().isOk());

        mvc.perform(post("/admin/biz-news")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("queryType", QueryType.JPA.name())
                        .content(JacksonUtils.object2String(bizNews))
                )
                .andDo(print())
                .andExpect(status().isOk());

        mvc.perform(post("/admin/biz-news")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("queryType", QueryType.MYBATIS.name())
                        .content(JacksonUtils.object2String(bizNews))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }


}
