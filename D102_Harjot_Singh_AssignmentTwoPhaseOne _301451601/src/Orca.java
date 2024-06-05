import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import processing.core.PVector;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

public class Orca {
	
	private int size;
	private PVector pos, speed, originalSpeed;
	private double scalex = 1;
	private double scaley = 1;
	public boolean detectFish = false;
	public Color body = new Color(153, 204, 255);
	public Color bodyDarker = new Color(102, 153, 204);
	
	public Orca(int x, int y, int size, int speedx, int speedy) {
	        pos = new PVector(x, y);
	        this.size = size;
	        this.speed = new PVector(speedx, speedy);
	        this.originalSpeed = new PVector(speedx, speedy);
	}

	public void draw(Graphics g) {		
		Graphics2D g2 = (Graphics2D) g;
		
        	AffineTransform old = g2.getTransform();
		
			g2.translate(pos.x,  pos.y);
			
			g2.rotate(speed.heading());
//			g2.drawRect(-size/2, -size/2, size, size);
			g2.setColor(body);
			g2.scale(scalex, scaley);
			g2.drawOval(-size/2, -size/2, size, size);
			g2.fillOval(-size/2, -size/2, size, size);
			
			
			g2.setColor(Color.black);
			g2.translate(size/2 - size/5, -size/8);
			g2.drawOval(size/20, size/15, size/18, size/18);
			g2.fillOval(size/20, size/15, size/18, size/18);
			g2.drawOval(-size/20, size/15, size/18, size/18);
			g2.fillOval(-size/20, size/15, size/18, size/18);
		
			
			g2.setStroke(new BasicStroke(2));
			g2.drawArc(-size/60, 20, 10, 15, 0, -180);
			
			g2.setColor(body);
//			g2.fillRoundRect(-size+20, size/2-20, 60, 15, 20, 20);
			g2.translate(-size/2-size/4, size/2 + size/25);
			g2.rotate(-Math.PI/3);
//			g2.fillRoundRect(0, 0, 60, 15, 20, 20);
			g2.fillOval(0,-50,20,80);
			g2.rotate(Math.PI/2);
			g2.fillOval(-50,-30,20,40);
			

	        g2.setTransform(old);

	}
	
	public void move() {
		pos.add(speed);
	}
	
	public void checkCollision(Dimension panelSize) {
		if ((pos.x < (size/2) + 20) || (pos.x > panelSize.width - (size/2) - 20)) {
			speed.x *= -1;
			if(speed.x < 0) {
				pos.x -= 50;
			}else {
				pos.x += 50;
			}
		}
		
		if ((pos.y < (size/2) + 20) || (pos.y > panelSize.height - (size/2) - 115)) {
			speed.y *= -1;
			if(speed.y < 0) {
				pos.y -= 50;
			}else {
				pos.y += 50;
			}
			
		}
	}

	public boolean detectFishFunction(PVector fishPos) {
		PVector direction = PVector.sub(fishPos, pos);
        direction.setMag(3);
        speed = direction.copy();
        
        if (PVector.dist(pos, fishPos) <= size / 2 + 10) {
            detectFish = true;
            speed = originalSpeed;
        }
		
		return detectFish;
	}

}
