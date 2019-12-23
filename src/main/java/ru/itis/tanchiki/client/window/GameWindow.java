//package ru.itis.tanchiki.client.window;
//
//import ru.kpfu.itis.rodsher.tanchiki.models.*;
//
//import javax.imageio.ImageIO;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//
//public class GameWindow extends JFrame {
//    private GameState gameState;
//
//
//    public GameWindow() throws HeadlessException, IOException {
//        super();
//        this.setBounds(200,200,512,512);
//        this.setVisible(true);
//        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        KeyboardListener keyboardListener = new KeyboardListener(gameState);
//        this.addKeyListener(keyboardListener);
//        gameState = new GameState(GameRules.getTankHp(), GameRules.getWallHp());
//        BufferedImage floorTexture = ImageIO.read(new File("textures/Floor.png"));
//        BufferedImage[] wallTextures = {
//                ImageIO.read(new File("textures/Wall.png")),
//                ImageIO.read(new File("textures/Wall2.png")),
//                ImageIO.read(new File("textures/Wall3.png"))
//        };
//        gameState.setField(FieldGenerator.generateStaticField(GameRules.getWallHp(),floorTexture,  wallTextures));
//        Timer timer = new Timer(40, (e)->{
//            draw();
//        });
//        timer.start();
//    }
//
//    public GameWindow(GraphicsConfiguration gc) {
//        super(gc);
//    }
//
//    public GameWindow(String title) throws HeadlessException {
//        super(title);
//    }
//
//    public GameWindow(String title, GraphicsConfiguration gc) {
//        super(title, gc);
//    }
//
//    public GameState getGameState() {
//        return gameState;
//    }
//
//    private void draw() {
//        long t = System.nanoTime();
//        BufferedImage frame = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
//        int cellWidth = this.getWidth() / GameRules.getFieldWidth();
//        int cellHeight = this.getHeight() / GameRules.getFieldHeight();
//        Graphics g = frame.getGraphics();
//        //пол
//        for(int i = 0; i < GameRules.getFieldWidth(); i++) {
//            for(int j =0; j < GameRules.getFieldHeight(); j++) {
//                g.drawImage(gameState.getField().getFloorCells()[i][j].getTexture(), i*cellWidth, j*cellHeight,
//                        cellWidth, cellHeight, null);
//                if(gameState.getField().getFloorCells()[i][j].getEntity() instanceof Wall) {
//                    g.drawImage(gameState.getField().getFloorCells()[i][j].getEntity().getTexture(), i*cellWidth, j*cellHeight,
//                            cellWidth, cellHeight, null);
//                }
//                else if(gameState.getField().getFloorCells()[i][j].getEntity() instanceof Tank) {
//                    Tank ta = (Tank)gameState.getField().getFloorCells()[i][j].getEntity();
//                    g.drawImage(ta.getTexture(), i*cellWidth, j*cellHeight,
//                            cellWidth, cellHeight, null);
//                }
//            }
//        }
//        //сетка
//        for(int i = 0; i < GameRules.getFieldWidth(); i++) {
//            g.drawLine(i*cellWidth, 0, i*cellWidth, this.getHeight());
//        }
//        for(int i = 0; i < GameRules.getFieldHeight(); i++) {
//            g.drawLine(0,i*cellHeight, this.getWidth(), i*cellHeight);
//        }
//
//        //счетчик фпс (должен рисоваться последним)
//        g.drawString((long)10e8/(System.nanoTime() - t) + " fps", 20, 20);
//        g.dispose();
//        this.getContentPane().getGraphics().drawImage(frame, 0 ,0, this.getWidth(), this.getHeight(), null);
//        this.getContentPane().getGraphics().dispose();
//    }
//}
