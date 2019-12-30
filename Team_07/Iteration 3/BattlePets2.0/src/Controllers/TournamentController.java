package Controllers;

import DataStructures.Battle;
import DataStructures.Tournament;
import DataStructures.TournamentNode;
import Participants.Playable;
import Utilities.IOManager;

import java.util.Iterator;
import java.util.List;

class TournamentController {

    private BattleController battleController;
    private int participantsPerBattle;
    int DEFAULT_WINNER_INDEX = 0;

    /**
     * TournamentController(int participantsPerBattle, int fightsPerBattle)
     * constructor for tournament controller that sets number of participants in a battle and creates a battleController
     * @param participantsPerBattle - number of participants specified by user per battle
     * @param fightsPerBattle - number of fights that each battle has
     */
    TournamentController(int participantsPerBattle, int fightsPerBattle) {
        this.participantsPerBattle = participantsPerBattle;
        battleController = new BattleController(fightsPerBattle);
    }

    /**
     * runTournament(Playable[] participants)
     * runs a tournament
     * iterates through tournament nodes to create the tournament structure
     * @param participants - list of playable participants
     * @return - tournament - after tournament logic of creating bracket is done returns that such tournament
     */
    Tournament runTournament(Playable[] participants)
    {
        Tournament tournament = new Tournament(participants.length, participantsPerBattle);
        Iterator iterator = tournament.iterator();
        TournamentNode currentNode;
        int currentIndex = 0;

        while(iterator.hasNext())
        {
            currentNode = (TournamentNode)iterator.next();
            //If the node is a leaf it is representing 1 participant. So inject,
            //else there are multiple participants here and a battle decides the winner
            if(currentNode.isLeaf()) {
                Playable toAdd = participants[currentIndex++];
                injectParticipant(currentNode, toAdd);
            }
            else {
                conductBattle(currentNode, tournament);
            }
        }
        return tournament;
    }

    /**
     * conductBattle(TournamentNode currentNode, Tournament tournament)
     * Conducts battle through tournaments prompts user who has won a battle in a tournament and then the winner of the
     * tournament
     * @param currentNode - tournament node that holds children tournament data
     * @param tournament - holds information of all nodes about the tournament
     */
    private void conductBattle(TournamentNode currentNode, Tournament tournament){
        Battle battle;
        Playable battleWinner;
        List<Playable> battleParticipants = currentNode.getParticipants();

        if (battleParticipants.size() > 1) {

            IOManager.promptWithPlayables("A tournament battle is conducted between the following players:", battleParticipants);

            battle = battleController.runBattle(battleParticipants.toArray(new Playable[0]));
            battleWinner = battle.getWinners().get(DEFAULT_WINNER_INDEX);
            currentNode.assignWinner(battleWinner);

            if (currentNode.getParent() != null) //It is not the final round
            {
                IOManager.promptLine(battleWinner.getPlayerName() + " has qualified for the next tier of the tournament!");
                //The battle was a tie
                if(battle.getWinners().size() > 1)
                    IOManager.promptLine("Unfortunately for those that tied for the win, they do not progress to the next round");
            }
            else { //It is the final round of the tournament
                tournament.setWinners(battle.getWinners());
                IOManager.promptWithPlayables("Player(s) who won the tournament:", battle.getWinners());
            }
            IOManager.endLine();
        } else {
            throw new IllegalStateException("A tournament Node was encountered that was not a leaf which had " + battleParticipants.size() + " participants");
        }
    }

    /**
     * injectParticipant(TournamentNode currentNode, Playable toAdd)
     * adds playables to tournament
     * @param currentNode - current node of a tournament that stores data
     * @param toAdd - playable that is needed to add to the tournament
     */
    private void injectParticipant(TournamentNode currentNode, Playable toAdd) {
        currentNode.addParticipant(toAdd);
        currentNode.assignWinner(toAdd);
        //IOManager.promptLine(toAdd.getPlayerName() + " is injected into the tournament");
    }
}
