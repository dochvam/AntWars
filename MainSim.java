public class MainSim {

	public static void main(String[] args) {
		int dim = 100;

		StdDraw.setCanvasSize(800,800);
		StdDraw.setYscale(-1,dim+1);
		StdDraw.setXscale(-1,dim+1);
		StdDraw.enableDoubleBuffering();

		Ant[] ants = new Ant[20];

		for (int i = 0; i < 20; i++) {
			ants[i] = new Ant(Math.random()*100, Math.random()*100, Math.random()*5+2, Math.random()*Math.PI,dim);
		}

		for (int i = 0; i < 500; i++) {
			StdDraw.clear();
			for (int j = 0; j < ants.length; j++) {
				ants[j].update();
				ants[j].display();
			}
			StdDraw.show();
			StdDraw.pause(100);
		}
	}
}