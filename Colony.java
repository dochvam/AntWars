import java.util.ArrayList;

public class Colony {
	ArrayList<AntAgent> members;
	int x;
	int y;

	public Colony(int x,int y) {
		this.x = x;
		this.y = y;
		this.members = new ArrayList<>();
	}

	public void spawn() {
		members.add(new AntAgent(null,Terrain t,150,150));
	}
}