package com.pal.config;

import com.pal.pojo.Configuration;
import com.pal.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * @author pal
 * @date 2020/8/28 11:20 上午
 */
public class XMLMapperBuilder {
    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration){
        this.configuration = configuration;
    }

    /**
     * mapper.xml解析与封装，具体注释看XMLConfigBuilder
     * @param in
     * @throws DocumentException
     */
    public void parse(InputStream in) throws DocumentException {
        Document read = new SAXReader().read(in);
        Element rootElement = read.getRootElement();
        List<Element> list = rootElement.selectNodes("//select");
        for (Element element : list) {
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(element.attributeValue("id"));
            mappedStatement.setResultType(element.attributeValue("resultType"));
            mappedStatement.setParamterType(element.attributeValue("paramterType"));
            //去除两端空格拿到文本信息
            mappedStatement.setSql(element.getTextTrim());
            //key:namespace.id
            configuration.getMappedStatementMap().put(rootElement.attributeValue("namespace")+"."+element.attributeValue("id"),mappedStatement);
        }
    }
}
