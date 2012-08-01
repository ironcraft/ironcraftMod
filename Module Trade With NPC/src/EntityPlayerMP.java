package net.minecraft.src;

import java.io.*;
import java.util.*;

import net.minecraft.TradeToulonais.ContainerMerchantToulonais;
import net.minecraft.TradeToulonais.IMerchantToulonais;
import net.minecraft.TradeToulonais.InventoryMerchantToulonais;
import net.minecraft.TradeToulonais.MerchantRecipeListToulonais;
import net.minecraft.server.MinecraftServer;

public class EntityPlayerMP extends EntityPlayer implements ICrafting
{
    private StringTranslate field_71148_cg;
    public NetServerHandler field_71135_a;
    public MinecraftServer field_71133_b;
    public ItemInWorldManager field_71134_c;
    public double field_71131_d;
    public double field_71132_e;
    public final List field_71129_f = new LinkedList();
    public final List field_71130_g = new LinkedList();
    private int field_71149_ch;
    private int field_71146_ci;
    private boolean field_71147_cj;
    private int field_71144_ck;
    private int field_71145_cl;
    private int field_71142_cm;
    private int field_71143_cn;
    private boolean field_71140_co;
    private ItemStack field_71141_cp[] =
    {
        null, null, null, null, null
    };
    private int field_71139_cq;
    public boolean field_71137_h;
    public int field_71138_i;
    public boolean field_71136_j;

    public EntityPlayerMP(MinecraftServer par1MinecraftServer, World par2World, String par3Str, ItemInWorldManager par4ItemInWorldManager)
    {
        super(par2World);
        field_71148_cg = new StringTranslate("en_US");
        field_71149_ch = 0xfa0a1f01;
        field_71146_ci = 0xfa0a1f01;
        field_71147_cj = true;
        field_71144_ck = 0xfa0a1f01;
        field_71145_cl = 60;
        field_71142_cm = 0;
        field_71143_cn = 0;
        field_71140_co = true;
        field_71139_cq = 0;
        field_71136_j = false;
        par4ItemInWorldManager.field_73090_b = this;
        field_71134_c = par4ItemInWorldManager;
        field_71142_cm = par1MinecraftServer.func_71203_ab().func_72395_o();
        ChunkCoordinates chunkcoordinates = par2World.getSpawnPoint();
        int i = chunkcoordinates.posX;
        int j = chunkcoordinates.posZ;
        int k = chunkcoordinates.posY;

        if (!par2World.worldProvider.hasNoSky && par2World.getWorldInfo().func_76077_q() != EnumGameType.ADVENTURE)
        {
            i += rand.nextInt(20) - 10;
            k = par2World.getTopSolidOrLiquidBlock(i, j);
            j += rand.nextInt(20) - 10;
        }

        setLocationAndAngles((double)i + 0.5D, k, (double)j + 0.5D, 0.0F, 0.0F);
        field_71133_b = par1MinecraftServer;
        stepHeight = 0.0F;
        username = par3Str;
        yOffset = 0.0F;
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);

        if (par1NBTTagCompound.hasKey("playerGameType"))
        {
            field_71134_c.func_73076_a(EnumGameType.func_77146_a(par1NBTTagCompound.getInteger("playerGameType")));
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("playerGameType", field_71134_c.func_73081_b().func_77148_a());
    }

    /**
     * Decrease the player level, used to pay levels for enchantments on items at enchanted table.
     */
    public void removeExperience(int par1)
    {
        super.removeExperience(par1);
        field_71144_ck = -1;
    }

    public void func_71116_b()
    {
        craftingInventory.func_75132_a(this);
    }

    public ItemStack[] func_70035_c()
    {
        return field_71141_cp;
    }

    /**
     * sets the players height back to normal after doing things like sleeping and dieing
     */
    protected void resetHeight()
    {
        yOffset = 0.0F;
    }

