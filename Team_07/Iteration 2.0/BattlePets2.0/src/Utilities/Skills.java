package Utilities;

public enum Skills
{
	ROCK_THROW,
	SCISSORS_POKE,
	PAPER_CUT,
	SHOOT_THE_MOON,
	REVERSAL_OF_FORTUNE;

	/**
	 * converts  skill Enums to strings
	 * @return the string conversion
	 */
	@Override
	public String toString()
	{		
		return Utils.convertEnumString(this.name());
	}

}
