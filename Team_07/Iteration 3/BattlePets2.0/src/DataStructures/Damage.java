package DataStructures;

/**
 * calculates the damage dealt by a pet
 */
public class Damage
{
	private double randomDamage;
	private double conditionalDamage;

	/**
	 * constructor to instantiate random damage and conditional damage
	 * @param conditionalDamage the amount of conditional damage.
	 * @param randomDamage the amount of random damage.
	 */
	public Damage(double randomDamage, double conditionalDamage) {
		this.randomDamage = randomDamage;
		this.conditionalDamage = conditionalDamage;
	}

	public double getRandomDamage()
	{
		return randomDamage;
	}

	public void setRandomDamage(double randomDamage)
	{
		this.randomDamage = randomDamage;
	}
	
	public double getConditionalDamage()
	{
		return conditionalDamage;
	}
	
	public void setConditionalDamage(double conditionalDamage)
	{
		this.conditionalDamage = conditionalDamage;
	}

	/**
	 * adds the random damage and conditional damage
	 * @return the total damage dealt
	 */
	public double calculateTotalDamage()
	{
		return randomDamage + conditionalDamage;
	}

}
