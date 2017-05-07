public class Ant {

	protected double x;
	protected double y;
	protected double speed;
	protected double dir;
	final int id = (int)(Math.random() * 100000);
	final int dim;
	protected int[] color = new int[3];

	public Ant(double x, double y, double speed, double dir, int dim) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.dir = dir;
		this.color[0] = 50;
		this.color[1] = 50;
		this.color[2] = 255;
		this.dim = dim;
	}

	public void display() {
		StdDraw.setPenColor(color[0],color[1],color[2]);
		StdDraw.filledRectangle(x+1, y+1, 1, 1);
	}

	public void update() {
		double tempx = this.speed * Math.cos(this.dir);
		double tempy = this.speed * Math.sin(this.dir);

		if (this.y+tempy < 0 || this.y+tempy > dim) {
			this.dir = 2*Math.PI - this.dir;
			tempx = this.speed * Math.cos(this.dir);
			tempy = this.speed * Math.sin(this.dir);
	    }

	    if (this.x+tempx < 0 || this.x+tempx > dim) {
			this.dir = (Math.PI - this.dir)%(2*Math.PI);
			tempx = this.speed * Math.cos(this.dir);
			tempy = this.speed * Math.sin(this.dir);
	    }
	
		this.x += tempx;
		this.y += tempy;

	}

	public static void main(String[] args) {
		int dim = 100;

		StdDraw.setCanvasSize(800,800);
		StdDraw.setYscale(-1,dim);
		StdDraw.setXscale(-1,dim);
		StdDraw.enableDoubleBuffering();

		Ant tant = new Ant(10,10,1,0.5,dim);
		Ant uant = new Ant(10.5,11,1,1,dim);
		tant.display();
		uant.display();

		for (int i = 0; i < 100; i++) {
			StdDraw.clear();
			tant.update();
			uant.update();
			tant.display();
			uant.display();
			StdDraw.show();
			StdDraw.pause(5);
		}
	}
}