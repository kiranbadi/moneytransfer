/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revolut.kb.moneytransfer;

/**
 *
 * @author Kiran
 */
import javax.servlet.ServletException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.tools.RunScript;

public class StartServerServices {

    private static final Logger LOGGER = LogManager.getLogger(StartServerServices.class);

    public static void main(String[] args) throws LifecycleException, InterruptedException, ServletException {
        StartH2Database();
        String webappDirLocation = "src/main/webapp/";
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        Context ctx = tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
        LOGGER.info("configuring webapp with basedir: " + new File(webappDirLocation).getAbsolutePath());
        File additionWebInfClasses = new File("target/classes");
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes", additionWebInfClasses.getAbsolutePath(), "/"));
        ctx.setResources(resources);
        tomcat.start();
        tomcat.getServer().await();

    }

    
    // FIXME : Resources are not getting found when run from jar.
    private static void StartH2Database() {
        try {
          //  Server server = Server.createTcpServer().start();
            LOGGER.info("Starting H2 DATABASE ");
            String url = "jdbc:h2:~/test";
            Class.forName("org.h2.Driver");
            Connection con = DriverManager.getConnection(url, "sa", "");
          File script = new File(StartServerServices.class.getResource("/moneytransferdata.sql").getFile());
            RunScript.execute(con, new FileReader(script));
        } catch (ClassNotFoundException ex) {
            LOGGER.error("ClassNotFoundException \n{}", ex);
        } catch (SQLException ex) {
            LOGGER.error("SQLException \n{}", ex);
        } catch (FileNotFoundException ex) {
            LOGGER.error("FileNotFoundException \n {}", ex);
        }

    }

}
