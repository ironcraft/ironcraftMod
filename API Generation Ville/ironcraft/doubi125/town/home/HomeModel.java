package ironcraft.doubi125.town.home;

import ironcraft.doubi125.town.home.WallFurnitureModel.WindowModel;
import ironcraft.doubi125.town.home.model.HomeTheme;
import ironcraft.doubi125.util.Dir;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.src.World;

public abstract class HomeModel
{
	public abstract int getMinFloorCount();
	public abstract int getMaxFloorCount();
	public abstract int getMinFloorHeight();
	public abstract int getMaxFloorHeight();
	public abstract void buildWalls(World world, HomeFloor floor);
	public abstract void buildRoof(World world, HomeRoof roof);
	public abstract void buildFlooring(World world, HomeFloor floor);
	public abstract int getWallOffset();
	public abstract String getName();
	public abstract Dir getRoofDir(Random rand, HomeRoof roof);

	public int getFloorCount(Random rand)
	{
		return rand.nextInt(getMaxFloorCount() - getMinFloorCount() + 1) + getMinFloorCount();
	}
	
	public int getFloorHeight(Random rand)
	{
		return rand.nextInt(getMaxFloorHeight() - getMinFloorHeight() + 1) + getMinFloorHeight();
	}
	
	protected void registerTheme(HomeTheme theme)
	{
		themes.add(theme);
	}
	
	public ArrayList<HomeTheme> getThemes()
	{
		return themes;
	}
	
	public HomeTheme getTheme(int selectedTheme)
	{
		return themes.get(selectedTheme);
	}
	
	protected void registerWindow(WindowModel window)
	{
		windows.add(window);
	}
	
	public WindowModel getWindowModel(Random rand)
	{
		return windows.get(rand.nextInt(windows.size()));
	}
	
	public int getMinSize()
	{
		return 10;
	}

	private ArrayList<HomeTheme> themes = new ArrayList<HomeTheme>();
	private ArrayList<WindowModel> windows = new ArrayList<WindowModel>();
}
