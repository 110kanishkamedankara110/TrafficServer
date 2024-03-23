package web.listeners;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import web.util.MessageReciver;

@WebListener
public class ContextLisdtener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("initialized......................");
        new MessageReciver().startMessageListener();
    }
}
