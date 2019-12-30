package Controllers;

import DataStructures.Damage;
import DataStructures.Round;
import DataStructures.SkillData;
import Participants.Playable;
import Utilities.IOManager;
import Utilities.RandomNumber;
import Utilities.Skills;

import java.util.*;

/**
 * This class has all logic and functionality needed to run a round and reset for a new round
 */

class RoundController {

    private final int DEFAULT_MAX_RANDOM_DAMAGE = 5;
    private final double DEFAULT_CONDITIONAL_DAMAGE = 0;
    private Playable[] participants;
    private List<Playable> awakeParticipants = new ArrayList<>();
    private List<Double> cumulativeRandomDamageDifferences;
    private List<Double> roundRandomDamageDifferences;
    private Round currentRound;

    private final Map<Skills, Integer> DEFAULT_RECHARGE_TIMES = new HashMap<>()
    {
        {
            put(Skills.ROCK_THROW, 1);
            put(Skills.SCISSORS_POKE, 1);
            put(Skills.PAPER_CUT, 1);
            put(Skills.SHOOT_THE_MOON, 6);
            put(Skills.REVERSAL_OF_FORTUNE, 6);
        }
    };

    /**
     * Constructor for the roundController class which requires a list of participants to be used for all rounds in the
     * battle.
     * @param participants - list of starting participants for the round(s)
     */
    RoundController(Playable[] participants)
    {
        this.participants = participants;
        awakeParticipants.addAll(Arrays.asList(participants));
        cumulativeRandomDamageDifferences = new ArrayList<Double>(Collections.nCopies(participants.length, 0.0));
    }

    /**
     * runRound() runs round logic
     * Starts by prompting user to choose pet information(type, hp, name)
     * Each pet then chooses skill if inputs are correct damage is then calculated
     * After damage is calculated information is displayed to the user about different damage types per pet
     * Set recharge times
     * @return - the round that has just been run
     */
    Round runRound()
    {
        currentRound = new Round();
        printPetInfo();

        //Both pets choose skills first
        petsChooseSkill();

        //Then the damage is done and calculated for all skills
        currentRound.setFinalAwakePets(dealDamage());

        //Prints skill used and damage done - NOW CALLED IN DEAL DAMAGE BEFORE REMOVING SLEEPING PETS FROM LIST
        //printSkillUsedDamageDone();

        //Loops through pets and sets recharge times properly
        updateRechargeTimes();


        return currentRound;
    }

    /**
     * calculateSkillDamage(Integer attackerIndex, Integer targetIndex) runs damage logic
     * checks each different skill chosen by the different pet types and sets conditional damage
     * returns damage object with random and conditional damage
     * @param skillData - skillData to use for calculations
     * @return - calculate damage based on pet type and skill chosen
     */
    private Damage calculateSkillDamage(SkillData skillData)
    {
        double condDamage = DEFAULT_CONDITIONAL_DAMAGE;
        double randDamage = RandomNumber.INSTANCE.makeNumberBelowMax(DEFAULT_MAX_RANDOM_DAMAGE);
        System.out.println(randDamage);

        switch (awakeParticipants.get(skillData.getAttackerIndex()).getPetType())
        {
            case INTELLIGENCE:
                condDamage = intelligenceCondDamage(skillData, condDamage);
                break;
            case SPEED:
                condDamage = speedCondDamage(skillData, condDamage);
                break;
            case POWER:
                condDamage = powerCondDamage(skillData, condDamage, randDamage);
                break;
            default:

        }
        if(skillData.getSkillChosen() == Skills.REVERSAL_OF_FORTUNE)
            randDamage += condDamage;

        return  new Damage(randDamage, condDamage);
    }

    /**
     * calculateShootTheMoonCondDamage(Utilities.Skills chosenSkill) runs conditional damage logic for shoot the moon skill
     * checks if the predection of the attacker is equal to the skill chosen of the target
     * returns a double for conditional damage
     * @param chosenSkill - skill chosen by target
     * @param skillData - skillData used to calculate the damage
     * @return - the conditional damage done
     */
    private double calculateShootTheMoonCondDamage(Skills chosenSkill, SkillData skillData)
    {
        double condDamage = 0.0;
        Skills predictSkill = skillData.getSkillPredicted();
        if (chosenSkill == predictSkill)
        {
            condDamage += 20.0;
        }
        return condDamage;
    }

