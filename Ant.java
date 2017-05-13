import java.util.*;

public class Ant {

	protected int x;
	protected int y;
	protected int dir; //1=up,2=right,3=down,4=left
	final double id = Math.random();
	protected Terrain terr;

	// sensory input
	protected int[][] fieldOfView = new int[11][11];
	protected int[][] senseFood = new int[11][11];
	protected int[][] sensePath = new int[11][11];
	protected ArrayList<Point> memory = new ArrayList<>();


	public Ant(int x, int y, Terrain t) {
		this.x = x;
		this.y = y;
		this.dir = (int)(Math.random() * 4) + 1;
		this.terr = t;
	}

	public void display() {
		this.terr.display();
		StdDraw.setPenColor(110,0,255);
		if (this.dir % 2 != 0)
			StdDraw.filledRectangle(x-1, y-2.5, 2, 5);
		else
			StdDraw.filledRectangle(x-2.5, y-1, 5, 2);
	}

	public void step() {
		if (this.dir == 1) y++;
		else if (this.dir == 2) x++;
		else if (this.dir == 3) y--;
		else if (this.dir == 4) x--;

		if (Math.random() > 0.9) chooseDir();

		this.terr.paths[x][y] = this.dir;

		System.out.println(scoreView());
	}

	public void turnRight() {
		if (this.dir == 4) this.dir = 1;
		else this.dir++;
		sense();
	}
	public void turnLeft() {
		if (this.dir == 1) this.dir = 4;
		else this.dir--;
		sense();
	}

	public void sense() {
		//update senses
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				if (this.dir % 2 != 0) {
					fieldOfView[i][j] = this.terr.land[x-5+i][y+j];
					senseFood[i][j] = this.terr.food.foodSites[this.x-5+i][this.y+j];
					sensePath[i][j] = this.terr.paths[this.x-5+i][this.y+j];
				}
				else {
					fieldOfView[i][j] = this.terr.land[x+i][y-5+j];
					senseFood[i][j] = this.terr.food.foodSites[this.x+i][this.y-5+j];
					sensePath[i][j] = this.terr.paths[this.x+i][this.y-5+j];
				}
			}
		}
	}

	public void chooseDir() {
		sense();
		int straightView = scoreView();
		turnRight();
		int rightView = scoreView();
		turnLeft(); turnLeft();
		int leftView = scoreView();
		turnRight();

		if (straightView > leftView) {
			if (rightView > straightView) {
				turnRight();
			}
		} else {
			if (rightView > leftView) {
				turnRight();
			}
			else turnLeft();
		}
	}

	public int scoreView() {
		int score = 0;

		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				try {
					if (fieldOfView[i][j] == 1) score += 1;
					else if (fieldOfView[i][j] == 3) score += 1; 
					score += 5*senseFood[i][j];
				} catch (Exception e) {}
			}
		}
		return score;
	}

	public static void main(String[] args) {

	}
}















