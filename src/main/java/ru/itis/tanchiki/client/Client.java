package ru.itis.tanchiki.client;

import ru.itis.tanchiki.client.network.NetworkListener;
import ru.itis.tanchiki.client.network.NetworkSender;
import ru.itis.tanchiki.client.window.MenuWindow;
import ru.itis.tanchiki.client.window.TextureManager;
import ru.kpfu.itis.rodsher.tanchiki.models.Floor;
import ru.kpfu.itis.rodsher.tanchiki.models.GameRules;
import ru.kpfu.itis.rodsher.tanchiki.models.GameState;
import ru.kpfu.itis.rodsher.tanchiki.models.Player;

import java.net.ConnectException;
import java.net.Socket;

public class Client {
    private Player player;
    private Socket socket;
    private GameState gameState;
    private GameRules gameRules;
    private NetworkListener networkListener;
    private NetworkSender networkSender;
    private MenuWindow menuWindow;
    private TextureManager textureManager;
    private boolean GameStateLoaded = false;

    public Client() {
        textureManager = new TextureManager();
    }

    public void start() {
        try {
            menuWindow = new MenuWindow("textures/Logo.png", this);
            menuWindow.setVisible(true);
        }catch (Exception e){
            throw new IllegalStateException(e);
        }

    }

    public void connectToServer(String ip) throws ConnectException{
        try {
            this.socket = new Socket(ip, 4322);
            networkSender = new NetworkSender(socket.getOutputStream());
            networkListener = new NetworkListener(this, socket.getInputStream());
            new Thread((networkListener)).start();
        }catch (ConnectException ex) {
            throw ex;
        }
        catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public void setNewGameState(int tankHealth, int wallHealth) {
        gameState = new GameState(tankHealth, wallHealth, gameRules.getFieldWidth(), gameRules.getFieldHeight());
        for (int i = 0; i < gameRules.getFieldWidth(); i++) {
            for (int j = 0; j < gameRules.getFieldHeight(); j++) {
                gameState.getField().getFloorCells()[i][j] = new Floor(textureManager.getFloorTexture());
            }
        }
    }

    public void setNewGameRules(float tankSpeed, float bulletSpeed, int tankHp, int wallHp) {
        this.gameRules = new GameRules(tankSpeed, bulletSpeed, tankHp, wallHp);
    }

    public void setNewPlayer(String name) {
        player = new Player(name);
    }

    public Player getPlayer() {
        return player;
    }

    public Socket getSocket() {
        return socket;
    }

    public GameState getGameState() {
        return gameState;
    }

    public GameRules getGameRules() {
        return gameRules;
    }

    public NetworkListener getNetworkListener() {
        return networkListener;
    }

    public NetworkSender getNetworkSender() {
        return networkSender;
    }

    public MenuWindow getMenuWindow() {
        return menuWindow;
    }

    public TextureManager getTextureManager() {
        return textureManager;
    }

    public boolean isGameStateLoaded() {
        return GameStateLoaded;
    }

    public void setGameStateLoaded(boolean gameStateLoaded) {
        GameStateLoaded = gameStateLoaded;
    }
}
