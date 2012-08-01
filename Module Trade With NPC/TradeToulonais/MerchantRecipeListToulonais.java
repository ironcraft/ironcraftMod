package net.minecraft.TradeToulonais;

import java.io.*;
import java.util.ArrayList;

import net.minecraft.src.*;

public class MerchantRecipeListToulonais extends ArrayList
{
    public MerchantRecipeListToulonais()
    {
    }

    public MerchantRecipeListToulonais(NBTTagCompound par1NBTTagCompound)
    {
        func_77201_a(par1NBTTagCompound);
    }

    public MerchantRecipeToulonais func_77203_a(ItemStack par1ItemStack, ItemStack par2ItemStack, int par3)
    {
        if (par3 > 0 && par3 < size())
        {
            MerchantRecipeToulonais merchantrecipe = (MerchantRecipeToulonais)get(par3);

            if (par1ItemStack.itemID == merchantrecipe.func_77394_a().itemID && (par2ItemStack == null && !merchantrecipe.func_77398_c() || merchantrecipe.func_77398_c() && par2ItemStack != null && merchantrecipe.func_77396_b().itemID == par2ItemStack.itemID))
            {
                if (par1ItemStack.stackSize >= merchantrecipe.func_77394_a().stackSize && (!merchantrecipe.func_77398_c() || par2ItemStack.stackSize >= merchantrecipe.func_77396_b().stackSize))
                {
                    return merchantrecipe;
                }
                else
                {
                    return null;
                }
            }
        }

        for (int i = 0; i < size(); i++)
        {
            MerchantRecipeToulonais merchantrecipe1 = (MerchantRecipeToulonais)get(i);

            if (par1ItemStack.itemID == merchantrecipe1.func_77394_a().itemID && par1ItemStack.stackSize >= merchantrecipe1.func_77394_a().stackSize && (!merchantrecipe1.func_77398_c() && par2ItemStack == null || merchantrecipe1.func_77398_c() && par2ItemStack != null && merchantrecipe1.func_77396_b().itemID == par2ItemStack.itemID && par2ItemStack.stackSize >= merchantrecipe1.func_77396_b().stackSize))
            {
                return merchantrecipe1;
            }
        }

        return null;
    }

    public void func_77205_a(MerchantRecipeToulonais par1MerchantRecipe)
    {
        for (int i = 0; i < size(); i++)
        {
            MerchantRecipeToulonais merchantrecipe = (MerchantRecipeToulonais)get(i);

            if (par1MerchantRecipe.func_77393_a(merchantrecipe))
            {
                if (par1MerchantRecipe.func_77391_b(merchantrecipe))
                {
                    set(i, par1MerchantRecipe);
                }

                return;
            }
        }

        add(par1MerchantRecipe);
    }

    public void func_77200_a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeByte((byte)(size() & 0xff));

        for (int i = 0; i < size(); i++)
        {
            MerchantRecipeToulonais merchantrecipe = (MerchantRecipeToulonais)get(i);
            Packet.writeItemStack(merchantrecipe.func_77394_a(), par1DataOutputStream);
            Packet.writeItemStack(merchantrecipe.func_77397_d(), par1DataOutputStream);
            ItemStack itemstack = merchantrecipe.func_77396_b();
            par1DataOutputStream.writeBoolean(itemstack != null);

            if (itemstack != null)
            {
                Packet.writeItemStack(itemstack, par1DataOutputStream);
            }
        }
    }

    public static MerchantRecipeListToulonais func_77204_a(DataInputStream par0DataInputStream) throws IOException
    {
        MerchantRecipeListToulonais merchantrecipelist = new MerchantRecipeListToulonais();
        int i = par0DataInputStream.readByte() & 0xff;

        for (int j = 0; j < i; j++)
        {
            ItemStack itemstack = Packet.readItemStack(par0DataInputStream);
            ItemStack itemstack1 = Packet.readItemStack(par0DataInputStream);
            ItemStack itemstack2 = null;

            if (par0DataInputStream.readBoolean())
            {
                itemstack2 = Packet.readItemStack(par0DataInputStream);
            }

            merchantrecipelist.add(new MerchantRecipeToulonais(itemstack, itemstack2, itemstack1));
        }

        return merchantrecipelist;
    }

    public void func_77201_a(NBTTagCompound par1NBTTagCompound)
    {
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("RecipesT");

        for (int i = 0; i < nbttaglist.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
            add(new MerchantRecipeToulonais(nbttagcompound));
        }
    }

    public NBTTagCompound func_77202_a()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        NBTTagList nbttaglist = new NBTTagList("RecipesT");

        for (int i = 0; i < size(); i++)
        {
            MerchantRecipeToulonais merchantrecipe = (MerchantRecipeToulonais)get(i);
            nbttaglist.appendTag(merchantrecipe.func_77395_g());
        }

        nbttagcompound.setTag("RecipesT", nbttaglist);
        return nbttagcompound;
    }
}
