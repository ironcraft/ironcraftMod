package ironcraft.doubi125.town;

import ironcraft.doubi125.town.home.HomeModel;
import ironcraft.doubi125.town.home.HomePlot;
import ironcraft.doubi125.town.home.model.BevelHouseModel;
import ironcraft.doubi125.town.home.model.HomeTheme;
import ironcraft.doubi125.town.home.model.SimpleHomeModel;
import ironcraft.doubi125.util.Out;

import java.util.Random;
import java.util.Stack;

import net.minecraft.src.World;

public class TownGenerator
{
	private TownGenerator()
	{
		
	}
	
	public void genTest(World world, int x, int y, int z, HomeModel model, HomeTheme theme)
	{
    	HomePlot plot = new HomePlot(world, x - w/2, y, z - h/2, w, h, model, theme);
    	plot.generate(new Random());
    	plot.build(world);
    	endBuildingCreation();
	}
	
	public void switchTheme()
	{
		selectedTheme = (selectedTheme+1)%getSelectedModel().getThemes().size();
		Out.sendMessage("Theme switched : " + getSelectedTheme().getName());
	}
	
	public HomeModel getSelectedModel()
	{
		return models[selectedModel];
	}
	
	public void setSelectedModel(int id)
	{
		selectedModel = id;
	}
	
	public HomeTheme getSelectedTheme()
	{
		return getSelectedModel().getTheme(selectedTheme);
	}
	
	public void setSelectedTheme(int id)
	{
		selectedTheme = id;
	}
	
	public void switchModel()
	{
		selectedModel = (selectedModel+1)%models.length;
		Out.sendMessage("Model switched : " + models[selectedModel].getName());
	}
	
	public void incrW()
	{
		w = Math.min(w+1, 99);
	}
	
	public void decrW(int min)
	{
		w = Math.max(w-1, min);
	}
	
	public void incrH()
	{
		h = Math.min(h+1, 99);
	}
	
	public void decrH(int min)
	{
		h = Math.max(h-1, min);
	}
	
	public void updateDims(HomeModel model)
	{
		h = Math.max(h, model.getMinSize());
		w = Math.max(w, model.getMinSize());
	}
	
	public void undo(World world)
	{
		if(modifs.size() != 0)
		{
			WorldModif modif = modifs.pop();
			modif.undo(world);
		}
		else Out.out("nothing to undo");
	}
	
	public void endBuildingCreation()
	{
		modifs.add(currentBuilding);
		currentBuilding = new WorldModif();
	}
	
	public void changeBlock(World world, int x, int y, int z, int blockID)
	{
		currentBuilding.changeBlock(world, x, y, z, blockID);
	}
	
	private Stack<WorldModif> modifs = new Stack<WorldModif>();
	private WorldModif currentBuilding = new WorldModif();
	private class WorldModif
	{
		public void changeBlock(World world, int x, int y, int z, int blockID)
		{
			modifs.add(new BlockModif(world, x, y, z, blockID));
		}
		
		public void undo(World world)
		{
			while(modifs.size() != 0)
			{
				modifs.pop().undo(world);
			}
		}
		
		private Stack<BlockModif> modifs = new Stack<BlockModif>();
		
		private class BlockModif
		{
			public BlockModif(World world, int x, int y, int z, int blockID)
			{
				this.x = x;
				this.y = y;
				this.z = z;
				this.initBlockID = world.getBlockId(x, y, z);
				this.finalBlockID = blockID;
			}
			
			public void undo(World world)
			{
				world.setBlockWithNotify(x, y, z, initBlockID);
			}
			
			private int x, y, z, initBlockID, finalBlockID;
		}
	}
	
	public HomeModel[] getModels()
	{
		return models;
	}
	
	public int getW()
	{
		return w;
	}
	
	public int getH()
	{
		return h;
	}
	
	public static TownGenerator generator = new TownGenerator();
	private HomeModel[] models = {new SimpleHomeModel(), new BevelHouseModel()};
	private int selectedTheme = 0, selectedModel = 0;
	private int w = 15, h = 15;
}
