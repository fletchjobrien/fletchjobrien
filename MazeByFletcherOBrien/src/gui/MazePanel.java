package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;

import generation.Wall;

/**
 * Add functionality for double buffering to an AWT Panel class.
 * Used for drawing a maze.
 * 
 * @author Peter Kemper
 *
 */
public class MazePanel extends Panel implements P5PanelF21  {
	//wall class
	private Color wallcolor;
	
	private Color currentcolor;
	
	private static final long serialVersionUID = 2787329533730973905L;
	/* Panel operates a double buffer see
	 * http://www.codeproject.com/Articles/2136/Double-buffer-in-standard-Java-AWT
	 * for details
	 */
	// bufferImage can only be initialized if the container is displayable,
	// uses a delayed initialization and relies on client class to call initBufferImage()
	// before first use
	private Image bufferImage;  
	private Graphics2D graphics; // obtained from bufferImage, 
	// graphics is stored to allow clients to draw on the same graphics object repeatedly
	// has benefits if color settings should be remembered for subsequent drawing operations
	
	/**
	 * Constructor. Object is not focusable.
	 */
	public MazePanel() {
		setFocusable(false);
		bufferImage = null; // bufferImage initialized separately and later
		graphics = null;	// same for graphics
	}
	
	@Override
	public void update(Graphics g) {
		paint(g);
	}
	/**
	 * Method to draw the buffer image on a graphics object that is
	 * obtained from the superclass. 
	 * Warning: do not override getGraphics() or drawing might fail. 
	 */
	public void update() {
		paint(getGraphics());
	}
	
	/**
	 * Draws the buffer image to the given graphics object.
	 * This method is called when this panel should redraw itself.
	 * The given graphics object is the one that actually shows 
	 * on the screen.
	 */
	@Override
	public void paint(Graphics g) {
		if (null == g) {
			System.out.println("MazePanel.paint: no graphics object, skipping drawImage operation");
		}
		else {
			g.drawImage(bufferImage,0,0,null);	
		}
	}

	/**
	 * Obtains a graphics object that can be used for drawing.
	 * This MazePanel object internally stores the graphics object 
	 * and will return the same graphics object over multiple method calls. 
	 * The graphics object acts like a notepad where all clients draw 
	 * on to store their contribution to the overall image that is to be
	 * delivered later.
	 * To make the drawing visible on screen, one needs to trigger 
	 * a call of the paint method, which happens 
	 * when calling the update method. 
	 * @return graphics object to draw on, null if impossible to obtain image
	 */
	public Graphics getBufferGraphics() {
		// if necessary instantiate and store a graphics object for later use
		if (null == graphics) { 
			if (null == bufferImage) {
				bufferImage = createImage(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT);
				if (null == bufferImage)
				{
					System.out.println("Error: creation of buffered image failed, presumedly container not displayable");
					return null; // still no buffer image, give up
				}		
			}
			graphics = (Graphics2D) bufferImage.getGraphics();
			if (null == graphics) {
				System.out.println("Error: creation of graphics for buffered image failed, presumedly container not displayable");
			}
			else {
				// System.out.println("MazePanel: Using Rendering Hint");
				// For drawing in FirstPersonDrawer, setting rendering hint
				// became necessary when lines of polygons 
				// that were not horizontal or vertical looked ragged
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
						RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			}
		}
		return graphics;
	}

	@Override
	public void commit() {
		update();
		
	}
	
	/**
	 * Calculates the weighted average of the two given colors.
	 * The weight for the first color is expected to be between
	 * 0 and 1. The weight for the other color is then 1-weight0.
	 * The result is the weighted average of the red, green, and
	 * blue components of the colors. The resulting alpha value
	 * for transparency is the max of the alpha values of both colors.
	 * @param fstColor is the first color
	 * @param sndColor is the second color
	 * @param weightFstColor is the weight of fstColor, {@code 0.0 <= weightFstColor <= 1.0}
	 * @return blend of both colors as weighted average of their rgb values
	 */
	public int blend(int fstColor, int sndColor, double weightFstColor) {
		Color fc = new Color(fstColor);
		Color sc = new Color(sndColor);
		if (weightFstColor < 0.1)
			return sndColor;
		if (weightFstColor > 0.95)
			return fstColor;
	    double r = weightFstColor * fc.getRed() + (1-weightFstColor) * sc.getRed();
	    double g = weightFstColor * fc.getGreen() + (1-weightFstColor) * sc.getGreen();
	    double b = weightFstColor * fc.getBlue() + (1-weightFstColor) * sc.getBlue();
	    double a = Math.max(fc.getAlpha(), sc.getAlpha());
	    
	    Color result = new Color((int) r, (int) g, (int) b, (int) a);

	    return result.getRGB();
	  }
	
