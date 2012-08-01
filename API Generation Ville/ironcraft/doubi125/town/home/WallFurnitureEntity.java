package ironcraft.doubi125.town.home;

import java.util.Random;

import net.minecraft.src.World;
import ironcraft.doubi125.town.Buildable;

public class WallFurnitureEntity implements Buildable
{
	
	public WallFurnitureEntity(WallEntity wall, int pos, WallFurnitureModel model)
	{
		this.wall = wall;
		this.pos = pos;
		this.model = model;
	}

	public void generate(Random rand)
	{
		
	}

	public void build(World world)
	{
		model.build(world, wall, pos);
	}
	
	public int getPos()
	{
		return pos;
	}
	
	public void move(int x)
	{
		pos += x;
	}

	private WallEntity wall;
	private int pos;
	private WallFurnitureModel model;
}
