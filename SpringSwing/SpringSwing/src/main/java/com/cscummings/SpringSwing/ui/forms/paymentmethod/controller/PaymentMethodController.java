package pl.dmichalski.reservations.business.ui.forms.paymentmethod.controller;

import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import pl.dmichalski.reservations.business.app.service.payment.PaymentMethodService;
import pl.dmichalski.reservations.business.domain.entity.payment.PaymentMethodEntity;
import pl.dmichalski.reservations.business.ui.forms.paymentmethod.model.PaymentMethodTableModel;
import pl.dmichalski.reservations.business.ui.forms.paymentmethod.view.PaymentMethodTableBtnPanel;
import pl.dmichalski.reservations.business.ui.forms.paymentmethod.view.PaymentMethodTableFrame;
import pl.dmichalski.reservations.business.ui.forms.paymentmethod.view.modal.AddPaymentMethodFrame;
import pl.dmichalski.reservations.business.ui.forms.paymentmethod.view.modal.PaymentMethodFormBtnPanel;
import pl.dmichalski.reservations.business.ui.forms.paymentmethod.view.modal.PaymentMethodFormPanel;
import pl.dmichalski.reservations.business.ui.shared.controller.AbstractFrameController;
import pl.dmichalski.reservations.business.util.constant.ConstMessagesEN;
import pl.dmichalski.reservations.business.util.notification.Notifications;
import pl.dmichalski.reservations.business.validation.ValidationError;
import pl.dmichalski.reservations.business.validation.payment.PaymentMethodValidator;

@Controller
@AllArgsConstructor
public class PaymentMethodController extends AbstractFrameController {

    private final PaymentMethodTableFrame tableFrame;
    private final AddPaymentMethodFrame addFrame;
    private final PaymentMethodTableModel tableModel;
    private final PaymentMethodService paymentMethodService;
    private final PaymentMethodValidator validator;

    @PostConstruct
    private void prepareListeners() {
        PaymentMethodTableBtnPanel tableBtnPanel = tableFrame.getTableBtnPanel();
        PaymentMethodFormBtnPanel formBtnPanel = addFrame.getFormBtnPanel();

        registerAction(tableBtnPanel.getAddBtn(), (e) -> showAddModal());
        registerAction(tableBtnPanel.getRemoveBtn(), (e) -> removeEntity());
        registerAction(formBtnPanel.getSaveBtn(), (e) -> saveEntity());
        registerAction(formBtnPanel.getCancelBtn(), (e) -> closeModalWindow());
    }

    @Override
    public void prepareAndOpenFrame() {
        loadEntities();
        showTableFrame();
    }

    private void loadEntities() {
        List<PaymentMethodEntity> entities = paymentMethodService.findAll();
        tableModel.clear();
        tableModel.addEntities(entities);
    }

    private void showTableFrame() {
        tableFrame.setVisible(true);
    }

    private void showAddModal() {
        addFrame.setVisible(true);
    }

    private void saveEntity() {
        PaymentMethodFormPanel formPanel = addFrame.getFormPanel();
        PaymentMethodEntity entity = formPanel.getEntityFromForm();
        Optional<ValidationError> errors = validator.validate(entity);
        if (errors.isPresent()) {
            ValidationError validationError = errors.get();
            Notifications.showFormValidationAlert(validationError.getMessage());
        } else {
            paymentMethodService.save(entity);
            tableModel.addEntity(entity);
            closeModalWindow();
        }
    }

    private void closeModalWindow() {
        addFrame.getFormPanel().clearForm();
        addFrame.dispose();
    }

    private void removeEntity() {
        try {
            JTable table = tableFrame.getTablePanel().getTable();
            int selectedRow = table.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(null,
                        ConstMessagesEN.Messages.NON_ROW_SELECTED,
                        ConstMessagesEN.Messages.ALERT_TILE,
                        JOptionPane.ERROR_MESSAGE);
            } else {
                PaymentMethodEntity entity = tableModel.getEntityByRow(selectedRow);
                paymentMethodService.remove(entity);
                tableModel.removeRow(selectedRow);
            }
        } catch (Exception e) {
            Notifications.showDeleteRowErrorMessage();
        }
    }

}