	/**
     * Determine the color for this wall.
     *
     * @param distance
     *            to exit
     * @param cc
     *            obscure
     */
    public static int createColor(Wall w, final int distance, final int cc) {
        final int d = distance / 4;
        // mod used to limit the number of colors to 6
        final int rgbValue = w.calculateRGBValue(d);
        //System.out.println("Initcolor rgb: " + rgbValue);
        switch (((d >> 3) ^ cc) % 6) {
        case 0:
            return(new Color(rgbValue, 0, 0).getRGB());
        case 1:
        	return(new Color(0, 255, 0).getRGB());
        case 2:
        	return(new Color(0, 0, rgbValue).getRGB());
        case 3:
        	return(new Color(rgbValue, 255, 0).getRGB());
        case 4:
        	return(new Color(0, 255, rgbValue).getRGB());
        case 5:
        	return(new Color(rgbValue, 0, rgbValue).getRGB());
        default:
        	return(new Color(0, 0, 0).getRGB());
        }
    }

	@Override
	public boolean isOperational() {
		if (bufferImage == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void setColor(int rgb) {
		currentcolor = new Color(rgb);
	}

	@Override
	public int getColor() {
		if (currentcolor == null) {
			System.out.println("Error: Tried to get color but a color hasn't been set yet.");
			return -1;
		}
		return currentcolor.getRGB();
	}

	@Override
	public void addBackground(float percentToExit) {
		graphics.setBackground(currentcolor);
	}

	@Override
	public void addFilledRectangle(int x, int y, int width, int height) {
		graphics.fillRect(x, y, width, height);
	}

	@Override
	public void addFilledPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		graphics.fillPolygon(xPoints, yPoints, nPoints);
	}

	@Override
	public void addPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		graphics.drawPolygon(xPoints, yPoints, nPoints);
	}

	@Override
	public void addLine(int startX, int startY, int endX, int endY) {
		graphics.drawLine(startX, startY, endX, endY);
	}

	@Override
	public void addFilledOval(int x, int y, int width, int height) {
		graphics.fillOval(x, y, width, height);
	}

	@Override
	public void addArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
		graphics.drawArc(x, y, width, height, startAngle, arcAngle);
	}

	@Override
	public void addMarker(float x, float y, String str) {
		Font mf = Font.decode(str);
		GlyphVector gv = mf.createGlyphVector(graphics.getFontRenderContext(), str);
        Rectangle2D rect = gv.getVisualBounds();
        // need to update x, y by half of rectangle width, height
        // to serve as x, y coordinates for drawing a GlyphVector
        x -= rect.getWidth() / 2;
        y += rect.getHeight() / 2;
        
        graphics.drawGlyphVector(gv, x, y);
	}

	@Override
	public void setRenderingHint(P5RenderingHints hintKey, P5RenderingHints hintValue) {
		Key hk = null;
		Object hv = null;
		if (hintKey.equals(P5RenderingHints.KEY_RENDERING)) {
			hk = RenderingHints.KEY_RENDERING;
		}
		if (hintKey.equals(P5RenderingHints.KEY_ANTIALIASING)) {
			hk = RenderingHints.KEY_ANTIALIASING;
		}
		if (hintValue.equals(P5RenderingHints.VALUE_RENDER_QUALITY)) {
			hv = RenderingHints.VALUE_RENDER_QUALITY;
		}
		if (hintValue.equals(P5RenderingHints.VALUE_ANTIALIAS_ON)) {
			hv = RenderingHints.VALUE_ANTIALIAS_ON;
		}
		graphics.setRenderingHint(hk, hv);
	}

}
