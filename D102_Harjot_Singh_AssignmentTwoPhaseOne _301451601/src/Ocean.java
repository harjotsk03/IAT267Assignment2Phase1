import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Ocean {
	private Dimension size;
	private Color bgColor = new Color(0,150,187);
    private Color sandColor = new Color(237, 201, 175);
    
    private Rectangle2D.Double ocean;
    private Rectangle2D.Double sand;
    
    private final int ORIGIN = 20;
    private final int START = 40;
    private int WIDTH = 0;
    private int HEIGHT = 0;
    		
	
	public Ocean ( Dimension size) {
		this.size = size;
		ocean = new Rectangle2D.Double();
		sand = new Rectangle2D.Double();
		this.WIDTH = size.width;
		this.HEIGHT = size.height;
		
		setOceanAttributes();
		setSandAttributes();
	}
	
	private void setOceanAttributes() {
		ocean.setFrame(ORIGIN, ORIGIN, WIDTH, HEIGHT - START);	
	}

	private void setSandAttributes() {
		sand.setFrame(ORIGIN, HEIGHT - (ORIGIN * 5), WIDTH, START * 2);
		
	}

	public void drawBackground(Graphics g2d) {
		
		g2d.setColor(bgColor);
		((Graphics2D) g2d).fill(ocean);
		
		g2d.setColor(sandColor);
		((Graphics2D) g2d).fill(sand);
	}

    public void setSize(Dimension size) {
        this.size = size;
    }
}
