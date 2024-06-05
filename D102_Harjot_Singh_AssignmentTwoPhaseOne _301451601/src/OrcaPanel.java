import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

import processing.core.PVector;

public class OrcaPanel extends JPanel implements ActionListener {
    
    private static final long serialVersionUID = 1L;
    private Orca orca;
    private Ocean backgroundObject;
    private Timer t, mouseHeldTimer;
    private Dimension size;
    private int fishSpawnCounter = 0;
    private static final int FISH_SPAWN_DELAY = 150;
    private ArrayList<Bubbles> bubbles;
    private ArrayList<Fish> fishArray;
    private int bubblesLength = 6;
    private Seaweed seaweed1;
    private boolean mouseHeld;
    private MouseEvent currentMouseEvent;
    
    public OrcaPanel(Dimension initialSize) {
        super();
        
        bubbles = new ArrayList<>();
        fishArray = new ArrayList<>(); // Initialize the fish array
        
        for (int i = 0; i < bubblesLength; i++) {
            bubbles.add(new Bubbles(Util.randomInt(0, initialSize.width - 30), 
                                    Util.randomInt(initialSize.height - 150, initialSize.height - 100), 
                                    Util.randomInt(1, 4), 
                                    Util.randomInt(10, 35)));
        }
        
        size = initialSize;
        backgroundObject = new Ocean(initialSize);
        
        orca = new Orca(
            initialSize.width / 2, 
            initialSize.height / 2,
            Math.min(initialSize.width, initialSize.height) / 4,
            (int) Util.random(1, 7), 
            (int) Util.random(1, 7)
        );
        
        seaweed1 = new Seaweed(Util.randomInt(100, initialSize.width - 100), 
                               initialSize.height - 100, 
                               Util.randomInt(50, 90), 4, 2);

        t = new Timer(33, this);
        t.start();
        
        MyMouseAdapter myMouseAdapter = new MyMouseAdapter();
        addMouseListener(myMouseAdapter);
        addMouseMotionListener(myMouseAdapter);
        
        // Timer to handle actions while mouse is held down
        mouseHeldTimer = new Timer(33, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mouseHeld) {
                    handleMouseHeld();
                }
            }
        });
        mouseHeldTimer.start();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        size = getSize();
        
        backgroundObject.setSize(size);
        backgroundObject.drawBackground(g);
        
        orca.draw(g);
        
        for (Fish fish : fishArray) {
            fish.draw(g);
        }
        
        for (int i = 0; i < bubblesLength; i++) {
            bubbles.get(i).drawBubble(g);
        }
        
        seaweed1.drawSeaweed(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        orca.move();
        orca.checkCollision(size);
        
        ArrayList<Fish> caughtFish = new ArrayList<>();
        
        for (int i = fishArray.size() - 1; i >= 0; i--) {
            Fish fish = fishArray.get(i);
            fish.move();
            fish.checkCollision(size);

            orca.detectFishFunction(fish.pos);

            if (orca.detectFish) {
                caughtFish.add(fish);
                orca.detectFish = false; 
                fishSpawnCounter = 0; 
                
            }
        }

        fishArray.removeAll(caughtFish);
        
        fishSpawnCounter++;
        
        if (fishSpawnCounter >= FISH_SPAWN_DELAY) {
            spawnFish(size);
            fishSpawnCounter = 0;
        }
        
        for (int i = 0; i < bubbles.size(); i++) {
            Bubbles bubble = bubbles.get(i);
            bubble.moveBubble();
            if (!bubble.isOnScreen(size)) {
                bubbles.remove(i);
                bubbles.add(new Bubbles(Util.randomInt(30, size.width - 30), 
                                        size.height, 
                                        Util.randomInt(2, 6), 
                                        Util.randomInt(10, 30)));
            }
        }
        
        seaweed1.move();
        seaweed1.checkWalls(size);
        
        repaint();
    }
    
    private void spawnFish(Dimension initialSize) {
        Fish newFish = new Fish(
            (int) Util.random(100, initialSize.width - 100), 
            initialSize.height / 3,
            Math.min(initialSize.width, initialSize.height) / 15,
            (int) Util.random(1, 10),
            (int) Util.random(0, 0),
            Util.randomColor()
        );
        
        fishArray.add(newFish);
    }
    
    private void spawnFishClick(Dimension initialSize, double x, double y) {
        Fish newFish = new Fish(
            (int) x, 
            (int) y,
            Math.min(initialSize.width, initialSize.height) / 15,
            (int) Util.random(1, 10),
            (int) Util.random(0, 0),
            Util.randomColor()
        );
        
        fishArray.add(newFish);
    }

    private void handleMouseHeld() {
        // This method can be used for additional functionality if needed.
    }

    private class MyMouseAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            mouseHeld = true;
            currentMouseEvent = e;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            mouseHeld = false;
            currentMouseEvent = e;
            handleMouseReleased();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            double mouseX = e.getX();
            double mouseY = e.getY();
            
            int clicked = e.getClickCount();
            
            if(clicked == 2) {
            	spawnFishClick(size, mouseX, mouseY);	
            }
            
        }
    }

    private void handleMouseReleased() {
        if (currentMouseEvent != null) {
            for (int i = 0; i < fishArray.size(); i++) {
            	Fish currentFish = fishArray.get(i);
                if (currentFish.checkMouseHit(currentMouseEvent)) {
                    if(currentFish.scale <= 2) {
                    	currentFish.scale += 1;
                    }
                    if(currentMouseEvent.isControlDown()) {
                    	fishArray.remove(i);
                    }
                }
            }
        }
    }
}