package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

    /*
    TODO, polir o front end com fonts customizadas, separar melhor os métodos
    (aplciar solid em geral)
    */

public class TodoListGui extends JFrame implements ActionListener{
    private JPanel taskPanel, taskComponentPanel;
    private JLabel dateLabel;

    public TodoListGui(){
        super("To Do List Application");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(CommonConstants.GUI_SIZE);
        setLayout(new BorderLayout());
        initComponents();
        pack();
        setLocationRelativeTo(null);
        setResizable(false);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                super.windowClosing(e);
            }
        });
    }

    private void initComponents() {
        header();
        taskPanel();
        setTaskComponentPanel();
        setScrollingTaskPanel();
    }

    public void header(){
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.DARK_GRAY);
        headerPanel.setPreferredSize(new Dimension(400, 50));

        dateLabel = new JLabel("", SwingConstants.CENTER);
        dateLabel.setForeground(Color.WHITE);
        updateDateTime();

        Timer timer = new Timer(1000, e -> updateDateTime());
        timer.start();

        headerPanel.add(dateLabel, BorderLayout.CENTER);

        JButton addTaskButton = new JButton("+");
        headerPanel.add(addTaskButton, BorderLayout.EAST);

        addTaskButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addTaskButton.addActionListener(this);

        this.getContentPane().add(headerPanel, BorderLayout.NORTH);
    }

    private void updateDateTime() {
        String pattern = "EEEE, dd MMMM yyyy HH:mm:ss"; // Formato da data e hora
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String dateTime = simpleDateFormat.format(new Date());
        dateLabel.setText(dateTime);
    }

    public void taskPanel() {
        taskPanel = new JPanel();
    }

    public void setTaskComponentPanel() {
        taskPanel = new JPanel(new BorderLayout());
        taskComponentPanel = new JPanel();
        taskComponentPanel.setLayout(new BoxLayout(taskComponentPanel, BoxLayout.Y_AXIS));
        taskPanel.add(taskComponentPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(taskPanel);
        scrollPane.setBorder(BorderFactory.createLoweredBevelBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    public void setScrollingTaskPanel() {
        JScrollPane scrollPane = new JScrollPane(taskPanel);
        scrollPane.setBounds(8, 70, CommonConstants.TASKPANEL_SIZE.width, CommonConstants.TASKPANEL_SIZE.height);
        scrollPane.setBorder(BorderFactory.createLoweredBevelBorder());
        scrollPane.setMaximumSize(CommonConstants.TASKPANEL_SIZE);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(20);

        this.getContentPane().add(scrollPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command.equalsIgnoreCase("+")){
            AddTaskDialog taskDialog = new AddTaskDialog(TodoListGui.this, taskComponentPanel);
            taskDialog.setVisible(true);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TodoListGui().setVisible(true);
            }
        });
    }


}
