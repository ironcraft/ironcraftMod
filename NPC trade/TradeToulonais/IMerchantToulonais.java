package net.minecraft.TradeToulonais;

import net.minecraft.src.*;

public interface IMerchantToulonais
{
    public abstract void func_56218_c_(EntityPlayer entityplayer);

    public abstract EntityPlayer func_56221_a();

    public abstract MerchantRecipeListToulonais func_56220_b(EntityPlayer entityplayer);

    public abstract void func_56217_a(MerchantRecipeListToulonais merchantrecipelist);

    public abstract void func_56219_a(MerchantRecipeToulonais merchantrecipe);
}
