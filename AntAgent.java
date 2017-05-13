import java.util.*;

public class AntAgent {

	protected double mutateChance;
	protected int health;
	protected int lifetime;
	protected int rCounter;
	protected int rTime;
	protected int rAge;
	protected int actCount;
	protected double[][] genome;
	protected boolean shoot;
	protected boolean alive = true;

	public AntAgent(double[][] actMatrix) {
		this.mutateChance = 0.1;
		this.health = 100;
		this.lifetime = 0;
		this.rCounter = 0;
		this.rTime = 30;
		this.actCount = 4;
		this.shoot = false;
		if (shoot) this.actCount++;
		if (actMatrix == null) {
			Random rnd = new Random();
			this.genome = new double[sense().length][actCount];
			for (int i = 0; i < this.genome.length; i++) {
				for (int j = 0; j < this.genome[0].length; j++) {
					this.genome[i][j] = rnd.nextGaussian() * 0.3;
				}
			}
		} else this.genome = actMatrix;

		if (actCount != this.genome.length) throw new Exception("Incorrect dim for weight matrix");
	}

	public void live() {
		if (this.health <= 0) this.die();
		this.act();
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

	}
	public void act(int action) {

	}
	public int[] see() {
		int[] seeVal = new int[55];
		return seeVal;
	}
	public int hear() {
		int hearCount = 0;
		return hearCount;
	}
	public int smell() {
		int smellCount = 0;
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
	public double[] action(ArrayList<Integer> senses) {
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
		return AntAgent(childGenes);
	}

	public static double[][] mutateGenome(double[][] genome) {
		Random rnd = new Random();
		for (int i = 0; i < genome.length; i++) {
			for (int j = 0; j < genome[0].length; j++) {
				if (Math.random() <= this.mutateChance) {
					genome[i][j] = rnd.nextGaussian() * Math.abs(0.08*genome[i][j]) + genome[i][j];
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