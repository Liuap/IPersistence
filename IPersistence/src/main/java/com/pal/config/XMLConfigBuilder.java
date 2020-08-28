package com.pal.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.pal.io.Resources;
import com.pal.pojo.Configuration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author pal
 * @date 2020/8/28 10:44 上午
 */
public class XMLConfigBuilder {


    private Configuration configuration;

    public XMLConfigBuilder(){
        this.configuration = new Configuration();
    }
    /**
     * 将配置文件进行解析并封装
     * @param inputStream
     * @return
     */
    public Configuration parseConfig(InputStream inputStream) throws DocumentException, PropertyVetoException {
        //sqlMapConfig的解析
        Document document = new SAXReader().read(inputStream);
        //根对象 <configuration>
        Element rootElement = document.getRootElement();
        // selectNodes("//...") 根对象范围内的搜索,list内是每条property
        List<Element> list = rootElement.selectNodes("//property");

        //得到结果并封装成key：value
        Properties properties = new Properties();
        for (Element element : list) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name,value);
        }

        //连接池 c3p0
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("driverClass"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        comboPooledDataSource.setUser(properties.getProperty("username"));
        comboPooledDataSource.setPassword(properties.getProperty("password"));

        //设置数据库连接池对象给配置封装类
        configuration.setDataSource(comboPooledDataSource);
        
        //mapper.xml的解析，拿到路径 -> 加载为输入流 -> 解析
        List<Element> mapperList = rootElement.selectNodes("//mapper");
        for (Element element : mapperList) {
            String mapperPath = element.attributeValue("resource");
            InputStream resourceAsSteam = Resources.getResourceAsSteam(mapperPath);
            //传入configuration，以便解析数据封装类在封装进configuration的map后返回
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
            //封装的数据保存到 xmlMapperBuilder
            xmlMapperBuilder.parse(resourceAsSteam);
        }

        
        
        return configuration;
    }
}
