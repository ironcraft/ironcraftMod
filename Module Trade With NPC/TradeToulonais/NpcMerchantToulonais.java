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
	private MerchantRecipeListToulonais field_70936_c;
	private EntityPlayer field_70935_b;

    public NpcMerchantToulonais(EntityPlayer par1EntityPlayer)
    {
        field_56222_b = par1EntityPlayer;
        field_56224_a = new InventoryMerchantToulonais(par1EntityPlayer, this);
    }

    public EntityPlayer foncPlayer()
    {
        return field_56222_b;
    }

    public void func_70932_a_(EntityPlayer entityplayer)
    {
    }

    public MerchantRecipeListToulonais func_70934_b(EntityPlayer par1EntityPlayer)
    {
        return field_70936_c;
    }

    public void func_70930_a(MerchantRecipeListToulonais par1MerchantRecipeList)
    {
        field_70936_c = par1MerchantRecipeList;
    }

    public void func_70933_a(MerchantRecipeToulonais merchantrecipe)
    {
    }

    public EntityPlayer func_70931_l_()
    {
        return field_70935_b;
    }




}
