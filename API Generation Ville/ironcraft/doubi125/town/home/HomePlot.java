package ironcraft.doubi125.town.home;

import java.util.Random;

import net.minecraft.src.Material;
import net.minecraft.src.World;
import ironcraft.doubi125.town.BoundBox;
import ironcraft.doubi125.town.home.model.HomeTheme;
import ironcraft.doubi125.util.Out;

public class HomePlot extends BoundBox
{

	public HomePlot(World world, int x, int y, int z, int w, int h, HomeModel model, HomeTheme theme)
	{
		super(x, y, z, w, h);
		this.world = world;
		this.model = model;
		this.theme = theme;
	}

	public void generate(Random rand)
	{
		int sum = 0;
		int count = 0;
		for(int i=0; i<getW(); i++)
		{
			for(int j=0; j<getH(); j++)
			{
				sum += getGroundHeight(getX() + i, getY(), getZ() + j);
				count++;
			}
		}
		int y = sum/(getW()*getH());
		setY(y);
		addChild(new HomeFloor(getX(), getY(), getZ(), getW(), getH(), 0, model.getFloorCount(rand), model, theme));
		genChildren(rand);
	}
	
	private int getGroundHeight(int x, int y, int z)
	{
		int pos = y;
		if(world.getBlockMaterial(x, y, z) == Material.air)
		{
			while(world.getBlockMaterial(x, pos, z) == Material.air)
				pos--;
		}
		else
		{
			while(world.getBlockMaterial(x, pos, z) != Material.air)
				pos++;
			pos--;
		}
		return pos;
	}

	public void build(World world)
	{
		buildChildren(world);
	}
	
	private World world;
	private HomeModel model;
	private HomeTheme theme;
}
