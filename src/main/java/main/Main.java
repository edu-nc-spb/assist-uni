package main;

import example.Calculator;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import services.Student;
import services.Teacher;


public class Main {
    public static void main(String[] args) throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setResourceBase("WEB-INF");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});
        //context.setResourceBase("WEB-INF");

        Server jettyServer = new Server(8080);
        jettyServer.setHandler(handlers);

        ServletHolder jerseyServlet1 = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet1.setInitOrder(0);


        jerseyServlet1.setInitParameter(
                "jersey.config.server.provider.classnames",
                "services.Teacher, services.Student");

        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }
}
