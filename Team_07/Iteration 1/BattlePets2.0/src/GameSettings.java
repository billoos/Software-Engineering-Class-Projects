import java.util.Objects;

public class GameSettings
{
    private final Integer DEFAULT_RNG_SEED = 7;
    private final Integer DEFAULT_NUMBER_OF_PLAYERS = 2;

    private Integer rngSeed = DEFAULT_RNG_SEED;
    private Integer numberOfPlayers = DEFAULT_NUMBER_OF_PLAYERS;
    private Integer fightsPerBattle;


    /*private GameSettings(GameSettingsBuilder gameSettingsBuilder)
    {
        if(gameSettingsBuilder.fightsPerBattle == null || gameSettingsBuilder.fightsPerBattle <= 0)
            throw new IllegalArgumentException("Must choose a number for fights per battle.");
        else
            this.fightsPerBattle = gameSettingsBuilder.fightsPerBattle;
        this.rngSeed = Objects.requireNonNullElse(gameSettingsBuilder.rngSeed, DEFAULT_RNG_SEED);
        this.numberOfPlayers = Objects.requireNonNullElse(gameSettingsBuilder.numberOfPlayers, DEFAULT_NUMBER_OF_PLAYERS);
    }*/

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

    public void setRngSeed(Integer rngSeed) {
        this.rngSeed = rngSeed;
    }

    public void setNumberOfPlayers(Integer numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public void setFightsPerBattle(Integer fightsPerBattle) {
        this.fightsPerBattle = fightsPerBattle;
    }
    /*public static class GameSettingsBuilder
    {
        int rngSeed;
        int numberOfPlayers;
        int fightsPerBattle;

        public GameSettingsBuilder() {}

        public GameSettingsBuilder withRNGSeed(Integer rngSeed)
        {

            this.rngSeed = rngSeed;
            return this;
        }

        public GameSettingsBuilder withNumPlayers(Integer numPlayers)
        {
            this.numberOfPlayers = numPlayers;
            return this;
        }

        public GameSettingsBuilder withFightsPerBattle(Integer fightsPerBattle)
        {
            this.fightsPerBattle = fightsPerBattle;
            return this;
        }

        public GameSettings build()
        {
            return new GameSettings(this);
        }
    }*/
}
