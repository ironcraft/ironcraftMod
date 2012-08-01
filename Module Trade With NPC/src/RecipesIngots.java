package net.minecraft.src;

public class RecipesIngots
{
    private Object recipeItems[][];

    public RecipesIngots()
    {
        recipeItems = (new Object[][]
                {
                    new Object[] {
                        Block.blockGold, new ItemStack(Item.ingotGold, 9)
                    }, new Object[] {
                        Block.blockSteel, new ItemStack(Item.ingotIron, 9)
                    }, new Object[] {
                        Block.field_72071_ax, new ItemStack(Item.field_77702_n, 9)
                    }, new Object[] {
                        Block.blockDiamond, new ItemStack(Item.fuckingCashOrs, 9)
                    }, new Object[] {
                        Block.blockLapis, new ItemStack(Item.dyePowder, 9, 4)
                    }
                });
    }

    /**
     * Adds the ingot recipes to the CraftingManager.
     */
    public void addRecipes(CraftingManager par1CraftingManager)
    {
        Object aobj[][] = recipeItems;
        int i = aobj.length;

        for (int j = 0; j < i; j++)
        {
            Object aobj1[] = aobj[j];
            Block block = (Block)aobj1[0];
            ItemStack itemstack = (ItemStack)aobj1[1];
            par1CraftingManager.addRecipe(new ItemStack(block), new Object[]
                    {
                        "###", "###", "###", '#', itemstack
                    });
            par1CraftingManager.addRecipe(itemstack, new Object[]
                    {
                        "#", '#', block
                    });
        }

        par1CraftingManager.addRecipe(new ItemStack(Item.ingotGold), new Object[]
                {
                    "###", "###", "###", '#', Item.goldNugget
                });
        par1CraftingManager.addRecipe(new ItemStack(Item.goldNugget, 9), new Object[]
                {
                    "#", '#', Item.ingotGold
                });
    }
}
