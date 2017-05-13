public class Colony {
	ArrayList<Ant> members;
	int x;
	int y;

	public Colony(x,y) {
		this.x = x;
		this.y = y;
		this.members = new ArrayList<>();
	}

	public spawn() {
		members.add(new Ant(x,y));
	}
}