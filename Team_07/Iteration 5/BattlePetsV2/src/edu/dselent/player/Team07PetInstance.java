package edu.dselent.player;

import edu.dselent.event.*;
import edu.dselent.settings.PlayerInfo;
import edu.dselent.skill.Skills;

import java.util.*;

import static edu.dselent.player.PetTypes.INTELLIGENCE;
import static edu.dselent.player.PetTypes.POWER;
import static edu.dselent.player.PetTypes.SPEED;
import static edu.dselent.skill.Skills.*;

public class Team07PetInstance extends PetInstance{
    private FightLogger fightLogger;

    private int seed = 893429839;
    private Random random;
    private Skills toBePredicted = null;
    private double potentialReversalDamage = 0;

    public Team07PetInstance(int playableUid, PlayerInfo playerInfo){
        super(playableUid, playerInfo);
    }

    @Override
    public Skills chooseSkill() {
        Skills choice;
        //1v1 mode
        if(fightLogger.getFightSize() == 2)
            choice = chooseSkillSolos();
        //Royale Mode
        else
            choice = chooseSkillRoyale();
        if(getSkillRechargeTime(choice) != 0){
            System.out.print("recharging skill chosen");
        }
        return choice;
    }

    @Override
    public void update(Object event) {

        if(event instanceof FightStartEvent){
            clearData();
            random = new Random(seed++);
            fightLogger = new FightLogger(getPlayableUid(),(FightStartEvent)event);
        }

        else if(event instanceof RoundStartEvent){
            fightLogger.processRoundStartEvent((RoundStartEvent)event);
        }

        else if(event instanceof AttackEvent){
            AttackEvent attackEvent = (AttackEvent)event;
            updateReversalDamage(attackEvent);
            fightLogger.processAttackEvent(attackEvent);
        }
    }

    @Override
    public Set<Skills> getSkillSet() {
        return new HashSet<>(Arrays.asList(Skills.values()));
    }

    /**
     * Chooses skills in the event of one remaining player.
     * @return the chosen skill
     */
    private Skills chooseSkillSolos(){
        Skills choice = null;
        //You could also use target info, but I think we should play defensively in free for all.
        switch(fightLogger.getCurrentAttacker().getPetType()){
            case POWER:
                choice = chooseSkillSolosVSPower();
                break;
            case SPEED:
                choice = chooseSkillSolosVSSpeed();
                break;
            case INTELLIGENCE:
                choice = chooseSkillSolosVSIntelligence();
                break;
        }
        return choice;
    }

