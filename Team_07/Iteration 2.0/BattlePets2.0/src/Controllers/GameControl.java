package Controllers;//import java.util.InputMismatchException;

import Controllers.BattleController;
import DataStructures.GameSettings;
import Participants.Pet;
import Participants.Playable;
import Utilities.IOManager;
import Utilities.PetTypes;
import Utilities.PlayerTypes;

class GameControl
{
    //private DataStructures.GameSettings.GameSettingsBuilder gameSettingsBuilder;
    private Playable[] participants;
    private int playableId = 666;
    /**
     * run()
     * prompts user from rng seed, fights per battle, setting pet, asking to play again
     * given information and creates game, participants, and runs game
     */
    void run()
    {
        GameSettings gameSettings;
        BattleController battleController;
        int input = 1;
        while(input != -1) {
            //Get rng seed
            IOManager.promptLine("Please input your RNG Seed.");
            int rngSeed = IOManager.getInputInteger();

            //Get fights per battle
            int fightsPerBattle = -1;
            while (fightsPerBattle <= 0) {
                IOManager.promptLine("Please input a positive number for the number of fights per battle.");
                fightsPerBattle = IOManager.getInputInteger();
            }

            //Get number of players
            int numPlayers = 0;
            while(numPlayers <= 1) {
                IOManager.promptLine("Please input your number of players. (2 or more)");
                numPlayers = IOManager.getInputInteger();
            }

            //Creation and assigning of game settings
            gameSettings = new GameSettings.GameSettingsBuilder()
                    .withRNGSeed(rngSeed)
                    .withFightsPerBattle(fightsPerBattle)
                    .withNumPlayers(numPlayers)
                    .build();

            //Creation of participants
            participants = new Pet[gameSettings.getNumberOfPlayers()];
            for (int i = 0; i < gameSettings.getNumberOfPlayers(); i++) {
                IOManager.endLine();
                IOManager.promptLine("Player " + (i + 1) + "'s turn to create their pet");
                participants[i] = setPet();
            }

            //Running the game
            battleController = new BattleController(participants, fightsPerBattle);
            battleController.runBattle();

            IOManager.promptLine("Would you like to play again? Quit (-1), Play again (Any other number)");
            input = IOManager.getInputInteger();
        }
    }

    GameControl() {
    }

    /**
     * setPet()
     * builds a pet by taking in petType, name, petName, starting hp
     * @return Participants.Playable
     */
    private Playable setPet()
    {
        Pet.PetBuilder petBuilder = new Pet.PetBuilder();
        IOManager.promptLine("Choose petType: POWER(1) SPEED(2) INTELLIGENCE(3) ");
        PetTypes petType = null;
        boolean isValid = false;
        while(!isValid) {
            switch (IOManager.getInputInteger()) {
                case 1:
                    petType = PetTypes.POWER;
                    isValid = true;
                    break;
                case 2:
                    petType = PetTypes.SPEED;
                    isValid = true;
                    break;
                case 3:
                    petType = PetTypes.INTELLIGENCE;
                    isValid = true;
                    break;
                default:
                    IOManager.promptLine("Invalid pet type, please choose again.");
            }
        }

        IOManager.promptLine("What is your name?");
        String playerName = IOManager.getInputString();
        IOManager.promptLine("What is your pet's name?");
        String petName = IOManager.getInputString();
        IOManager.promptLine("What is your pet's starting HP?");
        Double startingHP = IOManager.getInputDouble();
        while (startingHP <= 0)
        {
            IOManager.promptLine("Starting HP must be greater than 0. Please reenter starting Hp: ");
            IOManager.endLine();
            startingHP = IOManager.getInputDouble();
        }

        return petBuilder.withPetName(petName)
                .withPlayerType(PlayerTypes.HUMAN)
                .withPetType(petType)
                .withStartingHp(startingHP)
                .withPlayerName(playerName)
                .withPlayableId(playableId++)
                .build();
    }
}
