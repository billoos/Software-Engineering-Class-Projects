package Controllers;

import DataStructures.GameSettings;
import Participants.HumanPet;
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
        //BattleController battleController;
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

            participants = new HumanPet[gameSettings.getNumberOfPlayers()];
            for (int i = 0; i < gameSettings.getNumberOfPlayers(); i++) {
                IOManager.endLine();
                IOManager.promptLine("Player " + (i + 1) + "'s turn to create their pet");
                participants[i] = setPet();
            }

            //Ask for regular or tournament mode
            IOManager.promptLine("Please input true if wishing to run regular mode and false if wishing to run tournament mode");
            boolean regularMode = IOManager.getInputBoolean();


            if(regularMode) {
                runRegularMode(fightsPerBattle);
            }
            else //It is a tournament
            {
                //Get the number of participants in a single tournament battle
                int participantsPerBattle = 0;
                while(participantsPerBattle < 2 || participantsPerBattle > numPlayers) {
                    IOManager.promptLine("Enter the number of pets per tournament round (At least 2 & at most equal to the number of total pets)");
                    participantsPerBattle = IOManager.getInputInteger();
                }

                runTournamentMode(participantsPerBattle, fightsPerBattle);
            }

            IOManager.promptLine("Would you like to play again? Quit (-1), Play again (Any other number)");
            input = IOManager.getInputInteger();
        }
    }


    /**
     * runTournament(int participantsPerBattle, int fightsPerBattle)
     * runs tournament logic to create a tournament
     * @param participantsPerBattle - number of participants that each battle will have
     * @param fightsPerBattle - number of fights each battle has
     */
    private void runTournamentMode(int participantsPerBattle, int fightsPerBattle)
    {
        TournamentController tournamentController = new TournamentController(participantsPerBattle, fightsPerBattle);
        tournamentController.runTournament(participants);
    }

    /**
     * runRegularMode(int fightsPerBattle)
     * runs non tournament normal battle structure
     * @param fightsPerBattle =  number of fights per battle
     */
    private void runRegularMode(int fightsPerBattle)
    {
        //Running the game
        BattleController battleController = new BattleController(fightsPerBattle);
        battleController.runBattle(participants);
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
        HumanPet.PetBuilder petBuilder = new HumanPet.PetBuilder();
        //THE FOLLOWING COMMENTED CODE PROBABLY NEEDS TO BE CHANGED BECAUSE OF THE NEW ABSTRACT
        //IOManager.promptLine("Enter true is pet is a human pet and false if it is a computer pet");
        //boolean isHumanPet = IOManager.getInputBoolean();
        //PlayerTypes playerType = null;
        //if(isHumanPet)
          //  playerType = PlayerTypes.HUMAN;
        //else
          //  playerType = PlayerTypes.COMPUTER;

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
                //THE FOLLOWING COMMENTED CODE PROBABLY NEEDS TO BE CHANGED BECAUSE OF THE NEW ABSTRACT
                //.withPlayerType(playerType)
                .build();
    }
}
