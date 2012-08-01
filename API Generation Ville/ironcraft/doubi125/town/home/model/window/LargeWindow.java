package ironcraft.doubi125.town.home.model.window;

import ironcraft.doubi125.town.home.WallEntity;
import ironcraft.doubi125.town.home.WallFurnitureModel.WindowModel;
import ironcraft.doubi125.util.blocks.SimpleBlock;
import net.minecraft.src.Block;
import net.minecraft.src.World;

public class LargeWindow extends WindowModel
{

	public void build(World world, WallEntity wall, int pos)
	{
		for(int i=1; i<4; i++)
		{
			wall.setBlock(world, pos+i, 2, 0, new SimpleBlock(Block.glass));
			wall.setBlock(world, pos+i, 3, 0, new SimpleBlock(Block.glass));
		}
	}

	public int getSize()
	{
		return 5;
	}

}
