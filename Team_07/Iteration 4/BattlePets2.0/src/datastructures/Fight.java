package datastructures;

import participants.Playable;

import java.util.ArrayList;
import java.util.List;

public class Fight {
    private List<Round> roundList = new ArrayList<Round>();
    private List<Playable> winners;

    /**
     * default constructor
     */
    public Fight() { }

    /**
     * adds a round to the list
     * @param round The round to be added to the list
     */
    public void addRound(Round round){
        roundList.add(round);
    }

    public void setWinners(List<Playable> winners) {
        this.winners = winners;
    }

    public List<Playable> getWinners() {
        return winners;
    }

    public List<Round> getRoundList() {
        return roundList;
    }
}
