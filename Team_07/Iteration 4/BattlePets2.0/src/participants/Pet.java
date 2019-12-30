package participants;

import datastructures.PlayableStatistics;
import events.AttackEvent;
import events.FightStartEvent;
import events.RoundStartEvent;
import utilities.IOManager;
import utilities.PetTypes;
import utilities.PlayerTypes;
import utilities.Skills;

import java.util.HashMap;
import java.util.Map;

public abstract class Pet implements Playable{
    protected final String DEFAULT_NAME = "Doug";
    protected final PlayerTypes DEFAULT_PLAYER_TYPE = PlayerTypes.HUMAN;
    protected final PetTypes DEFAULT_PET_TYPE = PetTypes.POWER;
    protected final Double DEFAULT_HP = 100.0;

    protected final Map<Skills, Integer> STARTING_RECHARGE_TIMES = new HashMap<>()
    {
        {
            put(Skills.ROCK_THROW, 0);
            put(Skills.SCISSORS_POKE, 0);
            put(Skills.PAPER_CUT, 0);
            put(Skills.SHOOT_THE_MOON, 0);
            put(Skills.REVERSAL_OF_FORTUNE, 0);
        }
    };

    String petName;
    PlayerTypes playerType;
    protected PetTypes petType;
    protected Double currentHp;
    protected Double startingHp;
    protected String playerName;
    protected Integer playableId;
    protected Map<Skills, Integer> rechargeTimes;

    protected PlayableStatistics statistics = new PlayableStatistics();

    @Override
    public String getPlayerName()
    {
        return playerName;
    }
    @Override
    public String getPetName() {
        return petName;
    }

    @Override
    public PlayerTypes getPlayerType() {
        return playerType;
    }

    @Override
    public PetTypes getPetType() {
        return petType;
    }

    @Override
    public double getCurrentHp() {
        return currentHp;
    }

    @Override
    public void updateHp(double hp) {
        currentHp = hp;
    }

    @Override
    public void resetHp() {
        currentHp = startingHp;
    }

    /**
     * isAwake()
     * overiden from Participants.Playable Interface
     * Checks to see if current hp is above zero to return true otherwise false
     * @return awake
     */
    @Override
    public boolean isAwake() {
        boolean awake = false;
        if (this.currentHp > 0)
        {
            awake = true;
        }
        return awake;
    }

    @Override
    public int getSkillRechargeTime(Skills skill) {
        return rechargeTimes.get(skill);
    }

    @Override
    public double calculateHpPercent() {
        return currentHp / startingHp;
    }

    @Override
    public double getStartingHp() {
        return startingHp;
    }

    /**
     * reset()
     * overiden from the playable interface
     * sets currethp back to staringhp
     * rechargeTime map is set back to original
     */
    @Override
    public void reset() {
        currentHp = startingHp;
        rechargeTimes = new HashMap<>(STARTING_RECHARGE_TIMES);
    }

    /**
     * decrementRechargeTimes()
     * overiden from the playable interface
     * after each round recharge times for skill are decremented
     */
    @Override
    public void decrementRechargeTimes() {
        for (Map.Entry<Skills, Integer> entry : rechargeTimes.entrySet())
        {
            if(entry.getValue() > 0)
            {
                entry.setValue(entry.getValue() - 1);
            }
        }
    }

    /**
     * Sets the recharge time for the skill specified
     * @param skill the skill to have its recharge time set
     * @param rechargeTime the time to set this skill's recharge to
     */
    @Override
    public void setRechargeTime(Skills skill, int rechargeTime)
    {
        rechargeTimes.replace(skill, rechargeTime);
    }

    @Override
    public int getPlayableId() {
        return playableId;
    }

    /**
     * Updates the Pet with the received event. This is the default for updating statistics
     * @param event - event that occurred
     */
    @Override
    public void update(Object event) {
        if(event instanceof FightStartEvent){
            statistics.fightEnd();
            statistics.fightStart();
        }
        else if(event instanceof AttackEvent){
            AttackEvent attackEvent = (AttackEvent) event;
            if(attackEvent.getAttackingPlayableUid() == playableId)
                statistics.damageDone(attackEvent.getDamage());
            if(attackEvent.getVictimPlayableUid() == playableId)
                statistics.damageReceived(attackEvent.getDamage());
        }
        else if(event instanceof RoundStartEvent){
            statistics.roundStart(isAwake());
        }
        //IOManager.promptLine(playerName + "Event:");
        //IOManager.promptLine(event.toString());
    }

    /**
     * Displays statistics and other information for the Pet
     */
    public void display(){
        IOManager.promptLine("---------------------------------------------------------------------------------------");
        IOManager.promptLine("Player name: " + playerName);
        IOManager.promptLine("Player type: " + playerType.toString());
        IOManager.promptLine("Pet name: " + petName);
        IOManager.promptLine("Pet type: " + petType.toString());
        statistics.display();
        IOManager.promptLine("---------------------------------------------------------------------------------------");
    }
}
