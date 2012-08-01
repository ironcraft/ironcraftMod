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
import ironcraft.doubi125.util.blocks.SimpleBlock;
import net.minecraft.src.Block;
import net.minecraft.src.World;

public class BevelHouseModel extends HomeModel
{
	public BevelHouseModel()
	{
		BuildingBlock[] blocks1 = {BuildingBlock.planksOak, BuildingBlock.planksPine, BuildingBlock.stone, BuildingBlock.slabStone, BuildingBlock.slabDoubleStone, BuildingBlock.fenceWood};
		OrientableBlock[] stairs1 = {BuildingBlock.stairWood, BuildingBlock.stairCobble};
		registerTheme(new HomeTheme("Wood", blocks1, stairs1));

		BuildingBlock[] blocks2 = {BuildingBlock.stone, BuildingBlock.cobblestone, BuildingBlock.planksOak, BuildingBlock.slabCobble, BuildingBlock.slabDoubleCobble, BuildingBlock.fenceWood};
		OrientableBlock[] stairs2 = {BuildingBlock.stairCobble, BuildingBlock.stairWood};
		registerTheme(new HomeTheme("Stone", blocks2, stairs2));
		
		BuildingBlock[] blocks3 = {BuildingBlock.woolWhite, BuildingBlock.woolOrange, BuildingBlock.woolGray, BuildingBlock.slabStone, BuildingBlock.slabDoubleStone, BuildingBlock.fenceWood};
		OrientableBlock[] stairs3 = {BuildingBlock.stairCobble, BuildingBlock.stairWood};
		registerTheme(new HomeTheme("Cloth", blocks3, stairs3));

		registerWindow(new WindowTest());
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
		BlockPattern edgeBlocks = new BlockPattern(theme.getBlock(1), theme.getBlock(0));
		BlockPattern wallBlocks = new BlockPattern(floor.getTheme().getBlock(1), theme.getBlock(0));
		for(int i=0; i<floor.getFloorHeight(); i++)
			ToolBox.buildBevelWalls(world, floor, 4, i, edgeBlocks, wallBlocks);
		if(floor.getFloor() != 0)
		{
			for(int i=0; i<floor.getW()-8; i++)
			{
				floor.setBlock(world, i+4, 0, -1, theme.getBlock(3));
				floor.setBlock(world, i+4, 0, floor.getH(), theme.getBlock(3));
			}
			
			for(int i=0; i<floor.getH()-8; i++)
			{
				floor.setBlock(world, -1, 0, i+4, theme.getBlock(3));
				floor.setBlock(world, floor.getW(), 0, i+4, theme.getBlock(3));
			}
			
			for(int i=0; i<3; i++)
			{
				floor.setBlock(world, i, 0, 2-i, theme.getBlock(3));
				floor.setBlock(world, i, 0, floor.getH() + i - 3, theme.getBlock(3));
				floor.setBlock(world, floor.getW() - i - 1, 0, floor.getH() + i - 3, theme.getBlock(3));
				floor.setBlock(world, floor.getW() - i - 1, 0, 2-i, theme.getBlock(3));
			}
			floor.setBlock(world, -1, 0, 3, theme.getBlock(4));
			floor.setBlock(world, -1, 0, floor.getH()-4, theme.getBlock(4));
			floor.setBlock(world, floor.getW(), 0, 3, theme.getBlock(4));
			floor.setBlock(world, floor.getW(), 0, floor.getH()-4, theme.getBlock(4));
			
			floor.setBlock(world, 3, 0, -1, theme.getBlock(4));
			floor.setBlock(world, floor.getW()-4, 0, -1, theme.getBlock(4));
			floor.setBlock(world, 3, 0, floor.getH(), theme.getBlock(4));
			floor.setBlock(world, floor.getW()-4, 0, floor.getH(), theme.getBlock(4));
		}
		
		for(int i=0; i<floor.getFloorHeight(); i++)
		{
			BuildingBlock blockID;
			if(i==0)
				blockID = theme.getBlock(4);
			else
				blockID = theme.getBlock(5);
			floor.setBlock(world, -1, i, 3, blockID);
			floor.setBlock(world, -1, i, floor.getH()-4, blockID);
			floor.setBlock(world, floor.getW(), i, 3, blockID);
			floor.setBlock(world, floor.getW(), i, floor.getH()-4, blockID);

			floor.setBlock(world, 3, i, -1, blockID);
			floor.setBlock(world, floor.getW()-4, i, -1, blockID);
			floor.setBlock(world, 3, i, floor.getH(), blockID);
			floor.setBlock(world, floor.getW()-4, i, floor.getH(), blockID);
		}
	}

	public void buildRoof(World world, HomeRoof roof)
	{
		HomeTheme theme = roof.getTheme();
		ToolBox.buildBevelFlooring(world, roof, 4, 0, theme.getBlock(0));
		ToolBox.buildBevelWalls(world, roof, 4, 1, theme.getBlock(1));
		for(int i=0; i<roof.getW()-8; i++)
		{
			roof.setBlock(world, i+4, 0, -1, theme.getBlock(3));
			roof.setBlock(world, i+4, 0, roof.getH(), theme.getBlock(3));
		}
		
		for(int i=0; i<roof.getH()-8; i++)
		{
			roof.setBlock(world, -1, 0, i+4, theme.getBlock(3));
			roof.setBlock(world, roof.getW(), 0, i+4, theme.getBlock(3));
		}
		
		for(int i=0; i<3; i++)
		{
			roof.setBlock(world, i, 0, 2-i, theme.getBlock(3));
			roof.setBlock(world, i, 0, roof.getH() + i - 3, theme.getBlock(3));
			roof.setBlock(world, roof.getW() - i - 1, 0, roof.getH() + i - 3, theme.getBlock(3));
			roof.setBlock(world, roof.getW() - i - 1, 0, 2-i, theme.getBlock(3));
		}
		roof.setBlock(world, -1, 0, 3, theme.getBlock(3));
		roof.setBlock(world, -1, 0, roof.getH()-4, theme.getBlock(3));
		roof.setBlock(world, roof.getW(), 0, 3, theme.getBlock(3));
		roof.setBlock(world, roof.getW(), 0, roof.getH()-4, theme.getBlock(3));
		
		roof.setBlock(world, 3, 0, -1, theme.getBlock(3));
		roof.setBlock(world, roof.getW()-4, 0, -1, theme.getBlock(3));
		roof.setBlock(world, 3, 0, roof.getH(), theme.getBlock(3));
		roof.setBlock(world, roof.getW()-4, 0, roof.getH(), theme.getBlock(3));
	}
	
	public void buildFlooring(World world, HomeFloor floor)
	{
		HomeTheme theme = floor.getTheme();
		ToolBox.buildBevelFlooring(world, floor, 4, 0, theme.getBlock(2));
	}

	public int getWallOffset()
	{
		return 5;
	}
	
	public int getMinSize()
	{
		return 15;
	}
	
	public String getName()
	{
		return "Wooden Model";
	}

	public Dir getRoofDir(Random rand, HomeRoof roof)
	{
		return Dir.N;
	}
}
