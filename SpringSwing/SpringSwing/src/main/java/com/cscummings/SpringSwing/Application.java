package com.cscummings.SpringSwing;

import javax.swing.SwingUtilities;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.cscummings.SpringSwing.ui.mainMenu.controller.MainMenuController;
import com.cscummings.SpringSwing.util.ui.LookAndFeelUtils;


@SpringBootApplication
public class Application 
{
    public static void main(String[] args) {
        LookAndFeelUtils.setWindowsLookAndFeel();
        ConfigurableApplicationContext context = createApplicationContext(args);
        displayMainFrame(context);
    }

    private static ConfigurableApplicationContext createApplicationContext(String[] args) {
        return new SpringApplicationBuilder(Application.class)
                .headless(false)
                .run(args);
    }

    private static void displayMainFrame(ConfigurableApplicationContext context) {
        SwingUtilities.invokeLater(() -> {
            MainMenuController mainMenuController = (MainMenuController) context.getBean(MainMenuController.class);
            mainMenuController.prepareAndOpenFrame();
        });
    }

}
