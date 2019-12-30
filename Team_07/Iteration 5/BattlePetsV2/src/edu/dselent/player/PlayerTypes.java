package edu.dselent.player;

import edu.dselent.utils.Utils;

public enum PlayerTypes
{
	HUMAN,
	COMPUTER,
	// Add more here
	TEAM07;
	
	@Override
	public String toString()
	{		
		return Utils.convertEnumString(this.name());
	}
}
