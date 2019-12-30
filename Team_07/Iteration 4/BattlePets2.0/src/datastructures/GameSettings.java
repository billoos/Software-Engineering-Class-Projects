package datastructures;

import utilities.RandomNumber;

import java.util.Objects;

public class GameSettings
{
    private final Integer DEFAULT_RNG_SEED = 7;
    private final Integer DEFAULT_NUMBER_OF_PLAYERS = 2;
    private final Boolean DEFAULT_ISREGULARMODE = true;
    private final Integer DEFAULT_PARTICIPANTSPERTOURNAMENTBATTLE = 0;

    private Integer rngSeed = DEFAULT_RNG_SEED;
    private Integer numberOfPlayers = DEFAULT_NUMBER_OF_PLAYERS;
    private Integer fightsPerBattle;
    private Boolean isRegularMode = DEFAULT_ISREGULARMODE;
    private Integer participantsPerTournamentBattle;

    /**
     * datastructures.GameSettings(GameSettingsBuilder gameSettingsBuilder)
     * initializes fightsPerBattle, rngseed, number of players using builder pattern
     * @param gameSettingsBuilder
     */
    private GameSettings(GameSettingsBuilder gameSettingsBuilder)
    {
        this.fightsPerBattle = gameSettingsBuilder.fightsPerBattle;
        this.rngSeed = Objects.requireNonNullElse(gameSettingsBuilder.rngSeed, DEFAULT_RNG_SEED);
        this.numberOfPlayers = Objects.requireNonNullElse(gameSettingsBuilder.numberOfPlayers, DEFAULT_NUMBER_OF_PLAYERS);
        this.isRegularMode = Objects.requireNonNullElse(gameSettingsBuilder.isRegularMode ,DEFAULT_ISREGULARMODE);
        this.participantsPerTournamentBattle = Objects.requireNonNullElse(gameSettingsBuilder.participantsPerTournamentBattle, DEFAULT_PARTICIPANTSPERTOURNAMENTBATTLE);
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

    public Boolean getIsRegularMode(){return isRegularMode;}

    public Integer getParticipantsPerTournamentBattle(){return participantsPerTournamentBattle;}


    /**
     * Builder pattern to initialize the game settings
     */
    public static class GameSettingsBuilder
    {
        private int rngSeed;
        private int numberOfPlayers;
        private int fightsPerBattle;
        private boolean isRegularMode;
        private int participantsPerTournamentBattle;

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
         * Initializes isRegularMode
         * @return this - current isRegularMode
         */

        public GameSettingsBuilder withIsRegularMode(Boolean isRegularMode)
        {
            this.isRegularMode = isRegularMode;
            return this;
        }

        /**
         * Initializes participantsPerTournamentBattle
         * @return this - current participantsPerTournamentBattle
         */

        public GameSettingsBuilder withParticipantsPerTournamentBattle(Integer participantsPerTournamentBattle)
        {
            this.participantsPerTournamentBattle = participantsPerTournamentBattle;
            return this;
        }

        /**
         * datastructures.GameSettings build()
         * using builder pattern function is called after with fucntions are called and builds a new gamesetting object
         * @return this
         */
        public GameSettings build()
        {
            return new GameSettings(this);
        }
    }
}


