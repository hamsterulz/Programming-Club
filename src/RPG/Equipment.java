package RPG;

import java.io.*;
import java.util.Arrays;
import java.util.Random;

public class Equipment implements Serializable {
	public static final String[] WeaponAttributes = new String[]{
			"Accurate",
			"Advanced",
			"Anti-Tank",
			"Atomic",
			"Awkward",
			"Bad",
			"Beautiful",
			"Broken",
			"Chemical",
			"Concealed",
			"Crude",
			"Customary",
			"Dangerous",
			"Deadly",
			"Devastating",
			"Effective",
			"Excellent",
			"Fatal",
			"Fearsome",
			"Fine",
			"Good",
			"Great",
			"Gross",
			"Heavy",
			"Keen",
			"Large",
			"Lethal",
			"Magic",
			"Old",
			"Ordinary",
			"Poor",
			"Powerful",
			"Reliable",
			"Sad",
			"Sharp",
			"Small",
			"Terrible",
			"Tiny",
			"Ultamite",
			"Unusual",
			"Useless",
			"Weak",};
	
	
	
	public String name;
	public String type;
	public int level;
	public double levelMod = 1.25;
	public double xP = 0;
	public double xPGain = 100;
	//weapon stats
	private int[] range;
	public int[] accuracy;
	public double attack;
	public int stunChance;
	public int[] bleedChance, bleedNumber;
	public int bN;
	public int critChance;
	public int critRating;
	public int disarmChance;
	public int recoilChance = 0;
	public int instantKillChance = 0;	
	
	/**
	 * Weapon:		Range		Acc			damage		LvlUp		stun%		bleed(3 turns)	disarm%		Crit%	CritRating	add effect			
	 * Sword		1			75			5			*1.25		10							10			10		+100%		benefit against sword opponent?
	 * Cross-Bow	1,2,3		90,70,60	6.5																10		+100%
	 * Long-Bow		2,3,4		75,55,45	6																10		+100%
	 * Axe			1			60			6.5													15			10		+150%
	 * Pike			1,2			40,70		6																10		+100%
	 * Spear		1			70			5.5																10		+100%		Throw Spear
	 * Dagger		1			80			2									3 p/t 45% 					10		+100%
	 * Hammer		1			75			4						15							1.5			10		+100%
	 * Fists		1			95			3.5													5			10		+100%
	 * Staff		1,2,3		90,90,90	4						5										10		+100%		Increase mana
	 * Throw Knife	1,2			80,75		6.5																10		+100%
	 * Shuriken		1,2,3		95,90,85	4.5															10		+100%
	 * LightSaber	1,2,3,4,5	100			999 					100							100			100		+100%		If hits, target dies
	 * 
	 * When reaches lvl 10 get ultamite ability?
	 * 
	Accurate	+10% Acc
	Advanced	+10% lvlUp modifier
	Anti-Tank	increased damage to bosses (+100%dmg)
	Atomic		Weapon has bleed 2 p/t 50%
	Awkward		-10% Acc
	Bad			-10% Acc -3 dmg
	Beautiful	-5% Acc +5% dmg
	Broken		50% chance to break (ALT -50% dmg)
	Chemical	Gain random buff/debuff everytime you hit
	Concealed	Decrease chance that A.I attacks(needs to be worked on)
	Crude		Xp gain -15%
	Customary	If have ordinary and customary on same weapon, gain all weapon effects
	Dangerous	+100% dmg 	20% chance to hit yourself
	Deadly		+15% dmg
	Devastating	+10% critChance	+5% critRating
	Effective	+5% dmg
	Excellent	+15% Acc +10% dmg
	Fatal		5% chance to insta-kill enemy (not bosses)
	Fearsome	+25% stun chance
	Fine		+7% Acc +2 dmg
	Good		+5% Acc +1 dmg
	Great		+10% Acc +3 dmg
	Gross		-10% Acc 	10% chance holder is infected with bleed 1  On hit, 10% change target is infected with bleed 2?
	Heavy		-15% Acc +20% dmg +10% stun
	Keen		shiny
	Large		-10% Acc +20% dmg
	Lethal		10% chance to insta-kill enemy (not boss)
	Magic		%5 chance to teleport player to random spot on map on hit, 5% chance to teleport the target to a random place on the map (not bosses)
	Old			-2 dmg
	Ordinary	If have ordinary and customary on same weapon, gain all weapon effects
	Poor		-5% Acc -1 dmg
	Powerful	+20% dmg
	Reliable	+(100-oldAcc)*.5 Acc
	Sad			Have effect bleed 2 while equipped
	Sharp		Armor is ignored
	Small		(dagger ?+10% Acc : -10% Acc)  -20% dmg
	Terrible	-15% Acc -10% dmg
	Tiny		(dagger ?+10% Acc : -10% Acc)  -30% dmg
	Ultamite	shiny +10% stun + 10% Acc +10% dmg +10% chance insta-kill (not bosses)
	Unusual		1% to (teleport wielder, turn into a frog (1 dmg, 1 turn), decrease Acc to 0 (1 turn), +400% dmg (1 turn))
	Useless		It's useless (0 Absolute Acc)
	Weak		-20% dmg
	*/
	