    public float getEyeHeight()
    {
        return 1.62F;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        field_71134_c.func_73075_a();
        field_71145_cl--;
        craftingInventory.updateCraftingResults();

        for (int i = 0; i < 5; i++)
        {
            ItemStack itemstack = func_71124_b(i);

            if (itemstack != field_71141_cp[i])
            {
                func_71121_q().func_73039_n().func_72784_a(this, new Packet5PlayerInventory(entityId, i, itemstack));
                field_71141_cp[i] = itemstack;
            }
        }

        if (!field_71129_f.isEmpty())
        {
            ArrayList arraylist = new ArrayList();
            Iterator iterator = field_71129_f.iterator();
            ArrayList arraylist1 = new ArrayList();

            do
            {
                if (!iterator.hasNext() || arraylist.size() >= 5)
                {
                    break;
                }

                ChunkCoordIntPair chunkcoordintpair = (ChunkCoordIntPair)iterator.next();
                iterator.remove();

                if (chunkcoordintpair != null && worldObj.blockExists(chunkcoordintpair.chunkXPos << 4, 0, chunkcoordintpair.chunkZPos << 4))
                {
                    arraylist.add(worldObj.getChunkFromChunkCoords(chunkcoordintpair.chunkXPos, chunkcoordintpair.chunkZPos));
                    arraylist1.addAll(((WorldServer)worldObj).func_73049_a(chunkcoordintpair.chunkXPos * 16, 0, chunkcoordintpair.chunkZPos * 16, chunkcoordintpair.chunkXPos * 16 + 16, 256, chunkcoordintpair.chunkZPos * 16 + 16));
                }
            }
            while (true);

            if (!arraylist.isEmpty())
            {
                field_71135_a.func_72567_b(new Packet56MapChunks(arraylist));
                TileEntity tileentity;

                for (Iterator iterator2 = arraylist1.iterator(); iterator2.hasNext(); func_71119_a(tileentity))
                {
                    tileentity = (TileEntity)iterator2.next();
                }
            }
        }

        if (!field_71130_g.isEmpty())
        {
            int j = Math.min(field_71130_g.size(), 127);
            int ai[] = new int[j];
            Iterator iterator1 = field_71130_g.iterator();

            for (int k = 0; iterator1.hasNext() && k < j; iterator1.remove())
            {
                ai[k++] = ((Integer)iterator1.next()).intValue();
            }

            field_71135_a.func_72567_b(new Packet29DestroyEntity(ai));
        }
    }

    public void func_71127_g()
    {
        super.onUpdate();

        for (int i = 0; i < inventory.getSizeInventory(); i++)
        {
            ItemStack itemstack = inventory.getStackInSlot(i);

            if (itemstack == null || !Item.itemsList[itemstack.itemID].func_77643_m_() || field_71135_a.func_72568_e() > 2)
            {
                continue;
            }

            Packet packet = ((ItemMapBase)Item.itemsList[itemstack.itemID]).func_77871_c(itemstack, worldObj, this);

            if (packet != null)
            {
                field_71135_a.func_72567_b(packet);
            }
        }

        if (inPortal)
        {
            if (field_71133_b.func_71255_r())
            {
                if (craftingInventory != inventorySlots)
                {
                    closeScreen();
                }

                if (ridingEntity != null)
                {
                    mountEntity(ridingEntity);
                }
                else
                {
                    timeInPortal += 0.0125F;

                    if (timeInPortal >= 1.0F)
                    {
                        timeInPortal = 1.0F;
                        timeUntilPortal = 10;
                        byte byte0 = 0;

                        if (dimension == -1)
                        {
                            byte0 = 0;
                        }
                        else
                        {
                            byte0 = -1;
                        }

                        field_71133_b.func_71203_ab().func_72356_a(this, byte0);
                        field_71144_ck = -1;
                        field_71149_ch = -1;
                        field_71146_ci = -1;
                        triggerAchievement(AchievementList.portal);
                    }
                }

                inPortal = false;
            }
        }
        else
        {
            if (timeInPortal > 0.0F)
            {
                timeInPortal -= 0.05F;
            }

            if (timeInPortal < 0.0F)
            {
                timeInPortal = 0.0F;
            }
        }

        if (timeUntilPortal > 0)
        {
            timeUntilPortal--;
        }

        if (getHealth() != field_71149_ch || field_71146_ci != foodStats.getFoodLevel() || (foodStats.getSaturationLevel() == 0.0F) != field_71147_cj)
        {
            field_71135_a.func_72567_b(new Packet8UpdateHealth(getHealth(), foodStats.getFoodLevel(), foodStats.getSaturationLevel()));
            field_71149_ch = getHealth();
            field_71146_ci = foodStats.getFoodLevel();
            field_71147_cj = foodStats.getSaturationLevel() == 0.0F;
        }

        if (experienceTotal != field_71144_ck)
        {
            field_71144_ck = experienceTotal;
            field_71135_a.func_72567_b(new Packet43Experience(experience, experienceTotal, experienceLevel));
        }
    }

