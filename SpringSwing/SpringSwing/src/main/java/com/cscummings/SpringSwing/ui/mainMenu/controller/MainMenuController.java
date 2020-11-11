package com.cscummings.SpringSwing.ui.mainMenu.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

import com.cscummings.SpringSwing.ui.mainMenu.view.MainMenuFrame;
import com.cscummings.ui.forms.controller.FormsController;

@Controller
@AllArgsConstructor
public class MainMenuController extends AbstractFrameController {

    private final MainMenuFrame mainMenuFrame;
    private final FormsController formsController;
    private final ReportsController reportsController;

    public void prepareAndOpenFrame() {
        registerAction(mainMenuFrame.getFormsBtn(), (e) -> openFormsWindow());
        registerAction(mainMenuFrame.getReportsBtn(), (e) -> openReportsWindow());
        mainMenuFrame.setVisible(true);
    }

    private void openFormsWindow() {
        formsController.prepareAndOpenFrame();
    }

    private void openReportsWindow() {
        reportsController.prepareAndOpenFrame();
    }

}