	/**	Armor:						Defense
	 * Chestplate:Heavy	Armor			 			
	 * 
	 * Molten						500
	 * DragonScale					300
	 * Titanium						200
	 * Gold							120
	 * TurtleShell					70
	 * Steel						30
	 * Ivory						15
	 * Wood							10
	 * 
	 * Adjectives
	 * 
	 * 
	 * Tunic:LightArmor			
	 * 
	 * Mithril						200	
	 * Scalemail					100	
	 * Silk							35
	 * Chainmail					50
	 * Reinforced Leather			25
	 * Leather						10
	 * Wool							5
	 * Cloth						2
	 * 
	 * Heavy Headgear			- Less Vision/Attack
	 * 
	 * Molten Crown					120
	 * DragonScale					95
	 * Titanium FaceGuard			65
	 * Gold							45
	 * BearHide						35
	 * Steel GreatHelm				17
	 * Ivory FaceMask				10
	 * Wood Cap						4
	 * 
	 * Light Headgear			- Extra Vision? Extra Attack
	 * 
	 * Mithril						50
	 * Scalemail					40				
	 * Silk							30		
	 * Chainmail					20
	 * Reinforced Leather			15
	 * Leather						10			
	 * Wool							5
	 * Cloth						2
	 * 
	 * Heavy Boots				- Less Speed
	 * 
	 * Molten 						100
	 * DragonScale					80
	 * Titanium 					60
	 * Gold							42
	 * BearHide						30
	 * Steel 						13
	 * Ivory 						7
	 * Wood 						2						
	 * 
	 * Light Boots				- Moar Speed
	 * 
	  * Mithril						45
	 * Scalemail					35				
	 * Silk							30		
	 * Chainmail					22
	 * Reinforced Leather			12
	 * Leather						8			
	 * Wool							5
	 * Cloth						3
	 */
	
	/**Accessory:
	 * 
	 *
	 * 
	 * 
	 */
	
	public Equipment(String nameInput, String type,int level) {
		this.level = level;
		this.type = type;
		name = nameInput.split(":")[1];
		String[] Attributes = nameInput.split(":")[0].split(" ");
		if(type.equals("Weapon"))
			setUpWeapon(Attributes);
		
		
		/*
		if (name.substring(0, 5).equals("sword")) {
			range = new int[] { 1 };
			attack = 4;
			accuracy = 110;
			name = nameInput.substring(5);
		} else if (name.substring(0, 3).equals("bow")) {
			range = new int[] { -2, -3 };
			attack = 2;
			accuracy = 90;
			name = nameInput.substring(3);
		} else if (name.substring(0, 5).equals("throw")) {
			range = new int[] { 3 };
			attack = 2;
			accuracy = 95;
			name = nameInput.substring(5);
		} else {
			range = new int[] { 1 };
			attack = 0;
			accuracy = 100;
			name = nameInput;
		}
		*/
	}

