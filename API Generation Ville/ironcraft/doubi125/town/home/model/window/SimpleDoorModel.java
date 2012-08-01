package ironcraft.doubi125.town.home.model.window;

import net.minecraft.src.World;
import ironcraft.doubi125.town.home.WallEntity;
import ironcraft.doubi125.town.home.WallFurnitureModel.DoorModel;
import ironcraft.doubi125.util.Out;

public class SimpleDoorModel extends DoorModel
{

	public void build(World world, WallEntity wall, int pos)
	{
		wall.setDoor(world, pos + 1, 1, 0, 0);
	}

	public int getSize()
	{
		return 3;
	}

}
