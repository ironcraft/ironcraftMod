package ironcraft.doubi125.town.home;

import ironcraft.doubi125.town.BoundBox;
import ironcraft.doubi125.town.home.WallFurnitureModel.DoorModel;
import ironcraft.doubi125.town.home.WallFurnitureModel.WindowModel;
import ironcraft.doubi125.town.home.model.HomeTheme;
import ironcraft.doubi125.town.home.model.window.SimpleDoorModel;
import ironcraft.doubi125.town.home.model.window.WindowTest;
import ironcraft.doubi125.util.Dir;

import java.util.Random;

import net.minecraft.src.World;

public class HomeFloor extends BoundBox
{
	public HomeFloor(int x, int y, int z, int w, int h, int floor, int floorCount, HomeModel model, HomeTheme theme)
	{
		super(x, y, z, w, h);
		this.floor = floor;
		this.floorCount = floorCount;
		this.model = model;
		this.theme = theme;
	}

	public void generate(Random rand)
	{
		floorHeight = model.getFloorHeight(rand);
		WindowModel window = model.getWindowModel(rand);
		if(floor < floorCount)
		{
			addChild(new HomeFloor(getX(), getY() + floorHeight, getZ(), getW(), getH(), floor + 1, floorCount, model, theme));
		}
		else
			addChild(new HomeRoof(getX(), getY()+getFloorHeight(), getZ(), getW(), getH(), model, theme));
		if(floor == 0)
		{
			Dir dirDoor = Dir.getDir(rand.nextInt(4));
			for(Dir dir : Dir.dirList)
			{
				if(dir == dirDoor)
					addChild(genWall(dir, new SimpleDoorModel(), window));
				else addChild(genWall(dir, null, window));
			}
		}
		else
		{
			for(Dir dir : Dir.dirList)
			{
				addChild(genWall(dir, null, window));
			}
		}
		
		genChildren(rand);
	}
	
	private WallEntity genWall(Dir dir, DoorModel door, WindowModel window)
	{
		switch(dir)
		{
		case N:
			return new WallEntity(window, door, getX() + model.getWallOffset(), getY(), getZ() + getH() - 1, dir, getW() - 2*model.getWallOffset());
		case S:
			return new WallEntity(window, door, getX() + getW() - 1 - model.getWallOffset(), getY(), getZ(), dir, getW() - 2*model.getWallOffset());
		case E:
			return new WallEntity(window, door, getX(), getY(), getZ() + model.getWallOffset(), dir, getH() - 2*model.getWallOffset());
		case W:
			return new WallEntity(window, door, getX() + getW() - 1, getY(), getZ() + getH() - 1 - model.getWallOffset(), dir, getH() - 2*model.getWallOffset());
		}
		return null;
	}

	public void build(World world)
	{
		model.buildWalls(world, this);
		model.buildFlooring(world, this);
		buildChildren(world);
	}
	
	public int getFloorHeight()
	{
		return floorHeight;
	}
	
	public int getFloor()
	{
		return floor;
	}
	
	public HomeTheme getTheme()
	{
		return theme;
	}
	
	private int floor, floorCount, floorHeight;
	private HomeModel model;
	private HomeTheme theme;
}
