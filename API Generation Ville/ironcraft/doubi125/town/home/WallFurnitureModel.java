package ironcraft.doubi125.town.home;

import java.util.Random;

import net.minecraft.src.World;
import ironcraft.doubi125.town.Buildable;

public interface WallFurnitureModel
{
	public void build(World world, WallEntity wall, int pos);
	public int getSize();
	
	public abstract class DoorModel implements WallFurnitureModel {}
	public abstract class WindowModel implements WallFurnitureModel {}
}
