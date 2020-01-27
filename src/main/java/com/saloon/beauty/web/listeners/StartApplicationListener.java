package com.saloon.beauty.web.listeners;

import com.saloon.beauty.repository.entity.Languages;
import com.saloon.beauty.services.emailService.EmailSenderService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Configures application on startup
 */
public class StartApplicationListener implements ServletContextListener{

    private ScheduledExecutorService scheduler;

    // Public constructor is required by servlet spec
    public StartApplicationListener() {}

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext context = sce.getServletContext();

        addLanguagesListToContext(context);
        setDefaultApplicationLanguage(context);

        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new EmailSenderService(), 0, 30, TimeUnit.MINUTES);
    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        scheduler.shutdownNow();
    }

    /**
     * Adds languages list to the application(servlet) context
     * @param sc - the application(servlet) context
     */
    private void addLanguagesListToContext(ServletContext sc) {
        List<Languages> languages = Stream
                .of(Languages.ENGLISH, Languages.RUSSIAN, Languages.UKRAINIAN)
                .collect(Collectors.toList());
        sc.setAttribute("languages", languages);
    }

    /**
     * Sets a default application language in the application(servlet) context
     * @param sc - the application(servlet) context
     */
    private void setDefaultApplicationLanguage(ServletContext sc) {
        sc.setAttribute("language", Languages.ENGLISH.getCode());
    }
}