	public void setUpWeapon(String[] Attributes)
	{
		for(int h = Attributes.length-1; h >=0;h--)
		{
			switch(Attributes[h])
			{
			//Weapons
			case "Sword":
				range = new int[]{1};
				accuracy = new int[]{75};
				attack = 5 * Math.pow(levelMod, level-1);
				stunChance = 10;
				bleedChance = new int[2];
				bleedNumber = new int[2];
				critChance = 10;
				critRating = 100;
				disarmChance = 10;
				break;
			case "Cross-Bow":
				range = new int[]{1,2,3};
				accuracy = new int[]{90,70,60};
				attack = 6.5 * Math.pow(levelMod, level-1);
				stunChance = 0;
				bleedChance = new int[2];
				bleedNumber = new int[2];
				critChance = 10;
				critRating = 100;
				disarmChance = 0;
				break;
			case "Long-Bow":
				range = new int[]{2,3,4};
				accuracy = new int[]{75,55,45};
				attack = 6 * Math.pow(levelMod, level-1);
				stunChance = 0;
				bleedChance = new int[2];
				bleedNumber = new int[2];
				critChance = 10;
				critRating = 100;
				disarmChance = 0;
				break;
			case "Axe":
				range = new int[]{1};
				accuracy = new int[]{60};
				attack = 6.5 * Math.pow(levelMod, level-1);
				stunChance = 0;
				bleedChance = new int[2];
				bleedNumber = new int[2];
				critChance = 10;
				critRating = 150;
				disarmChance = 15;
				break;
			case "Pike":
				range = new int[]{1,2};
				accuracy = new int[]{40,70};
				attack = 6 * Math.pow(levelMod, level-1);
				stunChance = 0;
				bleedChance = new int[2];
				bleedNumber = new int[2];
				critChance = 10;
				critRating = 100;
				disarmChance = 0;
				break;
			case "Spear":
				range = new int[]{1};
				accuracy = new int[]{70};
				attack = 5.5 * Math.pow(levelMod, level-1);
				stunChance = 0;
				bleedChance = new int[2];
				bleedNumber = new int[2];
				critChance = 10;
				critRating = 100;
				disarmChance = 0;
				break;
			case "Dagger":
				range = new int[]{1};
				accuracy = new int[]{80};
				attack = 2 * Math.pow(levelMod, level-1);
				stunChance = 0;
				bleedChance = new int[]{45,0};
				bleedNumber = new int[]{(int)(3*(Math.pow(levelMod, level-1))),0};
				bN^=1;
				critChance = 10;
				critRating = 100;
				disarmChance = 0;	
				break;
			case "Hammer":
				range = new int[]{1};
				accuracy = new int[]{75};
				attack = 4 * Math.pow(levelMod, level-1);
				stunChance = 15;
				bleedChance = new int[2];
				bleedNumber = new int[2];
				critChance = 10;
				critRating = 100;
				disarmChance = 1;
				break;
			case "Fists":
				range = new int[]{1};
				accuracy = new int[]{95};
				attack = 3.5 * Math.pow(levelMod, level-1);
				stunChance = 0;
				bleedChance = new int[2];
				bleedNumber = new int[2];
				critChance = 10;
				critRating = 100;
				disarmChance = 5;

				break;
			case "Staff":
				range = new int[]{1,2,3};
				accuracy = new int[]{90,90,90};
				attack = 4 * Math.pow(levelMod, level-1);
				stunChance = 5;
				bleedChance = new int[2];
				bleedNumber = new int[2];
				critChance = 10;
				critRating = 100;
				disarmChance = 0;
				break;
			case "Throwing-Knife":
				range = new int[]{1,2};
				accuracy = new int[]{80,75};
				attack = 6.5 * Math.pow(levelMod, level-1);
				stunChance = 0;
				bleedChance = new int[2];
				bleedNumber = new int[2];
				critChance = 10;
				critRating = 100;
				disarmChance = 0;
				break;
			case "Shuriken":
				range = new int[]{1,2,3};
				accuracy = new int[]{95,90,85};
				attack = 4.5 * Math.pow(levelMod, level-1);
				stunChance = 0;
				bleedChance = new int[2];
				bleedNumber = new int[2];
				critChance = 10;
				critRating = 100;
				disarmChance = 0;
				break;
			case "LightSaber":
				range = new int[]{1,2,3,4,5};
				accuracy = new int[]{100,100,100,100,100};
				attack = 999 * Math.pow(levelMod, level-1);
				stunChance = 0;
				bleedChance = new int[2];
				bleedNumber = new int[2];
				critChance = 10;
				critRating = 100;
				disarmChance = 0;
				break;
///////////////////
//Weapon Adjectives
///////////////////
			case "Accurate":
				for(int i = 0; i < accuracy.length;i++)
					accuracy[i]+=10;
				break;
			case "Advanced":
				levelMod*=1.1;
				break;
			case "Anti-Tank":
				//increase damage to bosses
				break;
			case "Atomic":
				bleedNumber[bN] = (int)(2*(Math.pow(levelMod, level-1)));
				bleedChance[bN] = 50;
				bN^=1;
				break;
			case "Awkward":
				for(int i = 0; i < accuracy.length;i++)
					accuracy[i]-=10;
				break;
			case "Bad":
				for(int i = 0; i < accuracy.length;i++)
					accuracy[i]-=10;
				attack -= 3 *(Math.pow(levelMod, level-1));
				break;
			case "Beautiful":
				for(int i = 0; i < accuracy.length;i++)
					accuracy[i]-=5;
				attack *= 1.05;
				break;
			case "Broken":
				//could also do 50 % chance to break
				attack/=2;
				break;
			case "Chemical":
				//gain random buff/debuff everytime is hit?
				break;
			case "Concealed":
				//decrease threat rating?
				break;
			case "Crude":
				xPGain-=15;
				break;
			case "Customary":
				//negates all other effects?
				break;
			case "Dangerous":
				attack *= 2;
				recoilChance +=20;
				break;
			case "Deadly":
				attack *= 1.15;
				break;
			case "Devastating":
				critChance +=10;
				critRating+=5;
				break;
			case "Effective":
				attack *= 1.05;
				break;
			case "Excellent":
				for(int i = 0; i < accuracy.length;i++)
					accuracy[i]+=15;

				attack *= 1.1;
				break;
			case "Fatal":
				instantKillChance+=5;
				break;
			case "Fearsome":
				stunChance+=25;
				break;
			case "Fine":
				for(int i = 0; i < accuracy.length;i++)
					accuracy[i]+=7;
				attack += 2 *(Math.pow(levelMod, level-1));
				break;
			case "Good":
				for(int i = 0; i < accuracy.length;i++)
					accuracy[i]+=5;
				attack += 1 *(Math.pow(levelMod, level-1));
				break;
			case "Great":
				for(int i = 0; i < accuracy.length;i++)
					accuracy[i]+=10;
				attack += 3 *(Math.pow(levelMod, level-1));
				break;
			case "Gross":
				for(int i = 0; i < accuracy.length;i++)
					accuracy[i]-=10;
				bleedChance[bN] = 10;
				bleedNumber[bN] = (int)(2*(Math.pow(levelMod, level-1)));
				bN^=1;
				recoilChance +=5;//instead of a bleed 
				break;
			case "Heavy":
				for(int i = 0; i < accuracy.length;i++)
					accuracy[i]-=15;
				attack *=1.2;
				stunChance+=10;
				break;
			case "Keen":
				//SHINY
				break;
			case "Large":
				for(int i = 0; i < accuracy.length;i++)
					accuracy[i]-=10;
				attack *=1.2;
				break;
			case "Lethal":
				instantKillChance +=10;
				break;
			case "Magic":
				//5% chance to teleport user and/or target somewhere random
				break;
			case "Old":
				attack -= 2 *(Math.pow(levelMod, level-1));
				break;
			case "Ordinary":
				//see customary
				break;
			case "Poor":
				for(int i = 0; i < accuracy.length;i++)
					accuracy[i]-=5;
				attack -= 1 *(Math.pow(levelMod, level-1));
				break;
			case "Powerful":
				attack *=1.2;
				break;
			case "Reliable":
				for(int i = 0; i < accuracy.length;i++)
					accuracy[i]+=(100-accuracy[i])/2;
				break;
			case "Sad":
				recoilChance+=5;
				break;
			case "Sharp":
				//Armor is ignored?
				break;
			case "Small":
				for(int i = 0; i < accuracy.length;i++)
					accuracy[i]+=name.contains("Dagger") ? 10:-10;
				attack *=.8;
				break;
			case "Terrible":
				break;
			case "Tiny":
				for(int i = 0; i < accuracy.length;i++)
					accuracy[i]+=name.contains("Dagger") ? 10:-10;
				attack *=.7;
				break;
			case "Ultamite":
				//Shiny
				stunChance+=10;
				for(int i = 0; i < accuracy.length;i++)
					accuracy[i]+=10;
				attack *=1.1;
				instantKillChance+=10;
				break;
			case "Unusual":
				//1% chances to do random things
				break;
			case "Useless":
				for(int i = 0; i < accuracy.length;i++)
					accuracy[i]-=200;
				break;
			case "Weak":
				attack *=.8;
				break;			
				
			default:
				break;
			}
		}
	}
	
