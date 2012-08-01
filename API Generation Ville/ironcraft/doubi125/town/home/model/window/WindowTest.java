package ironcraft.doubi125.town.home.model.window;

import ironcraft.doubi125.town.home.WallEntity;
import ironcraft.doubi125.town.home.WallFurnitureModel.WindowModel;
import ironcraft.doubi125.util.blocks.SimpleBlock;
import net.minecraft.src.Block;
import net.minecraft.src.World;

public class WindowTest extends WindowModel
{

	public void build(World world, WallEntity wall, int pos)
	{
		wall.setBlock(world, pos+1, 2, 0, new SimpleBlock(Block.glass));
		wall.setBlock(world, pos+2, 2, 0, new SimpleBlock(Block.glass));
	}

	public int getSize()
	{
		return 4;
	}

}