    public ItemStack func_71124_b(int par1)
    {
        if (par1 == 0)
        {
            return inventory.getCurrentItem();
        }
        else
        {
            return inventory.armorInventory[par1 - 1];
        }
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource par1DamageSource)
    {
        field_71133_b.func_71203_ab().func_72384_a(new Packet3Chat(par1DamageSource.func_76360_b(this)));
        inventory.dropAllItems();
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
    {
        if (field_71145_cl > 0)
        {
            return false;
        }

        if (!field_71133_b.func_71219_W() && (par1DamageSource instanceof EntityDamageSource))
        {
            Entity entity = par1DamageSource.getEntity();

            if (entity instanceof EntityPlayer)
            {
                return false;
            }

            if (entity instanceof EntityArrow)
            {
                EntityArrow entityarrow = (EntityArrow)entity;

                if (entityarrow.shootingEntity instanceof EntityPlayer)
                {
                    return false;
                }
            }
        }

        return super.attackEntityFrom(par1DamageSource, par2);
    }

    /**
     * returns if pvp is enabled or not
     */
    protected boolean isPVPEnabled()
    {
        return field_71133_b.func_71219_W();
    }

    public void travelToTheEnd(int par1)
    {
        if (dimension == 1 && par1 == 1)
        {
            triggerAchievement(AchievementList.theEnd2);
            worldObj.setEntityDead(this);
            field_71136_j = true;
            field_71135_a.func_72567_b(new Packet70GameEvent(4, 0));
        }
        else
        {
            triggerAchievement(AchievementList.theEnd);
            ChunkCoordinates chunkcoordinates = field_71133_b.func_71218_a(par1).func_73054_j();

            if (chunkcoordinates != null)
            {
                field_71135_a.func_72569_a(chunkcoordinates.posX, chunkcoordinates.posY, chunkcoordinates.posZ, 0.0F, 0.0F);
            }

            field_71133_b.func_71203_ab().func_72356_a(this, 1);
            field_71144_ck = -1;
            field_71149_ch = -1;
            field_71146_ci = -1;
        }
    }

    private void func_71119_a(TileEntity par1TileEntity)
    {
        if (par1TileEntity != null)
        {
            Packet packet = par1TileEntity.func_70319_e();

            if (packet != null)
            {
                field_71135_a.func_72567_b(packet);
            }
        }
    }

    /**
     * Called whenever an item is picked up from walking over it. Args: pickedUpEntity, stackSize
     */
    public void onItemPickup(Entity par1Entity, int par2)
    {
        if (!par1Entity.isDead)
        {
            EntityTracker entitytracker = func_71121_q().func_73039_n();

            if (par1Entity instanceof EntityItem)
            {
                entitytracker.func_72784_a(par1Entity, new Packet22Collect(par1Entity.entityId, entityId));
            }

            if (par1Entity instanceof EntityArrow)
            {
                entitytracker.func_72784_a(par1Entity, new Packet22Collect(par1Entity.entityId, entityId));
            }

            if (par1Entity instanceof EntityXPOrb)
            {
                entitytracker.func_72784_a(par1Entity, new Packet22Collect(par1Entity.entityId, entityId));
            }
        }

        super.onItemPickup(par1Entity, par2);
        craftingInventory.updateCraftingResults();
    }

    /**
     * Swings the item the player is holding.
     */
    public void swingItem()
    {
        if (!isSwinging)
        {
            swingProgressInt = -1;
            isSwinging = true;
            func_71121_q().func_73039_n().func_72784_a(this, new Packet18Animation(this, 1));
        }
    }

    /**
     * Attempts to have the player sleep in a bed at the specified location.
     */
    public EnumStatus sleepInBedAt(int par1, int par2, int par3)
    {
        EnumStatus enumstatus = super.sleepInBedAt(par1, par2, par3);

        if (enumstatus == EnumStatus.OK)
        {
            Packet17Sleep packet17sleep = new Packet17Sleep(this, 0, par1, par2, par3);
            func_71121_q().func_73039_n().func_72784_a(this, packet17sleep);
            field_71135_a.func_72569_a(posX, posY, posZ, rotationYaw, rotationPitch);
            field_71135_a.func_72567_b(packet17sleep);
        }

        return enumstatus;
    }

    /**
     * Wake up the player if they're sleeping.
     */
    public void wakeUpPlayer(boolean par1, boolean par2, boolean par3)
    {
        if (isPlayerSleeping())
        {
            func_71121_q().func_73039_n().func_72789_b(this, new Packet18Animation(this, 3));
        }

        super.wakeUpPlayer(par1, par2, par3);

        if (field_71135_a != null)
        {
            field_71135_a.func_72569_a(posX, posY, posZ, rotationYaw, rotationPitch);
        }
    }

    /**
     * Called when a player mounts an entity. e.g. mounts a pig, mounts a boat.
     */
    public void mountEntity(Entity par1Entity)
    {
        super.mountEntity(par1Entity);
        field_71135_a.func_72567_b(new Packet39AttachEntity(this, ridingEntity));
        field_71135_a.func_72569_a(posX, posY, posZ, rotationYaw, rotationPitch);
    }

    /**
     * Takes in the distance the entity has fallen this tick and whether its on the ground to update the fall distance
     * and deal fall damage if landing on the ground.  Args: distanceFallenThisTick, onGround
     */
    protected void updateFallState(double d, boolean flag)
    {
    }

    public void func_71122_b(double par1, boolean par3)
    {
        super.updateFallState(par1, par3);
    }

    private void func_71117_bO()
    {
        field_71139_cq = field_71139_cq % 100 + 1;
    }

    /**
     * Displays the crafting GUI for a workbench.
     */
    public void displayWorkbenchGUI(int par1, int par2, int par3)
    {
        func_71117_bO();
        field_71135_a.func_72567_b(new Packet100OpenWindow(field_71139_cq, 1, "Crafting", 9));
        craftingInventory = new ContainerWorkbench(inventory, worldObj, par1, par2, par3);
        craftingInventory.windowId = field_71139_cq;
        craftingInventory.func_75132_a(this);
    }

    public void displayGUIEnchantment(int par1, int par2, int par3)
    {
        func_71117_bO();
        field_71135_a.func_72567_b(new Packet100OpenWindow(field_71139_cq, 4, "Enchanting", 9));
        craftingInventory = new ContainerEnchantment(inventory, worldObj, par1, par2, par3);
        craftingInventory.windowId = field_71139_cq;
        craftingInventory.func_75132_a(this);
    }

    /**
     * Displays the GUI for interacting with a chest inventory. Args: chestInventory
     */
    public void displayGUIChest(IInventory par1IInventory)
    {
        if (craftingInventory != inventorySlots)
        {
            closeScreen();
        }

        func_71117_bO();
        field_71135_a.func_72567_b(new Packet100OpenWindow(field_71139_cq, 0, par1IInventory.getInvName(), par1IInventory.getSizeInventory()));
        craftingInventory = new ContainerChest(inventory, par1IInventory);
        craftingInventory.windowId = field_71139_cq;
        craftingInventory.func_75132_a(this);
    }

    /**
     * Displays the furnace GUI for the passed in furnace entity. Args: tileEntityFurnace
     */
    public void displayGUIFurnace(TileEntityFurnace par1TileEntityFurnace)
    {
        func_71117_bO();
        field_71135_a.func_72567_b(new Packet100OpenWindow(field_71139_cq, 2, par1TileEntityFurnace.getInvName(), par1TileEntityFurnace.getSizeInventory()));
        craftingInventory = new ContainerFurnace(inventory, par1TileEntityFurnace);
        craftingInventory.windowId = field_71139_cq;
        craftingInventory.func_75132_a(this);
    }

    /**
     * Displays the dipsenser GUI for the passed in dispenser entity. Args: TileEntityDispenser
     */
    public void displayGUIDispenser(TileEntityDispenser par1TileEntityDispenser)
    {
        func_71117_bO();
        field_71135_a.func_72567_b(new Packet100OpenWindow(field_71139_cq, 3, par1TileEntityDispenser.getInvName(), par1TileEntityDispenser.getSizeInventory()));
        craftingInventory = new ContainerDispenser(inventory, par1TileEntityDispenser);
        craftingInventory.windowId = field_71139_cq;
        craftingInventory.func_75132_a(this);
    }

    /**
     * Displays the GUI for interacting with a brewing stand.
     */
    public void displayGUIBrewingStand(TileEntityBrewingStand par1TileEntityBrewingStand)
    {
        func_71117_bO();
        field_71135_a.func_72567_b(new Packet100OpenWindow(field_71139_cq, 5, par1TileEntityBrewingStand.getInvName(), par1TileEntityBrewingStand.getSizeInventory()));
        craftingInventory = new ContainerBrewingStand(inventory, par1TileEntityBrewingStand);
        craftingInventory.windowId = field_71139_cq;
        craftingInventory.func_75132_a(this);
    }

    public void func_71030_a(IMerchant par1IMerchant)
    {
        func_71117_bO();
        craftingInventory = new ContainerMerchant(inventory, par1IMerchant, worldObj);
        craftingInventory.windowId = field_71139_cq;
        craftingInventory.func_75132_a(this);
        InventoryMerchant inventorymerchant = ((ContainerMerchant)craftingInventory).func_75174_d();
        field_71135_a.func_72567_b(new Packet100OpenWindow(field_71139_cq, 6, inventorymerchant.getInvName(), inventorymerchant.getSizeInventory()));
        MerchantRecipeList merchantrecipelist = par1IMerchant.func_70934_b(this);

        if (merchantrecipelist != null)
        {
            try
            {
                ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
                DataOutputStream dataoutputstream = new DataOutputStream(bytearrayoutputstream);
                dataoutputstream.writeInt(field_71139_cq);
                merchantrecipelist.func_77200_a(dataoutputstream);
                field_71135_a.func_72567_b(new Packet250CustomPayload("MC|TrList", bytearrayoutputstream.toByteArray()));
            }
            catch (IOException ioexception)
            {
                ioexception.printStackTrace();
            }
        }
    }
    
    public void fonc_Marchant(IMerchantToulonais par1IMerchant)
    {
        func_71117_bO();
        craftingInventory = new ContainerMerchantToulonais(inventory, par1IMerchant, worldObj);
        craftingInventory.windowId = field_71139_cq;
        craftingInventory.func_75132_a(this);
        InventoryMerchantToulonais inventorymerchant = ((ContainerMerchantToulonais)craftingInventory).func_75174_d();
        field_71135_a.func_72567_b(new Packet100OpenWindow(field_71139_cq, 7, inventorymerchant.getInvName(), inventorymerchant.getSizeInventory()));
        MerchantRecipeListToulonais merchantrecipelist = par1IMerchant.func_70934_b(this);

        if (merchantrecipelist != null)
        {
            try
            {
                ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
                DataOutputStream dataoutputstream = new DataOutputStream(bytearrayoutputstream);
                dataoutputstream.writeInt(field_71139_cq);
                merchantrecipelist.func_77200_a(dataoutputstream);
                field_71135_a.func_72567_b(new Packet250CustomPayload("MC|TrListT", bytearrayoutputstream.toByteArray()));
            }
            catch (IOException ioexception)
            {
                ioexception.printStackTrace();
            }
        }
    }

    /**
     * inform the player of a change in a single slot
     */
    public void updateCraftingInventorySlot(Container par1Container, int par2, ItemStack par3ItemStack)
    {
        if (par1Container.getSlot(par2) instanceof SlotCrafting)
        {
            return;
        }

        if (field_71137_h)
        {
            return;
        }
        else
        {
            field_71135_a.func_72567_b(new Packet103SetSlot(par1Container.windowId, par2, par3ItemStack));
            return;
        }
    }

    public void func_71120_a(Container par1Container)
    {
        func_71110_a(par1Container, par1Container.func_75138_a());
    }

    public void func_71110_a(Container par1Container, List par2List)
    {
        field_71135_a.func_72567_b(new Packet104WindowItems(par1Container.windowId, par2List));
        field_71135_a.func_72567_b(new Packet103SetSlot(-1, -1, inventory.getItemStack()));
    }

    /**
     * send information about the crafting inventory to the client(currently only for furnace times)
     */
    public void updateCraftingInventoryInfo(Container par1Container, int par2, int par3)
    {
        field_71135_a.func_72567_b(new Packet105UpdateProgressbar(par1Container.windowId, par2, par3));
    }

    /**
     * sets current screen to null (used on escape buttons of GUIs)
     */
    public void closeScreen()
    {
        field_71135_a.func_72567_b(new Packet101CloseWindow(craftingInventory.windowId));
        func_71128_l();
    }

    public void func_71113_k()
    {
        if (field_71137_h)
        {
            return;
        }
        else
        {
            field_71135_a.func_72567_b(new Packet103SetSlot(-1, -1, inventory.getItemStack()));
            return;
        }
    }

    public void func_71128_l()
    {
        craftingInventory.onCraftGuiClosed(this);
        craftingInventory = inventorySlots;
    }

    /**
     * Adds a value to a statistic field.
     */
    public void addStat(StatBase par1StatBase, int par2)
    {
        if (par1StatBase == null)
        {
            return;
        }

        if (!par1StatBase.isIndependent)
        {
            for (; par2 > 100; par2 -= 100)
            {
                field_71135_a.func_72567_b(new Packet200Statistic(par1StatBase.statId, 100));
            }

            field_71135_a.func_72567_b(new Packet200Statistic(par1StatBase.statId, par2));
        }
    }

    public void func_71123_m()
    {
        if (ridingEntity != null)
        {
            mountEntity(ridingEntity);
        }

        if (riddenByEntity != null)
        {
            riddenByEntity.mountEntity(this);
        }

        if (sleeping)
        {
            wakeUpPlayer(true, false, false);
        }
    }

    public void func_71118_n()
    {
        field_71149_ch = 0xfa0a1f01;
    }

    /**
     * Add a chat message to the player
     */
    public void addChatMessage(String par1Str)
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        String s = stringtranslate.translateKey(par1Str);
        field_71135_a.func_72567_b(new Packet3Chat(s));
    }

