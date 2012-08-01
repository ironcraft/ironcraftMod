package ironcraft.doubi125.town;

import ironcraft.doubi125.util.BuildingBlock;
import ironcraft.doubi125.util.Dir;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.ItemDoor;
import net.minecraft.src.World;

public abstract class TownComponent
{
	public TownComponent(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public int getZ()
	{
		return z;
	}

	public void setZ(int z)
	{
		this.z = z;
	}
	
	public void addChild(TownComponent child)
	{
		children.add(child);
	}

	public abstract void generate(Random rand);
	public abstract void build(World world);
	
	public void genChildren(Random rand)
	{
		for(TownComponent child : children)
		{
			child.generate(rand);
		}
	}
	
	public void buildChildren(World world)
	{
		for(TownComponent child : children)
		{
			child.build(world);
		}
	}
	
	public void setBlock(World world, int x, int y, int z, BuildingBlock block)
	{
		TownGenerator.generator.changeBlock(world, x + this.x, y + this.y, z + this.z, block.getBlockID());
		block.setBlock(world, x + this.x, y + this.y, z + this.z);
	}
	
	public void setDoor(World world, int x, int y, int z, Dir dir, int doorID)
	{
		ItemDoor.placeDoorBlock(world, x + this.x, y + this.y, z + this.z, dir.getID(), getDoorBlock(doorID));
	}
	
	private Block getDoorBlock(int doorID)
	{
		if(doorID == 0)
			return Block.doorWood;
		else return Block.doorSteel;
	}
	
	private int x, y, z;
	private ArrayList<TownComponent> children = new ArrayList<TownComponent>();
}
