package ironcraft.doubi125.town.home.model;

import ironcraft.doubi125.util.BuildingBlock;
import ironcraft.doubi125.util.blocks.OrientableBlock;

public class HomeTheme
{
	
	public HomeTheme(String name, BuildingBlock[] blocks, OrientableBlock[] stairs)
	{
		this.blocks = blocks;
		this.stairs = stairs;
		this.name = name;
	}

	public BuildingBlock getBlock(int id)
	{
		return blocks[id];
	}
	
	public OrientableBlock getStairBlock(int id)
	{
		return stairs[id];
	}
	
	public String getName()
	{
		return name;
	}
	
	private BuildingBlock[] blocks;
	private OrientableBlock[] stairs;
	private String name;
}
