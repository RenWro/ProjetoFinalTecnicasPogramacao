package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// A classe para o diálogo de adicionar tarefa
class AddTaskDialog extends JDialog {

    private JPanel titlePanel;
    private JLabel titleLabel;
    private JPanel descriptionPanel;
    private JLabel descriptionLabel;
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JButton addButton;
    private JPanel taskComponentPanel;

    public AddTaskDialog(Frame owner, JPanel taskComponentPanel) {
        super(owner, "Adicionar Tarefa", true);
        setSize(400, 300);
        setLayout(new BorderLayout());


        titlePanel = new JPanel(new BorderLayout());
        titleLabel = new JLabel("Título", SwingConstants.CENTER);
        titleField = new JTextField();

        // adiciona o JLabel e o TextField ao painel
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(titleField, BorderLayout.CENTER);

        // Adiciona o painel de título ao norte do BorderLayout do JDialog
        add(titlePanel, BorderLayout.NORTH);

        // Cria um JPanel para a descrição
        descriptionPanel = new JPanel(new BorderLayout());
        descriptionLabel = new JLabel("Descrição", SwingConstants.CENTER);
        descriptionArea = new JTextArea();

        // Adiciona o JLabel e o JTextArea com JScrollPane ao painel
        descriptionPanel.add(descriptionLabel, BorderLayout.NORTH);
        descriptionPanel.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);

        add(descriptionPanel, BorderLayout.CENTER);

        addButton = new JButton("Adicionar Tarefa");
        add(addButton, BorderLayout.SOUTH);


        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText().trim();
                String description = descriptionArea.getText().trim();

                if (!title.isEmpty() && !description.isEmpty()) {
                    TaskComponent taskComponent = new TaskComponent(taskComponentPanel, title, description);
                    taskComponentPanel.add(taskComponent);
                    taskComponentPanel.revalidate();
                    taskComponentPanel.repaint();
                    dispose(); // Fecha o TaskDialog
                } else {
                    JOptionPane.showMessageDialog(AddTaskDialog.this, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Centralizar na tela principal
        setLocationRelativeTo(owner);
    }
}