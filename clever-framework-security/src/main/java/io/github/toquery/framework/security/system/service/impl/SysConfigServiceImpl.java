package io.github.toquery.framework.security.system.service.impl;

import com.google.common.collect.Maps;
import io.github.toquery.framework.curd.service.impl.AppBaseServiceImpl;
import io.github.toquery.framework.security.system.domain.SysConfig;
import io.github.toquery.framework.security.system.repository.SysConfigRepository;
import io.github.toquery.framework.security.system.service.ISysConfigService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author toquery
 * @version 1
 */
@Service
public class SysConfigServiceImpl extends AppBaseServiceImpl<Long, SysConfig, SysConfigRepository> implements ISysConfigService {

    /**
     * 查询条件表达式
     */
    private Map<String, String> expressionMap = new LinkedHashMap<String, String>() {
        {
            put("bizId", "bizId:EQ");
            put("configGroup", "configGroup:EQ");
            put("configName", "configName:EQ");
            put("configValue", "configValue:EQ");

            put("configGroupLike", "configGroup:LIKE");
            put("configNameLike", "configName:LIKE");
            put("configValueLike", "configValue:LIKE");
        }
    };

    @Override
    public Map<String, String> getQueryExpressions() {
        return expressionMap;
    }

    @Override
    public List<SysConfig> reSave(Long bizId, String configGroup, List<SysConfig> sysConfigList) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("configGroup", configGroup);
        params.put("bizId", bizId);
        this.delete(params);
        return this.save(sysConfigList);
    }
}
