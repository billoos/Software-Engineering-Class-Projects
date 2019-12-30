package DataStructures;

import Utilities.RandomNumber;

import java.util.Objects;

public class GameSettings
{
    private final Integer DEFAULT_RNG_SEED = 7;
    private final Integer DEFAULT_NUMBER_OF_PLAYERS = 2;

    private Integer rngSeed = DEFAULT_RNG_SEED;
    private Integer numberOfPlayers = DEFAULT_NUMBER_OF_PLAYERS;
    private Integer fightsPerBattle;

    /**
     * DataStructures.GameSettings(GameSettingsBuilder gameSettingsBuilder)
     * initializes fightsPerBattle, rngseed, number of players using builder pattern
     * @param gameSettingsBuilder
     */
    private GameSettings(GameSettingsBuilder gameSettingsBuilder)
    {
        this.fightsPerBattle = gameSettingsBuilder.fightsPerBattle;
        this.rngSeed = Objects.requireNonNullElse(gameSettingsBuilder.rngSeed, DEFAULT_RNG_SEED);
        this.numberOfPlayers = Objects.requireNonNullElse(gameSettingsBuilder.numberOfPlayers, DEFAULT_NUMBER_OF_PLAYERS);
    }

    public Integer getRngSeed()
    {
        return rngSeed;
    }

    public Integer getNumberOfPlayers()
    {
        return numberOfPlayers;
    }

    public Integer getFightsPerBattle()
    {
        return fightsPerBattle;
    }


    /**
     * Builder pattern to initialize the game settings
     */
    public static class GameSettingsBuilder
    {
        private int rngSeed;
        private int numberOfPlayers;
        private int fightsPerBattle;

        public GameSettingsBuilder() {}

        /**
         * Initializes rngSeed and sets the seed
         * @return this - current rngSeed
         */
        public GameSettingsBuilder withRNGSeed(Integer rngSeed)
        {

            this.rngSeed = rngSeed;
            RandomNumber.INSTANCE.setSeed(rngSeed);
            return this;
        }

        /**
         * Initializes numberOfPlayers
         * @return this - current numberOfPlayers
         */
        public GameSettingsBuilder withNumPlayers(Integer numPlayers)
        {
            this.numberOfPlayers = numPlayers;
            return this;
        }

        /**
         * Initializes fightsPerBattle
         * @return this - current fightsPerBattle
         */
        public GameSettingsBuilder withFightsPerBattle(Integer fightsPerBattle)
        {
            this.fightsPerBattle = fightsPerBattle;
            return this;
        }

        /**
         * DataStructures.GameSettings build()
         * using builder pattern function is called after with fucntions are called and builds a new gamesetting object
         * @return this
         */
        public GameSettings build()
        {
            return new GameSettings(this);
        }
    }
}