    /**
     * chooseSkillSolosVSPower()
     * chooses skill on logic versus power
     * @return skill chosen
     */
    private Skills chooseSkillSolosVSPower(){

        Skills choice;

        boolean canReversalOfFortune = getSkillRechargeTime(Skills.REVERSAL_OF_FORTUNE)==0;
        boolean canShootTheMoon= getSkillRechargeTime(Skills.SHOOT_THE_MOON)==0;
        boolean canScissorsPoke = getSkillRechargeTime(Skills.SCISSORS_POKE)==0;
        boolean canRockThrow = getSkillRechargeTime(Skills.ROCK_THROW)==0;
        boolean canPaperCut = getSkillRechargeTime(Skills.PAPER_CUT)==0;

        boolean opponentCannotReversalOfFortune = fightLogger.getCurrentVictim().getRechargeTime(Skills.REVERSAL_OF_FORTUNE)>0;
        boolean opponentCannotShootTheMoon = fightLogger.getCurrentVictim().getRechargeTime(Skills.SHOOT_THE_MOON)>0;
        boolean opponentCannotScissorsPoke = fightLogger.getCurrentVictim().getRechargeTime(Skills.SCISSORS_POKE)>0;
        boolean opponentCannotRockThrow = fightLogger.getCurrentVictim().getRechargeTime(Skills.ROCK_THROW) > 0;
        boolean opponentCannotPaperCut = fightLogger.getCurrentVictim().getRechargeTime(Skills.PAPER_CUT)>0;
        boolean opponentRechargeBasic = (opponentCannotScissorsPoke || opponentCannotRockThrow || opponentCannotPaperCut);

        List<Skills> validChoices = new ArrayList<>();
        PetData attacker = fightLogger.getCurrentAttacker();
        if(fightLogger.getCurrentAttacker().getPlayerType() != PlayerTypes.COMPUTER)
        {
            for(Skills skill:Skills.values()){
                if(attacker.getRechargeTime(skill) == 0 && skill!= REVERSAL_OF_FORTUNE)
                    validChoices.add(skill);
            }
        }
        else
        {
            for(Skills skill:Skills.values()){
                if(attacker.getRechargeTime(skill) == 0)
                    validChoices.add(skill);
            }
        }


        if(fightLogger.getCurrentVictim().getCurrentHp()<=2*potentialReversalDamage && canReversalOfFortune)
        {
            choice = Skills.REVERSAL_OF_FORTUNE;
        }
        else if(fightLogger.getMyself().getCurrentHp()<=2*fightLogger.getCurrentAttacker().getPotentialReversalDamage() &&
                canShootTheMoon&&!opponentCannotReversalOfFortune && fightLogger.getCurrentAttacker().getPlayerType() != PlayerTypes.COMPUTER)
        {
            choice = Skills.SHOOT_THE_MOON;
            toBePredicted = Skills.REVERSAL_OF_FORTUNE;
        }

        else if(opponentCannotRockThrow && canScissorsPoke)
        {
            choice = SCISSORS_POKE;
        }
        else if(opponentCannotScissorsPoke && canPaperCut)
        {
            choice = PAPER_CUT;
        }
        else if(opponentCannotPaperCut && canRockThrow)
        {
            choice = ROCK_THROW;
        }
        else if(opponentCannotShootTheMoon && canShootTheMoon) //do not care if basic or reversal is recharging
        {
            choice = SHOOT_THE_MOON;
            int choiceIndex = random.nextInt(validChoices.size());
            toBePredicted =  validChoices.get(choiceIndex);

        }
        else
        {
            int rand = random.nextInt(5);
            if(canShootTheMoon && rand == 1 && fightLogger.getCurrentAttacker().getPlayerType() != PlayerTypes.COMPUTER)
            {
                choice = SHOOT_THE_MOON;
                int choiceIndex = random.nextInt(validChoices.size());
                toBePredicted =  validChoices.get(choiceIndex);
            }
            else
            {
                choice = randomValidBasicSkillChoice();
            }
        }

        return choice;
    }
    //JACK
    private Skills chooseSkillSolosVSSpeed(){
        Skills choice = null;

        //First round
        if(fightLogger.getRoundNumber() == 0) {
            int choiceNumber = random.nextInt(2);
            switch(choiceNumber){
                case 0:
                    choice = ROCK_THROW;
                    break;
                case 1:
                    choice = PAPER_CUT;
                    break;
            }
        }
        else{ //Non-first round choice
            boolean enemyReversalOfFortuneRecharging = fightLogger.getCurrentVictim().getRechargeTime(Skills.REVERSAL_OF_FORTUNE) > 0;
            boolean enemyShootTheMoonRecharging = fightLogger.getCurrentVictim().getRechargeTime(SHOOT_THE_MOON) > 0;
            boolean enemyRockThrowRecharging = fightLogger.getCurrentVictim().getRechargeTime(ROCK_THROW) > 0;
            boolean enemyPaperCutRecharging = fightLogger.getCurrentVictim().getRechargeTime(PAPER_CUT) > 0;
            boolean enemyScissorPokeRecharging = fightLogger.getCurrentVictim().getRechargeTime(SCISSORS_POKE) > 0;
            boolean enemyBasicRecharging = (enemyRockThrowRecharging || enemyPaperCutRecharging || enemyScissorPokeRecharging);

            boolean reversalOfFortuneRecharging = getSkillRechargeTime(REVERSAL_OF_FORTUNE) > 0;
            boolean shootTheMoonRecharging = getSkillRechargeTime(SHOOT_THE_MOON) > 0;
            boolean rockThrowRecharging = getSkillRechargeTime(ROCK_THROW) > 0;
            boolean paperCutRecharging = getSkillRechargeTime(PAPER_CUT) > 0;
            boolean scissorPokeRecharging = getSkillRechargeTime(SCISSORS_POKE) > 0;

            //if they will knock us out with RoF, predict RoF
            if(fightLogger.getCurrentAttacker().getPotentialReversalDamage() >= getCurrentHp()
                    && getSkillRechargeTime(SHOOT_THE_MOON) == 0
                    && fightLogger.getCurrentAttacker().getPlayerType() != PlayerTypes.COMPUTER
                    && !enemyReversalOfFortuneRecharging)
            {
                choice = SHOOT_THE_MOON;
                toBePredicted = REVERSAL_OF_FORTUNE;
            }

            //If they have only two choices, and we can choose shoot the moon, choose shoot the moon
            else if(enemyReversalOfFortuneRecharging && enemyShootTheMoonRecharging
                    && enemyBasicRecharging && !shootTheMoonRecharging) {
                choice = SHOOT_THE_MOON;
                toBePredicted = chooseRandomAvailableFromAttacker();
            }
            //If we have a lot of damage difference, choose shoot the moon
            else if (potentialReversalDamage * 2 >= fightLogger.getCurrentVictim().getCurrentHp())
                choice = Skills.REVERSAL_OF_FORTUNE;
            else {
                //Different speed choices depending on what they could deal damage with. We either defend or attack.
                if (calculateHpPercent() >= 75) {

                    if (enemyRockThrowRecharging) {
                        if(!scissorPokeRecharging)
                            choice = SCISSORS_POKE;
                        else
                            choice = PAPER_CUT;
                    } else {
                        if(!paperCutRecharging)
                            choice = PAPER_CUT;
                        else
                            choice = ROCK_THROW;
                    }
                } else if (calculateHpPercent() >= 25 && calculateHpPercent() <= 75) {
                    if (enemyScissorPokeRecharging) {
                        if(!paperCutRecharging)
                            choice = PAPER_CUT;
                        else
                            choice = ROCK_THROW;
                    } else {
                        if(!rockThrowRecharging)
                            choice = ROCK_THROW;
                        else
                            choice = SCISSORS_POKE;
                    }
                } else if (calculateHpPercent() <= 25) {
                    if (enemyPaperCutRecharging) {
                        if(!rockThrowRecharging)
                            choice = ROCK_THROW;
                        else
                            choice = SCISSORS_POKE;
                    } else {
                        if(!scissorPokeRecharging)
                            choice = SCISSORS_POKE;
                        else
                            choice = PAPER_CUT;
                    }
                }
            }
        } //End non-first Round choice

        return choice;
    }

