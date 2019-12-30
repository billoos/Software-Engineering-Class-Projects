package DataStructures;

import Participants.Playable;
import java.util.List;

public class Battle {

    private Fight[] fights;
    private List<Playable> winners;


    /**
     * creates a new fight with a set amount of fights and catches illegal arguments
     * @param numberOfFights - number of fights per battle
     */
    public Battle(int numberOfFights)
    {
        if (numberOfFights < 0)
            throw new IllegalArgumentException("A number of fights less than zero reached DataStructures.Battle.java");

        fights = new Fight[numberOfFights];
    }

    /**
     * adds a fight to the battle
     * @param fight - DataStructures.Fight to be added to the list
     */
    public void addFight(Fight fight) {
        int nextIndex = 0;
        while(nextIndex < fights.length)
        {
            if(fights[nextIndex] == null){
                break;
            }
            nextIndex++;
        }
        fights[nextIndex] = fight;
    }

    public void setWinners(List<Playable> winners) {
        this.winners = winners;
    }

    public Fight[] getFights() {
        return fights;
    }

    public List<Playable> getWinners() {
        return winners;
    }
}
