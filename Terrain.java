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

		// initialize grass seeds
		for (int i = 0; i < dim*dim*0.0045; i++) {
			done = false;
			while (!done) {
				int tempx = (int)(Math.random()*dim);
				int tempy = (int)(Math.random()*dim);
				// System.out.println(tempx + " " + tempy);
				if (this.land[tempx][tempy] == 0) {
					done = true;
					land[tempx][tempy] = 1;
				}
			}
		}

		// initialize sand seeds
		for (int i = 0; i < dim*dim*0.001; i++) {
			done = false;
			while (!done) {
				int tempx = (int)(Math.random()*dim);
				int tempy = (int)(Math.random()*dim);
				if (land[tempx][tempy] == 0) {
					done = true;
					land[tempx][tempy] = 2;
				}
			}
		}

		// initialize forest seeds
		for (int i = 0; i < dim*dim*0.0015; i++) {
			done = false;
			while (!done) {
				int tempx = (int)(Math.random()*dim);
				int tempy = (int)(Math.random()*dim);
				if (land[tempx][tempy] == 0) {
					done = true;
					land[tempx][tempy] = 3;
				}
			}
		}

		// initialize swamp seeds
		for (int i = 0; i < dim*dim*0.0005; i++) {
			done = false;
			while (!done) {
				int tempx = (int)(Math.random()*dim);
				int tempy = (int)(Math.random()*dim);
				if (land[tempx][tempy] == 0) {
					done = true;
					land[tempx][tempy] = 4;
				}
			}
		}

		// initialize sand seeds
		for (int i = 0; i < dim*dim*0.002; i++) {
			done = false;
			while (!done) {
				int tempx = (int)(Math.random()*dim);
				int tempy = (int)(Math.random()*dim);
				if (land[tempx][tempy] == 0) {
					done = true;
					land[tempx][tempy] = 5;
				}
			}
		}

		// initialize lava seeds
		for (int i = 0; i < dim*dim*0.0005; i++) {
			done = false;
			while (!done) {
				int tempx = (int)(Math.random()*dim);
				int tempy = (int)(Math.random()*dim);
				if (land[tempx][tempy] == 0) {
					done = true;
					land[tempx][tempy] = 6;
				}
			}
		}


		done = false;
		while (!done) {
			done = true;
			int[][] nextLand = this.land;
			for (int i = 0; i < dim; i++) {
				for (int j = 0; j < dim; j++) {
					if (land[i][j] == 0) {
						ArrayList<Integer> vals = new ArrayList<>();
						int neighbors = 0;
						done = false;
						for (int x = -1; x < 2; x++) {
							for (int y = -1; y < 2; y++) {
								try {
									int temp = land[i+x][j+y];
									if (temp != 0) {
										neighbors++;
										vals.add(temp);
									}
								} catch (Exception e) {}
							}
						}

						if (neighbors != 0) nextLand[i][j] = vals.get((int)Math.floor(Math.random()*neighbors));

					}
				}
			}
			this.land = nextLand;
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










