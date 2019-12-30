package Participants;

import DataStructures.Fight;
import Utilities.PlayerTypes;

public class Player
{
    private PlayerTypes playerTypes;
    private Pet pet;
    private int fightsWon;
    private String playerName;

    public Player() {
        playerTypes = PlayerTypes.HUMAN;
        //pet default
        fightsWon = 0;


    }
    public String getPlayerName() {
        return playerName;
    }
    public Pet getPet() {
        return pet;
    }
    public PlayerTypes getPlayerTypes() {
        return playerTypes;
    }
    public void setFightsWon(int fightsWon) {
        this.fightsWon = fightsWon;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setPlayerTypes(PlayerTypes playerTypes) {
        this.playerTypes = playerTypes;
    }

    public void setFightsWon(Fight[] fights){
        fightsWon = fights.length;
    }
    public int getFightsWon(){
        return fightsWon;
    }

}
