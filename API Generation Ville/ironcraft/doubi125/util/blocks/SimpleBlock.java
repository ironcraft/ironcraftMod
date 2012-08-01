package ironcraft.doubi125.util.blocks;

import ironcraft.doubi125.util.BuildingBlock;
import net.minecraft.src.Block;
import net.minecraft.src.World;

public class SimpleBlock extends BuildingBlock
{
	public SimpleBlock(int id)
	{
		this.id = id;
	}
	
	public SimpleBlock(Block block)
	{
		this.id = block.blockID;
	}

	public int getBlockID()
	{
		return id;
	}
	
	public void setBlock(World world, int x, int y, int z)
	{
		world.setBlockWithNotify(x, y, z, id);
	}
	
	private int id;
}
