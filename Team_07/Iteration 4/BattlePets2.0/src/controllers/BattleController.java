package controllers;

import datastructures.Battle;
import datastructures.Fight;
import events.FightStartEvent;
import events.PlayerEventInfo;
import participants.Pet;
import participants.Playable;
import utilities.EventBus;
import utilities.IOManager;
import utilities.Skills;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * runs and controls the logic for a battle
 */
class BattleController {

    private Battle currentBattle;
    private Playable[] participants;
    private FightController fightController;
    private int fightsPerBattle;


    /**
     * BattleController(int fightsPerBattle)
     * constructor to instantiate participants, currentBattle, fightController
     * @param fightsPerBattle - number of fights per battle
     */
    BattleController(int fightsPerBattle) {
        this.fightsPerBattle = fightsPerBattle;
    }

    /**
     * Battle runBattle(Playable[] participants)
     * logic to run and maintain the current battle
     * @param participants - array of participants
     * @return the current battle state.
     */
    Battle runBattle(Playable[] participants) {

        EventBus eventBus = new EventBus();

        for (Playable participant: participants)
            eventBus.register(participant);

        this.participants = participants;
        int currentFightNumber = 1;
        currentBattle = new Battle(fightsPerBattle);
        fightController = new FightController(participants, eventBus);
        IOManager.promptLine("BATTLE START!!!!!!!!!!!!!");
        while(!isConcluded()) //While the battle is still going, keep doing rounds.
        {
            IOManager.promptLine("Fight number: " + currentFightNumber);
            eventBus.notify(new FightStartEvent(currentFightNumber, findPlayerEventInfoList()));
            currentBattle.addFight(fightController.runFight());
            currentFightNumber++;
        }
        currentBattle.setWinners(findWinners());
        IOManager.promptWithPlayables("Player(s) who won the battle:",currentBattle.getWinners());
        IOManager.promptLine("Winner information:");
        for(Playable participant:currentBattle.getWinners()){
            if(participant instanceof Pet)
                ((Pet) participant).display();
        }
        return currentBattle;
    }

    /**
     * private boolean isConcluded()
     * logic to determine if the battle is concluded or not
     * @return whether the battle is concluded
     */
    private boolean isConcluded() {
        boolean concluded = false;
        Fight[] fights = currentBattle.getFights();

        //If the last fight slot is filled
        if(fights[fights.length - 1] != null){
            concluded = true;
        }
        return concluded;
    }

    /**
     * private List<Playable> findWinners()
     * finds the winner(s) of the battle
     * @return the winner(s) of the battle as a list
     */
    private List<Playable> findWinners() {
        int[] points = new int[participants.length]; //first entry in points[] pairs with first participant, etc.
        int maxPoints = 0;
        int i = 0;
        List<Playable> winners = new ArrayList<>();

        for(Playable participant:participants)
        {
            points[i] = 0;

            for(Fight fight:currentBattle.getFights())
                if(fight.getWinners().contains(participant))
                    points[i]++;

            maxPoints = (points[i] > maxPoints ? points[i] : maxPoints);
            i++;
        }

        i = 0;
        for(Playable participant:participants) {
            if (points[i] == maxPoints)
                winners.add(participant);
            i++;
        }

        IOManager.promptLine("The winning point total is: " + maxPoints);
        return winners;
    }

    /**
     * @return a list of player info
     */
    private List<PlayerEventInfo> findPlayerEventInfoList()
    {
        List<PlayerEventInfo> playerEventInfos = new ArrayList<>();
        for(Playable participant: participants)
        {
            PlayerEventInfo.PlayerEventInfoBuilder playerEventInfoBuilder = new PlayerEventInfo.PlayerEventInfoBuilder();

            playerEventInfos.add(
                    playerEventInfoBuilder.withPlayableUid(participant.getPlayableId())
                    .withPetName(participant.getPetName())
                    .withPetType(participant.getPetType())
                    .withPlayerType(participant.getPlayerType())
                    .withStartingHp(participant.getStartingHp())
                    .withSkillSet(new HashSet<>(Arrays.asList(Skills.values())))
                    .build());

        }
        return playerEventInfos;
    }

}