package ironcraft.doubi125.town.home;

import ironcraft.doubi125.town.BoundBox;
import ironcraft.doubi125.town.home.model.HomeTheme;
import ironcraft.doubi125.util.Dir;
import ironcraft.doubi125.util.Out;

import java.util.Random;

import net.minecraft.src.World;

public class HomeRoof extends BoundBox
{
	public HomeRoof(int x, int y, int z, int w, int h, HomeModel model, HomeTheme theme)
	{
		super(x, y, z, w, h);
		this.model = model;
		this.theme = theme;
	}

	public HomeTheme getTheme()
	{
		return theme;
	}

	public void generate(Random rand)
	{
		Out.out("test");
		roofDir = model.getRoofDir(rand, this);
	}
	
	public void build(World world)
	{
		model.buildRoof(world, this);
	}
	
	public Dir getRoofDir()
	{
		return roofDir;
	}
	
	
	private HomeModel model;
	private HomeTheme theme;
	Dir roofDir;
}
