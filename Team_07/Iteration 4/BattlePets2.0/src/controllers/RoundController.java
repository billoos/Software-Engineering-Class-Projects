package controllers;

import datastructures.Damage;
import datastructures.Round;
import datastructures.SkillData;
import events.AttackEvent;
import events.AttackEventShootTheMoon;
import participants.Playable;
import utilities.EventBus;
import utilities.IOManager;
import utilities.RandomNumber;
import utilities.Skills;

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
    private List<Boolean> smellyParticipants;
    private List<Playable> sleepyParticipants;
    private Round currentRound;
    private EventBus eventBus;

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
    RoundController(Playable[] participants, EventBus eventBus)
    {
        this.participants = participants;
        int startingLength = participants.length;
        awakeParticipants.addAll(Arrays.asList(participants));
        cumulativeRandomDamageDifferences = new ArrayList<>(Collections.nCopies(startingLength, 0.0));
        roundRandomDamageDifferences = new ArrayList<>(Collections.nCopies(startingLength, 0.0));
        smellyParticipants = new ArrayList<>(Collections.nCopies(startingLength, false));
        sleepyParticipants = new ArrayList<>(startingLength);
        this.eventBus = eventBus;
    }

    /**
     * runRound() runs round logic
     * Starts by prompting user to choose pet information(type, hp, name)
     * Each pet then chooses skill if inputs are correct damage is then calculated
     * After damage is calculated information is displayed to the user about different damage types per pet
     * Set recharge times
     * @return - currentRound the round that has just been run
     */
    Round runRound()
    {
        currentRound = new Round();
        printPetInfo();             //Print initial pet information for the round
        petsChooseSkill();          //Both pets choose skills first
        dealDamage();               //Then the damage is done and calculated for all skills
        sleepScent();               //deals any needed sleep scent damage
        printSkillUsedDamageDone(); //prints skills used and damage dealt by each
        updateRechargeTimes();      //Loops through pets and sets recharge times properly
        removeEliminatedParticipants();//removes sleeping participants from awakeParticipants
        currentRound.setFinalAwakePets(awakeParticipants); //Awake pets in current round (used for winner calculations)
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
                if (awakeParticipants.get(skillData.getTargetIndex()).calculateHpPercent() < 0.25 &&
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
                if (awakeParticipants.get(skillData.getTargetIndex()).getSkillRechargeTime(Skills.SHOOT_THE_MOON) > 0)
                    condDamage += 2.0;
                break;
            case SCISSORS_POKE:
                if (awakeParticipants.get(skillData.getTargetIndex()).getSkillRechargeTime(Skills.PAPER_CUT) > 0)
                    condDamage += 3.0;
                if (awakeParticipants.get(skillData.getTargetIndex()).getSkillRechargeTime(Skills.SCISSORS_POKE) > 0)
                    condDamage += 2.0;
                if (awakeParticipants.get(skillData.getTargetIndex()).getSkillRechargeTime(Skills.SHOOT_THE_MOON) > 0)
                    condDamage += 2.0;
                break;
            case PAPER_CUT:
                if (awakeParticipants.get(skillData.getTargetIndex()).getSkillRechargeTime(Skills.ROCK_THROW) > 0)
                    condDamage += 3.0;
                if (awakeParticipants.get(skillData.getTargetIndex()).getSkillRechargeTime(Skills.PAPER_CUT) > 0)
                    condDamage += 2.0;
                if (awakeParticipants.get(skillData.getTargetIndex()).getSkillRechargeTime(Skills.SHOOT_THE_MOON) > 0)
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
     * printPetInfo()
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
     * petsChooseSkill()
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
     * dealDamage()
     * Beast of a function that deals damage to each pet based on the skills they used, calculates
     * cumulativeRandomDamage, and handles sleep scent
     */
    private void dealDamage()
    {
        int participantIndex;
        Playable target;
        SkillData skillData;
        Damage damageDone;
        double randomDamageDone;

        //reset roundRandomDamageDifferences and smellyPets for a new round;
        for(participantIndex = 0; participantIndex < awakeParticipants.size(); participantIndex++) {
            roundRandomDamageDifferences.set(participantIndex, 0.0);
            smellyParticipants.set(participantIndex, false);
        }

        //calculate damage for each participant in the round
        for(participantIndex = 0; participantIndex < awakeParticipants.size(); participantIndex++){
            skillData = currentRound.getSkillDataList().get(participantIndex);
            if(skillData.getAttackerIndex() != participantIndex)
                throw new IllegalStateException("Skill data: " + skillData.getAttackerIndex()
                        + " is desynced with participant: " + participantIndex);

            damageDone = calculateSkillDamage(skillData);
            skillData.setDamage(damageDone);
            target = awakeParticipants.get(skillData.getTargetIndex());
            target.updateHp(target.getCurrentHp() - damageDone.calculateTotalDamage());
            if (skillData.getSkillChosen() == Skills.SHOOT_THE_MOON)
            {
                AttackEventShootTheMoon.AttackEventShootTheMoonBuilder attackEventShootTheMoonBuilder = new AttackEventShootTheMoon.AttackEventShootTheMoonBuilder();
                AttackEventShootTheMoon attackEventShootTheMoon = attackEventShootTheMoonBuilder
                        .withAttackingPlayableUid(awakeParticipants.get(skillData.getAttackerIndex()).getPlayableId())
                        .withVictimPlayableUid(awakeParticipants.get(skillData.getTargetIndex()).getPlayableId())
                        .withAttackingSkillChoice(skillData.getSkillChosen())
                        .withDamage(damageDone)
                        .withPredictedSkillEnum(skillData.getSkillPredicted())
                        .build();
                eventBus.notify(attackEventShootTheMoon);
            }
            else
            {
                eventBus.notify(new AttackEvent(awakeParticipants.get(skillData.getAttackerIndex()).getPlayableId(),
                                                awakeParticipants.get(skillData.getTargetIndex()).getPlayableId(),
                                                skillData.getSkillChosen(),
                                                damageDone));
            }

            randomDamageDone = skillData.getDamage().getRandomDamage();
            roundRandomDamageDifferences.set(skillData.getAttackerIndex(),
                    roundRandomDamageDifferences.get(participantIndex) - randomDamageDone);
            roundRandomDamageDifferences.set(skillData.getTargetIndex(),
                    roundRandomDamageDifferences.get(skillData.getTargetIndex()) + randomDamageDone);

            currentRound.updateSkillData(participantIndex, skillData);
        }

        //update cumulativeDamageDifferences using roundRandomDamageDifferences
        for(participantIndex = 0; participantIndex < cumulativeRandomDamageDifferences.size(); participantIndex++)
            cumulativeRandomDamageDifferences.set(participantIndex,
                    cumulativeRandomDamageDifferences.get(participantIndex)
                            + roundRandomDamageDifferences.get(participantIndex));
    }

    /**
     * sleepScent()
     * This method goes through the list of participants and deals sleep scent damages for any pets that qualify.
     */
    private void sleepScent()
    {
        int participantIndex;
        int targetIndex;
        double sleepScentDamage;
        boolean isSleepy;
        SkillData skillData;
        Playable target;

        while(!determineSleepScentParticipants()) {
            participantIndex = 0;
            for (Playable participant : awakeParticipants) {
                skillData = currentRound.getSkillDataList().get(participantIndex);

                //check if the current pet is in the sleepyParticipant list
                isSleepy = false;
                for (Playable sleepyParticipant : sleepyParticipants)
                    if (participant.getPlayableId() == sleepyParticipant.getPlayableId())
                        isSleepy = true;

                //deal sleep scent if pet was in sleepyParticipant
                if (isSleepy) {
                    sleepScentDamage = cumulativeRandomDamageDifferences.get(participantIndex);
                    skillData.getDamage().setRandomDamage(skillData.getDamage().getRandomDamage() + sleepScentDamage);

                    targetIndex = skillData.getTargetIndex();
                    target = awakeParticipants.get(targetIndex);
                    target.updateHp(target.getCurrentHp() - sleepScentDamage);
                    cumulativeRandomDamageDifferences.set(participantIndex,
                            cumulativeRandomDamageDifferences.get(participantIndex) - sleepScentDamage);
                    cumulativeRandomDamageDifferences.set(targetIndex,
                            cumulativeRandomDamageDifferences.get(targetIndex) + sleepScentDamage);

                    IOManager.promptLine(participant.getPetName() + " dealt sleep scent damage: " + sleepScentDamage);
                }
                participantIndex++;
            }
        }
    }

    /**
     * determineSleepScentParticipants()
     * This method goes through the list of participants and find any that qualify for sleep scent
     * @return - if there are any more participants to run sleep scent for
     */
    private boolean determineSleepScentParticipants()
    {
        sleepyParticipants.clear();
        int participantIndex = 0;
        for(Playable participant: awakeParticipants) {
            if (!participant.isAwake() && !smellyParticipants.get(participantIndex)) {
                sleepyParticipants.add(participant);
                smellyParticipants.set(participantIndex, true);
            }
            participantIndex++;
        }
        return sleepyParticipants.isEmpty();
    }

    /**
     * removeEliminatedParticipants()
     * This method removes eliminated participants after sleep scent has finished.
     */
    private void removeEliminatedParticipants()
    {
        int participantIndex;

        //sets up sleepy participants
        for(Playable participant: awakeParticipants)
            if (!participant.isAwake())
                sleepyParticipants.add(participant);

        //removes sleeping pets from all relevant lists.
        for (Playable participant : sleepyParticipants) {
            IOManager.promptLine(participant.getPetName() + " fell asleep with "
                    + participant.getCurrentHp() + " HP!!");
            if (sleepyParticipants.size() != awakeParticipants.size()) {
                participantIndex = awakeParticipants.indexOf(participant);
                cumulativeRandomDamageDifferences.remove(participantIndex);
                roundRandomDamageDifferences.remove(participantIndex);
                smellyParticipants.remove(participantIndex);
                awakeParticipants.remove(participant);
            }
        }
    }

    /**
     * printSkillUsedDamageDone()
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
     * updateRechargeTimes()
     * updates recharge times for each pet
     */
    private void updateRechargeTimes()
    {
        int rechargeTime;
        Playable participant;

        for(SkillData skillData:currentRound.getSkillDataList())
        {
            participant = awakeParticipants.get(skillData.getAttackerIndex());
            participant.decrementRechargeTimes();

            rechargeTime = DEFAULT_RECHARGE_TIMES.get(skillData.getSkillChosen());
            participant.setRechargeTime(skillData.getSkillChosen(), rechargeTime);
        }
    }

    /**
     * reset()
     * resets data in roundController to handle another round
     */
    void reset()
    {
        awakeParticipants.clear();
        awakeParticipants.addAll(Arrays.asList(participants));
        cumulativeRandomDamageDifferences = new ArrayList<>(Collections.nCopies(participants.length, 0.0));
        roundRandomDamageDifferences = new ArrayList<>(Collections.nCopies(participants.length, 0.0));
        smellyParticipants = new ArrayList<>(Collections.nCopies(participants.length, false));
    }
}
