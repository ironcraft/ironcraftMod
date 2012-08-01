package ironcraft.doubi125.town.home.model;

import java.util.Random;

import ironcraft.doubi125.town.home.HomeFloor;
import ironcraft.doubi125.town.home.HomeModel;
import ironcraft.doubi125.town.home.HomeRoof;
import ironcraft.doubi125.town.home.model.window.LargeWindow;
import ironcraft.doubi125.town.home.model.window.WindowTest;
import ironcraft.doubi125.util.BuildingBlock;
import ironcraft.doubi125.util.Dir;
import ironcraft.doubi125.util.ToolBox;
import ironcraft.doubi125.util.blocks.BlockPattern;
import ironcraft.doubi125.util.blocks.OrientableBlock;
import net.minecraft.src.World;

public class SimpleHomeModel extends HomeModel
{
	public SimpleHomeModel()
	{
		BuildingBlock[] blocks = {BuildingBlock.planksOak, BuildingBlock.woodOak, BuildingBlock.cobblestone, BuildingBlock.slabWood};
		OrientableBlock[] stairs = {BuildingBlock.stairWood, BuildingBlock.stairCobble};
		registerTheme(new HomeTheme("Wood", blocks, stairs));
		
		BuildingBlock[] blocks1 = {BuildingBlock.stone, BuildingBlock.stoneBrick, BuildingBlock.stoneBrick, BuildingBlock.slabCobble};
		OrientableBlock[] stairs1 = {BuildingBlock.stairCobble, BuildingBlock.stairWood};
		registerTheme(new HomeTheme("Stone", blocks1, stairs1));
		
		registerWindow(new LargeWindow());
	}

	public int getMinFloorCount()
	{
		return 0;
	}

	public int getMaxFloorCount()
	{
		return 1;
	}

	public int getMinFloorHeight()
	{
		return 4;
	}

	public int getMaxFloorHeight()
	{
		return 5;
	}

	public void buildWalls(World world, HomeFloor floor)
	{
		HomeTheme theme = floor.getTheme();
		
		for(int i=1; i<floor.getFloorHeight(); i++)
		{
			if(i == 1 && floor.getFloor() == 0)
			{
				ToolBox.buildSimpleWalls(world, floor, i, theme.getBlock(2));
			}
			else
			{
				BlockPattern pattern = new BlockPattern(theme.getBlock(1), theme.getBlock(0));
				ToolBox.buildSimpleWalls(world, floor, i, pattern);
			}
		}

	}

	public void buildRoof(World world, HomeRoof roof)
	{
		HomeTheme theme = roof.getTheme();
		BlockPattern pattern = new BlockPattern(theme.getBlock(1), theme.getBlock(0));
		ToolBox.buildSimpleRoof(world, roof.getRoofDir(), roof, pattern, theme.getStairBlock(0), theme.getBlock(3));
		ToolBox.buildSimpleWalls(world, roof, 0, theme.getBlock(1));
	}

	public void buildFlooring(World world, HomeFloor floor)
	{
		HomeTheme theme = floor.getTheme();
		ToolBox.buildSimpleFlooring(world, floor, 0, theme.getBlock(2));
		ToolBox.buildSimpleWalls(world, floor, 0, theme.getBlock(1));
	}

	public int getWallOffset()
	{
		return 1;
	}

	public String getName()
	{
		return "Simple Home Model";
	}

	public Dir getRoofDir(Random rand, HomeRoof roof)
	{
		if(roof.getW()/roof.getH() < 1.1 && roof.getW()/roof.getH() > 0.8)
			return Dir.getDir(rand.nextInt(4));
		else
			if(roof.getW() > roof.getH())
				return Dir.E;
			else return Dir.N;
	}
}
