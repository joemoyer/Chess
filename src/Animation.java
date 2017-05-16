import java.awt.Image;

public class Animation {
	Image image;
	int x, y, speed;
	int value = 0;

	public Animation(Image image, int x, int y, int speed) {
		this.image = image;
		this.x = x;
		this.y = y;
		this.speed = speed;
	}

	public void update() {
		
		switch (Board.display) {
		case Straight:
			
			y-= speed;
			
			break;
			
		case Wobbly:
			
			y -= speed;
			x += 3 * Math.cos((double) y / (double) 40);
			
			break;
			
		case Circular:
			
			value++;
			y -= 1 + 3 * Math.sin((double) value / (double) 40);
			x += 3 * Math.cos((double) value / (double) 40);
			if (value > 10000) {
				value = 0;
			}
			
			break;
			
		default:
			break;
		}
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