    /**
     * calculateReversalOfFateCondDamage()
     * @param skillData - skillData to be used in calculation
     * @return - damage dealt by reversal of fortune
     */
    private double calculateReversalOfFateCondDamage(SkillData skillData)
    {
        Double reversalDamage;
        reversalDamage =  cumulativeRandomDamageDifferences.get(skillData.getAttackerIndex());
        return reversalDamage;
    }

    /**
     * calculates conditional damage for power types
     * @param skillData - skillData to use in calculation
     * @param condDamage - conditional variable damage to be modified and returned
     * @param randDamage - random damage to be used in calculations
     * @return - updated CondDamage
     */
    private double powerCondDamage(SkillData skillData, double condDamage, double randDamage)
    {
        switch(skillData.getSkillChosen())
        {
            case ROCK_THROW:
                if (currentRound.getSkillDataList().get(skillData.getTargetIndex()).getSkillChosen() == Skills.SCISSORS_POKE)
                    condDamage = randDamage * 5.0;
                break;
            case SCISSORS_POKE:
                if (currentRound.getSkillDataList().get(skillData.getTargetIndex()).getSkillChosen() == Skills.PAPER_CUT)
                    condDamage = randDamage * 5.0;
                break;
            case PAPER_CUT:
                if (currentRound.getSkillDataList().get(skillData.getTargetIndex()).getSkillChosen() == Skills.ROCK_THROW)
                    condDamage = randDamage * 5.0;
                break;
            case SHOOT_THE_MOON:
                condDamage = calculateShootTheMoonCondDamage(currentRound.getSkillDataList().get(skillData.getTargetIndex()).getSkillChosen(), skillData);
                break;
            case REVERSAL_OF_FORTUNE:
                condDamage = calculateReversalOfFateCondDamage(skillData);
                break;
            default:
        }

        return condDamage;
    }

    /**
     * calculates conditional damage for speed types
     * @param skillData - skillData to use in calculation
     * @param condDamage - conditional variable damage to be modified and returned
     * @return - updated condDamage
     */
    private double speedCondDamage(SkillData skillData, double condDamage)
    {
        switch(skillData.getSkillChosen())
        {
            case ROCK_THROW:
                if (awakeParticipants.get(skillData.getTargetIndex()).calculateHpPercent() >= 0.75 &&
                        (currentRound.getSkillDataList().get(skillData.getTargetIndex()).getSkillChosen() == Skills.PAPER_CUT ||
                                currentRound.getSkillDataList().get(skillData.getTargetIndex()).getSkillChosen() == Skills.SCISSORS_POKE))
                    condDamage = condDamage + 10;
                break;
            case SCISSORS_POKE:
                if (awakeParticipants.get(skillData.getTargetIndex()).calculateHpPercent() < 0.75 &&
                        awakeParticipants.get(skillData.getTargetIndex()).calculateHpPercent() >= 0.25 &&
                        (currentRound.getSkillDataList().get(skillData.getTargetIndex()).getSkillChosen() == Skills.ROCK_THROW ||
                                currentRound.getSkillDataList().get(skillData.getTargetIndex()).getSkillChosen() == Skills.PAPER_CUT))
                    condDamage = condDamage + 10;
                break;
            case PAPER_CUT:
                if (awakeParticipants.get(skillData.getTargetIndex()).calculateHpPercent() < 0.75 &&
                        (currentRound.getSkillDataList().get(skillData.getTargetIndex()).getSkillChosen() == Skills.ROCK_THROW ||
                                currentRound.getSkillDataList().get(skillData.getTargetIndex()).getSkillChosen() == Skills.SCISSORS_POKE))
                    condDamage = condDamage + 10;
                break;
            case SHOOT_THE_MOON:
                condDamage = calculateShootTheMoonCondDamage(currentRound.getSkillDataList().get(skillData.getTargetIndex()).getSkillChosen(), skillData);
                break;
            case REVERSAL_OF_FORTUNE:
                condDamage = calculateReversalOfFateCondDamage(skillData);
                break;
            default:
        }
        return condDamage;
    }

