
package Participants;

import Utilities.IOManager;
import Utilities.PetTypes;
import Utilities.PlayerTypes;
import Utilities.Skills;

import java.util.HashMap;

public class ComputerPet extends Pet {

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
    }

    @Override
    public Skills getSkillPrediction() {
        Skills predictSkill;

        IOManager.promptLine(this.petName + ": ");
        IOManager.promptLine("Please choose a skill to predict: Rock Throw (1), Scissors Poke (2), Paper Cut (3), " +
                "Shoot the Moon (4), Reversal of Fortune(5)");
        int skillInt = pickRandom();
        predictSkill = Skills.values()[skillInt - 1];
        return predictSkill;
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

        if (this.playerType == PlayerTypes.COMPUTER)
        {
            IOManager.promptLine("AI: " + playerName + " is choosing a skill");

            while (!isValid)
            {
                int choice = pickRandom();
                switch (choice) {
                    case 1:
                        if (getSkillRechargeTime(Skills.ROCK_THROW) == 0) {
                            skill = Skills.ROCK_THROW;
                            isValid = true;
                        }
                        break;

                    case 2:
                        if (getSkillRechargeTime(Skills.SCISSORS_POKE) == 0) {
                            skill = Skills.SCISSORS_POKE;
                            isValid = true;
                        }
                        break;

                    case 3:
                        if (getSkillRechargeTime(Skills.PAPER_CUT) == 0) {
                            skill = Skills.PAPER_CUT;
                            isValid = true;
                        }
                        break;

                    case 4:
                        if (getSkillRechargeTime(Skills.SHOOT_THE_MOON) == 0) {
                            skill = Skills.SHOOT_THE_MOON;
                            isValid = true;
                        }
                        break;

                    case 5:
                        if (getSkillRechargeTime(Skills.REVERSAL_OF_FORTUNE) == 0) {
                            skill = Skills.REVERSAL_OF_FORTUNE;
                            isValid = true;
                        }
                        break;

                    default:
                        IOManager.promptLine("An AI tried to pick an invalid skill");
                }
            }
        }
        IOManager.promptLine("AI: " + playerName + " has chosen a skill");
        return skill;
    }

    private int pickRandom() {
        int range = 5;

        return (int)(Math.random() * range);
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