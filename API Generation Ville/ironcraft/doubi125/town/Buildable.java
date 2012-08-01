package ironcraft.doubi125.town;

import java.util.Random;

import net.minecraft.src.World;

public interface Buildable
{
	public void generate(Random rand);
	public void build(World world);
}
