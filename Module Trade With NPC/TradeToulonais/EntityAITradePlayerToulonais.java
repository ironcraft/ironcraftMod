package net.minecraft.TradeToulonais;

import net.minecraft.src.Container;
import net.minecraft.src.EntityAIBase;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityToulonais;

public class EntityAITradePlayerToulonais extends EntityAIBase
{
    private EntityToulonais field_75276_a;

    public EntityAITradePlayerToulonais(EntityToulonais par1EntityVillager)
    {
        field_75276_a = par1EntityVillager;
        setMutexBits(5);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (!field_75276_a.isEntityAlive())
        {
            return false;
        }

        if (field_75276_a.isInWater())
        {
            return false;
        }

        if (!field_75276_a.onGround)
        {
            return false;
        }

        if (field_75276_a.velocityChanged)
        {
            return false;
        }

        EntityPlayer entityplayer = field_75276_a.func_70931_l_();

        if (entityplayer == null)
        {
            return false;
        }

        if (field_75276_a.getDistanceSqToEntity(entityplayer) > 16D)
        {
            return false;
        }

        return entityplayer.craftingInventory instanceof Container;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        field_75276_a.getNavigator().clearPathEntity();
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        field_75276_a.func_70932_a_(null);
    }
}
