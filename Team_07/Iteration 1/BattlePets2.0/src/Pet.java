import java.util.HashMap;
import java.util.Map;


public class Pet implements Playable
{
    final String DEFAULT_NAME = "Doug";
    //These Defaults should be initialized at some point
    final PlayerTypes DEFAULT_PLAYER_TYPE = PlayerTypes.HUMAN;
    final PetTypes DEFAULT_PET_TYPE = PetTypes.POWER;

    final Double DEFAULT_HP = 100.0;
    final int DEFAULT_RECHARGE_TIME = 1;
    final Map<Skills, Integer> STARTING_RECHARGE_TIMES = new HashMap<Skills, Integer>()
    {
        {
            put(Skills.ROCK_THROW, 0);
            put(Skills.SCISSORS_POKE, 0);
            put(Skills.PAPER_CUT, 0);
        }
    };

    String petName;
    PlayerTypes playerType;
    PetTypes petType;
    Double currentHp;
    Double startingHp;
    String playerName;
    Map<Skills, Integer> rechargeTimes;

    /**
     * Pet(PetBuilder pb)
     * implementation of Builder pattern for creating pets
     * checks to make sure that no states are null and if they are set to Default Values
     * @param pb
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

    @Override
    public void setCurrentHp(double currentHp) {
        this.currentHp = currentHp;
    }

    /**
     * isAwake()
     * overiden from Playable Interface
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
    public Skills getSkillPrediction() {
        return null;
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
        if (this.playerType == PlayerTypes.HUMAN)
        {
            IOManager.promptLine("Player: " + playerName);
            IOManager.promptLine("Please choose a skill: Rock Throw (1), Scissors Poke (2), Paper Cut (3)");
            int choice = IOManager.getInputInteger();
            if (choice == 1)
            {
                if(getSkillRechargeTime(Skills.ROCK_THROW) == 0) {
                    skill = Skills.ROCK_THROW;
                }
                else
                {
                    IOManager.promptLine("Recharging Skill chosen. Choose a valid skill");
                    skill = chooseSkill();
                }
            }
            else if (choice == 2)
            {
                if(getSkillRechargeTime(Skills.SCISSORS_POKE) == 0) {
                    skill = Skills.SCISSORS_POKE;
                }
                else
                {
                    IOManager.promptLine("Recharging Skill chosen. Choose a valid skill");
                    skill = chooseSkill();
                }
            }
            else if (choice == 3)
            {
                if(getSkillRechargeTime(Skills.PAPER_CUT) == 0) {
                    skill = Skills.PAPER_CUT;
                }
                else
                {
                    IOManager.promptLine("Recharging Skill chosen. Choose a valid skill");
                    skill = chooseSkill();
                }
            }
            else
                throw new IllegalArgumentException("Invalid skill chosen: " + choice);
        }


        //else if(this.playerType == PlayerTypes.COMPUTER)
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
        //This might be different depending on how we want to handle recharge times.
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



    public static class PetBuilder
    {

        String petName;
        PlayerTypes playerType;
        PetTypes petType;
        //Double currentHp;
        Double startingHp;
        String playerName;
        Map<Skills, Integer> rechargeTimes;

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
        /*
        public PetBuilder withCurrentHp(Double currentHp)
        {
            this.currentHp = currentHp;
            return this;
        }*/
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