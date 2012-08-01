package net.minecraft.TradeToulonais;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;


public class InventoryMerchantToulonais implements IInventory
{
    private final IMerchantToulonais field_70476_a;
    private ItemStack field_70474_b[];
    private final EntityPlayer field_70475_c;
    private MerchantRecipeToulonais  field_70472_d;
    private int field_70473_e;

    public InventoryMerchantToulonais(EntityPlayer par1EntityPlayer, IMerchantToulonais par2IMerchant)
    {
        field_70474_b = new ItemStack[3];
        field_70475_c = par1EntityPlayer;
        field_70476_a = par2IMerchant;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return field_70474_b.length;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int par1)
    {
        return field_70474_b[par1];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (field_70474_b[par1] != null)
        {
            if (par1 == 2)
            {
                ItemStack itemstack = field_70474_b[par1];
                field_70474_b[par1] = null;
                return itemstack;
            }

            if (field_70474_b[par1].stackSize <= par2)
            {
                ItemStack itemstack1 = field_70474_b[par1];
                field_70474_b[par1] = null;

                if (func_70469_d(par1))
                {
                    func_70470_g();
                }

                return itemstack1;
            }

            ItemStack itemstack2 = field_70474_b[par1].splitStack(par2);

            if (field_70474_b[par1].stackSize == 0)
            {
                field_70474_b[par1] = null;
            }

            if (func_70469_d(par1))
            {
                func_70470_g();
            }

            return itemstack2;
        }
        else
        {
            return null;
        }
    }

    private boolean func_70469_d(int par1)
    {
        return par1 == 0 || par1 == 1;
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (field_70474_b[par1] != null)
        {
            ItemStack itemstack = field_70474_b[par1];
            field_70474_b[par1] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        field_70474_b[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > getInventoryStackLimit())
        {
            par2ItemStack.stackSize = getInventoryStackLimit();
        }

        if (func_70469_d(par1))
        {
            func_70470_g();
        }
    }

    /**
     * Returns the name of the inventory.
     */
    /*
     * fonct affichage item
     * @see net.minecraft.src.IInventory#getInvName()
     */
    public String getInvName()
    {
        return "mob.toulonais";
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return field_70476_a.func_70931_l_() == par1EntityPlayer;
    }

    public void openChest()
    {
    }

    public void closeChest()
    {
    }

    /**
     * Called when an the contents of an Inventory change, usually
     */
    public void onInventoryChanged()
    {
        func_70470_g();
    }

    public void func_70470_g()
    {
        field_70472_d = null;
        ItemStack itemstack = field_70474_b[0];
        ItemStack itemstack1 = field_70474_b[1];

        if (itemstack == null)
        {
            itemstack = itemstack1;
            itemstack1 = null;
        }

        if (itemstack == null)
        {
            setInventorySlotContents(2, null);
        }
        else
        {
            MerchantRecipeListToulonais  merchantrecipelist  = field_70476_a.func_70934_b(field_70475_c);

            if (merchantrecipelist  != null)
            {
                MerchantRecipeToulonais  merchantrecipe  = merchantrecipelist.func_77203_a(itemstack, itemstack1, field_70473_e);

                if (merchantrecipe  != null)
                {
                    field_70472_d = merchantrecipe;
                    setInventorySlotContents(2, merchantrecipe.func_77397_d().copy());
                }
                else if (itemstack1 != null)
                {
                    MerchantRecipeToulonais  merchantrecipe1 = merchantrecipelist.func_77203_a(itemstack1, itemstack, field_70473_e);

                    if (merchantrecipe1 != null)
                    {
                        field_70472_d = merchantrecipe1;
                        setInventorySlotContents(2, merchantrecipe1.func_77397_d().copy());
                    }
                    else
                    {
                        setInventorySlotContents(2, null);
                    }
                }
                else
                {
                    setInventorySlotContents(2, null);
                }
            }
        }
    }

    public MerchantRecipeToulonais  func_70468_h()
    {
        return field_70472_d;
    }

    public void func_70471_c(int par1)
    {
        field_70473_e = par1;
        func_70470_g();
    }
}
