/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game;

import Entity.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author hp
 */
public class GamePanel extends JPanel implements Runnable{
    final int originalTileSize = 16;   //tile ki size 16X16
    final int scale = 3;// scaling krenge
    public final int tileSize = originalTileSize*scale;//tile ki size 48X48
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize*maxScreenCol;  //768
    final int screenHeight = tileSize*maxScreenRow;  //576
    
    
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this,keyH);
    
    int FPS = 60;
    int playerX = 100;
    int playerY = 100; //default position
    int playerSpeed =4;
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);//rendering performance improved
        this.addKeyListener(keyH);
        this.setFocusable(true); //focused rhega jese hi key input milega
    }
    
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
//    @Override
//    public void run() {
//        
//        
//        double drawInterval = 1000000000/FPS; //itni baar screen draw kr skte hai 
//        double nextDrawTime = System.nanoTime()+drawInterval;
//        while(gameThread!=null){
//            //is method mein we are going to update the character's position and draw screen with that new position
//            update();
//            repaint();
//        
//            
//            
//            try {
//                double remainingTime = nextDrawTime - System.nanoTime();
//                remainingTime = remainingTime/1000000; //millisecs mein kr rhe
//                
//                if(remainingTime<0){
//                remainingTime =0;      
//                }
//                
//                Thread.sleep((long)remainingTime);
//                
//                nextDrawTime = nextDrawTime + drawInterval; 
//            } catch (InterruptedException ex) {
//                Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
    @Override
    public void run(){
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime ;
        long timer=0;
        int drawCount=0;
        
        while(gameThread!=null){
            currentTime = System.nanoTime();
            
            delta+= (currentTime-lastTime)/drawInterval;
            timer+= (currentTime-lastTime);
            lastTime = currentTime;
            
            if(delta>=1){
                update();
                repaint();
                delta--;
                drawCount++;
            }
            
            if(timer>=1000000000){
                System.out.println("FPS = "+drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
    
    public void update(){
        player.update();
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g; //converting it into 2d grpahics
        
        player.draw(g2);
        
        g2.dispose(); //system resource release krta hai
    }
}
