package pl.dmichalski.reservations.business.ui.forms.address.view.modal;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JPanel;

import lombok.Getter;
import org.springframework.stereotype.Component;
import pl.dmichalski.reservations.business.util.constant.ConstMessagesEN;

@Component
@Getter
public class AddressFormBtnPanel extends JPanel {

    private JButton saveBtn;
    private JButton cancelBtn;

    @PostConstruct
    private void preparePanel() {
        initComponents();
    }

    private void initComponents() {
        saveBtn = new JButton(ConstMessagesEN.Labels.ADD_BTN);
        add(saveBtn);

        cancelBtn = new JButton(ConstMessagesEN.Labels.CANCEL_BTN);
        add(cancelBtn);
    }

}
