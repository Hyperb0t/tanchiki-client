package ru.itis.tanchiki.client.network;

import ru.itis.tanchiki.client.Client;
import ru.kpfu.itis.rodsher.tanchiki.models.*;
import ru.kpfu.itis.rodsher.tanchiki.protocol.EventType;
import ru.kpfu.itis.rodsher.tanchiki.protocol.PackageParser;
import ru.kpfu.itis.rodsher.tanchiki.protocol.Protocol;
import ru.kpfu.itis.rodsher.tanchiki.protocol.ProtocolContent;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class NetworkListener implements Runnable{
    private Client client;
    private BufferedReader reader;
    private PackageParser parser;

    public NetworkListener(Client client, InputStream inputStream) {
        this.client = client;
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        parser = new PackageParser();
    }

    @Override
    public void run() {
        init();
        String inputLine;
        try {
            while ((inputLine = reader.readLine()) != null) {
                handleMessage(inputLine);
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private void handleMessage(String inputLine) {
        Protocol packet = parser.parse(inputLine);
        if(packet.getEventType().equals(EventType.PLAYER_CONNECTED)) {
            acceptPlayerConnected(packet);
        }
        else if(packet.getEventType().equals(EventType.TANK_MOVED)) {
            moveTankInModel(client.getGameState(), packet);
        }
        else if(packet.getEventType().equals(EventType.PLAYER_LEFT)) {
            client.getGameState().getField().getEntities().stream().forEach(en->{if(en instanceof Tank)
                System.out.println(en);});
            for(int i = 0; i < client.getGameState().getPlayers().size(); i++) {
                if(client.getGameState().getPlayers().get(i).getName().equals(packet.getFromContent(0).getPlayerName())) {
                    client.getGameState().getField().getEntities().remove
                            (client.getGameState().getField().getTankByPlayer(client.getGameState().getPlayers().get(i)));
                    client.getGameState().getPlayers().remove(client.getGameState().getPlayers().get(i));
                    return;
                }
            }
            for(AbstractEntity en : client.getGameState().getField().getEntities()) {
                if(en instanceof Tank && en.getX().equals(packet.getFromContent(0).getX())
                        && en.getY().equals(packet.getFromContent(0).getY())) {
                    client.getGameState().getField().getEntities().remove(en);
                    client.getGameState().getField().getFloorCells()
                            [Math.round(packet.getFromContent(0).getX())][Math.round(packet.getFromContent(0).getY())]
                            .setEntity(null);
                }
            }
            System.out.println("after removing");
            client.getGameState().getField().getEntities().stream().forEach(en->{if(en instanceof Tank)
                System.out.println(en);});
        }
    }

    private void init() {
        String inputLine;
        try {
            if ((inputLine = reader.readLine()) != null) {
                Protocol packet = parser.parse(inputLine);
                acceptGameRules(packet);
            }
            if ((inputLine = reader.readLine()) != null) {
                Protocol packet = parser.parse(inputLine);
                acceptGameField(packet);
            }
            if ((inputLine = reader.readLine()) != null) {
                Protocol packet = parser.parse(inputLine);
                acceptPlayerConnected(packet);
            }
        }catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private void acceptGameRules(Protocol packet) {
        if(packet.getEventType().equals(EventType.GAME_RULES_SETTING)) {
            client.setNewGameRules(packet.getFromContent(0).getTankSpeed(),
                    packet.getFromContent(0).getShellSpeed(),
                    packet.getFromContent(0).getTankHealth(),
                    packet.getFromContent(0).getWallHealth());
        }
    }

    private void acceptGameField(Protocol packet) {
        client.setNewGameState(client.getGameRules().getTankHp(), client.getGameRules().getWallHp());
        if(packet.getEventType().equals(EventType.GAME_FIELD_SETTING)) {
            List<ProtocolContent> contents = packet.getContent();
            for(ProtocolContent content : contents) {
                if(content.getEntityType().equals(Wall.class)) {
                    Wall wall = new Wall(content.getX(), content.getY(), client.getGameRules().getWallHp(),
                            client.getTextureManager().getWallTextures());
                    client.getGameState().getField().addEntity(wall);
                }
                if(content.getEntityType().equals(Tank.class)) {
                    BufferedImage[] tankTextures = (client.getGameState().getPlayers().size()%2 == 0) ?
                            client.getTextureManager().getBlueTankTextures() : client.getTextureManager().getGreenTankTextures();
                    Tank tank = new Tank(content.getX(), content.getY(), client.getGameRules().getTankHp(),
                            Direction.UP, tankTextures, null);
                    client.getGameState().getField().addEntity(tank);
                }
            }
        }
    }

    private void acceptPlayerConnected(Protocol packet) {
        if(packet.getEventType().equals(EventType.PLAYER_CONNECTED)) {
            Player player = new Player(packet.getFromContent(0).getPlayerName());
            client.getGameState().addPlayer(player);
            BufferedImage[] tankTextures = (client.getGameState().getPlayers().size()%2 == 0) ?
                    client.getTextureManager().getBlueTankTextures() : client.getTextureManager().getGreenTankTextures();
            Tank tank = new Tank(packet.getFromContent(0).getX(), packet.getFromContent(0).getY(),
                    client.getGameRules().getTankHp(), Direction.UP, tankTextures, player);
            client.getGameState().getField().addEntity(tank);
            if(client.getPlayer() == null) {
                client.setNewPlayer(packet.getFromContent(0).getPlayerName());
            }
            client.setGameStateLoaded(true);
        }
    }
    private void moveTankInModel(GameState gameState, Protocol packet) {
        Tank ta = null;
        CollisionManager collisionManager = new CollisionManager();
        for (AbstractEntity en : gameState.getField().getEntities()) {
            if (en instanceof Tank && en.getX().equals(packet.getFromContent(0).getX())
                    && en.getY().equals(packet.getFromContent(0).getY())) {
                ta = (Tank) en;
                if(collisionManager.canMove(gameState, ta, packet.getFromContent(0).getDirection(), client.getGameRules().getTankSpeed())) {
                    en.setX(packet.getFromContent(0).getNewX());
                    en.setY(packet.getFromContent(0).getNewY());
                    ta.setDirection(packet.getFromContent(0).getDirection());
                }
            }
        }
        if(collisionManager.canMove(gameState, ta, packet.getFromContent(0).getDirection(), client.getGameRules().getTankSpeed())) {
            gameState.getField().getFloorCells()
                    [Math.round(packet.getFromContent(0).getX())][Math.round(packet.getFromContent(0).getY())]
                    .setEntity(null);
            gameState.getField().getFloorCells()
                    [Math.round(packet.getFromContent(0).getNewX())][Math.round(packet.getFromContent(0).getNewY())]
                    .setEntity(ta);
        }
    }
}
