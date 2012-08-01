package ironcraft.doubi125.util;

import ironcraft.doubi125.util.blocks.OrientableBlock;
import ironcraft.doubi125.util.blocks.SimpleBlock;
import ironcraft.doubi125.util.blocks.SpecialBlock;
import net.minecraft.src.Block;
import net.minecraft.src.World;

public abstract class BuildingBlock
{
	public abstract int getBlockID();
	public abstract void setBlock(World world, int x, int y, int z);

	public static final BuildingBlock stone = new SimpleBlock(Block.stone);
    public static final BuildingBlock grass = new SimpleBlock(Block.grass);
    public static final BuildingBlock dirt = new SimpleBlock(Block.dirt);
    public static final BuildingBlock cobblestone = new SimpleBlock(Block.cobblestone);

    public static final BuildingBlock planksOak = new SpecialBlock(Block.planks, 0);
    public static final BuildingBlock planksPine = new SpecialBlock(Block.planks, 1);
    public static final BuildingBlock planksBirch = new SpecialBlock(Block.planks, 2);
    public static final BuildingBlock planksJungle = new SpecialBlock(Block.planks, 3);
    
    public static final BuildingBlock sand = new SimpleBlock(Block.sand);
    public static final BuildingBlock gravel = new SimpleBlock(Block.gravel);

    public static final BuildingBlock woodOak = new SpecialBlock(Block.wood, 0);
    public static final BuildingBlock woodPine = new SpecialBlock(Block.wood, 1);
    public static final BuildingBlock woodBirch = new SpecialBlock(Block.wood, 2);
    public static final BuildingBlock woodJungle = new SpecialBlock(Block.wood, 3);

    public static final BuildingBlock leavesOak = new SpecialBlock(Block.leaves, 0);
    public static final BuildingBlock leavesPine = new SpecialBlock(Block.leaves, 1);
    public static final BuildingBlock leavesBirch = new SpecialBlock(Block.leaves, 2);
    public static final BuildingBlock leavesJungle = new SpecialBlock(Block.leaves, 3);
    
    public static final BuildingBlock glass = new SimpleBlock(Block.glass);
    public static final BuildingBlock blockLapis = new SimpleBlock(Block.blockLapis);
    public static final BuildingBlock sandStone = new SimpleBlock(Block.sandStone);

    public static final BuildingBlock woolWhite = new SpecialBlock(Block.cloth, 0);
    public static final BuildingBlock woolOrange = new SpecialBlock(Block.cloth, 1);
    public static final BuildingBlock woolMagenta = new SpecialBlock(Block.cloth, 2);
    public static final BuildingBlock woolLightBlue = new SpecialBlock(Block.cloth, 3);
    public static final BuildingBlock woolYellow = new SpecialBlock(Block.cloth, 4);
    public static final BuildingBlock woolLime = new SpecialBlock(Block.cloth, 5);
    public static final BuildingBlock woolPink = new SpecialBlock(Block.cloth, 6);
    public static final BuildingBlock woolGray = new SpecialBlock(Block.cloth, 7);
    public static final BuildingBlock woolLightGray = new SpecialBlock(Block.cloth, 8);
    public static final BuildingBlock woolCyan = new SpecialBlock(Block.cloth, 9);
    public static final BuildingBlock woolPurple = new SpecialBlock(Block.cloth, 10);
    public static final BuildingBlock woolBlue = new SpecialBlock(Block.cloth, 11);
    public static final BuildingBlock woolBrown = new SpecialBlock(Block.cloth, 12);
    public static final BuildingBlock woolGreen = new SpecialBlock(Block.cloth, 13);
    public static final BuildingBlock woolRed = new SpecialBlock(Block.cloth, 14);
    public static final BuildingBlock woolBlack = new SpecialBlock(Block.cloth, 15);
    
    public static final BuildingBlock blockGold = new SimpleBlock(Block.blockGold);
    public static final BuildingBlock blockSteel = new SimpleBlock(Block.blockSteel);

    public static final BuildingBlock slabDoubleStone = new SpecialBlock(Block.stairDouble, 0);
    public static final BuildingBlock slabDoubleSand = new SpecialBlock(Block.stairDouble, 1);
    public static final BuildingBlock slabDoubleWood = new SpecialBlock(Block.stairDouble, 2);
    public static final BuildingBlock slabDoubleCobble = new SpecialBlock(Block.stairDouble, 3);
    public static final BuildingBlock slabDoubleStoneBrick = new SpecialBlock(Block.stairDouble, 4);

    public static final BuildingBlock slabStone =  new SpecialBlock(Block.stairSingle, 0);
    public static final BuildingBlock slabSand =  new SpecialBlock(Block.stairSingle, 1);
    public static final BuildingBlock slabWood =  new SpecialBlock(Block.stairSingle, 2);
    public static final BuildingBlock slabCobble =  new SpecialBlock(Block.stairSingle, 3);
    public static final BuildingBlock slabStoneBrick =  new SpecialBlock(Block.stairSingle, 4);
    
    public static final BuildingBlock brick = new SimpleBlock(Block.brick);
    public static final BuildingBlock cobblestoneMossy = new SimpleBlock(Block.cobblestoneMossy);
    
    public static final OrientableBlock stairWood = new OrientableBlock(Block.stairCompactPlanks, 0);
    public static final OrientableBlock stairCobble = new OrientableBlock(Block.stairCompactCobblestone, 0);
    public static final OrientableBlock stairBrick = new OrientableBlock(Block.stairsBrick, 0);
    public static final OrientableBlock stairStoneBrick = new OrientableBlock(Block.stairsStoneBrickSmooth, 0);
    public static final OrientableBlock stairNetherBrick = new OrientableBlock(Block.stairsNetherBrick, 0);
    
    public static final BuildingBlock blockDiamond = new SimpleBlock(Block.blockDiamond);
    public static final BuildingBlock ladder = new OrientableBlock(Block.ladder, 2);
    public static final BuildingBlock blockSnow = new SimpleBlock(Block.blockSnow);
    public static final BuildingBlock blockClay = new SimpleBlock(Block.blockClay);
   
    public static final BuildingBlock fenceWood = new SimpleBlock(Block.fence);
    public static final BuildingBlock fenceIron = new SimpleBlock(Block.fenceIron);
    
    public static final BuildingBlock netherrack = new SimpleBlock(Block.netherrack);
    public static final BuildingBlock slowSand = new SimpleBlock(Block.slowSand);
    public static final BuildingBlock glowStone = new SimpleBlock(Block.glowStone);
    public static final BuildingBlock stoneBrick = new SimpleBlock(Block.stoneBrick);
    public static final BuildingBlock thinGlass = new SimpleBlock(Block.thinGlass);
    public static final BuildingBlock netherBrick = new SimpleBlock(Block.netherBrick);
}
