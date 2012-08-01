package net.minecraft.src;

import java.util.HashMap;
import java.util.Map;

public class FurnaceRecipes
{
    private static final FurnaceRecipes smeltingBase = new FurnaceRecipes();

    /** The list of smelting results. */
    private Map smeltingList;
    private Map field_77605_c;

    /**
     * Used to call methods addSmelting and getSmeltingResult.
     */
    public static final FurnaceRecipes smelting()
    {
        return smeltingBase;
    }

    private FurnaceRecipes()
    {
        smeltingList = new HashMap();
        field_77605_c = new HashMap();
        addSmelting(Block.oreIron.blockID, new ItemStack(Item.ingotIron), 0.7F);
        addSmelting(Block.oreGold.blockID, new ItemStack(Item.ingotGold), 1.0F);
        addSmelting(Block.field_72073_aw.blockID, new ItemStack(Item.field_77702_n), 1.0F);
        addSmelting(Block.sand.blockID, new ItemStack(Block.glass), 0.1F);
        addSmelting(Item.porkRaw.shiftedIndex, new ItemStack(Item.porkCooked), 0.3F);
        addSmelting(Item.beefRaw.shiftedIndex, new ItemStack(Item.beefCooked), 0.3F);
        addSmelting(Item.chickenRaw.shiftedIndex, new ItemStack(Item.chickenCooked), 0.3F);
        addSmelting(Item.fishRaw.shiftedIndex, new ItemStack(Item.fishCooked), 0.3F);
        addSmelting(Block.cobblestone.blockID, new ItemStack(Block.stone), 0.1F);
        addSmelting(Item.clay.shiftedIndex, new ItemStack(Item.brick), 0.2F);
        addSmelting(Block.cactus.blockID, new ItemStack(Item.dyePowder, 1, 2), 0.2F);
        addSmelting(Block.wood.blockID, new ItemStack(Item.coal, 1, 1), 0.1F);
        addSmelting(Block.oreDiamond.blockID, new ItemStack(Item.fuckingCashOrs), 1.0F);
        addSmelting(Block.oreCoal.blockID, new ItemStack(Item.coal), 0.1F);
        addSmelting(Block.oreRedstone.blockID, new ItemStack(Item.redstone), 0.7F);
        addSmelting(Block.oreLapis.blockID, new ItemStack(Item.dyePowder, 1, 4), 0.2F);
    }

    /**
     * Adds a smelting recipe.
     */
    public void addSmelting(int par1, ItemStack par2ItemStack, float par3)
    {
        smeltingList.put(Integer.valueOf(par1), par2ItemStack);
        field_77605_c.put(Integer.valueOf(par2ItemStack.itemID), Float.valueOf(par3));
    }

    /**
     * Returns the smelting result of an item.
     */
    public ItemStack getSmeltingResult(int par1)
    {
        return (ItemStack)smeltingList.get(Integer.valueOf(par1));
    }

    public Map getSmeltingList()
    {
        return smeltingList;
    }

    public float func_77601_c(int par1)
    {
        if (field_77605_c.containsKey(Integer.valueOf(par1)))
        {
            return ((Float)field_77605_c.get(Integer.valueOf(par1))).floatValue();
        }
        else
        {
            return 0.0F;
        }
    }
}
