package net.minecraft.TradeToulonais;
import net.minecraft.src.*;
/*@autor : MysterHyde
 * Class :EntityAILookAtTradePlayerToulonais
 * note : terminé
 */
public class EntityAILookAtTradePlayerToulonais extends EntityAIBase
{
    private EntityToulonais field_56834_a;

    public EntityAILookAtTradePlayerToulonais(EntityToulonais par1EntityToulonais)
    {
        field_56834_a = par1EntityToulonais;
        setMutexBits(5);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (field_56834_a.isInWater())
        {
            return false;
        }

        if (!field_56834_a.onGround)
        {
            return false;
        }

        if (field_56834_a.velocityChanged)
        {
            return false;
        }

        EntityPlayer entityplayer = field_56834_a.func_56221_a();

        if (entityplayer == null)
        {
            return false;
        }

        if (field_56834_a.getDistanceSqToEntity(entityplayer) > 16D)
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
        field_56834_a.getNavigator().clearPathEntity();
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        field_56834_a.func_56218_c_(null);
    }
}
