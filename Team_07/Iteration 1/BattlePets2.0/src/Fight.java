import java.util.ArrayList;

public class Fight {
    private ArrayList<Round> roundList = new ArrayList<Round>();
    private Pet winner;

    Fight() { }

    public void addRound(Round round){
        roundList.add(round);
    }

    public void setWinner(Pet winner) {
        this.winner = winner;
    }

    public Pet getWinner() {
        return winner;
    }
}
