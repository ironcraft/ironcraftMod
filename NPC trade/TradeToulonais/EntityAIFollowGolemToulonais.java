package net.minecraft.TradeToulonais;

import java.util.*;
/*@autor : MysterHyde
 * Class :ContainerMerchantToulonais
 * note : terminé
 */
import net.minecraft.src.*;
public class EntityAIFollowGolemToulonais extends EntityAIBase
{

    private EntityToulonais theToulonais;
    private EntityIronGolem theGolem;
    private int field_48402_c;
    private boolean field_48400_d;

    public EntityAIFollowGolemToulonais(EntityToulonais par1EntityToulonais)
    {
        field_48400_d = false;
        theToulonais = par1EntityToulonais;
        setMutexBits(3);
    }

 

	/**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (theToulonais.getGrowingAge() >= 0)
        {
            return false;
        }

        if (!theToulonais.worldObj.isDaytime())
        {
            return false;
        }

        List list = theToulonais.worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityIronGolem.class, theToulonais.boundingBox.expand(6D, 2D, 6D));

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

            if (entityirongolem.func_48117_D_() <= 0)
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
        return theGolem.func_48117_D_() > 0;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        field_48402_c = theToulonais.getRNG().nextInt(320);
        field_48400_d = false;
        theGolem.getNavigator().clearPathEntity();
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        theGolem = null;
        theToulonais.getNavigator().clearPathEntity();
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        theToulonais.getLookHelper().setLookPositionWithEntity(theGolem, 30F, 30F);

        if (theGolem.func_48117_D_() == field_48402_c)
        {
            theToulonais.getNavigator().func_48667_a(theGolem, 0.15F);
            field_48400_d = true;
        }

        if (field_48400_d && theToulonais.getDistanceSqToEntity(theGolem) < 4D)
        {
            theGolem.func_48116_a(false);
            theToulonais.getNavigator().clearPathEntity();
        }
    }
}
