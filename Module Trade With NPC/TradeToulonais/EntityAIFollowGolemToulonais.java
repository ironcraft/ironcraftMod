package net.minecraft.TradeToulonais;

import java.util.*;

import net.minecraft.src.*;

public class EntityAIFollowGolemToulonais extends EntityAIBase
{
    private EntityToulonais theVillager;
    private EntityIronGolem theGolem;
    private int field_75293_c;
    private boolean field_75291_d;

    public EntityAIFollowGolemToulonais(EntityToulonais par1EntityVillager)
    {
        field_75291_d = false;
        theVillager = par1EntityVillager;
        setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (theVillager.getGrowingAge() >= 0)
        {
            return false;
        }

        if (!theVillager.worldObj.isDaytime())
        {
            return false;
        }

        List list = theVillager.worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityIronGolem.class, theVillager.boundingBox.expand(6D, 2D, 6D));

        if (list.isEmpty())
        {
            return false;
        }

        Iterator iterator = list.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            EntityIronGolem entityirongolem = (EntityIronGolem)iterator.next();

            if (entityirongolem.func_70853_p() <= 0)
            {
                continue;
            }

            theGolem = entityirongolem;
            break;
        }
        while (true);

        return theGolem != null;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return theGolem.func_70853_p() > 0;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        field_75293_c = theVillager.getRNG().nextInt(320);
        field_75291_d = false;
        theGolem.getNavigator().clearPathEntity();
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        theGolem = null;
        theVillager.getNavigator().clearPathEntity();
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        theVillager.getLookHelper().setLookPositionWithEntity(theGolem, 30F, 30F);

        if (theGolem.func_70853_p() == field_75293_c)
        {
            theVillager.getNavigator().tryMoveToEntityLiving(theGolem, 0.15F);
            field_75291_d = true;
        }

        if (field_75291_d && theVillager.getDistanceSqToEntity(theGolem) < 4D)
        {
            theGolem.func_70851_e(false);
            theVillager.getNavigator().clearPathEntity();
        }
    }
}
