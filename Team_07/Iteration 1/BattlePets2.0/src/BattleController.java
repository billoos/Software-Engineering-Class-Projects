public class BattleController {

    private Battle currentBattle;
    private Pet[] participants;
    private FightController fightController;
    private int currentFightNumber = 1;

    public BattleController(Pet[] participants, int fightsPerBattle) {
        this.participants = participants;
        currentBattle = new Battle(fightsPerBattle);
        fightController = new FightController(participants);
    }

    public Battle runBattle() {
        IOManager.promptLine("BATTLE START!!!!!!!!!!!!!");
        while(isConcluded() == false) //While the battle is still going, keep doing rounds.
        {
            IOManager.promptLine("Fight number: " + currentFightNumber);
            currentBattle.addFight(fightController.runFight());
            currentFightNumber++;
        }
        currentBattle.setWinner(findWinner());
        if(currentBattle.getWinner() != null) {
            IOManager.promptLine("Player who won the battle: " + currentBattle.getWinner().getPlayerName());
        } else {
            IOManager.promptLine("The Battle was a tie!");
        }
        IOManager.endLine();
        return currentBattle;
    }

    private boolean isConcluded() {
        boolean concluded = false;
        Fight[] fights = currentBattle.getFights();

        //If the last fight slot is filled
        if(fights[fights.length - 1] != null){
            concluded = true;
        }
        return concluded;
    }

    //Returns null in the case of a tie
    private Pet findWinner() {
        int points;
        int maxPoints = 0;
        Pet winner = null;

        for(Pet participant:participants)
        {
            points = 0;

            for(Fight fight:currentBattle.getFights())
            {
                if(fight.getWinner() == participant)
                {
                    points++;
                }
            }

            if(points > maxPoints)
            {
                maxPoints = points;
                winner = participant;
            }
            else if(points == maxPoints) //There is more than one pet with the same maximum points. Tie
            {
                winner = null;
            }
        }
        return winner;
    }
}
