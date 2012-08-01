package ironcraft.doubi125.util;

import net.minecraft.client.Minecraft;

public class Out
{
	public static void out(String str, int... args)
	{
		System.out.print(str + " : ");
		for(int i=0; i<args.length; i++)
		{
			if(i != 0)
				System.out.print(", ");
			System.out.print(args[i]);
		}
		System.out.println();
	}
	
	public static void out(int... args)
	{
		for(int i=0; i<args.length; i++)
		{
			if(i != 0)
				System.out.print(", ");
			System.out.print(args[i]);
		}
		System.out.println();
	}
	
	public static void out(String str)
	{
		System.out.println(str);
	}
	
	public static void out(int var)
	{
		System.out.println(var);
	}
	
	public static void sendMessage(String str)
	{
		mc.ingameGUI.addChatMessage(str);
	}
	
	public static void setMinecraft(Minecraft mc)
	{
		Out.mc = mc;
	}
	
	public static void sendMessage(int eventKey)
	{
		mc.ingameGUI.addChatMessage("" + eventKey);
	}
	
	private static Minecraft mc;
}













