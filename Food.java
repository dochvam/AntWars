public class Food {

	int[][] foodSites;
	int dim;

	public Food(int[][] land) {
		this.dim = land.length;
		this.foodSites = new int[dim][dim];
		initFood(land);
	}

	private void initFood(int[][] land) {

		int totalFood = 0;
		int x = (int)(Math.random() * dim);
		int y = (int)(Math.random() * dim);

		while (totalFood < 15) {
			x++;
			if (x == dim) {
				x = x % dim;
				y++;
			}
			if (y == dim) y = y % dim;

			if (land[x][y] == 1) {
				if (Math.random() < 0.00001) {
					foodSites[x][y] = 1;
					totalFood++;
				}
			} else if (land[x][y] == 2) {
				if (Math.random() < 0.000002) {
					foodSites[x][y] = 1;
					totalFood++;
				}
			} else if (land[x][y] == 3) {
				if (Math.random() < 0.00002) {
					foodSites[x][y] = 1;
					totalFood++;
				}		
			} else if (land[x][y] == 4) {
				if (Math.random() < 0.00001) {
					foodSites[x][y] = 1;
					totalFood++;
				}				
			}
		}

	}

	public void display() {
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				if (foodSites[i][j] == 1) {
					StdDraw.setPenColor(0,0,0);
					StdDraw.filledCircle(i+0.5, j+0.5, dim/100);
				}
			}
		}
	}
}