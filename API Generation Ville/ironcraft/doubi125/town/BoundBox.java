package ironcraft.doubi125.town;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.src.World;

public abstract class BoundBox extends TownComponent implements Buildable
{

	public BoundBox(int x, int y, int z, int w, int h)
	{
		super(x, y, z);
		this.w = w;
		this.h = h;
	}

	public int getW()
	{
		return w;
	}

	public void setW(int w)
	{
		this.w = w;
	}

	public int getH()
	{
		return h;
	}

	public void setH(int h)
	{
		this.h = h;
	}
	
	private int w, h;
}
