package GUI;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class TaskComponent extends JPanel implements ActionListener{

    private JLabel titleLabel;
    private JTextPane descriptionField;
    private JCheckBox checkBox;
    private JButton deleteButton;
    private JButton editButton;


    // this panel is used so that we can make updates to the task component panel when deleting tasks
    private JPanel parentPanel;

    public TaskComponent(JPanel parentPanel, String title, String description){
        this.parentPanel = parentPanel;

        setLayout(new BorderLayout());

        // title
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // checkbox
        checkBox = new JCheckBox();
        checkBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        checkBox.addActionListener(this);
        headerPanel.add(checkBox, BorderLayout.EAST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Alinha o checkbox ao topo
        headerPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        add(headerPanel, BorderLayout.NORTH);

        // description
        JPanel bodyPanel = new JPanel(new BorderLayout());
        descriptionField = new JTextPane();
        bodyPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        descriptionField.setEditable(false);
        descriptionField.setContentType("text/html");
        descriptionField.setText(description);

        bodyPanel.add(descriptionField, BorderLayout.CENTER);
        add(bodyPanel, BorderLayout.CENTER);

        // Painel lateral para os botões
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        deleteButton = new JButton("X");
        editButton = new JButton("E");

        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(editButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(1, 1)));
        buttonPanel.add(deleteButton);

        deleteButton.addActionListener(this);

        add(buttonPanel, BorderLayout.EAST);


        editButton.setPreferredSize(new Dimension(42,30));
        deleteButton.setPreferredSize(new Dimension(42, 30));

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre o EditTaskDialog para editar o TaskComponent
                EditTaskDialog editDialog = new EditTaskDialog((JFrame) JOptionPane.getFrameForComponent(TaskComponent.this), TaskComponent.this);
                editDialog.setVisible(true);
            }
        });

    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if(checkBox.isSelected()){
            // replaces all html tags to empty string to grab the main text
            String taskText = descriptionField.getText().replaceAll("<[^>]*>", "");

            // add strikethrough text
            descriptionField.setText("<html><s>"+ taskText + "</s></html>");
        }else if(!checkBox.isSelected()){
            String taskText = descriptionField.getText().replaceAll("<[^>]*>", "");

            descriptionField.setText(taskText);
        }

        if(e.getActionCommand().equalsIgnoreCase("X")){
            // delete this component from the parent panel
            parentPanel.remove(this);
            parentPanel.repaint();
            parentPanel.revalidate();
        }
    }

    public String getTitle() {
        return titleLabel.getText(); // Retorna o texto atual do titleLabel
    }

    public String getDescription() {
        // Remove todas as tags HTML do texto para edição
        String text = descriptionField.getText();
        return text.replaceAll("<html>|</html>|<head>|</head>|<body>|</body>", "").trim();
    }

    public void setTitle(String title) {
        titleLabel.setText(title); // Atualiza o texto do titleLabel
    }

    public void setDescription(String description) {
        descriptionField.setText(description); // Atualiza o texto do descriptionField
    }


}