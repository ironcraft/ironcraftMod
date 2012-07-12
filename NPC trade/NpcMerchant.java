package net.minecraft.src;

public class NpcMerchant implements IMerchant
{
    private InventoryMerchant field_56224_a;
    private EntityPlayer field_56222_b;
    private MerchantRecipeList field_56223_c;

    public NpcMerchant(EntityPlayer par1EntityPlayer)
    {
        field_56222_b = par1EntityPlayer;
        field_56224_a = new InventoryMerchant(par1EntityPlayer, this);
    }

    public EntityPlayer func_56221_a()
    {
        return field_56222_b;
    }

    public void func_56218_c_(EntityPlayer entityplayer)
    {
    }

    public MerchantRecipeList func_56220_b(EntityPlayer par1EntityPlayer)
    {
        return field_56223_c;
    }

    public void func_56217_a(MerchantRecipeList par1MerchantRecipeList)
    {
        field_56223_c = par1MerchantRecipeList;
    }

    public void func_56219_a(MerchantRecipe merchantrecipe)
    {
    }
}
