package Participants;
//This class is currently irrelevant.
//It is for the eventuality that a player might later own multiple pets.
//This class can be used in that circumstance to easier facilitate the switch.
import DataStructures.Fight;
import Utilities.PlayerTypes;

public class Player
{
    private PlayerTypes playerTypes;
    private HumanPet pet;
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
    public HumanPet getPet() {
        return pet;
    }
    public PlayerTypes getPlayerTypes() {
        return playerTypes;
    }
    public void setFightsWon(int fightsWon) {
        this.fightsWon = fightsWon;
    }

    public void setPet(HumanPet pet) {
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
