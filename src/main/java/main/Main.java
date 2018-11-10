package main;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        int port=8080;
        try{
            port = Integer.parseInt(args[0]);
        } catch (Exception ignore) {}

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setResourceBase("src/main/webapp");
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});

        Server jettyServer = new Server(port);
        jettyServer.setHandler(handlers);

        ServletHolder jerseyServlet1 = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet1.setInitOrder(0);

        jerseyServlet1.setInitParameter(
                "jersey.config.server.provider.packages",
                "resources");

        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }
}
