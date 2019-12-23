package ru.itis.tanchiki.client.window;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ConnectException;

// Чтобы сменить JPanel во frame:
//      frame.getContentPane().removeAll();
//      frame.getContentPane().add( Твоя панель );
//      frame.revalidate();
//      frame.repaint();
// Можешь попробовать иначе, но проверь работоспособность

public class ConnectButtonListener implements ActionListener {
    private MenuWindow frame;

    public ConnectButtonListener(MenuWindow frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JTextField ipField = (JTextField) ((JPanel) ((JPanel) frame.getContentPane().getComponent(0)).getComponent(1)).getComponent(0);
        String ip = ipField.getText();
        boolean isIpCorrect = false;
        if(ip.matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}")) {
            isIpCorrect = true;
            String ipParts[] = ip.split("\\.");
            for(int i = 0; i < ipParts.length; i++) {
                if(Integer.parseInt(ipParts[i]) > 255) {
                    isIpCorrect = false;
                }
            }
        }
        if(isIpCorrect) {
            // JOptionPane.showMessageDialog(ipField.getParent(), "Correct ip");
            // Переход по полю "ip"
            try {
                frame.getClient().connectToServer(ip);
            }catch (ConnectException ex) {
                JOptionPane.showMessageDialog(frame, "Can't connect to server", "", JOptionPane.INFORMATION_MESSAGE);
            }
            while(!frame.getClient().isGameStateLoaded()) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            frame.showGamePanel();
        }
        else {
            JOptionPane.showMessageDialog(frame, "Please enter correct IP of the Server!", "Wrong IP", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