    private Skills chooseSkillSolosVSIntelligence(){
        Skills choice = null;//First round
        if(fightLogger.getRoundNumber() == 0) {
            choice = randomValidBasicSkillChoice();
        }
        else { //Non-first round choice
            PetData attacker = fightLogger.getCurrentAttacker();
            boolean enemyReversalOfFortuneRecharging = attacker.getRechargeTime(Skills.REVERSAL_OF_FORTUNE) > 0;
            boolean enemyShootTheMoonRecharging = attacker.getRechargeTime(Skills.SHOOT_THE_MOON) > 0;
            boolean enemyRockThrowRecharging = attacker.getRechargeTime(Skills.ROCK_THROW) > 0;
            boolean enemyPaperCutRecharging = attacker.getRechargeTime(Skills.PAPER_CUT) > 0;
            boolean enemyScissorPokeRecharging = attacker.getRechargeTime(Skills.SCISSORS_POKE) > 0;
            boolean enemyBasicRecharging = (enemyRockThrowRecharging || enemyPaperCutRecharging || enemyScissorPokeRecharging);

            //if they will knock us out with RoF, predict RoF
            if(fightLogger.getCurrentAttacker().getPotentialReversalDamage() >= getCurrentHp()
                    && getSkillRechargeTime(SHOOT_THE_MOON) == 0
                    && fightLogger.getCurrentAttacker().getPlayerType() != PlayerTypes.COMPUTER
                    && !enemyReversalOfFortuneRecharging)
            {
                choice = SHOOT_THE_MOON;
                toBePredicted = REVERSAL_OF_FORTUNE;
            }

            //else if we knock them out, choose RoF
            else if (potentialReversalDamage * 2 >= attacker.getCurrentHp())
                choice = Skills.REVERSAL_OF_FORTUNE;

            //else if they have only two choices, choose shoot the moon
            else if (enemyReversalOfFortuneRecharging
                    && enemyShootTheMoonRecharging
                    && enemyBasicRecharging
                    && getSkillRechargeTime(SHOOT_THE_MOON) == 0) {
                choice = SHOOT_THE_MOON;

                if(enemyRockThrowRecharging)
                {
                    if(getSkillRechargeTime(ROCK_THROW) == 0)
                        toBePredicted = PAPER_CUT;
                    else
                        toBePredicted = SCISSORS_POKE;
                }
                else if(enemyPaperCutRecharging)
                {
                    if(getSkillRechargeTime(PAPER_CUT) == 0)
                        toBePredicted = SCISSORS_POKE;
                    else
                        toBePredicted = ROCK_THROW;
                }
                else
                {
                    if (getSkillRechargeTime(SCISSORS_POKE) == 0)
                        toBePredicted = ROCK_THROW;
                    else
                        toBePredicted = PAPER_CUT;
                }
            }
            else
            {
                //Different choices depending on what they could deal damage with. We attack.
                if(!enemyBasicRecharging)
                    choice = randomValidBasicSkillChoice();
                else {
                    if (enemyPaperCutRecharging)
                    {
                        if(getSkillRechargeTime(PAPER_CUT) > 0)
                            choice = ROCK_THROW;
                        else
                            choice = PAPER_CUT;
                    }
                    else if(enemyScissorPokeRecharging)
                    {
                        if(getSkillRechargeTime(SCISSORS_POKE) > 0)
                            choice = PAPER_CUT;
                        else
                            choice = SCISSORS_POKE;
                    }
                    else
                    {
                        if(getSkillRechargeTime(ROCK_THROW) > 0)
                            choice = SCISSORS_POKE;
                        else
                            choice = ROCK_THROW;
                    }
                }
            }
        }//End non-first Round choice

        if(choice == null)
            choice = randomValidBasicSkillChoice();
        return choice;
    }

