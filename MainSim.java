import java.util.Random;

public class MainSim {

	public static void main(String[] args) throws Exception {
		int dim = 400;
		
		StdDraw.setCanvasSize(800,800);
		StdDraw.setYscale(-1,dim+1);
		StdDraw.setXscale(-1,dim+1);
		StdDraw.enableDoubleBuffering();
		
		Terrain terr = new Terrain(dim);
		
		AntAgent[] ants = new AntAgent[5];
		
		Random rnd = new Random();
		double[][] genome = new double[124][3];
		int i,j,k;
		for (i = 0; i < 5; i++) {
			for (k = 0; k < genome.length; k++) {
				for (j = 0; j < genome[0].length; j++) {
					genome[k][j] = rnd.nextGaussian() * 0.3;
				}
			}
			//ants[i] = new Ant(Math.random()*100, Math.random()*100, Math.random()*5+2, Math.random()*Math.PI,dim);
			ants[i] = new AntAgent(genome, terr, rnd.nextInt(350), rnd.nextInt(350));
		}

		for (i = 0; i < 1000; i++) {
			//StdDraw.clear();
			for (j = 0; j < ants.length; j++) {
				ants[j].live();
				ants[j].display();
			}
			StdDraw.show();
			//StdDraw.pause(100);
		}
	}
}