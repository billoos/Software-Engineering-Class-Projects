package utilities;

import datastructures.Damage;
import events.*;

import java.util.ArrayList;
import java.util.List;

public class EventBus {
    private List<Observer> observerList;

    public EventBus() {
        observerList = new ArrayList<>();
    }

    public void register(Observer observer) {
        observerList.add(observer);
    }

    public void unregister(Observer observer) {
        observerList.remove(observer);
    }

    public void notify(Object event){
        if(event instanceof FightStartEvent)
            notifyFightStartEvent((FightStartEvent) event);
        else if(event instanceof RoundStartEvent)
            notifyRoundStartEvent((RoundStartEvent) event);
        else if(event instanceof AttackEventShootTheMoon)
            notifyAttackEventShootTheMoon((AttackEventShootTheMoon) event);
        else if(event instanceof AttackEvent)
            notifyAttackEvent((AttackEvent) event);
    }


    //Copy and notify methods
    /**
     * copies input event and updates all observers.
     * @param event to copy and update observers with
     */
    private void notifyFightStartEvent(FightStartEvent event) {
        FightStartEvent copy;
        for(Observer observer:observerList){
            //Recreates the list
            List<PlayerEventInfo> eventInfoList = new ArrayList<>();
            for(PlayerEventInfo eventInfo:event.getPlayerEventInfos())
                eventInfoList.add(new PlayerEventInfo(eventInfo));
            copy = new FightStartEvent(event.getFightIndex(), eventInfoList);
            observer.update(copy);
        }
    }

    /**
     * copies input event and updates all observers.
     * @param event to copy and update observers with
     */
    private void notifyRoundStartEvent(RoundStartEvent event) {
        RoundStartEvent copy;
        for(Observer observer:observerList){
            copy = new RoundStartEvent(event.getRoundNumber());
            observer.update(copy);
        }
    }

    /**
     * copies input event and updates all observers.
     * @param event to copy and update observers with
     */
    private void notifyAttackEvent(AttackEvent event) {
        AttackEvent copy;
        Damage damage;
        for(Observer observer:observerList){
            damage = new Damage(event.getDamage().getRandomDamage(),event.getDamage().getConditionalDamage());
            copy = new AttackEvent(event.getAttackingPlayableUid(),
                                   event.getVictimPlayableUid(),
                                   event.getAttackingSkillChoice(),
                                   damage);
            observer.update(copy);
        }
    }

    /**
     * copies input event and updates all observers.
     * @param event to copy and update observers with
     */
    private void notifyAttackEventShootTheMoon(AttackEventShootTheMoon event) {
        AttackEventShootTheMoon.AttackEventShootTheMoonBuilder builder = new AttackEventShootTheMoon.AttackEventShootTheMoonBuilder()
                .withAttackingPlayableUid(event.getAttackingPlayableUid())
                .withVictimPlayableUid(event.getVictimPlayableUid())
                .withAttackingSkillChoice(event.getAttackingSkillChoice())
                .withPredictedSkillEnum(event.getPredictedSkillEnum());

        Damage damage;
        for(Observer observer:observerList){
            damage = new Damage(event.getDamage().getRandomDamage(),event.getDamage().getConditionalDamage());
            observer.update(builder.withDamage(damage).build());
        }
    }
}