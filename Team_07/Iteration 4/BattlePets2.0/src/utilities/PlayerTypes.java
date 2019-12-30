package utilities;

public enum PlayerTypes
{
	HUMAN,
	COMPUTER;
	
	@Override
	public String toString()
	{		
		return Utils.convertEnumString(this.name());
	}
}
