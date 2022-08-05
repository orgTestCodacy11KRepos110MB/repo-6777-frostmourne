package com.autohome.frostmourne.monitor.service.core.metric.jdbc;

import java.util.HashMap;
import java.util.Map;

import com.autohome.frostmourne.monitor.service.core.query.ISqlServerDataQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autohome.frostmourne.monitor.model.contract.MetricContract;
import com.autohome.frostmourne.monitor.model.enums.DataSourceType;
import com.autohome.frostmourne.monitor.service.core.domain.MetricData;
import com.autohome.frostmourne.monitor.service.core.metric.AbstractObjectMetric;

@Service
public class SqlServerObjectMetric extends AbstractObjectMetric {

    @Autowired
    protected ISqlServerDataQuery sqlServerDataQuery;

    @Override
    public Map<String, Object> pullMetric(MetricContract metricContract, Map<String, String> ruleSettings) {
        Map<String, Object> result = new HashMap<>();
        MetricData metricData = sqlServerDataQuery.querySql(metricContract);
        result.put("NUMBER", metricData.getMetricValue());
        if (metricData.getLatestDocument() != null) {
            result.putAll(metricData.getLatestDocument());
        }
        if (metricData.getTopNDocuments() != null) {
            result.put("TOP_N_DOCUMENTS", metricData.getTopNDocuments());
        }
        Map<String, String> dataNameProperties = metricContract.getDataNameContract().getSettings();
        result.putAll(dataNameProperties);
        return result;
    }

    @Override
    public boolean matchDataSourceType(String dataSourceType) {
        return dataSourceType.equalsIgnoreCase(DataSourceType.sqlserver.name());
    }
}