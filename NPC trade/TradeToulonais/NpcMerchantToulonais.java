package net.minecraft.TradeToulonais;

import net.minecraft.src.*;
/*@autor : MysterHyde
 * Class :NpcMerchantToulonais
 * @see net.minecraft.src.EntityPlayer#func_56241_a2(net.minecraft.TradeToulonais.IMerchantToulonais)
 */
public class NpcMerchantToulonais implements IMerchantToulonais
{
    private InventoryMerchantToulonais field_56224_a;
    private EntityPlayer field_56222_b;
    private MerchantRecipeListToulonais field_56223_c;

    public NpcMerchantToulonais(EntityPlayer par1EntityPlayer)
    {
        field_56222_b = par1EntityPlayer;
        field_56224_a = new InventoryMerchantToulonais(par1EntityPlayer, this);
    }

    public EntityPlayer func_56221_a()
    {
        return field_56222_b;
    }

    public void func_56218_c_(EntityPlayer entityplayer)
    {
    }

    public MerchantRecipeListToulonais func_56220_b(EntityPlayer par1EntityPlayer)
    {
        return field_56223_c;
    }

    public void func_56217_a(MerchantRecipeListToulonais par1MerchantRecipeList)
    {
        field_56223_c = par1MerchantRecipeList;
    }

    public void func_56219_a(MerchantRecipeToulonais merchantrecipe)
    {
    }
}