    /**
     * Chooses a skill in the event of royale mode
     * @return the chosen skill
     */
    private Skills chooseSkillRoyale(){
        Skills choice = null;
        PetTypes attacker = null;
        PetTypes target = null;
        switch(fightLogger.getCurrentAttacker().getPetType()){
            case POWER: attacker = POWER;
                break;
            case SPEED: attacker = SPEED;
                break;
            case INTELLIGENCE: attacker = INTELLIGENCE;
                break;
        }
        switch(fightLogger.getCurrentVictim().getPetType())
        {
            case POWER: target = POWER;
                break;
            case SPEED: target = SPEED;
                break;
            case INTELLIGENCE: target = INTELLIGENCE;
                break;
        }
        choice = getChoiceRoyale(attacker, target);
        return choice;
    }

    /**
     * Chooses a skill in royale mode
     * @param attacker the attacker type
     * @param target the target type
     * @return the chosen skill
     */
    private Skills getChoiceRoyale(PetTypes attacker, PetTypes target)
    {
        Skills optimalChoice = null;
        List<Skills> possibleChoices = new ArrayList<>();

        for (Skills skill: Skills.values())
        {
            if (getSkillRechargeTime(skill) == 0)
            {
                possibleChoices.add(skill);
            }
        }
        //Attacker Speed
        if (attacker == SPEED)
        {
            if (getCurrentHp() >= 75)
            {
                if(fightLogger.getCurrentVictim().getRechargeTime(Skills.ROCK_THROW) == 0)
                {
                    possibleChoices.remove(Skills.SCISSORS_POKE);
                    possibleChoices.remove(Skills.PAPER_CUT);
                    optimalChoice = Skills.ROCK_THROW;
                }
                else
                {
                    possibleChoices.remove(Skills.ROCK_THROW);
                }
            }
            else if(getCurrentHp() >= 25)
            {
                if(fightLogger.getCurrentVictim().getRechargeTime(Skills.SCISSORS_POKE) == 0)
                {
                    possibleChoices.remove(Skills.ROCK_THROW);
                    possibleChoices.remove(Skills.PAPER_CUT);
                    optimalChoice = Skills.SCISSORS_POKE;
                }
                else
                {
                    possibleChoices.remove(Skills.SCISSORS_POKE);
                }
            }
            else
            {
                if(fightLogger.getCurrentVictim().getRechargeTime(Skills.PAPER_CUT) == 0)
                {
                    possibleChoices.remove(Skills.ROCK_THROW);
                    possibleChoices.remove(Skills.SCISSORS_POKE);
                    optimalChoice = Skills.PAPER_CUT;
                }
                else
                {
                    possibleChoices.remove(Skills.PAPER_CUT);
                }
            }
        }
        //Attacker Intelligence
        else if(attacker == INTELLIGENCE)
        {
            possibleChoices.remove(Skills.SHOOT_THE_MOON);
        }
        //Attacker Power
        else if(attacker == POWER)
        {
            if (fightLogger.getCurrentVictim().getRechargeTime(Skills.ROCK_THROW) > 0)
            {
                optimalChoice = Skills.SCISSORS_POKE;
            }
            else if (fightLogger.getCurrentVictim().getRechargeTime(Skills.SCISSORS_POKE) > 0)
            {
                optimalChoice = Skills.PAPER_CUT;
            }
            else if (fightLogger.getCurrentVictim().getRechargeTime(Skills.PAPER_CUT) > 0)
            {
                optimalChoice = Skills.ROCK_THROW;
            }
            else if (getSkillRechargeTime(Skills.REVERSAL_OF_FORTUNE) > 0)
            {
                optimalChoice = Skills.SHOOT_THE_MOON;
                royalePredict(fightLogger.getMyself().getLastSkillUsed());
            }
            else
            {
                optimalChoice = Skills.REVERSAL_OF_FORTUNE;
            }
        }
        if (optimalChoice == null)
        {
            PetData targetOfTarget = fightLogger.getFightOrder().get(fightLogger.findById(this.getPlayableUid()) + 2 % fightLogger.getFightOrder().size());
            if(target == PetTypes.SPEED)
            {
                if(targetOfTarget.getCurrentHp() >= 75)
                {
                    optimalChoice = Skills.PAPER_CUT;
                }
                else if(targetOfTarget.getCurrentHp() >= 25)
                {
                    optimalChoice = Skills.ROCK_THROW;
                }
                else
                {
                    optimalChoice = Skills.SCISSORS_POKE;
                }
            }
            else if(target == PetTypes.INTELLIGENCE)
            {
                if (targetOfTarget.getLastSkillUsed() == Skills.ROCK_THROW)
                {
                    optimalChoice = Skills.SCISSORS_POKE;
                }
                else if (targetOfTarget.getLastSkillUsed() == Skills.PAPER_CUT)
                {
                    optimalChoice = Skills.ROCK_THROW;
                }
                else if (targetOfTarget.getLastSkillUsed() == Skills.SCISSORS_POKE)
                {
                    optimalChoice = Skills.PAPER_CUT;
                }
            }
            else
            {
                optimalChoice = Skills.PAPER_CUT;
            }
        }
        if (!possibleChoices.contains(optimalChoice))
        {
            if (possibleChoices.contains(Skills.SCISSORS_POKE))
            {
                optimalChoice = Skills.SCISSORS_POKE;
            }
            else if (possibleChoices.contains(Skills.PAPER_CUT))
            {
                optimalChoice = Skills.PAPER_CUT;
            }
            else if(possibleChoices.contains(Skills.ROCK_THROW))
            {
                optimalChoice = Skills.ROCK_THROW;
            }
            else if(possibleChoices.contains(Skills.REVERSAL_OF_FORTUNE))
            {
                optimalChoice = Skills.REVERSAL_OF_FORTUNE;
            }
            else
            {
                optimalChoice = Skills.SHOOT_THE_MOON;
                royalePredict(fightLogger.getMyself().getLastSkillUsed());
            }
        }
        return optimalChoice;
    }

