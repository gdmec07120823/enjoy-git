package com.lgd.home.common;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

/**
 * Created by LGD on 2019/08/15.
 */
//高版本需添加Integer.class
@Intercepts({@Signature(type = StatementHandler.class,method = "prepare",args = {Connection.class,Integer.class})})
@Component
public class ToPage implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler= (StatementHandler) invocation.getTarget();
        MetaObject metaObjectJhadler= SystemMetaObject.forObject(statementHandler);

        while (metaObjectJhadler.hasGetter("h")){
            Object obj=metaObjectJhadler.getValue("h");
            metaObjectJhadler=SystemMetaObject.forObject(obj);
        }

        while (metaObjectJhadler.hasGetter("target")){
            Object obj=metaObjectJhadler.getValue("target");
            metaObjectJhadler=SystemMetaObject.forObject(obj);
        }

        MappedStatement mappedStatement= (MappedStatement) metaObjectJhadler.getValue("delegate.mappedStatement");
        String mapid=mappedStatement.getId();
        //拦截已listPage结尾的数据
        if(mapid.matches(".*listPage$")){
            ParameterHandler parameterHandler= (ParameterHandler) metaObjectJhadler.getValue("delegate.parameterHandler");
            Map<String,Object> paraObject= (Map<String, Object>) parameterHandler.getParameterObject();
            int currpage= (int) paraObject.get("currpage");
            int pagesize= (int) paraObject.get("pagesize");
            String sql = (String) metaObjectJhadler.getValue("delegate.boundSql.sql");
            String limitsql;
            sql=sql.trim();
            limitsql=sql+" limit "+(currpage-1)*pagesize+","+pagesize;
            metaObjectJhadler.setValue("delegate.boundSql.sql",limitsql);
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {

        return Plugin.wrap(target,this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
