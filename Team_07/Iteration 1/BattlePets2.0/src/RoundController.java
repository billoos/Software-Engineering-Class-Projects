public class RoundController {

    private Pet[] participants;
    Round currentRound;


    public RoundController(Pet[] participants)
    {
        this.participants = participants;
    }

    /**
     * runRound() runs round logic
     * Starts by prompting user to choose pet information(type, hp, name)
     * Each pet then chooses skill if inputs are correct damage is then calculated
     * After damage is calculated information is displayed to the user about different damage types per pet
     * Set recharge times
     * @return currentRound
     */
    public Round runRound()
    {
        currentRound = new Round(participants.length);
        Integer participantIndex = 0;
        Integer targetIndex;
        Damage damageDone;
        Pet target;

        for(Pet participant:participants) {
            IOManager.promptLine(participant.getPlayerName() + "'s information:");
            IOManager.promptLine("Pet's type: " + participant.getPetType());
            IOManager.promptLine("Pet's HP: " + participant.getCurrentHp());
            IOManager.promptPartial("Pet's recharging skills:");

            for (Skills skill:Skills.values())
            {
                if(participant.getSkillRechargeTime(skill) > 0) {
                    IOManager.promptPartial(" " + skill + ": " + participant.getSkillRechargeTime(skill));
                }
            }
            IOManager.endLine();
            IOManager.endLine();
        }

        //Both pets choose skills first
        for (Pet participant:participants)
        {
            currentRound.addSkillUsed(participant.chooseSkill(), participantIndex);
            participantIndex++;
        }

        //Then the damage is done and calculated for both skills
        participantIndex = 0;
        for (Pet participant:participants)
        {
            targetIndex = (participantIndex + 1) % participants.length;
            damageDone = getSkillDamage(participantIndex, targetIndex);
            currentRound.addDamageDone(damageDone, participantIndex);
            participantIndex++;

            target = participants[targetIndex];
            target.updateHp(target.currentHp - damageDone.calculateTotalDamage());
        }

        //Prints skill used and damage done
        participantIndex = 0;
        Skills skillUsed;
        for(Pet participant:participants) {
            skillUsed = currentRound.getSkillsUsed()[participantIndex];
            damageDone = currentRound.getDamageDone()[participantIndex];
            IOManager.promptLine(participant.getPlayerName()+"'s chosen skill: " + skillUsed);
            IOManager.promptLine("Total damage: " + damageDone.calculateTotalDamage());
            IOManager.promptLine("  Conditional damage: " + damageDone.getConditionalDamage());
            IOManager.promptLine("  Random damage: " + damageDone.getRandomDamage());
            participantIndex++;
            IOManager.endLine();
        }

        //Loops through pets and sets recharge times properly
        participantIndex = 0;
        for (Pet participant:participants)
        {
            participant.decrementRechargeTimes();
            //Sets the recharge for the skill that was just used
            participant.setRechargeTime(currentRound.getSkillsUsed()[participantIndex], participant.DEFAULT_RECHARGE_TIME);
            participantIndex++;
        }

        return currentRound;
    }

    /**
     * getSkillDamage(Integer attackerIndex, Integer targetIndex) runs damage logic
     * checks each different skill chosen by the different pet types and sets conditional damage
     * returns damage object with random and conditional damage
     * @param attackerIndex
     * @param targetIndex
     * @return damageDone
     */

    private Damage getSkillDamage(Integer attackerIndex, Integer targetIndex)
    {
        Damage damageDone;
        double condDamage = 0;
        double randDamage = RandomNumber.INSTANCE.makeNumberBelowMax(5);

        if (participants[attackerIndex].petType == PetTypes.INTELLIGENCE)
        {
            if (currentRound.getSkillsUsed()[attackerIndex] == Skills.ROCK_THROW)
            {
                if (participants[targetIndex].getSkillRechargeTime(Skills.SCISSORS_POKE) > 0)
                {
                    condDamage = condDamage + 3.0;
                }
                if (participants[targetIndex].getSkillRechargeTime(Skills.ROCK_THROW) > 0)
                {
                    condDamage = condDamage + 2.0;
                }
            }
            else if (currentRound.getSkillsUsed()[attackerIndex] == Skills.SCISSORS_POKE)
            {
                if (participants[targetIndex].getSkillRechargeTime(Skills.PAPER_CUT) > 0)
                {
                    condDamage = condDamage + 3.0;
                }
                if (participants[targetIndex].getSkillRechargeTime(Skills.SCISSORS_POKE) > 0)
                {
                    condDamage = condDamage + 2.0;
                }
            }
            else if (currentRound.getSkillsUsed()[attackerIndex] == Skills.PAPER_CUT)
            {
                if (participants[targetIndex].getSkillRechargeTime(Skills.ROCK_THROW) > 0)
                {
                    condDamage = condDamage + 3.0;
                }
                if (participants[targetIndex].getSkillRechargeTime(Skills.PAPER_CUT) > 0)
                {
                    condDamage = condDamage + 2.0;
                }
            }
        }
        else if (participants[attackerIndex].petType == PetTypes.SPEED)
        {
            if (currentRound.getSkillsUsed()[attackerIndex] == Skills.ROCK_THROW)
            {
                if (participants[targetIndex].calculateHpPercent() >= 0.75 &&
                        (currentRound.getSkillsUsed()[targetIndex] == Skills.PAPER_CUT ||
                                currentRound.getSkillsUsed()[targetIndex] == Skills.SCISSORS_POKE))
                {
                    condDamage = condDamage + 10;
                }
            }
            else if (currentRound.getSkillsUsed()[attackerIndex] == Skills.SCISSORS_POKE)
            {
                if (participants[targetIndex].calculateHpPercent() < 0.75 &&
                        participants[targetIndex].calculateHpPercent() >= 0.25 &&
                        (currentRound.getSkillsUsed()[targetIndex] == Skills.ROCK_THROW ||
                                currentRound.getSkillsUsed()[targetIndex] == Skills.PAPER_CUT))
                {
                    condDamage = condDamage + 10;
                }
            }
            else if (currentRound.getSkillsUsed()[attackerIndex] == Skills.PAPER_CUT)
            {
                if (participants[targetIndex].calculateHpPercent() < 0.75 &&
                        (currentRound.getSkillsUsed()[targetIndex] == Skills.ROCK_THROW ||
                                currentRound.getSkillsUsed()[targetIndex] == Skills.SCISSORS_POKE))
                {
                    condDamage = condDamage + 10;
                }
            }
        }
        else if (participants[attackerIndex].petType == PetTypes.POWER)
        {
            if (currentRound.getSkillsUsed()[attackerIndex] == Skills.ROCK_THROW)
            {
                if (currentRound.getSkillsUsed()[targetIndex] == Skills.SCISSORS_POKE)
                {
                    condDamage = randDamage * 5.0;
                }
            }
            else if (currentRound.getSkillsUsed()[attackerIndex] == Skills.SCISSORS_POKE)
            {
                if (currentRound.getSkillsUsed()[targetIndex] == Skills.PAPER_CUT)
                {
                    condDamage = randDamage * 5.0;
                }
            }
            else if (currentRound.getSkillsUsed()[attackerIndex] == Skills.PAPER_CUT)
            {
                if (currentRound.getSkillsUsed()[targetIndex] == Skills.ROCK_THROW)
                {
                    condDamage = randDamage * 5.0;
                }
            }
        }
        damageDone = new Damage(randDamage, condDamage);
        return damageDone;
    }
}