    /**
     * tries to predict the next skill for shoot the moon
     * @param lastAttack the last attack performed
     */
    private void royalePredict(Skills lastAttack)
    {
        Skills skill;
        if(lastAttack == Skills.ROCK_THROW && fightLogger.getCurrentVictim().getLastSkillUsed() != Skills.SCISSORS_POKE)
        {
            skill = Skills.SCISSORS_POKE;
        }
        else if(lastAttack == Skills.SCISSORS_POKE && fightLogger.getCurrentVictim().getLastSkillUsed() != Skills.PAPER_CUT)
        {
            skill = Skills.PAPER_CUT;
        }
        else if(lastAttack == Skills.PAPER_CUT && fightLogger.getCurrentVictim().getLastSkillUsed() != Skills.ROCK_THROW)
        {
            skill = Skills.ROCK_THROW;
        }
        else
        {
            if(fightLogger.getCurrentVictim().getCurrentHp() < 10)
            {
                skill = Skills.REVERSAL_OF_FORTUNE;
            }
            else if (fightLogger.getCurrentVictim().getLastSkillUsed() != Skills.SCISSORS_POKE)
            {
                skill = Skills.SCISSORS_POKE;
            }
            else if (fightLogger.getCurrentVictim().getLastSkillUsed() != Skills.PAPER_CUT)
            {
                skill = Skills.PAPER_CUT;
            }
            else
            {
                skill = Skills.ROCK_THROW;
            }
        }
        toBePredicted = skill;
    }

