package ironcraft.doubi125.util.blocks;

import ironcraft.doubi125.util.BuildingBlock;

public class BlockPattern
{
	public BlockPattern(BuildingBlock... blocks)
	{
		this.blocks = blocks;
	}
	
	public BuildingBlock getBlockWithSimplePattern(int pos)
	{
		if(pos < blocks.length)
			return blocks[pos];
		else return blocks[blocks.length-1];
	}
	
	public BuildingBlock getBlockWithMirrorPattern(int pos, int wallSize)
	{
		int patternPos = Math.min(pos, wallSize-pos-1);
		return getBlockWithSimplePattern(patternPos);
	}
	
	public BuildingBlock getBlockWithRepeatedPattern(int pos)
	{
		return blocks[pos%blocks.length];
	}
	
	public BuildingBlock getBlockWithMirrorRepeatedPattern(int pos, int wallSize)
	{
		int patternPos = Math.min(pos, wallSize-pos-1);
		return getBlockWithRepeatedPattern(patternPos);
	}
	
	private BuildingBlock[] blocks;
}
