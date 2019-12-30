package datastructures;

import utilities.IOManager;

public class PlayableStatistics {

    //Win statistics
    private int totalBattlesWon = 0;
    private int totalFightsWon = 0;
    private int battleFightsWon = 0;

    //Participation
    private int battlesParticipatedIn = 0;
    private int fightsParticipatedIn = 0;
    private int roundsParticipatedIn = 0;
    private int battleRoundsParticipatedIn = 0;

    //Damage Dealt
    private Damage totalDamageDealt = new Damage(0,0);
    private Damage battleDamageDealt = new Damage(0,0);
    private Damage fightDamageDealt  = new Damage(0,0);

    //Damage received
    private Damage totalDamageReceived = new Damage(0,0);
    private Damage battleDamageReceived = new Damage(0,0);
    private Damage fightDamageReceived = new Damage(0,0);

    public void battleWon(){
        totalBattlesWon++;
    }

    public void fightWon(){
        totalFightsWon++;
        battleFightsWon++;
    }

    public void battleStart(){
        battlesParticipatedIn++;
    }

    public void fightStart(){
        fightsParticipatedIn++;
    }

    public void roundStart(boolean isAwake){
        if(isAwake) {
            battleRoundsParticipatedIn++;
            roundsParticipatedIn++;
        }
    }

    public void battleEnd(){
        battleDamageReceived.setRandomDamage(0);
        battleDamageReceived.setConditionalDamage(0);

        battleDamageDealt.setRandomDamage(0);
        battleDamageDealt.setConditionalDamage(0);

        battleFightsWon = 0;
        battleRoundsParticipatedIn = 0;
    }

    public void fightEnd(){
        fightDamageReceived.setRandomDamage(0);
        fightDamageReceived.setConditionalDamage(0);

        fightDamageDealt.setRandomDamage(0);
        fightDamageDealt.setConditionalDamage(0);
    }

    public void damageReceived(Damage damage){
        sumDamages(totalDamageReceived, damage);
        sumDamages(battleDamageReceived, damage);
        sumDamages(fightDamageReceived, damage);
    }

    public void damageDone(Damage damage){
        sumDamages(totalDamageDealt, damage);
        sumDamages(battleDamageDealt, damage);
        sumDamages(fightDamageDealt, damage);
    }

    //basically +=
    private void sumDamages(Damage original, Damage additional){
        original.setConditionalDamage(original.getConditionalDamage() + additional.getConditionalDamage());
        original.setRandomDamage(original.getRandomDamage() + additional.getRandomDamage());
    }

    /**
     * Displays the statistics for the Pet
     */
    public void display(){
        //Participation
        //IOManager.promptLine("  Battles Participated in: " + battlesParticipatedIn);
        IOManager.promptLine("  Fights Participated in: " + fightsParticipatedIn);
        IOManager.promptLine("  Rounds Participated in: " + roundsParticipatedIn);
        //Wins
        //IOManager.promptLine("  Total Battles Won: " + totalBattlesWon);
        //IOManager.promptLine("  Total Fights Won: " + totalFightsWon);
        //Damage dealt
        IOManager.promptLine("  Total Damage Dealt: " + totalDamageDealt.calculateTotalDamage());
        IOManager.promptLine("      Total Random Damage Dealt: " + totalDamageDealt.getRandomDamage());
        IOManager.promptLine("      Total Conditional Damage Dealt: " + totalDamageDealt.getConditionalDamage());
        IOManager.promptLine("      Damage Dealt per Round: " + totalDamageDealt.calculateTotalDamage()/roundsParticipatedIn);
        //Damage received
        IOManager.promptLine("  Total Damage Received: " + totalDamageReceived.calculateTotalDamage());
        IOManager.promptLine("      Total Random Damage Received: " + totalDamageReceived.getRandomDamage());
        IOManager.promptLine("      Total Conditional Damage Received: " + totalDamageReceived.getConditionalDamage());
        IOManager.promptLine("      Damage Received per Round: " + totalDamageReceived.calculateTotalDamage()/roundsParticipatedIn);
    }

    //GETTERS
    public int getTotalFightsWon() {
        return totalFightsWon;
    }

    public int getBattleFightsWon() {
        return battleFightsWon;
    }

    public int getFightsParticipatedIn() {
        return fightsParticipatedIn;
    }

    public int getBattlesParticipatedIn() {
        return battlesParticipatedIn;
    }

    public Damage getTotalDamageDealt() {
        return totalDamageDealt;
    }

    public Damage getBattleDamageDealt() {
        return battleDamageDealt;
    }

    public Damage getFightDamageDealt() {
        return fightDamageDealt;
    }

    public Damage getTotalDamageReceived() {
        return totalDamageReceived;
    }

    public Damage getBattleDamageReceived() {
        return battleDamageReceived;
    }

    public Damage getFightDamageReceived() {
        return fightDamageReceived;
    }
}
