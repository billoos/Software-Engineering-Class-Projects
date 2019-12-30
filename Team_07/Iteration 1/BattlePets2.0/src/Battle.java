public class Battle {

    private Fight[] fights;
    private Pet winner;

    Battle(int numberOfFights)
    {
        if (numberOfFights < 0)
            throw new IllegalArgumentException("A number of fights less than zero reached Battle.java");

        fights = new Fight[numberOfFights];
    }

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

    public void setWinner(Pet winner) {
        this.winner = winner;
    }

    public Fight[] getFights() {
        return fights;
    }

    public Pet getWinner() {
        return winner;
    }
}
