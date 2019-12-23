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

public class CreateButtonListener implements ActionListener {
    private MenuWindow frame;

    public CreateButtonListener(MenuWindow frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            frame.getClient().connectToServer("127.0.0.1");
        } catch (ConnectException ex) {
            ex.printStackTrace();
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
}
