package Controllers;

import DataStructures.Fight;
import DataStructures.Round;
import Participants.Playable;
import Utilities.IOManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

class FightController {

    private Playable[] participants;
    private RoundController roundController;

    /**
     * constructor to instantiate participants and round controller
     * @param participants The pets participating in the DataStructures.Fight
     */
    FightController(Playable[] participants) {
        this.participants = participants;
        roundController = new RoundController(participants);
    }

    /**
     * logic to control the fight and catch exceptions
     * @return the current fight state
     */
    Fight runFight() {
        int currentRoundNumber = 1;
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
        catch(InterruptedException e) {
            IOManager.promptLine(":(");
        }

        while(!isConcluded()) //While the fight is still going, keep doing rounds.
        {
            IOManager.promptLine("Round number: " + currentRoundNumber);
            currentFight.addRound(roundController.runRound());
            currentRoundNumber++;
        }
        currentFight.setWinners(findWinners(currentFight));
        IOManager.promptWithPlayables("Player(s) who won the fight:",currentFight.getWinners());

        for(Playable participant:participants)
        {
            participant.reset();
        }
        roundController.reset();
        return currentFight;
    }
    /**
     * determines if the fight is over
     * @return whether the fight is concluded
     */
    private boolean isConcluded() {
        int numberAsleep = 0;
        boolean concluded = false;

        for (Playable participant:participants)
        {
            if (!participant.isAwake())
            {
                numberAsleep++;
            }
        }

        if(numberAsleep >= participants.length - 1) {
            concluded = true;
        }

        return concluded;
    }

    /**
     * logic to find the winner of the fight
     * @param currentFight the current fight to be checked for winners
     * @return winners of the fight
     */
    private List<Playable> findWinners(Fight currentFight) {
        Round finalRound = currentFight.getRoundList().get(currentFight.getRoundList().size() - 1);
        List<Playable> potentialWinners = finalRound.getFinalAwakePets();
        List<Playable> winners = new ArrayList<>();
        double highestHP = -Double.MAX_VALUE;

        //Finds the highest HP among all potential winners.
        for(Playable potentialWinner: potentialWinners) {
            if (potentialWinner.getCurrentHp() > highestHP) {
                highestHP = potentialWinner.getCurrentHp();
            }
        }

        //Loop through again and add any with that max hp to the winner list.
        for(Playable potentialWinner: potentialWinners) {
            if (potentialWinner.getCurrentHp() == highestHP) {
                winners.add(potentialWinner);
            }
        }

        return winners;
    }
}