    /**
     * calculates conditional damage for intelligence types
     * @param skillData - skillData to use in calculation
     * @param condDamage - conditional variable damage to be modified and returned
     * @return - updated condDamage
     */
    private double intelligenceCondDamage(SkillData skillData, double condDamage)
    {
        switch (skillData.getSkillChosen())
        {
            case ROCK_THROW:
                if (awakeParticipants.get(skillData.getTargetIndex()).getSkillRechargeTime(Skills.SCISSORS_POKE) > 0)
                    condDamage += 3.0;
                if (awakeParticipants.get(skillData.getTargetIndex()).getSkillRechargeTime(Skills.ROCK_THROW) > 0)
                    condDamage += 2.0;
                break;
            case SCISSORS_POKE:
                if (awakeParticipants.get(skillData.getTargetIndex()).getSkillRechargeTime(Skills.PAPER_CUT) > 0)
                    condDamage += 3.0;
                if (awakeParticipants.get(skillData.getTargetIndex()).getSkillRechargeTime(Skills.SCISSORS_POKE) > 0)
                    condDamage += 2.0;
                break;
            case PAPER_CUT:
                if (awakeParticipants.get(skillData.getTargetIndex()).getSkillRechargeTime(Skills.ROCK_THROW) > 0)
                    condDamage += 3.0;
                if (awakeParticipants.get(skillData.getTargetIndex()).getSkillRechargeTime(Skills.PAPER_CUT) > 0)
                    condDamage += 2.0;
                break;
            case SHOOT_THE_MOON:
                condDamage = calculateShootTheMoonCondDamage(currentRound.getSkillDataList()
                        .get(skillData.getTargetIndex()).getSkillChosen(), skillData);
                break;
            case REVERSAL_OF_FORTUNE:
                condDamage = calculateReversalOfFateCondDamage(skillData);
                break;
            default:
        }
        return condDamage;
    }

    /**
     * Prints info for each pet such as type, hp, and recharging skills
     */
    private void printPetInfo()
    {
        for(Playable participant:awakeParticipants) {
            IOManager.promptLine(participant.getPlayerName() + "'s information:");
            IOManager.promptLine("Pet's type: " + participant.getPetType());
            IOManager.promptLine("Pet's HP: " + participant.getCurrentHp());
            IOManager.promptPartial("Pet's recharging skills:");

            for (Skills skill: Skills.values())
            {
                if(participant.getSkillRechargeTime(skill) > 0) {
                    IOManager.promptPartial(" " + skill + ": " + participant.getSkillRechargeTime(skill));
                }
            }
            IOManager.endLine();
            IOManager.endLine();
        }
    }

    /**
     * calls the Participants.Playable method chooseSkill and assigns attacker and target indexes
     */
    private void petsChooseSkill()
    {
        int participantIndex = 0;
        SkillData skillData;
        for (Playable participant:awakeParticipants)
        {
            skillData = new SkillData();
            skillData.setSkillChosen(participant.chooseSkill());

            if (skillData.getSkillChosen() == Skills.SHOOT_THE_MOON)
                skillData.setSkillPredicted(participant.getSkillPrediction());

            skillData.setAttackerIndex(participantIndex);
            //If one wanted to have a different targeting system, replace the following line with the correct logic
            skillData.setTargetIndex((participantIndex + 1) % awakeParticipants.size());
            currentRound.addSkillData(skillData);
            participantIndex++;
        }
    }

