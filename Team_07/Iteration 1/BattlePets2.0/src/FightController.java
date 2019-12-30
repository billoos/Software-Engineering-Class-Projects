import java.util.concurrent.TimeUnit;

public class FightController {

    private Pet[] participants;
    private RoundController roundController;
    private int currentRoundNumber = 1;

    public FightController(Pet[] participants) {
        this.participants = participants;
        roundController = new RoundController(participants);
    }

    public Fight runFight() {
        Fight currentFight = new Fight();

        try {
            IOManager.promptLine("3...");
            TimeUnit.MILLISECONDS.sleep(750);
            IOManager.promptLine("2...");
            TimeUnit.MILLISECONDS.sleep(750);
            IOManager.promptLine("1...");
            TimeUnit.MILLISECONDS.sleep(750);
            IOManager.promptLine("FIGHT!!!!!!!");
            IOManager.endLine();
            TimeUnit.MILLISECONDS.sleep(750);
        }
        catch(InterruptedException e)
        {
            IOManager.promptLine(":(");
        }

        while(isConcluded() == false) //While the fight is still going, keep doing rounds.
        {
            IOManager.promptLine("Round number: " + currentRoundNumber);
            currentFight.addRound(roundController.runRound());
            currentRoundNumber++;
        }
        currentFight.setWinner(findWinner());

        if(currentFight.getWinner() != null) {
            IOManager.promptLine("Player who won the fight: " + currentFight.getWinner().getPlayerName());
        } else {
            IOManager.promptLine("The Fight was a tie! (Brag to your friends, this is nigh impossible)");
        }
        IOManager.endLine();

        for(Pet participant:participants)
        {
            participant.reset();
        }
        return currentFight;
    }

    private boolean isConcluded() {
        int numberAsleep = 0;
        boolean concluded = false;

        for (Pet participant:participants)
        {
            if (participant.isAwake()  == false)
            {
                numberAsleep++;
            }
        }

        if(numberAsleep >= 1) {
            concluded = true;
        }

        return concluded;
    }

    //Returns null in the case of a tie
    private Pet findWinner() {
        Integer winnerIndex = null;
        double highestHP = -Double.MAX_VALUE;
        int index = 0;
        Pet winner = null;

        for (Pet participant:participants)
        {
            if(participant.getCurrentHp() > highestHP)
            {
                highestHP = participant.getCurrentHp();
                winnerIndex = index;
            }
            index++;
        }

        if(winnerIndex != null)
        {
            winner = participants[winnerIndex];
        }

        return winner;
    }
}
