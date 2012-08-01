package ironcraft.doubi125.util.blocks;

import net.minecraft.src.Block;
import net.minecraft.src.World;
import ironcraft.doubi125.util.BuildingBlock;

public class SpecialBlock extends BuildingBlock
{
	public SpecialBlock(int id, int metadata)
	{
		this.id = id;
		this.metadata = metadata;
	}
	
	public SpecialBlock(Block block, int metadata)
	{
		this.id = block.blockID;
		this.metadata = metadata;
	}
	
	public int getBlockID()
	{
		return id;
	}

	public void setBlock(World world, int x, int y, int z)
	{
		world.setBlockAndMetadataWithNotify(x, y, z, id, metadata);
	}
	
	int id, metadata;
}
