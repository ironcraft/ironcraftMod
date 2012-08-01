package net.minecraft.TradeToulonais;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.*;

public class SlotMerchantResultToulonais extends Slot
{
    private final InventoryMerchantToulonais field_75233_a;
    private EntityPlayer field_75232_b;
    private int field_75231_g;
    private final IMerchantToulonais field_75234_h;

    public SlotMerchantResultToulonais(EntityPlayer par1EntityPlayer, IMerchantToulonais par2IMerchant, InventoryMerchantToulonais par3InventoryMerchant, int par4, int par5, int par6)
    {
        super(par3InventoryMerchant, par4, par5, par6);
        field_75232_b = par1EntityPlayer;
        field_75234_h = par2IMerchant;
        field_75233_a = par3InventoryMerchant;
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    public boolean isItemValid(ItemStack par1ItemStack)
    {
        return false;
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    public ItemStack decrStackSize(int par1)
    {
        if (getHasStack())
        {
            field_75231_g += Math.min(par1, getStack().stackSize);
        }

        return super.decrStackSize(par1);
    }

    protected void func_75210_a(ItemStack par1ItemStack, int par2)
    {
        field_75231_g += par2;
        func_75208_c(par1ItemStack);
    }

    protected void func_75208_c(ItemStack par1ItemStack)
    {
        par1ItemStack.onCrafting(field_75232_b.worldObj, field_75232_b, field_75231_g);
        field_75231_g = 0;
    }

    /**
     * Called when the player picks up an item from an inventory slot
     */
    public void onPickupFromSlot(ItemStack par1ItemStack)
    {
        func_75208_c(par1ItemStack);
        MerchantRecipeToulonais merchantrecipe = field_75233_a.func_70468_h();

        if (merchantrecipe!= null)
        {
            ItemStack itemstack = field_75233_a.getStackInSlot(0);
            ItemStack itemstack1 = field_75233_a.getStackInSlot(1);

            if (func_75230_a(merchantrecipe, itemstack, itemstack1) || func_75230_a(merchantrecipe, itemstack1, itemstack))
            {
                if (itemstack != null && itemstack.stackSize <= 0)
                {
                    itemstack = null;
                }

                if (itemstack1 != null && itemstack1.stackSize <= 0)
                {
                    itemstack1 = null;
                }

                field_75233_a.setInventorySlotContents(0, itemstack);
                field_75233_a.setInventorySlotContents(1, itemstack1);
                field_75234_h.func_70933_a(merchantrecipe);
            }
        }
    }

    private boolean func_75230_a(MerchantRecipeToulonais par1MerchantRecipe, ItemStack par2ItemStack, ItemStack par3ItemStack)
    {
        ItemStack itemstack = par1MerchantRecipe.func_77394_a();
        ItemStack itemstack1 = par1MerchantRecipe.func_77396_b();

        if (par2ItemStack != null && par2ItemStack.itemID == itemstack.itemID)
        {
            if (itemstack1 != null && par3ItemStack != null && itemstack1.itemID == par3ItemStack.itemID)
            {
                par2ItemStack.stackSize -= itemstack.stackSize;
                par3ItemStack.stackSize -= itemstack1.stackSize;
                return true;
            }

            if (itemstack1 == null && par3ItemStack == null)
            {
                par2ItemStack.stackSize -= itemstack.stackSize;
                return true;
            }
        }

        return false;
    }
}
