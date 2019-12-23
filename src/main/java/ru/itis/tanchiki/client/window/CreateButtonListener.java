package ru.itis.tanchiki.client.window;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        frame.showGamePanel();
    }
}