    /**
     * gets a random valid skill from all skills
     * @return a random valid skill from all skills
     */
    private Skills randomValidSkillChoice(){
        Skills choice = null;
        boolean valid = false;
        while(!valid){
            choice = Skills.values()[random.nextInt(5)];
            if(getSkillRechargeTime(choice) == 0)
                valid = true;
        }
        return choice;
    }

    /**
     * gets a random valid skill from the basic three
     * @return a random valid skill from the basic three
     */
    private Skills randomValidBasicSkillChoice(){
        Skills choice = null;
        boolean valid = false;
        while(!valid){
            choice = Skills.values()[random.nextInt(3)];
            if(getSkillRechargeTime(choice) == 0)
                valid = true;
        }
        return choice;
    }

    private Skills chooseRandomAvailableFromAttacker(){
        List<Skills> validChoices = fightLogger.getCurrentAttacker().getAvailableSkills();
        int choiceIndex = random.nextInt(validChoices.size());
        return validChoices.get(choiceIndex);
    }

    /**
     * Clears all data
     */
    private void clearData(){
        fightLogger = null;
        potentialReversalDamage = 0;
    }

    @Override
    public Skills getSkillPrediction(){
        Skills tmp = toBePredicted;
        toBePredicted = null;
        return tmp;
    }

    /**
     * Adds the damage done or recieved to the potential damage from reversal of fortune
     * @param attackEvent the event to pull damage from
     */
    private void updateReversalDamage(AttackEvent attackEvent){
        if(attackEvent.getVictimPlayableUid() == super.getPlayableUid()){
            potentialReversalDamage += attackEvent.getDamage().getRandomDamage();
        }
        else if(attackEvent.getAttackingPlayableUid() == super.getPlayableUid()){
            potentialReversalDamage -= attackEvent.getDamage().getRandomDamage();
        }
    }
}
