package com.pal.config;

import com.pal.utils.ParameterMapping;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pal
 * @date 2020/8/28 2:48 下午
 */
@Data
@AllArgsConstructor
public class BoundSql {

    /**
     * 解析后的sql
     */
    private String sqlText;

    private List<ParameterMapping> parameterMappingList = new ArrayList<>();

}
