package br.com.joaodev.isispring.web;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import br.com.joaodev.isispring.util.IsiLogger;

public class IsiSpringWebApplication {
    public static void run(){
        // zerar o log do apache tomcat
        java.util.logging.Logger.getLogger("org.apache").setLevel(java.util.logging.Level.OFF);
        long ini, fim;
        IsiLogger.showBanner();
        try {
            ini = System.currentTimeMillis();
            IsiLogger.log("Main Module", "Starting isiWebApplication");
            Tomcat tomcat = new Tomcat();
            Connector connector = new Connector();
            connector.setPort(8080);
            tomcat.setConnector(connector);

            Context ctx = tomcat.addContext("", new File(".").getAbsolutePath());

            Tomcat.addServlet(ctx, "IsiDispatchServlet", new IsiDispatchServlet());

            ctx.addServletMappingDecoded("/*","IsiDispatchServlet");

            tomcat.start();
            fim = System.currentTimeMillis();
            IsiLogger.log("Main Module", "Isispring Web Apllication starten in "+ ((double)(fim -ini) / 1000)+ " seconds");

            tomcat.getServer().await();
            

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
