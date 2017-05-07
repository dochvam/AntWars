import java.util.*;

public class Terrain {

	int[][] land;
	int dim;
	double[][] colors = {{270,1,0.8},{320,0.5,0.5},{225,0.5,0.3},{280,0.5,0.2},{130,1,0.5},{0,1,0.5}};

	public Terrain(int dim) {
		this.dim = dim;
		this.land = new int[dim][dim];
		// System.out.println(land.length + " " + land[0].length);
		initLand();
	}

	public void initLand() {
		//types of land:
			// 1 = grass
			// 2 = sand
			// 3 = forest
			// 4 = swamp
			// 5 = ocean
			// 6 = lava
		boolean done = false;

		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				land[i][j] = 1;
			}
		}

		initLava();
		initWater();
		initTrees();
		initSwamp();
		initSand();

	}

	public void initLava() {
		// initialize lava
		PriorityQueue<Point> lavaseeds = new PriorityQueue<>();
		int numLavaLakes = (int)(Math.random() * 2) + 2;
		for (int i = 0; i < numLavaLakes; i++) {
			int quad = i % 4;
			int a = (quad < 2) ? 1:2;
			int b = (quad%2 == 0) ? 1:2;
			Point p = new Point((int)(Math.random()*a*dim/2 -1),(int)(Math.random()*b*dim/2-1),0);
			lavaseeds.add(p);

			for (int j = 0; j < Math.random()*7; j++) {
				try {
					int tx = p.x;
					int ty = p.y;
					double r1 = Math.random();
					double r2 = Math.random();
					if (r1 > 0.66) tx += j;
					else if (r1 > 0.33) tx += j*-1;
					if (r2 > 0.66) ty += j;
					else if (r2 > 0.33) ty = j*-1;
					if (land[tx][ty] == 1) lavaseeds.add(new Point(tx,ty,0));

				} catch (Exception e) {}
			}
		}

		int totalLava = 0;
		while (totalLava < dim*dim / 25 && lavaseeds.size() > 0) {
			Point temp = lavaseeds.poll();
			this.land[temp.x][temp.y] = 6;
			totalLava++;

			try {
				int dx = (int)(Math.random() * 3) - 1;
				int dy = (int)(Math.random() * 3) - 1;
				int val = land[temp.x + dx][temp.y + dy];
				// if (val == 1) {
					lavaseeds.add(new Point(temp.x + dx, temp.y + dy, temp.depth + 1));
				// }
			} catch (Exception e) {}
		}

		boolean done = false;
		while (!done) {
			for (int i = 0; i < dim; i++) {
				for (int j = 0; j < dim; j++) {
					int sum = 0;
					int neighbors = 0;
					for (int x = -1; x < 2; x++) {
						for (int y = -1; y < 2; y++) {
							try {
								if (land[i+x][j+y] == 6) sum++;
								neighbors++;
							} catch (Exception e) {}
						}
					}
					if (sum > 4){
						land[i][j] = 6; 
						totalLava++;
					}
					if (totalLava > dim*dim/8) done = true;
				}
			}
		}
	}

	public void initWater() {
		// initializing water
		PriorityQueue<Point> lakeseeds = new PriorityQueue<>();
		int numLakes = (int)(Math.random() * 5) + 5;
		for (int i = 0; i < numLakes; i++) {
			int quad = i % 4;
			int a = (quad < 2) ? 1:2;
			int b = (quad%2 == 0) ? 1:2;
			Point p = new Point((int)(Math.random()*a*dim/2 -1),(int)(Math.random()*b*dim/2-1),0);
			lakeseeds.add(p);

			for (int j = 0; j < Math.random()*10+10; j++) {
				try {
					int tx = p.x;
					int ty = p.y;
					double r1 = Math.random();
					double r2 = Math.random();
					if (r1 > 0.66) tx += j;
					else if (r1 > 0.33) tx += j*-1;
					if (r2 > 0.66) ty += j;
					else if (r2 > 0.33) ty = j*-1;
					if (land[tx][ty] == 1) lakeseeds.add(new Point(tx,ty,0));

				} catch (Exception e) {}
			}
		}

		int totalLake = 0;
		while (totalLake < dim*dim / 15 && lakeseeds.size() > 0) {
			Point temp = lakeseeds.poll();
			if (this.land[temp.x][temp.y] != 5) totalLake++;
			this.land[temp.x][temp.y] = 5;

			try {
				int dx = (int)(Math.random() * 3) - 1;
				int dy = (int)(Math.random() * 3) - 1;
				int val = land[temp.x + dx][temp.y + dy];
				// if (val == 1) {
				lakeseeds.add(new Point(temp.x + dx, temp.y + dy, temp.depth + 1));
				// }
			} catch (Exception e) {}
		}

		boolean done = false;
		while (!done) {
			for (int i = 0; i < dim; i++) {
				for (int j = 0; j < dim; j++) {
					int sum = 0;
					int neighbors = 0;
					for (int x = -1; x < 2; x++) {
						for (int y = -1; y < 2; y++) {
							try {
								if (land[i+x][j+y] == 5) sum++;
								neighbors++;
							} catch (Exception e) {}
						}
					}
					if (sum > 4){
						land[i][j] = 5; 
						totalLake++;
					}
					if (totalLake > (dim*dim)/10) done = true;
				}
			}
		}
	}


	public void initTrees() {
		// initialize forest
		PriorityQueue<Point> forestqueue = new PriorityQueue<>();
		for (int i = 0; i < Math.random() * 10 + dim/50; i++) {
			int quad = i % 4;
			int a = (quad < 2) ? 1:2;
			int b = (quad%2 == 0) ? 1:2;
			Point p = new Point((int)(Math.random()*a*dim/2 -1),(int)(Math.random()*b*dim/2-1),0);
			forestqueue.add(p);
			int lengthOfLine = (int)(Math.random() * 30 + 7);
			int xdir = (int)(Math.random() * 2);
			if (xdir == 0) xdir--;
			int ydir = (int)(Math.random() * 2);
			if (ydir == 0) ydir--;
			int xory = (int)(Math.random() * 2);
			
			int thisx = p.x;
			int thisy = p.y;
			for (int l = 0; l < lengthOfLine; l++) {
				if (xory == 1) {
					thisx += xdir;
					if (Math.random() > 0.8) thisy += ydir;
				} else {
					thisy += ydir;
					if (Math.random() > 0.8) thisx += xdir;
				}
				try {
					int z = land[thisx][thisy];
					forestqueue.add(new Point(thisx, thisy, 0));
				} catch (Exception e) {}
			}
		}

		int totalWoods = 0;
		while (totalWoods < dim*dim / 15 && forestqueue.size() > 0) {
		// while (forestqueue.size() > 0) {
			Point temp = forestqueue.poll();
			if (land[temp.x][temp.y] == 1) {
				this.land[temp.x][temp.y] = 3;
				totalWoods++;

				try {
					for (int i = 0; i < 2; i++){
						int dx = (int)(Math.random() * 3) - 1;
						int dy = (int)(Math.random() * 3) - 1;
						int val = land[temp.x + dx][temp.y + dy];
						forestqueue.add(new Point(temp.x + dx, temp.y + dy, temp.depth + 1));
					}
				} catch (Exception e) {}
			}
		}

		int[][] nextLand = new int[dim][dim];

		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				nextLand[i][j] = land[i][j];
				int sum = 0;
				int neighbors = 0;
				for (int x = -1; x < 2; x++) {
					for (int y = -1; y < 2; y++) {
						try {
							if (land[i+x][j+y] == 3) sum++;
							neighbors++;
						} catch (Exception e) {}
					}
				}
				if (sum > 2 && land[i][j] == 1){
					nextLand[i][j] = 3; 
					totalWoods++;
				}

			}
		}
		this.land = nextLand;
	}

	public void initSwamp() {
		PriorityQueue<Point> swampqueue = new PriorityQueue<>();
		for (int x = 0; x < dim; x++) {
			for (int y = 0; y < dim; y++) {
				int sum = 0;
				int neighbors = 0;
				for (int i = -1; i < 2; i++) {
					for (int j = -1; j < 2; j++) {
						try {
							if (land[i+x][j+y] == 5) sum++;
							neighbors++;
						} catch (Exception e) {}
					}
				}
				if (land[x][y] == 1 && sum > 0 && Math.random() < 0.02) {
					Point p = new Point(x,y,0);
					swampqueue.add(p);
				}
			}
		}

		int totalWoods = 0;
		while (totalWoods < dim*dim / 15 && swampqueue.size() > 0) {
		// while (swampqueue.size() > 0) {
			Point temp = swampqueue.poll();
			if (land[temp.x][temp.y] == 1) {
				this.land[temp.x][temp.y] = 4;
				totalWoods++;

				try {
					for (int i = 0; i < 2; i++){
						int dx = (int)(Math.random() * 3) - 1;
						int dy = (int)(Math.random() * 3) - 1;
						int val = land[temp.x + dx][temp.y + dy];
						swampqueue.add(new Point(temp.x + dx, temp.y + dy, temp.depth + 1));
					}
				} catch (Exception e) {}
			}
		}

		int[][] nextLand = new int[dim][dim];

		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				nextLand[i][j] = land[i][j];
				int sum = 0;
				int neighbors = 0;
				for (int x = -1; x < 2; x++) {
					for (int y = -1; y < 2; y++) {
						try {
							if (land[i+x][j+y] == 4) sum++;
							neighbors++;
						} catch (Exception e) {}
					}
				}
				if (sum > 2 && land[i][j] == 1){
					nextLand[i][j] = 4; 
					totalWoods++;
				}

			}
		}
		this.land = nextLand;
	}

	public void initSand() {
		PriorityQueue<Point> sandqueue = new PriorityQueue<>();
		for (int x = 0; x < dim; x++) {
			for (int y = 0; y < dim; y++) {
				int sum = 0;
				int neighbors = 0;
				for (int i = -1; i < 2; i++) {
					for (int j = -1; j < 2; j++) {
						try {
							if (land[i+x][j+y] == 5) sum++;
							neighbors++;
						} catch (Exception e) {}
					}
				}
				if (land[x][y] == 1 && sum > 0) {
					Point p = new Point(x,y,0);
					sandqueue.add(p);
				}
			}
		}
		sandqueue.add(new Point((int)(Math.random() * dim), (int)(Math.random() * dim), -30));

		int totalSand = 0;
		while (totalSand < dim*dim / 15 && sandqueue.size() > 0) {
		// while (sandqueue.size() > 0) {
			Point temp = sandqueue.poll();
			if (land[temp.x][temp.y] == 1 && temp.depth < 3) {
				this.land[temp.x][temp.y] = 2;
				totalSand++;

				try {
					for (int i = 0; i < 5; i++){
						int dx = (int)(Math.random() * 3) - 1;
						int dy = (int)(Math.random() * 3) - 1;
						int val = land[temp.x + dx][temp.y + dy];
						sandqueue.add(new Point(temp.x + dx, temp.y + dy, temp.depth + 1));
					}
				} catch (Exception e) {}
			}
		}
	}

	public void display() {
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				int[] rgb = getRGB((int)colors[land[i][j]-1][0], (float)colors[land[i][j]-1][1], (float)colors[land[i][j]-1][2] );

				StdDraw.setPenColor((int)rgb[0],(int)rgb[1], (int)rgb[2]);
				StdDraw.filledRectangle(i+0.5, j+0.5, 0.5, 0.5);
			}
		}
		StdDraw.show();
	}

	public int[] getRGB(int h, float s, float b){

		int[] returnable = new int[3];

		int[] x = {(int)(255*(s)), (int)(255*(s)), (int)(255*(s))};

		float t1; float t2;
		if (s == 0) return x;

		if (b < 0.5) t1 = b * (1+s);
		else t1 = (b+s) - (b*s);

		t2 = 2*b - t1;

		float hue = ((float)h / 360);


		float tr, tb, tg;

		tr = (float)((hue + 0.333) % 1);
		tb = (float)((hue));
		tg = (float)((hue - 0.333 + 1) % 1);

		x[0] = (int)(255*tr);
		x[1] = (int)(255*tb);
		x[2] = (int)(255*tg);
		// return x;

		//set Red
		if (6*tr < 1) returnable[0] = (int) (255 * ((t2 + (t1-t2) * 6 * tr) ));
		else if (2*tr<1) returnable[0] = (int) (255 * ((t1) ));
		else if (3*tr<2) returnable[0] = (int) (255 * ((t2 + (t1-t2) * (0.666 - tr) * 6) ));
		else returnable[0] = (int) (255 * (t2));

		//set Green
		if (6*tg < 1) returnable[1] = (int) (255 * ((t2 + (t1-t2) * 6 * tg) ));
		else if (2*tg<1) returnable[1] = (int) (255 * ((t1)));
		else if (3*tg<2) returnable[1] = (int) (255 * ((t2 + (t1-t2) * (0.666 - tg) * 6)));
		else returnable[1] = (int) (255 * (t2));

		//set Blue
		if (6*tb < 1) returnable[2] = (int) (255 * ((t2 + (t1-t2) * 6 * tb)));
		else if (2*tb<1) returnable[2] = (int) (255 * ((t1)));
		else if (3*tb<2) returnable[2] = (int) (255 * ((t2 + (t1-t2) * (0.666 - tb) * 6)));
		else returnable[2] = (int) (255 * (t2));

		return returnable;
	}

	public int getAreaOf(int type) {
		int sum = 0;
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				if (land[i][j] == type) sum++;
			}
		}
		return sum;
	}

	public static void main(String[] args) {
		int dim = 400;

		StdDraw.setCanvasSize(1200,800);
		StdDraw.setYscale(-1,dim + 2);
		StdDraw.setXscale(-1,dim*1.5 + 2);
		StdDraw.enableDoubleBuffering();

		Terrain terr = new Terrain(dim);
		terr.display();

		for (int i = 0; i < 7; i++) {
			int x = terr.getAreaOf(i);
			System.out.println(i + ": " +100*x/(dim*dim));
		}


		// for (int i = 0; i < 100; i++) {
		// 	for (int j = 0; j < 100; j++) {
		// 		System.out.print(terr.land[i][j]);
		// 	}
		// 	System.out.println("");
		// }
	}

}










