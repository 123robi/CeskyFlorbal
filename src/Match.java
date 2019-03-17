import java.util.List;

public class Match {
	private String home, away, date, kolo, score;
	private List<Penalty> penalties;

	public Match(String kolo, String home, String away) {
		this.kolo = kolo;
		this.home = home;
		this.away = away;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getAway() {
		return away;
	}

	public void setAway(String away) {
		this.away = away;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getKolo() {
		return kolo;
	}

	public void setKolo(String kolo) {
		this.kolo = kolo;
	}

	public List<Penalty> getPenalties() {
		return penalties;
	}

	public void setPenalties(List<Penalty> penalties) {
		this.penalties = penalties;
	}
	
	public void addPenalty(Penalty penalty) {
		this.penalties.add(penalty);	}
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	
}
