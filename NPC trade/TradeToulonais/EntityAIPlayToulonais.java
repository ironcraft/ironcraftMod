package net.minecraft.TradeToulonais;

import java.util.*;

import net.minecraft.src.*;

public class EntityAIPlayToulonais extends EntityAIBase
{
    private EntityToulonais villagerObj;
    private EntityLiving targetToulonais;
    private float field_48358_c;
    private int field_48356_d;

    public EntityAIPlayToulonais(EntityToulonais par1EntityToulonais, float par2)
    {
        villagerObj = par1EntityToulonais;
        field_48358_c = par2;
        setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (villagerObj.getGrowingAge() >= 0)
        {
            return false;
        }

        if (villagerObj.getRNG().nextInt(400) != 0)
        {
            return false;
        }

        List list = villagerObj.worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityToulonais.class, villagerObj.boundingBox.expand(6D, 3D, 6D));
        double d = Double.MAX_VALUE;
        Iterator iterator = list.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            EntityToulonais EntityToulonais = (EntityToulonais)iterator.next();

            if (EntityToulonais != villagerObj && !EntityToulonais.getIsPlayingFlag() && EntityToulonais.getGrowingAge() < 0)
            {
                double d1 = EntityToulonais.getDistanceSqToEntity(villagerObj);

                if (d1 <= d)
                {
                    d = d1;
                    targetToulonais = EntityToulonais;
                }
            }
        }
        while (true);

        if (targetToulonais == null)
        {
            Vec3 vec3 = RandomPositionGenerator.func_48622_a(villagerObj, 16, 3);

            if (vec3 == null)
            {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return field_48356_d > 0;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        if (targetToulonais != null)
        {
            villagerObj.setIsPlayingFlag(true);
        }

        field_48356_d = 1000;
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        villagerObj.setIsPlayingFlag(false);
        targetToulonais = null;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        field_48356_d--;

        if (targetToulonais != null)
        {
            if (villagerObj.getDistanceSqToEntity(targetToulonais) > 4D)
            {
                villagerObj.getNavigator().func_48667_a(targetToulonais, field_48358_c);
            }
        }
        else if (villagerObj.getNavigator().noPath())
        {
            Vec3 vec3 = RandomPositionGenerator.func_48622_a(villagerObj, 16, 3);

            if (vec3 == null)
            {
                return;
            }

            villagerObj.getNavigator().tryMoveToXYZ(vec3.xCoord, vec3.yCoord, vec3.zCoord, field_48358_c);
        }
    }
}
