package net.minecraft.TradeToulonais;

import java.util.Random;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityAIBase;
import net.minecraft.src.EntityToulonais;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Village;
import net.minecraft.src.World;

public class EntityAIVillagerMateToulonais extends EntityAIBase
{
    private EntityToulonais villagerObj;
    private EntityToulonais mate;
    private World worldObj;
    private int matingTimeout;
    Village villageObj;

    public EntityAIVillagerMateToulonais(EntityToulonais par1EntityVillager)
    {
        matingTimeout = 0;
        villagerObj = par1EntityVillager;
        worldObj = par1EntityVillager.worldObj;
        setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (villagerObj.getGrowingAge() != 0)
        {
            return false;
        }

        if (villagerObj.getRNG().nextInt(500) != 0)
        {
            return false;
        }

        villageObj = worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(villagerObj.posX), MathHelper.floor_double(villagerObj.posY), MathHelper.floor_double(villagerObj.posZ), 0);

        if (villageObj == null)
        {
            return false;
        }

        if (!checkSufficientDoorsPresentForNewVillager())
        {
            return false;
        }

        Entity entity = worldObj.findNearestEntityWithinAABB(net.minecraft.src.EntityToulonais.class, villagerObj.boundingBox.expand(8D, 3D, 8D), villagerObj);

        if (entity == null)
        {
            return false;
        }

        mate = (EntityToulonais)entity;
        return mate.getGrowingAge() == 0;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        matingTimeout = 300;
        villagerObj.setMating(true);
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        villageObj = null;
        mate = null;
        villagerObj.setMating(false);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return matingTimeout >= 0 && checkSufficientDoorsPresentForNewVillager() && villagerObj.getGrowingAge() == 0;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        matingTimeout--;
        villagerObj.getLookHelper().setLookPositionWithEntity(mate, 10F, 30F);

        if (villagerObj.getDistanceSqToEntity(mate) > 2.25D)
        {
            villagerObj.getNavigator().tryMoveToEntityLiving(mate, 0.25F);
        }
        else if (matingTimeout == 0 && mate.isMating())
        {
            giveBirth();
        }

        if (villagerObj.getRNG().nextInt(35) == 0)
        {
            worldObj.setEntityState(villagerObj, (byte)12);
        }
    }

    private boolean checkSufficientDoorsPresentForNewVillager()
    {
        int i = (int)((double)(float)villageObj.getNumVillageDoors() * 0.34999999999999998D);
        return villageObj.getNumVillagers() < i;
    }

    private void giveBirth()
    {
        EntityToulonais entityvillager = new EntityToulonais(worldObj);
        mate.setGrowingAge(6000);
        villagerObj.setGrowingAge(6000);
        entityvillager.setGrowingAge(-24000);
        entityvillager.setProfession(villagerObj.getRNG().nextInt(5));
        entityvillager.setLocationAndAngles(villagerObj.posX, villagerObj.posY, villagerObj.posZ, 0.0F, 0.0F);
        worldObj.spawnEntityInWorld(entityvillager);
        worldObj.setEntityState(entityvillager, (byte)12);
    }
}
