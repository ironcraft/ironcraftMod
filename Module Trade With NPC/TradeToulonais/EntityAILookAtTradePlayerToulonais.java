package net.minecraft.TradeToulonais;
import net.minecraft.src.*;
/*@autor : MysterHyde
 * Class :EntityAILookAtTradePlayerToulonais
 * note : terminé
 */
public class EntityAILookAtTradePlayerToulonais extends EntityAIBase
{
	 private final EntityToulonais field_75335_b;
	private EntityPlayer closestEntity;

	    public EntityAILookAtTradePlayerToulonais(EntityToulonais par1EntityVillager)
	    {
	        super(par1EntityVillager, net.minecraft.src.EntityPlayer.class, 8F);
	        field_75335_b = par1EntityVillager;
	    }

	    /**
	     * Returns whether the EntityAIBase should begin execution.
	     */
	    public boolean shouldExecute()
	    {
	        if (field_75335_b.func_70940_q())
	        {
	            closestEntity = field_75335_b.func_70931_l_();
	            return true;
	        }
	        else
	        {
	            return false;
	        }
	    }
}
