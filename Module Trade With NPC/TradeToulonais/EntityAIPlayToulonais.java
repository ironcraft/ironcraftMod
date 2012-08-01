package net.minecraft.TradeToulonais;

import java.util.*;

import net.minecraft.src.*;

public class EntityAIPlayToulonais extends EntityAIBase
{
    private EntityToulonais villagerObj;
    private EntityLiving targetVillager;
    private float field_75261_c;
    private int playTime;

    public EntityAIPlayToulonais(EntityToulonais par1EntityVillager, float par2)
    {
        villagerObj = par1EntityVillager;
        field_75261_c = par2;
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

            EntityToulonais entityvillager = (EntityToulonais)iterator.next();

            if (entityvillager != villagerObj && !entityvillager.isPlaying() && entityvillager.getGrowingAge() < 0)
            {
                double d1 = entityvillager.getDistanceSqToEntity(villagerObj);

                if (d1 <= d)
                {
                    d = d1;
                    targetVillager = entityvillager;
                }
            }
        }
        while (true);

        if (targetVillager == null)
        {
            Vec3 vec3 = RandomPositionGenerator.func_75463_a(villagerObj, 16, 3);

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
        return playTime > 0;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        if (targetVillager != null)
        {
            villagerObj.setPlaying(true);
        }

        playTime = 1000;
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        villagerObj.setPlaying(false);
        targetVillager = null;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        playTime--;

        if (targetVillager != null)
        {
            if (villagerObj.getDistanceSqToEntity(targetVillager) > 4D)
            {
                villagerObj.getNavigator().tryMoveToEntityLiving(targetVillager, field_75261_c);
            }
        }
        else if (villagerObj.getNavigator().noPath())
        {
            Vec3 vec3 = RandomPositionGenerator.func_75463_a(villagerObj, 16, 3);

            if (vec3 == null)
            {
                return;
            }

            villagerObj.getNavigator().tryMoveToXYZ(vec3.xCoord, vec3.yCoord, vec3.zCoord, field_75261_c);
        }
    }
}
