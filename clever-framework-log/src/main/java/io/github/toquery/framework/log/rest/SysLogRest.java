package io.github.toquery.framework.log.rest;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.toquery.framework.common.constant.AppCommonConstant;
import io.github.toquery.framework.core.security.userdetails.AppUserDetailService;
import io.github.toquery.framework.crud.controller.AppBaseCrudController;
import io.github.toquery.framework.log.entity.SysLog;
import io.github.toquery.framework.log.service.ISysLogService;
import io.github.toquery.framework.web.domain.ResponseBodyBuilder;
import io.github.toquery.framework.web.domain.ResponseBody;
import io.micrometer.core.annotation.Timed;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author toquery
 * @version 1
 */
@RestController
@RequestMapping("/sys/log")
@Timed(value = "system-log", description = "系统-日志")
public class SysLogRest extends AppBaseCrudController<ISysLogService, SysLog> {

    @Resource
    private AppUserDetailService userDetailsService;

    private static final String[] PAGE_SORT = new String[]{"createDateTime_desc"};

    @GetMapping
    public ResponseBody query(@RequestParam(value = "filter_createDateTimeGT", required = false) @DateTimeFormat(pattern = AppCommonConstant.DATE_TIME_PATTERN, iso = DateTimeFormat.ISO.DATE_TIME) @JsonFormat(pattern = AppCommonConstant.DATE_TIME_PATTERN) LocalDateTime createDataGT,
                              @RequestParam(value = "filter_createDateTimeLT", required = false) @DateTimeFormat(pattern = AppCommonConstant.DATE_TIME_PATTERN, iso = DateTimeFormat.ISO.DATE_TIME) @JsonFormat(pattern = AppCommonConstant.DATE_TIME_PATTERN) LocalDateTime createDataLT) {
        Map<String, Object> filterParam = super.getFilterParam();
        if (createDataGT != null && createDataLT != null) {
            filterParam.put("createDateTimeGT", createDataGT);
            filterParam.put("createDateTimeLT", createDataLT);
        }
        Page<SysLog> page = super.domainService.pageWithUser(filterParam, super.getRequestCurrent(), super.getRequestPageSize(), PAGE_SORT);
        return new ResponseBodyBuilder().page(page).build();
    }

    @GetMapping("{id}")
    public ResponseBody detailResponseBody(@PathVariable Long id) {
        SysLog sysLog = super.getById(id);
        Long userId = sysLog.getUserId();
        userId = userId == null ? sysLog.getCreateUserId() : userId;
        return super.handleResponseBody(new SysLog(sysLog, userDetailsService.getById(userId)));
    }

    @PostMapping
    public ResponseBody saveResponseResult(@Validated @RequestBody SysLog sysLog) {
        return super.saveResponseResult(sysLog);
    }


}
