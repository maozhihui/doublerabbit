package com.comba.server.common.data.rest;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author maozhihui
 * @Description:规则实体类
 * @create 2017/11/30 11:10
 **/
@Data
public class RuleRest {

    // 规则名称
    private String name;
    // 规则输入
    private Condition input;
    // 规则输出
    private List<Action> output;

    @Data
    public static class Condition{
        private String goalExpression;
        private List<SubCondition> conditions;
    }

    @Data
    public static class SubCondition{
        private String name;
        private String did;
        private String type;
        private String expression;
    }

    @Data
    public static class Action{
        private String type;
        private String did;
        private Map<String,String> attrs;
    }
}
