package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class EditTaskDialog extends JDialog {
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JButton updateButton;
    private TaskComponent taskComponent;

    public EditTaskDialog(JFrame owner, TaskComponent taskComponent) {
        super(owner, "Edit Task", true);
        this.taskComponent = taskComponent;
        setSize(400, 300);
        setLayout(new BorderLayout());

        titleField = new JTextField(taskComponent.getTitle());
        descriptionArea = new JTextArea(taskComponent.getDescription());

        add(titleField, BorderLayout.NORTH);
        add(new JScrollPane(descriptionArea), BorderLayout.CENTER);

        updateButton = new JButton("Update Task");
        add(updateButton, BorderLayout.SOUTH);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Atualiza o TaskComponent com os novos valores
                taskComponent.setTitle(titleField.getText().trim());
                taskComponent.setDescription(descriptionArea.getText().trim());
                dispose(); // Fecha o EditTaskDialog
            }
        });

        setLocationRelativeTo(owner);
    }
}
