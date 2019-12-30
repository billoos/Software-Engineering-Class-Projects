package Participants;

import Utilities.IOManager;
import Utilities.PetTypes;
import Utilities.PlayerTypes;
import Utilities.Skills;

import java.util.HashMap;
import java.util.Map;


public class Pet implements Playable
{
    private final String DEFAULT_NAME = "Doug";
    private final PlayerTypes DEFAULT_PLAYER_TYPE = PlayerTypes.HUMAN;
    private final PetTypes DEFAULT_PET_TYPE = PetTypes.POWER;
    private final Double DEFAULT_HP = 100.0;

    private final Map<Skills, Integer> STARTING_RECHARGE_TIMES = new HashMap<Skills, Integer>()
    {
        {
            put(Skills.ROCK_THROW, 0);
            put(Skills.SCISSORS_POKE, 0);
            put(Skills.PAPER_CUT, 0);
            put(Skills.SHOOT_THE_MOON, 0);
            put(Skills.REVERSAL_OF_FORTUNE, 0);
        }
    };

    private String petName;
    private PlayerTypes playerType;
    private PetTypes petType;
    private Double currentHp;
    private Double startingHp;
    private String playerName;
    private Integer playableId;
    private Map<Skills, Integer> rechargeTimes;

    /**
     * Participants.Pet(PetBuilder pb)
     * implementation of Builder pattern for creating pets
     * checks to make sure that no states are null and if they are set to Default Values
     * @param pb The pet builder
     */
    private Pet(PetBuilder pb)
    {

        if(pb.petName == null)
        {
            petName = DEFAULT_NAME;
        }
        else
        {
            petName = pb.petName;
        }
        if(pb.playerType == null)
        {
            playerType = DEFAULT_PLAYER_TYPE;
        }
        else
        {
           playerType = pb.playerType;
        }
        if(pb.petType == null)
        {
            petType = DEFAULT_PET_TYPE;
        }
        else
        {
            petType = pb.petType;
        }
        if(pb.startingHp <= 0)
        {
            startingHp = DEFAULT_HP;
        }
        else
        {
            startingHp = pb.startingHp;
        }
        currentHp = startingHp;
        if(pb.playerName == null)
        {
            playerName = DEFAULT_NAME;
        }
        else
        {
            playerName = pb.playerName;
        }
        if(pb.playableId == null)
        {
            throw new IllegalStateException("No id is specified for " + playerName);
        }
        else
        {
            playableId = pb.playableId;
        }
        rechargeTimes = new HashMap<>(STARTING_RECHARGE_TIMES);
    }
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
    public Skills getSkillPrediction()
    {
        Skills predictSkill = null;

        IOManager.promptLine(this.petName + ": ");
        IOManager.promptLine("Please choose a skill to predict: Rock Throw (1), Scissors Poke (2), Paper Cut (3), " +
                "Shoot the Moon (4), Reversal of Fortune(5)");
        int skillInt = IOManager.getInputInteger();
        while (skillInt > 5 || skillInt < 1)
        {
            IOManager.promptLine("Please enter a valid skill");
            skillInt = IOManager.getInputInteger();
        }
        switch(skillInt)
        {
            case 1: predictSkill = Skills.ROCK_THROW; break;
            case 2: predictSkill = Skills.SCISSORS_POKE; break;
            case 3: predictSkill = Skills.PAPER_CUT; break;
            case 4: predictSkill = Skills.SHOOT_THE_MOON; break;
            case 5: predictSkill = Skills.REVERSAL_OF_FORTUNE; break;
        }
        return predictSkill;
    }

    /**
     * chooseSkill()
     * prompts player to choose each skill if invalid skills is shown throw and exception
     * After each skill is chosen logic is run to place them on recharge and method returns skill chosen
     * @return skill
     */

    @Override
    public Skills chooseSkill()
    {
        Skills skill = null;
        boolean isValid = false;

        if (this.playerType == PlayerTypes.HUMAN)
        {
            IOManager.promptLine("Player: " + playerName);
            IOManager.promptLine("Please choose a skill: Rock Throw (1), Scissors Poke (2), Paper Cut (3), " +
                    "Shoot the Moon (4), Reversal of Fortune(5)");

            while (!isValid) {

                int choice = IOManager.getInputInteger();

                switch (choice) {
                    case 1:

                        if (getSkillRechargeTime(Skills.ROCK_THROW) == 0) {
                            skill = Skills.ROCK_THROW;
                            isValid = true;
                        }
                        else {
                            IOManager.promptLine("Recharging Skill chosen. Choose a valid skill");
                        }
                        break;


                    case 2:
                        if(getSkillRechargeTime(Skills.SCISSORS_POKE) == 0) {
                            skill = Skills.SCISSORS_POKE;
                            isValid = true;
                        }

                        else {
                            IOManager.promptLine("Recharging Skill chosen. Choose a valid skill");
                        }
                        break;


                    case 3:
                        if(getSkillRechargeTime(Skills.PAPER_CUT) == 0) {
                            skill = Skills.PAPER_CUT;
                            isValid = true;
                        }
                        else {
                            IOManager.promptLine("Recharging Skill chosen. Choose a valid skill");
                        }
                        break;

                    case 4:
                        if(getSkillRechargeTime(Skills.SHOOT_THE_MOON) == 0) {
                            skill = Skills.SHOOT_THE_MOON;
                            isValid = true;
                        }
                        else {
                            IOManager.promptLine("Recharging Skill chosen. Choose a valid skill");
                        }
                        break;

                    case 5:
                        if(getSkillRechargeTime(Skills.REVERSAL_OF_FORTUNE) == 0) {
                            skill = Skills.REVERSAL_OF_FORTUNE;
                            isValid = true;
                        }
                        else {
                            IOManager.promptLine("Recharging Skill chosen. Choose a valid skill");
                        }
                        break;



                    default:
                        IOManager.promptLine("Invalid skill please try again");


                }

            }
        }
        //else if(this.playerType == Utilities.PlayerTypes.COMPUTER)
        //{
        //AI logic choice
        //}




        return skill;
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

    @Override
    public void setRechargeTime(Skills skill, int rechargeTime)
    {
        rechargeTimes.replace(skill, rechargeTime);
    }

    @Override
    public int getPlayableId() {
        return playableId;
    }


    public static class PetBuilder
    {

        String petName;
        PlayerTypes playerType;
        PetTypes petType;
        Double startingHp;
        String playerName;
        Integer playableId;

        public PetBuilder withPetName(String petName)
        {
            this.petName = petName;
            return this;
        }
        public PetBuilder withPlayerType(PlayerTypes playerType)
        {
            this.playerType = playerType;
            return this;
        }
        public PetBuilder withPetType(PetTypes petType)
        {
            this.petType = petType;
            return this;
        }
        public PetBuilder withStartingHp(Double startingHp)
        {
            this.startingHp = startingHp;
            return this;
        }
        public PetBuilder withPlayerName(String playerName)
        {
            this.playerName = playerName;
            return this;
        }
        public PetBuilder withPlayableId(int playableId)
        {
            this.playableId = playableId;
            return this;
        }
        /**
         * build()
         * Builder Pattern called after each neccessary input builds a pet
         */

        public Pet build()
        {
            return new Pet(this);
        }
    }
}