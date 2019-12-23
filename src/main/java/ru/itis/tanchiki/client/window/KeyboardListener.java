package ru.itis.tanchiki.client.window;

import javafx.scene.input.KeyCode;
import ru.itis.tanchiki.client.Client;
import ru.kpfu.itis.rodsher.tanchiki.models.CollisionManager;
import ru.kpfu.itis.rodsher.tanchiki.models.Direction;
import ru.kpfu.itis.rodsher.tanchiki.models.GameState;
import ru.kpfu.itis.rodsher.tanchiki.models.Tank;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener {
    private Client client;
    private CollisionManager collisionManager;

    public KeyboardListener(Client client) {
        this.client = client;
        collisionManager = new CollisionManager();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(isMovingKey(e.getKeyChar())) {
            Tank ta = client.getGameState().getField().getTankByPlayer(client.getPlayer());
            float oldX = ta.getX();
            float oldY = ta.getY();
            client.getGameState().getField().getFloorCells()[Math.round(oldX)][Math.round(oldY)].setEntity(null);
            Direction direction = null;

            if (e.getKeyCode() == KeyEvent.VK_W) {
                ta.setCoordinates(ta.getX(), ta.getY() - client.getGameRules().getTankSpeed());
                direction = Direction.UP;
            } else if (e.getKeyCode() == KeyEvent.VK_S) {
                ta.setCoordinates(ta.getX(), ta.getY() + client.getGameRules().getTankSpeed());
                direction = Direction.DOWN;
            } else if (e.getKeyCode() == KeyEvent.VK_A) {
                ta.setCoordinates(ta.getX() - client.getGameRules().getTankSpeed(), ta.getY());
                direction = Direction.LEFT;
            } else if (e.getKeyCode() == KeyEvent.VK_D) {
                ta.setCoordinates(ta.getX() + client.getGameRules().getTankSpeed(), ta.getY());
                direction = Direction.RIGHT;
            }
            if(collisionManager.canMove(client.getGameState(), ta, direction, client.getGameRules().getTankSpeed())) {
                client.getNetworkSender().sendTankMoved(oldX, oldY, ta.getX(), ta.getY(), direction);
                ta.setDirection(direction);
                client.getGameState().getField().getFloorCells()
                        [Math.round(ta.getX())][Math.round(ta.getY())].setEntity(ta);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private boolean isMovingKey(char c) {
        return c == 'w' ||
                c == 's' ||
                c == 'a' ||
                c == 'd';
    }
}