    /**
     * Used for when item use count runs out, ie: eating completed
     */
    protected void onItemUseFinish()
    {
        field_71135_a.func_72567_b(new Packet38EntityStatus(entityId, (byte)9));
        super.onItemUseFinish();
    }

    /**
     * sets the itemInUse when the use item button is clicked. Args: itemstack, int maxItemUseDuration
     */
    public void setItemInUse(ItemStack par1ItemStack, int par2)
    {
        super.setItemInUse(par1ItemStack, par2);

        if (par1ItemStack != null && par1ItemStack.getItem() != null && par1ItemStack.getItem().getItemUseAction(par1ItemStack) == EnumAction.eat)
        {
            func_71121_q().func_73039_n().func_72789_b(this, new Packet18Animation(this, 5));
        }
    }

    protected void onNewPotionEffect(PotionEffect par1PotionEffect)
    {
        super.onNewPotionEffect(par1PotionEffect);
        field_71135_a.func_72567_b(new Packet41EntityEffect(entityId, par1PotionEffect));
    }

    protected void onChangedPotionEffect(PotionEffect par1PotionEffect)
    {
        super.onChangedPotionEffect(par1PotionEffect);
        field_71135_a.func_72567_b(new Packet41EntityEffect(entityId, par1PotionEffect));
    }

