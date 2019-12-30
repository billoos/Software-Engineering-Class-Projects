package participants;

import utilities.IOManager;
import utilities.PetTypes;
import utilities.PlayerTypes;
import utilities.Skills;

import java.util.HashMap;
import java.util.Random;

public class ComputerPet extends Pet {
    private Random random;

    /**
     * Participants.HumanPet(PetBuilder pb)
     * implementation of Builder pattern for creating pets
     * checks to make sure that no states are null and if they are set to Default Values
     * @param pb The pet builder
     */
    private ComputerPet(PetBuilder pb) {

        if (pb.petName == null) {
            petName = DEFAULT_NAME;
        } else {
            petName = pb.petName;
        }
        if (pb.playerType == null) {
            playerType = DEFAULT_PLAYER_TYPE;
        } else {
            playerType = pb.playerType;
        }
        if (pb.petType == null) {
            petType = DEFAULT_PET_TYPE;
        } else {
            petType = pb.petType;
        }
        if (pb.startingHp <= 0) {
            startingHp = DEFAULT_HP;
        } else {
            startingHp = pb.startingHp;
        }
        currentHp = startingHp;
        if (pb.playerName == null) {
            playerName = DEFAULT_NAME;
        } else {
            playerName = pb.playerName;
        }
        if (pb.playableId == null) {
            throw new IllegalStateException("No id is specified for " + playerName);
        } else {
            playableId = pb.playableId;
        }
        rechargeTimes = new HashMap<>(STARTING_RECHARGE_TIMES);
        random = new Random(playableId);
    }

    @Override
    public Skills getSkillPrediction() {
        Skills predictSkill;
        int skillInt = pickRandom();
        predictSkill = Skills.values()[skillInt];

        IOManager.promptLine("AI: " + this.playerName + " has predicted " + predictSkill.toString());
        return predictSkill;
    }

    @Override
    public void update(Object event) {
        super.update(event);
    }

    /**
     * chooseSkill()
     * prompts player to choose each skill if invalid skills is shown throw and exception
     * After each skill is chosen logic is run to place them on recharge and method returns skill chosen
     * @return skill
     */
    @Override
    public Skills chooseSkill() {
        Skills skill = null;
        boolean isValid = false;

        while (!isValid)
        {
            int choice = pickRandom();
            skill = Skills.values()[choice];
            if(getSkillRechargeTime(skill) == 0)
                isValid = true;
        }
        IOManager.promptLine("AI: " + playerName + " has chosen a skill");
        //IOManager.promptLine("AI: " + playerName + " has chosen " + skill.toString());
        return skill;
    }

    private int pickRandom() {
        return random.nextInt(Skills.values().length);
    }

    public static class PetBuilder {

        String petName;
        PlayerTypes playerType;
        PetTypes petType;
        Double startingHp;
        String playerName;
        Integer playableId;

        public PetBuilder withPetName(String petName) {
            this.petName = petName;
            return this;
        }

        public PetBuilder withPlayerType(PlayerTypes playerType) {
            this.playerType = playerType;
            return this;
        }

        public PetBuilder withPetType(PetTypes petType) {
            this.petType = petType;
            return this;
        }

        public PetBuilder withStartingHp(Double startingHp) {
            this.startingHp = startingHp;
            return this;
        }

        public PetBuilder withPlayerName(String playerName) {
            this.playerName = playerName;
            return this;
        }

        public PetBuilder withPlayableId(int playableId) {
            this.playableId = playableId;
            return this;
        }

        /**
         * build()
         * Builder Pattern called after each neccessary input builds a pet
         */

        public ComputerPet build() {
            return new ComputerPet(this);
        }
    }
}