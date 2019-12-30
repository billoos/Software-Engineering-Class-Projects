package edu.dselent.player;

import edu.dselent.event.AttackEvent;
import edu.dselent.event.FightStartEvent;
import edu.dselent.event.PlayerEventInfo;
import edu.dselent.event.RoundStartEvent;

import java.util.ArrayList;
import java.util.List;

public class FightLogger {
    private int roundNumber = -1;
    private int myId;

    private List<PetData> fightOrder;

    /**
     * makes a FightLogger
     * @param myId id of the AI who is tracking
     * @param fightStartEvent the fightStart event to construct from
     */
    public FightLogger(int myId, FightStartEvent fightStartEvent){
        this.myId = myId;

        //Populate the players
        fightOrder = new ArrayList<>();
        for(PlayerEventInfo info:fightStartEvent.getPlayerEventInfoList()){
            fightOrder.add(new PetData(info));
        }
    }

    /**
     * is called by our AI to log attack events
     * @param event the event to process
     */
    public void processAttackEvent(AttackEvent event){
        for(PetData data:fightOrder){
            data.processAttackEvent(event);
        }
    }

    /**
     * Is called by our AI to log round starts
     * @param event the event to process
     */
    public void processRoundStartEvent(RoundStartEvent event){
        roundNumber = event.getRoundNumber();
        //Remove asleep pets at the start of the round
        List<PetData> toRemove= new ArrayList<>(fightOrder.size());
        for(PetData data:fightOrder){
            if(data.isAsleep()){
                toRemove.add(data);
            }
        }
        for (PetData aToRemove : toRemove) fightOrder.remove(aToRemove);
    }

    /**
     * gives back the current attacker
     * @return the petData of the current Attacker pet
     */
    public PetData getCurrentAttacker(){
        int myIndex = findById(myId);
        int attackerIndex = (myIndex + fightOrder.size() - 1) % fightOrder.size();
        return fightOrder.get(attackerIndex);
    }

    /**
     * gives back the current victim
     * @return the PetData of the current Victim pet
     */
    public PetData getCurrentVictim(){
        int myIndex = findById(myId);
        int victimIndex = (myIndex + 1) % fightOrder.size();
        return fightOrder.get(victimIndex);
    }

    /**
     * gets the creator pet from the fightOrder
     * @return the Petdata associated with the fightLogger
     */
    public PetData getMyself(){
        return fightOrder.get(findById(myId));
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public int getFightSize(){
        return fightOrder.size();
    }

    public List<PetData> getFightOrder() {
        return fightOrder;
    }

    public int findById(int id){
        int index = -1;
        int i = 0;
        while(index == -1 && i < fightOrder.size()){
            if(fightOrder.get(i).getId() == id){
                index = i;
            }
            i++;
        }
        return index;
    }
}
