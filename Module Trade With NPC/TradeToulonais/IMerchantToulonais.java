package net.minecraft.TradeToulonais;

import net.minecraft.src.*;
/*@autor : MysterHyde
 * Interface :IMerchantToulonais
 * note : non terminé
 */
public interface IMerchantToulonais
{
    public abstract void func_70932_a_(EntityPlayer entityplayer);

    public abstract EntityPlayer func_70931_l_();

    public abstract MerchantRecipeListToulonais func_70934_b(EntityPlayer entityplayer);

    public abstract void func_70930_a(MerchantRecipeListToulonais merchantrecipelist);

    public abstract void func_70933_a(MerchantRecipeToulonais merchantrecipe);
}
