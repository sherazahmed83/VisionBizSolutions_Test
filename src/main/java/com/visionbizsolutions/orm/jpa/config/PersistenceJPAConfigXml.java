package com.visionbizsolutions.orm.jpa.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan({ "com.visionbizsolutions.orm.jpa" })
//@ImportResource({ "file:src/main/webapp/WEB-INF/spring-database.xml","file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml" })
public class PersistenceJPAConfigXml {

    public PersistenceJPAConfigXml() {
        super();
    }

}