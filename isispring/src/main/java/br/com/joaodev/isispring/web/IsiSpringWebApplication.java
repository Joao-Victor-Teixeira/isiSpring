package br.com.joaodev.isispring.web;

import java.io.File;
import java.util.List;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import br.com.joaodev.isispring.explorer.ClassExplorer;
import br.com.joaodev.isispring.util.IsiLogger;

public class IsiSpringWebApplication {
    public static void run(Class<?> sourceClass) {

        // zerar o log do apache tomcat
        java.util.logging.Logger.getLogger("org.apache").setLevel(java.util.logging.Level.OFF);
        long ini, fim;

        IsiLogger.showBanner();
        try {
            ini = System.currentTimeMillis();
            IsiLogger.log("Embedded Web Container", "Starting isiWebApplication");

            // Class explorer
            List<String> allClasses = ClassExplorer.retrieveAllClasses(sourceClass);
            allClasses.stream().forEach(c -> IsiLogger.log("Class Explorer", "Class found:" + c));
            // final do class explorer

            Tomcat tomcat = new Tomcat();
            Connector connector = new Connector();
            connector.setPort(8080);
            IsiLogger.log("Embedded Web Container", "Web Container started on port 8080");
            tomcat.setConnector(connector);

            Context ctx = tomcat.addContext("", new File(".").getAbsolutePath());

            Tomcat.addServlet(ctx, "IsiDispatchServlet", new IsiDispatchServlet());

            ctx.addServletMappingDecoded("/*", "IsiDispatchServlet");

            tomcat.start();

            fim = System.currentTimeMillis();
            IsiLogger.log("Embedded Web Container",
                    "Isispring Web Apllication starten in " + ((double) (fim - ini) / 1000) + " seconds");

            tomcat.getServer().await();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