    protected void onFinishedPotionEffect(PotionEffect par1PotionEffect)
    {
        super.onFinishedPotionEffect(par1PotionEffect);
        field_71135_a.func_72567_b(new Packet42RemoveEntityEffect(entityId, par1PotionEffect));
    }

    /**
     * Move the entity to the coordinates informed, but keep yaw/pitch values.
     */
    public void setPositionAndUpdate(double par1, double par3, double par5)
    {
        field_71135_a.func_72569_a(par1, par3, par5, rotationYaw, rotationPitch);
    }

    /**
     * Called when the player performs a critical hit on the Entity. Args: entity that was hit critically
     */
    public void onCriticalHit(Entity par1Entity)
    {
        func_71121_q().func_73039_n().func_72789_b(this, new Packet18Animation(par1Entity, 6));
    }

    public void onEnchantmentCritical(Entity par1Entity)
    {
        func_71121_q().func_73039_n().func_72789_b(this, new Packet18Animation(par1Entity, 7));
    }

    public void func_71016_p()
    {
        if (field_71135_a == null)
        {
            return;
        }
        else
        {
            field_71135_a.func_72567_b(new Packet202PlayerAbilities(capabilities));
            return;
        }
    }

    public WorldServer func_71121_q()
    {
        return (WorldServer)worldObj;
    }

