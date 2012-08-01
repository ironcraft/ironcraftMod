package ironcraft.doubi125.util.blocks;

import ironcraft.doubi125.util.Dir;
import ironcraft.doubi125.util.Out;
import net.minecraft.src.Block;
import net.minecraft.src.World;

public class OrientableBlock extends SpecialBlock
{
	public OrientableBlock(int id, int initMetadata, Dir dir)
	{
		super(id, 0);
		this.initMetadata = initMetadata;
		this.dir = dir;
	}
	
	public OrientableBlock(Block block, int initMetadata, Dir dir)
	{
		super(block, 0);
		this.initMetadata = initMetadata;
		this.dir = dir;
	}
	
	public OrientableBlock(Block block, int initMetadata)
	{
		this(block, initMetadata, Dir.N);
	}
	
	public void setBlock(World world, int x, int y, int z)
	{
		metadata = initMetadata + getMetadataFromDir(dir);
		Out.out(metadata);
		super.setBlock(world, x, y, z);
	}
	
	private int getMetadataFromDir(Dir dir)
	{
		switch(dir)
		{
		case N:
			return 2;
		case S:
			return 3;
		case E:
			return 1;
		case W:
			return 0;
		}
		return -1;
	}
	
	public void setDir(Dir dir)
	{
		this.dir = dir;
	}
	
	private Dir dir;
	private int initMetadata;
}
