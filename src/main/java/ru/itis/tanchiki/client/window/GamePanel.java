package ru.itis.tanchiki.client.window;

import ru.itis.tanchiki.client.Client;
import ru.kpfu.itis.rodsher.tanchiki.models.AbstractEntity;
import ru.kpfu.itis.rodsher.tanchiki.models.Tank;
import ru.kpfu.itis.rodsher.tanchiki.models.Wall;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel {
    Client client;
    Timer timer = new Timer(40, (e) -> {
        draw();
    });

    public GamePanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    public GamePanel(LayoutManager layout) {
        super(layout);
    }

    public GamePanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public GamePanel(Client client) {
        super();
        this.client = client;
        this.addKeyListener(new KeyboardListener(client));
    }

    public void startDrawing() {
        timer.start();
    }

    public void stopDrawing() {
        timer.stop();
    }

    private void draw() {
        long t = System.nanoTime();
        BufferedImage frame = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        int cellWidth = this.getWidth() / client.getGameRules().getFieldWidth();
        int cellHeight = this.getHeight() / client.getGameRules().getFieldHeight();
        Graphics g = frame.getGraphics();
        //пол
        for(int i = 0; i < client.getGameRules().getFieldWidth(); i++) {
            for(int j =0; j < client.getGameRules().getFieldHeight(); j++) {
                g.drawImage(client.getGameState().getField().getFloorCells()[i][j].getTexture(), i*cellWidth, j*cellHeight,
                        cellWidth, cellHeight, null);
            }
        }
        for(AbstractEntity en : client.getGameState().getField().getEntities()) {
            g.drawImage(en.getTexture(), Math.round(en.getX()*cellWidth), Math.round(en.getY() * cellHeight),
                    cellWidth, cellHeight, null);
        }
        //сетка
        for(int i = 0; i < client.getGameRules().getFieldWidth(); i++) {
            g.drawLine(i*cellWidth, 0, i*cellWidth, this.getHeight());
        }
        for(int i = 0; i < client.getGameRules().getFieldHeight(); i++) {
            g.drawLine(0,i*cellHeight, this.getWidth(), i*cellHeight);
        }

        //счетчик фпс (должен рисоваться последним)
        g.drawString((long)10e8/(System.nanoTime() - t) + " fps", 20, 20);
        g.dispose();
        this.getGraphics().drawImage(frame, 0 ,0, this.getWidth(), this.getHeight(), null);
        this.getGraphics().dispose();
    }

}
