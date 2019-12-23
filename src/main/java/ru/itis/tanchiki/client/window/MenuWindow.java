package ru.itis.tanchiki.client.window;

import ru.itis.tanchiki.client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuWindow extends JFrame {
    private JPanel mainPanel;
    private JPanel connectPanel;
    private GamePanel gamePanel;
    private Client client;

    public MenuWindow(String logoPath, Client client) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        super("Tanchiki");
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(512, 512));
        mainPanel = createMainPanel(new CreateButtonListener(this), logoPath);

        connectPanel = createConnectPanel(logoPath);
        JTextField ipField = (JTextField) ((JPanel) connectPanel.getComponent(1)).getComponent(0);
        JButton connectButton = (JButton) ((JPanel) connectPanel.getComponent(1)).getComponent(1);
        System.out.println(ipField.getText());
        connectButton.addActionListener(new ConnectButtonListener(this));

        getContentPane().add(mainPanel);
        this.client = client;
        this.gamePanel = new GamePanel(client);
    }

    private JPanel createMainPanel(ActionListener createButtonListener, String logoPath) {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.BLACK);
        JLabel logo = new JLabel();
        ImageIcon logoImage = new ImageIcon(logoPath);
        logo.setIcon(logoImage);
        logo.setMaximumSize(new Dimension(496, 80));
        mainPanel.add(logo, new GridBagConstraints(0, 0, 1, 1, 2, 0, GridBagConstraints.CENTER, 0, new Insets(0, 0, 0, 0), 0, 0));

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        buttonPanel.setBackground(Color.BLACK);
        Font buttonFont = new Font("Consolas", Font.BOLD, 30);
        Dimension buttonSize = new Dimension(165, 45);

        JButton create = new JButton("Create");
        create.addActionListener(createButtonListener);
        create.setFont(buttonFont);
//        create.setPreferredSize(buttonSize);
        buttonPanel.add(create, new int[]{0, 0});

        JButton connect = new JButton("Connect");
        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                getContentPane().add(connectPanel);
                revalidate();
                repaint();
            }
        });
        connect.setFont(buttonFont);
//        connect.setPreferredSize(buttonSize);
        buttonPanel.add(connect, new int[]{0, 1});

        JButton exit = new JButton("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                System.exit(0);
            }
        });
        exit.setFont(buttonFont);
//        exit.setPreferredSize(buttonSize);
        buttonPanel.add(exit, new int[]{0, 2});
        mainPanel.add(buttonPanel, new GridBagConstraints(0, 1, 1, 1, 2, 0, GridBagConstraints.CENTER, 0, new Insets(20, 0, 0, 0), 0, 0));
        return mainPanel;
    }

    private JPanel createConnectPanel(String logoPath) {
        JPanel connectPanel = new JPanel(new GridBagLayout());
        connectPanel.setBackground(Color.BLACK);
        JLabel logo = new JLabel();
        ImageIcon logoImage = new ImageIcon(logoPath);
        logo.setIcon(logoImage);
        logo.setMaximumSize(new Dimension(496, 80));
        connectPanel.add(logo, new GridBagConstraints(0, 0, 1, 1, 2, 0, GridBagConstraints.CENTER, 0, new Insets(55, 0, 0, 0), 0, 0));

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.BLACK);
        Font buttonFont = new Font("Consolas", Font.BOLD, 30);
        Dimension buttonSize = new Dimension(165, 45);

        final JTextField ipField = new JTextField();
        ipField.setFont(new Font("Consolas", Font.BOLD, 30));
        buttonPanel.add(ipField, new GridBagConstraints(0, 0, 2, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.BOTH, new Insets(0, 0, 0, 10), 0, 0));

        JButton connect = new JButton("Connect");
//        connect.addActionListener(connectToServerButtonListener);
        connect.setFont(buttonFont);
//        connect.setPreferredSize(buttonSize);
        buttonPanel.add(connect, new GridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, 0, new Insets(0, 0, 0, 0), 0, 0));

        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ipField.setText("");
                getContentPane().removeAll();
                getContentPane().add(mainPanel);
                revalidate();
                repaint();
            }
        });
        back.setFont(buttonFont);
//        back.setPreferredSize(buttonSize);
        buttonPanel.add(Box.createRigidArea(new Dimension(165, 20)), new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, 0, new Insets(0, 0, 0, 0), 0, 0));
        buttonPanel.add(back, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, 0, new Insets(10, 0, 0, 10), 0, 0));

        connectPanel.add(buttonPanel, new GridBagConstraints(0, 1, 1, 1, 2, 0, GridBagConstraints.CENTER, 0, new Insets(20, 0, 0, 0), 0, 0));
        return connectPanel;
    }

    public Client getClient() {
        return client;
    }

    public void showGamePanel() {
        getContentPane().removeAll();
        getContentPane().add(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
        revalidate();
        repaint();
        gamePanel.startDrawing();
    }
}
