package ironcraft.doubi125.util;

import java.util.ArrayList;

public enum Dir
{
	N, S, E, W;
	public int getID()
	{
		switch(this)
		{
		case N: return 3;
		case S: return 1;
		case E: return 0;
		case W: return 2;
		}
		return -1;
	}
	
	public static Dir getDir(int id)
	{
		switch(id%4)
		{
		case 3: return N;
		case 1: return S;
		case 0: return E;
		case 2: return W;
		}
		return null;
	}
	
	public static ArrayList<Dir> dirList = new ArrayList<Dir>();
	static
	{
		dirList.add(N);
		dirList.add(S);
		dirList.add(E);
		dirList.add(W);
	}
}
