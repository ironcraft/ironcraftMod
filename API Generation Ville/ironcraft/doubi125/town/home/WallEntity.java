package ironcraft.doubi125.town.home;

import ironcraft.doubi125.town.TownComponent;
import ironcraft.doubi125.town.home.WallFurnitureModel.DoorModel;
import ironcraft.doubi125.town.home.WallFurnitureModel.WindowModel;
import ironcraft.doubi125.util.BuildingBlock;
import ironcraft.doubi125.util.Dir;
import ironcraft.doubi125.util.Out;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.src.World;

public class WallEntity extends TownComponent
{
	public WallEntity(WindowModel win, DoorModel door, int x, int y, int z, Dir dir, int size)
	{
		super(x, y, z);
		this.dir = dir;
		this.win = win;
		this.size = size;
		this.door = door;
	}
	
	public WallEntity(WindowModel win, int x, int y, int z, Dir dir, int size)
	{
		this(win, null, x, y, z, dir, size);
	}

	public void generate(Random rand)
	{
		int windowCount;
		if(door == null)
		{
			if(size/win.getSize() <= 0)
				return;
			else windowCount = rand.nextInt(size/win.getSize()) + 1;
		}
		else
		{
			if((size-door.getSize())/win.getSize() > 0)
				windowCount = rand.nextInt((size-door.getSize())/win.getSize()) + 1;
			else 
			{
				if(size-door.getSize() > 0)
					pieces.add(new WallFurnitureEntity(this, rand.nextInt(size-door.getSize()), door));
				return;
			}
		}
		int doorPos = rand.nextInt(windowCount);
		int subSize;
		if(door == null)
			subSize = size/windowCount;
		else subSize = (size-door.getSize())/windowCount;
		int pos = 0;
		for(int i=0; i<windowCount; i++)
		{
			if(i == doorPos && door != null)
			{
				pieces.add(new WallFurnitureEntity(this, pos, door));
				pos += door.getSize();
			}
			pieces.add(new WallFurnitureEntity(this, pos + rand.nextInt(), win));
			pos += subSize;
		}
	}
	
	public void build(World world)
	{
		for(WallFurnitureEntity piece : pieces)
		{
			piece.build(world);
		}
	}
	
	public Dir getDir()
	{
		return dir;
	}
	
	public void setBlock(World world, int pos, int height, int offset, BuildingBlock block)
	{
		switch(dir)
		{
		case N:
			super.setBlock(world, pos, height, offset, block);
			break;
		case S:
			super.setBlock(world, -pos, height, -offset, block);
			break;
		case E:
			super.setBlock(world, -offset, height, pos, block);
			break;
		case W:
			super.setBlock(world, offset, height, -pos, block);
			break;
		}
	}
	
	public void setDoor(World world, int pos, int height, int offset, int blockID)
	{
		switch(dir)
		{
		case N:
			super.setDoor(world, pos, height, offset, dir, blockID);
			break;
		case S:
			super.setDoor(world, -pos, height, -offset, dir, blockID);
			break;
		case E:
			super.setDoor(world, -offset, height, pos, dir, blockID);
			break;
		case W:
			super.setDoor(world, offset, height, -pos, dir, blockID);
			break;
		}
	}
	
	private ArrayList<WallFurnitureEntity> pieces = new ArrayList<WallFurnitureEntity>();
	private Dir dir;
	private int size;
	private WindowModel win;
	private DoorModel door;
}