    public void func_71033_a(EnumGameType par1EnumGameType)
    {
        field_71134_c.func_73076_a(par1EnumGameType);
        field_71135_a.func_72567_b(new Packet70GameEvent(3, par1EnumGameType.func_77148_a()));
    }

    public void func_70006_a(String par1Str)
    {
        field_71135_a.func_72567_b(new Packet3Chat(par1Str));
    }

    public boolean func_70003_b(String par1Str)
    {
        if ("seed".equals(par1Str) && !field_71133_b.func_71262_S())
        {
            return true;
        }
        else
        {
            return field_71133_b.func_71203_ab().func_72353_e(username);
        }
    }

    public String func_71114_r()
    {
        String s = field_71135_a.field_72575_b.func_74430_c().toString();
        s = s.substring(s.indexOf("/") + 1);
        s = s.substring(0, s.indexOf(":"));
        return s;
    }

    public void func_71125_a(Packet204ClientInfo par1Packet204ClientInfo)
    {
        if (field_71148_cg.getLanguageList().containsKey(par1Packet204ClientInfo.func_73459_d()))
        {
            field_71148_cg.setLanguage(par1Packet204ClientInfo.func_73459_d());
        }

        int i = 256 >> par1Packet204ClientInfo.func_73461_f();

        if (i > 3 && i < 15)
        {
            field_71142_cm = i;
        }

        field_71143_cn = par1Packet204ClientInfo.func_73463_g();
        field_71140_co = par1Packet204ClientInfo.func_73460_h();

        if (field_71133_b.func_71264_H() && field_71133_b.func_71214_G().equals(username))
        {
            field_71133_b.func_71226_c(par1Packet204ClientInfo.func_73462_i());
        }
    }

    public StringTranslate func_71025_t()
    {
        return field_71148_cg;
    }

    public int func_71126_v()
    {
        return field_71143_cn;
    }

    public void func_71115_a(String par1Str, int par2)
    {
        String s = (new StringBuilder()).append(par1Str).append("\0").append(par2).toString();
        field_71135_a.func_72567_b(new Packet250CustomPayload("MC|TPack", s.getBytes()));
    }
}
