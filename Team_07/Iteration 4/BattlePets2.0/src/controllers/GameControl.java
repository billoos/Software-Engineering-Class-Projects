package controllers;

import datastructures.GameSettings;
import participants.ComputerPet;
import participants.HumanPet;
import participants.Playable;
import utilities.IOManager;
import utilities.PetTypes;
import utilities.PlayerTypes;

import java.util.Random;

class GameControl
{
    //private datastructures.GameSettings.GameSettingsBuilder gameSettingsBuilder;
    private Playable[] participants;
    private int playableId = 666;
    private int MAX_CHOICE = 3;
    private int MIN_CHOICE = 1;
    private double DEFAULT_AI_HEALTH = 100.00;
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
            //This will vary the id based on the chosen seed
            playableId += rngSeed;


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
            //Ask for regular or tournament mode
            IOManager.promptLine("Please input true if wishing to run regular mode and false if wishing to run tournament mode");
            boolean isRegularMode = IOManager.getInputBoolean();

            //Get the number of participants in a single tournament battle
            int participantsPerTournamentBattle = 0;
            while(!isRegularMode && (participantsPerTournamentBattle < 2 || participantsPerTournamentBattle > numPlayers)) {
                IOManager.promptLine("Enter the number of pets per tournament round (At least 2 & at most equal to the number of total pets)");
                participantsPerTournamentBattle = IOManager.getInputInteger();
            }

            gameSettings = new GameSettings.GameSettingsBuilder()
                    .withRNGSeed(rngSeed)
                    .withFightsPerBattle(fightsPerBattle)
                    .withNumPlayers(numPlayers)
                    .withIsRegularMode(isRegularMode)
                    .withParticipantsPerTournamentBattle(participantsPerTournamentBattle)
                    .build();


            participants = new Playable[gameSettings.getNumberOfPlayers()];
            for (int i = 0; i < gameSettings.getNumberOfPlayers(); i++) {
                IOManager.endLine();
                IOManager.promptLine("Player " + (i + 1) + "'s turn to create their pet");
                participants[i] = setPet();
            }

            if(isRegularMode) {
                runRegularMode(fightsPerBattle);
            }
            else //It is a tournament
            {
                runTournamentMode(gameSettings.getParticipantsPerTournamentBattle(), fightsPerBattle);
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

    /**
     * setPet()
     * builds a pet by taking in petType, name, petName, starting hp
     * @return Participants.Playable
     */
    private Playable setPet()
    {

        IOManager.promptLine("Enter 1 if the pet is a human pet, 2 if it is a computer pet, " +
                             "and 3 if it is an auto generated computer pet");
        int isHumanPet = IOManager.getInputInteger();

        if (isHumanPet != 1 && isHumanPet != 2 && isHumanPet != 3)
        {
            IOManager.promptLine("Invalid pet type, please choose again.");
            return setPet();
        }

        //old code
        /*
        IOManager.promptLine("Enter true if the pet is a human pet and false if it is a computer pet");
        boolean isHumanPet = IOManager.getInputBoolean();


        String randomChoice = "";
        if (isHumanPet == 3) {

            IOManager.promptLine("Would you like it automatically generated? \"/\" for yes, any other key for no");

            randomChoice =  IOManager.getInputString();

        }


        if (randomChoice.equals("/"))
*/

        // The user wishes the pet to be randomly generated
        if (isHumanPet == 3)
        {
            int max = MAX_CHOICE;
            int min = MIN_CHOICE;
            String tempRandomName = generateRandomName(4);

            ComputerPet.PetBuilder petBuilder = new ComputerPet.PetBuilder();

            IOManager.promptLine("Choose petType: POWER(1) SPEED(2) INTELLIGENCE(3) ");
            Random r = new Random();
            PetTypes AIPetType = PetTypes.values() [(r.nextInt((max - min) + 1) + min) - 1];
            IOManager.promptLine(AIPetType.toString());

            IOManager.promptLine("What is your name?");
            String AIPlayerName = "Player: " + playableId + " " + tempRandomName;
            IOManager.promptLine(AIPlayerName);

            IOManager.promptLine("What is your pet's name?");
            String AIPetName = "Pet: " + playableId + " " + tempRandomName;
            IOManager.promptLine(AIPetName);

            IOManager.promptLine("What is your pet's starting HP?");
            Double AIHP = DEFAULT_AI_HEALTH;
            IOManager.promptLine("HP:" + AIHP);


            return petBuilder.withPetName(AIPetName)
                    .withPlayerType(PlayerTypes.COMPUTER)
                    .withPetType(AIPetType)
                    .withStartingHp(AIHP)
                    .withPlayerName(AIPlayerName)
                    .withPlayableId(playableId++)
                    .build();
        }

        IOManager.promptLine("Choose petType: POWER(1) SPEED(2) INTELLIGENCE(3) ");
        PetTypes petType = null;
        boolean isValid = false;
        while(!isValid) {
            int choice = IOManager.getInputInteger();
            if(choice <= PetTypes.values().length && choice >= 1) {
                petType = PetTypes.values()[choice - 1];
                isValid = true;
            }
            else
                IOManager.promptLine("Invalid pet type, please choose again.");
            /* Old code
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
            }*/
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

            //Create with the specified parameters
        //TODO May have to be changed if we make the pet builder generic.
        if(isHumanPet == 1) {
            HumanPet.PetBuilder petBuilder = new HumanPet.PetBuilder();
            return petBuilder.withPetName(petName)
                    .withPlayerType(PlayerTypes.HUMAN)
                    .withPetType(petType)
                    .withStartingHp(startingHP)
                    .withPlayerName(playerName)
                    .withPlayableId(playableId++)
                    .build();
        }

        else
        {

            ComputerPet.PetBuilder petBuilder = new ComputerPet.PetBuilder();

            return petBuilder.withPetName(petName)
                    .withPlayerType(PlayerTypes.COMPUTER)
                    .withPetType(petType)
                    .withStartingHp(startingHP)
                    .withPlayerName(playerName)
                    .withPlayableId(playableId++)
                    .build();

        }
    }

    private String generateRandomName(int length) {
        //auto generated strings
        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
}