	public int[] getRange() {
		return range;
	}
	
	public int getAttack()
	{
		Random randGenerator = new Random();
		int damage = (int)attack;
		/////////////////////////////
		//System.out.println(damage);
		/////////////////////////////
		return damage;
	}
	
	public int getAccuracy(int n)
	{
		for(int i = 0; i < range.length; i++)
			if(range[i] == n)
				return accuracy[i];
		return 0;
	}

	public String getAttackEffects()
	{
		String s = "";
		Random randGenerator = new Random(System.currentTimeMillis());
		randGenerator.nextInt(100);
		if(randGenerator.nextInt(100) < stunChance)
			s+="Stun ";
		if(randGenerator.nextInt(100) < critChance)
			s+="Crit ";
		if(randGenerator.nextInt(100) < disarmChance)
			s+="Disarm ";
		if(randGenerator.nextInt(100) < instantKillChance)
			s+="Kill ";
		if(randGenerator.nextInt(100) < bleedChance[0])
			s+="Bleed1 ";
		if(randGenerator.nextInt(100) < bleedChance[1])
			s+="Bleed2 ";

		return s.trim();
	}
	
	public String toString()
	{
		String s = "";
		s+=name+ "\n"
		+ "Type: "+type+"\n"
		+ "Item Level: "+level+"\n"
		+ "Range: "+Arrays.toString(range)+"\n"
		+ "Accuracy: "+Arrays.toString(accuracy)+"\n"
		+ "Damage: "+ (int)attack+ "\n"
		+ "Critical Chance: " +critChance +"%\n"
		+ "Critical Rating: " +critRating +"\n"
		+ "Bleed Chance: " +Arrays.toString(bleedChance) +"%\n"
		+ "Bleed Rating: " +Arrays.toString(bleedNumber) +"\n"
		+ "Stun Chance: "+stunChance +"%\n"
		+ "Disarm Chance: "+disarmChance +"%\n"
		+ "Recoil Chance: "+recoilChance +"%\n"
		+ "Instant Kill Chance: "+instantKillChance +"%\n"

;
		
		return s;
	}

}
