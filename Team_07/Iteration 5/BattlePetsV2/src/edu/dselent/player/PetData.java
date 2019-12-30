package edu.dselent.player;

import edu.dselent.event.AttackEvent;
import edu.dselent.event.PlayerEventInfo;
import edu.dselent.skill.Skill;
import edu.dselent.skill.Skills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static edu.dselent.skill.Skills.*;
import static edu.dselent.skill.Skills.SHOOT_THE_MOON;

public class PetData {

    private int id;

    private double startingHp;
    private double currentHp;

    private double potentialReversalDamage;

    private PlayerTypes playerType;
    private PetTypes petType;

    private Map<Skills,Integer> cooldowns;
    private List<Skills> skillLog;

    private final Map<Skills, Integer> STARTING_RECHARGE_TIMES = new HashMap<>()
    {
        {
            put(ROCK_THROW, 0);
            put(SCISSORS_POKE, 0);
            put(PAPER_CUT, 0);
            put(SHOOT_THE_MOON, 0);
            put(Skills.REVERSAL_OF_FORTUNE, 0);
        }
    };

    private final Map<Skills, Integer> RECHARGE_TIMES = new HashMap<>()
    {
        {
            put(ROCK_THROW, 1);
            put(SCISSORS_POKE, 1);
            put(PAPER_CUT, 1);
            put(SHOOT_THE_MOON, 6);
            put(Skills.REVERSAL_OF_FORTUNE, 6);
        }
    };

    public PetData(PlayerEventInfo info){
        potentialReversalDamage = 0;
        skillLog = new ArrayList<>();
        cooldowns = new HashMap<>(STARTING_RECHARGE_TIMES);
        startingHp = info.getStartingHp();
        currentHp = startingHp;
        playerType = info.getPlayerType();
        petType = info.getPetType();
        id = info.getPlayableUid();
    }

    /**
     * is called by the fight logger to allow the petdata to track hp and such
     * @param attackEvent The event to process
     */
    public void processAttackEvent(AttackEvent attackEvent){
        //If the attack was from me
        if(attackEvent.getAttackingPlayableUid() == id){
            potentialReversalDamage -= attackEvent.getDamage().getRandomDamage();
            decrementRechargeTimes(); //this ends up being called once for each attacker
            Skills skillUsed = attackEvent.getAttackingSkillChoice();
            skillLog.add(skillUsed);
            cooldowns.replace(skillUsed, RECHARGE_TIMES.get(skillUsed));
        }
        //If the attack targeted me
        if(attackEvent.getVictimPlayableUid() == id){
            currentHp -= attackEvent.getDamage().calculateTotalDamage();
            potentialReversalDamage += attackEvent.getDamage().getRandomDamage();
        }
    }

    /**
     * returns if the pet is asleep
     * @return if the pet is asleep
     */
    public boolean isAsleep(){
        return (currentHp <= 0);
    }

    public int getId() {
        return id;
    }

    public double getStartingHp() {
        return startingHp;
    }

    public double getCurrentHp() {
        return currentHp;
    }

    public PlayerTypes getPlayerType() {
        return playerType;
    }

    public PetTypes getPetType() {
        return petType;
    }

    public double getPotentialReversalDamage() {
        return potentialReversalDamage;
    }

    public int getRechargeTime(Skills skill){
        return cooldowns.get(skill);
    }

    public Skills getLastSkillUsed(){
        return skillLog.get(skillLog.size() - 1);
    }

    public List<Skills> getSkillLog() {
        return skillLog;
    }

    public List<Skills> getAvailableSkills(){
        List<Skills> availableSkills = new ArrayList<>();
        for(Skills skill:Skills.values()){
            if(getRechargeTime(skill) == 0)
                availableSkills.add(skill);
        }
        return availableSkills;
    }

    /**
     * decrements the recharge times of the cooldowns map
     */
    private void decrementRechargeTimes(){
        for (Map.Entry<Skills, Integer> entry : cooldowns.entrySet())
        {
            if(entry.getValue() > 0)
            {
                entry.setValue(entry.getValue() - 1);
            }
        }
    }
}
