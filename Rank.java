import java.io.Serializable;
//import java.net.URL;

public class Rank implements Serializable{
	public String name;
	public  int score;
	//private int rank;
	private static final long serialVersionUID = 1209440797802235581L;
	public Rank(){
		this("", 0);
	}

	public Rank(String name, int score){
		this.name = name;
		this.score = score;
	}

	public Rank(Rank r){
		this.name = String.copyValueOf(r.name.toCharArray());
		this.score = r.score;
	}



	public void setName(String name){
		this.name = name;
	}

	public void setScore(int score){
		this.score = score;
	}

	public String getName(){
		return name;
	}

	public int getScore(){
		return score;
	}
}