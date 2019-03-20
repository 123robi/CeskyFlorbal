
public class Penalty {
	String time, player, team;
	boolean isGoal;
	String scoreBefore;

	public Penalty(String time, String player, String team, boolean isGoal) {
		this.time = time;
		this.player = player;
		this.team = team;
		this.isGoal = isGoal;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String name) {
		this.player = name;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public boolean isGoal() {
		return isGoal;
	}

	public void setGoal(boolean isGoal) {
		this.isGoal = isGoal;
	}

	public String getScoreBefore() {
		return scoreBefore;
	}

	public void setScoreBefore(String scoreBefore) {
		this.scoreBefore = scoreBefore;
	}
	


	
	
	
	
}
