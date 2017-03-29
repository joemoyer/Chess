import java.awt.Image;

public class Animation {
	Image image;
	int x, y, speed;
	
		public Animation (Image image, int x, int y, int speed) {
			this.image = image;
			this.x = x;
			this.y = y;
			this.speed = speed;
		}
		
		public void update() {
			y -= speed;
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
