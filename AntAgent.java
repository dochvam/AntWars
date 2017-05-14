import java.util.*;

public class AntAgent {

	protected double mutateChance;
	protected double mutateVal;
	protected int health;
	protected int lifetime;
	protected int rCounter;
	protected int rTime;
	protected int rAge;
	protected int actCount;
	protected double[][] genome;
	protected boolean alive = true;
	protected int x;
	protected int y;
	protected int dir;
	final double id = Math.random();
	protected Terrain terr;

	public AntAgent(double[][] actMatrix, int x, int y, int dir, Terrain t) {
		this.mutateChance = 0.1;
		this.mutateVal = 0.08;
		this.health = 100;
		this.lifetime = 0;
		this.rCounter = 0;
		this.rTime = 30;
		//DOUBLE CHECK
		this.actCount = 3;
		this.terr = t;
		if (actMatrix == null) {
			Random rnd = new Random();
			this.genome = new double[sense().size()][actCount];
			for (int i = 0; i < this.genome.length; i++) {
				for (int j = 0; j < this.genome[0].length; j++) {
					this.genome[i][j] = rnd.nextGaussian() * 0.3;
				}
			}
		} else this.genome = actMatrix;

		if (actCount != this.genome.length) throw new Exception("Incorrect dim for weight matrix");
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
		switch(this.dir){
			case 1:
				y++;
				break;
			case 2:
				x++;
				break;
			case 3:
				y--;
				break;
			case 4:
				x--;
				break;
		}
		this.terr.paths[x][y] = this.dir;

	}
	public void turnRight() {
		if (this.dir == 4) this.dir = 1;
		else this.dir++;
	}
	public void turnLeft() {
		if (this.dir == 1) this.dir = 4;
		else this.dir--;
	}

	public void live() {
		if (this.health <= 0) this.die();
		this.act(this.chooseAction(this.actions(this.sense())));
		this.display();
		if (this.rCounter==this.rTime && this.lifetime >= this.rAge) {
			this.reproduce();
		}
		this.placeLine();
		this.age();
	}

	public void die() {
		this.alive = false;
	}
	public void age() {
		this.lifetime++;
		this.rCounter++;
		this.rCounter = this.rCounter % this.rTime;
	}
	public void placeLine() {

	}
	public void chooseAction(double[] actVector) {
		maxVal = Collections.max(actVector);
		return Arrays.asList(actVector).indexOf(maxVal);

	}
	public void act(int action) {
		switch(action){
			case 1:
				this.turnLeft();
				break;
			case 2:
				this.turnRight();
				break;
			case 3:
				break;
		}
		this.step()
	}
	public int[] see() {
		int[] seeVal = new int[121];
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				if (this.dir % 2 != 0) {
					seeVal[i*11 + j] = this.terr.land[x-5+i][y+j];
				}
				else {
					seeVal[i*11 + j] = this.terr.land[x+i][y-5+j];
				}
			}
		}
		return seeVal;
	}
	public int hear() {
		int hearCount = 0;
		// not sure how to detect other ants atm
		return hearCount;
	}
	public int smell() {
		int smellCount = 0;
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				if (this.terr.food.foodSites[x-5+i][y-5+j] == 1)
					smellCount++;
			}
		}
		return smellCount;
	}
	public int senseLine() {
		int isOnLineVal = 0;
		return isOnLineVal;
	}
	public ArrayList<Integer> sense() {
		ArrayList<Integer> senses = new ArrayList<>();
		int[] s = see();
		for (int i : s) senses.add(i);
		senses.add(hear());
		senses.add(smell());
		senses.add(senseLine());
		return senses;
	}
	public double[] actions(ArrayList<Integer> senses) {
		double[] s = new double[senses.size()];
		for (int i = 0; i < senses.size(); i++) s[i] = (double)senses.get(i);
		
		double[] actVector = multMatrixVector(s, this.genome);

		for (int i = 0; i < actVector.length; i++) {
			actVector[i] = 1/(1+Math.pow(Math.E, (-1 * actVector[i])));
		}
		return actVector;
	}
	public AntAgent reproduce() {
		double[][] childGenes = mutateGenome(this.genome);
		return new AntAgent(childGenes, this.terr);
	}

	public double[][] mutateGenome(double[][] genome) {
		Random rnd = new Random();
		for (int i = 0; i < genome.length; i++) {
			for (int j = 0; j < genome[0].length; j++) {
				if (Math.random() <= this.mutateChance) {
					genome[i][j] = rnd.nextGaussian() * Math.abs(this.mutateVal*genome[i][j]) + genome[i][j];
				}
			}
		}
		return genome;
	}

	public static double[] multMatrixVector(double[] v, double[][] m) {
		int rows_v = 1;
		int cols_v = v.length;
		int rows_m = m.length;
		int cols_m = m[0].length;

		if (cols_v != rows_m) {
			System.out.println("Cannot multiply the matrices; incorrect dimensions");
			return null;
		}

		// create the result matrix
		double[] ans = new double[cols_m];

		for (int i = 0; i < rows_v; i++) {
			for (int j = 0; j < cols_m; j++) {
				for (int k = 0; k < cols_v; k++) {
					ans[j] += v[k] * m[k][j];
				}
			}
		}
		return ans;
	}

	public static void main(String[] args) {
		double[][] m = {{1,2},{3,4},{5,6}};
		double[] v = {0,4,5};
		double[] a = multMatrixVector(v,m);
		for (int i = 0; i < a.length; i++) {
			System.out.println(a[i]);
		}
	}
}
