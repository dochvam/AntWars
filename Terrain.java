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

		// initialize lava
		PriorityQueue<Point> lavaseeds = new PriorityQueue<>();
		int numLavaLakes = (int)(Math.random() * 3) + 1;
		for (int i = 0; i < numLavaLakes; i++) {
			Point p = new Point((int)(Math.random()*dim-1),(int)(Math.random()*dim-1),0);
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

		done = false;
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
					if (totalLava > dim*dim/10) done = true;
				}
			}
		}
		// initializing water
		PriorityQueue<Point> lakeseeds = new PriorityQueue<>();
		int numLakes = (int)(Math.random() * 3) + 2;
		for (int i = 0; i < numLakes; i++) {
			Point p = new Point((int)(Math.random()*dim-1),(int)(Math.random()*dim-1),0);
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
		while (totalLake < dim*dim / 7 && lakeseeds.size() > 0) {
			Point temp = lakeseeds.poll();
			this.land[temp.x][temp.y] = 5;
			totalLake++;

			try {
				int dx = (int)(Math.random() * 3) - 1;
				int dy = (int)(Math.random() * 3) - 1;
				int val = land[temp.x + dx][temp.y + dy];
				// if (val == 1) {
					lakeseeds.add(new Point(temp.x + dx, temp.y + dy, temp.depth + 1));
				// }
			} catch (Exception e) {}
		}

		done = false;
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
					if (totalLake > dim*dim/5) done = true;
				}
			}
		}


		System.out.println(totalLava + " " + lavaseeds.size());
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

	public static void main(String[] args) {
		int dim = 100;

		StdDraw.setCanvasSize(1200,800);
		StdDraw.setYscale(-1,dim + 2);
		StdDraw.setXscale(-1,dim*1.5 + 2);
		StdDraw.enableDoubleBuffering();

		Terrain terr = new Terrain(dim);
		terr.display();

		// for (int i = 0; i < 100; i++) {
		// 	for (int j = 0; j < 100; j++) {
		// 		System.out.print(terr.land[i][j]);
		// 	}
		// 	System.out.println("");
		// }
	}

}