    /**
     * Beast of a function that deals damage to each pet based on the skills they used, calculates
     * cumulativeRandomDamage, and handles sleep scent
     */
    private List<Playable> dealDamage()
    {
        int participantIndex = 0;
        Playable target;
        SkillData skillData;
        Damage damageDone;
        roundRandomDamageDifferences = new ArrayList<>(Collections.nCopies(awakeParticipants.size(), 0.0));

        List<Boolean> smellyParticipants = new ArrayList<>(Collections.nCopies(awakeParticipants.size(), false));
        boolean isSleepy;
        Double sleepScentDamage;
        List<Playable> sleepyParticipants = new ArrayList<>(awakeParticipants.size());

        for (Playable participant:awakeParticipants) {
            skillData = currentRound.getSkillDataList().get(participantIndex);
            if(skillData.getAttackerIndex() != participantIndex){
                throw new IllegalStateException("Skill data: " + skillData.getAttackerIndex() + " is desynced with participant: " + participantIndex);
            }
            damageDone = calculateSkillDamage(skillData);
            skillData.setDamage(damageDone);
            target = awakeParticipants.get(skillData.getTargetIndex());
            target.updateHp(target.getCurrentHp() - damageDone.calculateTotalDamage());
            roundRandomDamageDifferences.set(participantIndex, roundRandomDamageDifferences.get(participantIndex) - skillData.getDamage().getRandomDamage());
            roundRandomDamageDifferences.set(skillData.getTargetIndex(), roundRandomDamageDifferences.get(skillData.getTargetIndex()) + skillData.getDamage().getRandomDamage());

            currentRound.updateSkillData(participantIndex, skillData);

            participantIndex++;
        }

        for(int i = 0; i < cumulativeRandomDamageDifferences.size(); i++) {
            cumulativeRandomDamageDifferences.set(i, cumulativeRandomDamageDifferences.get(i) + roundRandomDamageDifferences.get(i));
        }

        participantIndex = 0;
        for(Playable participant: awakeParticipants) {
            if (!participant.isAwake() && !smellyParticipants.get(participantIndex)) {
                sleepyParticipants.add(participant);
                smellyParticipants.set(participantIndex, true);
            }
            participantIndex++;
        }
        while(!sleepyParticipants.isEmpty()) {
            participantIndex = 0;
            for (Playable participant : awakeParticipants) {
                skillData = currentRound.getSkillDataList().get(participantIndex);
                isSleepy = false;

                for (Playable sleepyParticipant : sleepyParticipants)
                    if (participant.getPlayableId() == sleepyParticipant.getPlayableId())
                        isSleepy = true;

                if (isSleepy) {
                    sleepScentDamage = cumulativeRandomDamageDifferences.get(participantIndex);
                    skillData.getDamage().setRandomDamage(skillData.getDamage().getRandomDamage() + sleepScentDamage);
                    target = awakeParticipants.get(skillData.getTargetIndex());
                    target.updateHp(target.getCurrentHp() - sleepScentDamage);
                    cumulativeRandomDamageDifferences.set(participantIndex, cumulativeRandomDamageDifferences.get(participantIndex) - sleepScentDamage);
                    cumulativeRandomDamageDifferences.set(skillData.getTargetIndex(), cumulativeRandomDamageDifferences.get(skillData.getTargetIndex()) + sleepScentDamage);
                    IOManager.promptLine(participant.getPetName() + " dealt sleep scent damage: " + sleepScentDamage);
                }
                participantIndex++;
            }
            sleepyParticipants.clear();
            participantIndex = 0;
            for(Playable participant: awakeParticipants) {
                if (participant.getCurrentHp() <= 0 && !smellyParticipants.get(participantIndex)) {
                    sleepyParticipants.add(participant);
                    smellyParticipants.set(participantIndex, true);
                }
                participantIndex++;
            }
        }

        printSkillUsedDamageDone();

        participantIndex = 0;
        for(Playable participant: awakeParticipants) {
            if (!participant.isAwake()) {
                smellyParticipants.remove(participantIndex);
                sleepyParticipants.add(participant);
            }
        }
        for (Playable participant : sleepyParticipants) {
            IOManager.promptLine(participant.getPetName() + " fell asleep with " + participant.getCurrentHp() + " HP!!");
            if (sleepyParticipants.size() != awakeParticipants.size()) {
                cumulativeRandomDamageDifferences.remove(awakeParticipants.indexOf(participant));
                awakeParticipants.remove(participant);
            }
        }

        //System.out.println(awakeParticipants.toString());
        return awakeParticipants;
    }

    /**
     * prints out end of round data like total damage and skills used.
     */
    private void printSkillUsedDamageDone()
    {
        SkillData skillData;
        Damage damageDone;
        int participantIndex = 0;
        for(Playable participant:awakeParticipants) {
            skillData = currentRound.getSkillDataList().get(participantIndex);
            damageDone = skillData.getDamage();
            IOManager.promptLine(participant.getPlayerName()+"'s chosen skill: " + skillData.getSkillChosen());
            IOManager.promptLine("Total damage: " + damageDone.calculateTotalDamage());
            IOManager.promptLine("  Conditional damage: " + damageDone.getConditionalDamage());
            IOManager.promptLine("  Random damage: " + damageDone.getRandomDamage());
            IOManager.endLine();
            participantIndex++;
        }
    }

    /**
     * updates recharge times for each pet
     */
    private void updateRechargeTimes()
    {
        //int participantIndex = 0;
        int rechargeTime;
        Playable participant;
        //DataStructures.SkillData skillData;

        for(SkillData skillData:currentRound.getSkillDataList())
        {
            participant = participants[skillData.getAttackerIndex()];
            participant.decrementRechargeTimes();

            rechargeTime = DEFAULT_RECHARGE_TIMES.get(skillData.getSkillChosen());
            participant.setRechargeTime(skillData.getSkillChosen(), rechargeTime);
        }
    }

    /**
     * resets data in roundController to handle another round
     */
    void reset()
    {
        awakeParticipants.clear();
        awakeParticipants.addAll(Arrays.asList(participants));
        cumulativeRandomDamageDifferences = new ArrayList<>(Collections.nCopies(participants.length, 0.0));
    }
}
