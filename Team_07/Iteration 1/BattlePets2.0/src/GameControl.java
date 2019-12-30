import java.util.InputMismatchException;
import java.util.Scanner;

public class GameControl

{
    //private GameSettings.GameSettingsBuilder gameSettingsBuilder;
    private Pet[] participants;

    /**
     * run()
     * prompts user from rng seed, fights per battle,setting pet, asking to play again
     * given information and creates game,participants, and runs game
     */
    public void run()
    {
        GameSettings gameSettings;
        BattleController battleController;
        int input = 1;
        while(input != -1) {
            //Get rng seed
            IOManager.promptLine("Please input your RNG Seed");
            int rngSeed = IOManager.getInputInteger();

            //Get fights per battle
            int fightsPerBattle = -1;
            while (fightsPerBattle <= 0) {
                IOManager.promptLine("Please input a positive number for the number of fights per battle");
                fightsPerBattle = IOManager.getInputInteger();
            }

            //Creation and assigning of game settings
            gameSettings = new GameSettings();
            gameSettings.setRngSeed(rngSeed);
            gameSettings.setFightsPerBattle(fightsPerBattle);

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

    public GameControl() {
    }

    /**
     * setPet()
     * builds a pet by taking in petType, name, petName, starting hp
     * @return pet
     */
    private Pet setPet()
    {
        Pet.PetBuilder petBuilder = new Pet.PetBuilder();
        IOManager.promptLine("Choose petType: POWER(1) SPEED(2) INTELLIGENCE(3) ");
        PetTypes petType;
        switch (IOManager.getInputInteger())
        {
            case 1:
                petType = PetTypes.POWER;
                break;
            case 2:
                petType = PetTypes.SPEED;
                break;
            case 3:
                petType = PetTypes.INTELLIGENCE;
                break;
            default:
                throw new InputMismatchException("Incorrect Input");
        }

        IOManager.promptLine("What is your name?");
        String playerName = IOManager.getInputString();
        IOManager.promptLine("What is your pet's name?");
        String petName = IOManager.getInputString();
        IOManager.promptLine("What is your pet's starting HP?");
        Double startingHP = IOManager.getInputDouble();

        return petBuilder.withPetName(petName)
                .withPlayerType(PlayerTypes.HUMAN)
                .withPetType(petType)
                .withStartingHp(startingHP)
                .withPlayerName(playerName)
                .build();
    }
}
