package net.minecraft.src;

import java.io.*;
import java.util.*;

import net.minecraft.TradeToulonais.ContainerMerchantToulonais;
import net.minecraft.TradeToulonais.IMerchantToulonais;
import net.minecraft.TradeToulonais.InventoryMerchantToulonais;
import net.minecraft.TradeToulonais.MerchantRecipeListToulonais;

public class EntityPlayerMP extends EntityPlayer implements ICrafting
{
    private StringTranslate field_56281_cn;
    public NetServerHandler field_56268_a;
    public MinecraftServer field_56266_b;
    public ItemInWorldManager field_56267_c;
    public double field_56264_d;
    public double field_56265_e;
    public List field_56262_f;
    private int field_56279_co;
    private int field_56280_cp;
    private boolean field_56277_cq;
    private int field_56278_cr;
    private int field_56276_cs;
    private int field_56274_ct;
    private int field_56272_cu;
    private boolean field_56271_cv;
    private ItemStack field_56270_cw[] =
    {
        null, null, null, null, null
    };
    private int field_56269_cx;
    public boolean field_56263_g;
    public int field_56273_h;
    public boolean field_56275_i;

    public EntityPlayerMP(MinecraftServer par1MinecraftServer, World par2World, String par3Str, ItemInWorldManager par4ItemInWorldManager)
    {
        super(par2World);
        field_56281_cn = new StringTranslate("en_US");
        field_56262_f = new LinkedList();
        field_56279_co = 0xfa0a1f01;
        field_56280_cp = 0xfa0a1f01;
        field_56277_cq = true;
        field_56278_cr = 0xfa0a1f01;
        field_56276_cs = 60;
        field_56274_ct = 0;
        field_56272_cu = 0;
        field_56271_cv = true;
        field_56269_cx = 0;
        field_56275_i = false;
        par4ItemInWorldManager.field_57368_b = this;
        field_56267_c = par4ItemInWorldManager;
        field_56274_ct = par1MinecraftServer.func_56339_Z().func_57122_o();
        ChunkCoordinates chunkcoordinates = par2World.getSpawnPoint();
        int i = chunkcoordinates.posX;
        int j = chunkcoordinates.posZ;
        int k = chunkcoordinates.posY;

        if (!par2World.worldProvider.hasNoSky && par2World.getWorldInfo().func_56915_q() != EnumGameType.ADVENTURE)
        {
            i += rand.nextInt(20) - 10;
            k = par2World.getTopSolidOrLiquidBlock(i, j);
            j += rand.nextInt(20) - 10;
        }

        setLocationAndAngles((double)i + 0.5D, k, (double)j + 0.5D, 0.0F, 0.0F);
        field_56266_b = par1MinecraftServer;
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
            field_56267_c.func_57359_a(EnumGameType.func_57536_a(par1NBTTagCompound.getInteger("playerGameType")));
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("playerGameType", field_56267_c.func_57362_b().func_57538_a());
    }

    /**
     * Decrease the player level, used to pay levels for enchantments on items at enchanted table.
     */
    public void removeExperience(int par1)
    {
        super.removeExperience(par1);
        field_56278_cr = -1;
    }

    public void func_56256_h()
    {
        craftingInventory.func_56981_a(this);
    }

    public ItemStack[] func_56178_y_()
    {
        return field_56270_cw;
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
        field_56267_c.func_57353_a();
        field_56276_cs--;
        craftingInventory.updateCraftingResults();

        for (int i = 0; i < 5; i++)
        {
            ItemStack itemstack = func_56253_e_(i);

            if (itemstack != field_56270_cw[i])
            {
                func_56260_H().func_56859_K().func_57593_a(this, new Packet5PlayerInventory(entityId, i, itemstack));
                field_56270_cw[i] = itemstack;
            }
        }

        for (int j = 0; j < 5 && !field_56262_f.isEmpty(); j++)
        {
            ChunkCoordIntPair chunkcoordintpair = (ChunkCoordIntPair)field_56262_f.get(0);

            if (chunkcoordintpair == null)
            {
                continue;
            }

            boolean flag = false;

            if (field_56268_a.func_56720_c() < 15)
            {
                flag = true;
            }

            if (!flag)
            {
                continue;
            }

            WorldServer worldserver = field_56266_b.func_56325_a(dimension);

            if (!worldserver.blockExists(chunkcoordintpair.chunkXPos << 4, 0, chunkcoordintpair.chunkZPosition << 4))
            {
                continue;
            }

            Chunk chunk = worldserver.getChunkFromChunkCoords(chunkcoordintpair.chunkXPos, chunkcoordintpair.chunkZPosition);

            if (!chunk.isTerrainPopulated)
            {
                continue;
            }

            field_56262_f.remove(chunkcoordintpair);
            field_56268_a.func_56717_b(new Packet51MapChunk(worldserver.getChunkFromChunkCoords(chunkcoordintpair.chunkXPos, chunkcoordintpair.chunkZPosition), true, 65535));
            List list = worldserver.func_56857_a(chunkcoordintpair.chunkXPos * 16, 0, chunkcoordintpair.chunkZPosition * 16, chunkcoordintpair.chunkXPos * 16 + 16, 256, chunkcoordintpair.chunkZPosition * 16 + 16);
            TileEntity tileentity;

            for (Iterator iterator = list.iterator(); iterator.hasNext(); func_56261_a(tileentity))
            {
                tileentity = (TileEntity)iterator.next();
            }
        }
    }

    public void func_56247_x()
    {
        super.onUpdate();

        for (int i = 0; i < inventory.getSizeInventory(); i++)
        {
            ItemStack itemstack = inventory.getStackInSlot(i);

            if (itemstack == null || !Item.itemsList[itemstack.itemID].func_56816_N_() || field_56268_a.func_56720_c() > 2)
            {
                continue;
            }

            Packet packet = ((ItemMapBase)Item.itemsList[itemstack.itemID]).func_56827_d(itemstack, worldObj, this);

            if (packet != null)
            {
                field_56268_a.func_56717_b(packet);
            }
        }

        if (inPortal)
        {
            if (field_56266_b.func_56354_p())
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

                        field_56266_b.func_56339_Z().func_57125_a(this, byte0);
                        field_56278_cr = -1;
                        field_56279_co = -1;
                        field_56280_cp = -1;
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

        if (getHealth() != field_56279_co || field_56280_cp != foodStats.getFoodLevel() || (foodStats.getSaturationLevel() == 0.0F) != field_56277_cq)
        {
            field_56268_a.func_56717_b(new Packet8UpdateHealth(getHealth(), foodStats.getFoodLevel(), foodStats.getSaturationLevel()));
            field_56279_co = getHealth();
            field_56280_cp = foodStats.getFoodLevel();
            field_56277_cq = foodStats.getSaturationLevel() == 0.0F;
        }

        if (experienceTotal != field_56278_cr)
        {
            field_56278_cr = experienceTotal;
            field_56268_a.func_56717_b(new Packet43Experience(experience, experienceTotal, experienceLevel));
        }
    }

    public ItemStack func_56253_e_(int par1)
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
        field_56266_b.func_56339_Z().func_57114_a(new Packet3Chat(par1DamageSource.func_57337_a(this)));
        inventory.dropAllItems();
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
    {
        if (field_56276_cs > 0)
        {
            return false;
        }

        if (!field_56266_b.func_56353_U() && (par1DamageSource instanceof EntityDamageSource))
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
        return field_56266_b.func_56353_U();
    }

    public void travelToTheEnd(int par1)
    {
        if (dimension == 1 && par1 == 1)
        {
            triggerAchievement(AchievementList.theEnd2);
            worldObj.setEntityDead(this);
            field_56275_i = true;
            field_56268_a.func_56717_b(new Packet70GameEvent(4, 0));
        }
        else
        {
            triggerAchievement(AchievementList.theEnd);
            ChunkCoordinates chunkcoordinates = field_56266_b.func_56325_a(par1).func_56847_G();

            if (chunkcoordinates != null)
            {
                field_56268_a.func_56719_a(chunkcoordinates.posX, chunkcoordinates.posY, chunkcoordinates.posZ, 0.0F, 0.0F);
            }

            field_56266_b.func_56339_Z().func_57125_a(this, 1);
            field_56278_cr = -1;
            field_56279_co = -1;
            field_56280_cp = -1;
        }
    }

    private void func_56261_a(TileEntity par1TileEntity)
    {
        if (par1TileEntity != null)
        {
            Packet packet = par1TileEntity.func_56203_d();

            if (packet != null)
            {
                field_56268_a.func_56717_b(packet);
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
            EntityTracker entitytracker = func_56260_H().func_56859_K();

            if (par1Entity instanceof EntityItem)
            {
                entitytracker.func_57593_a(par1Entity, new Packet22Collect(par1Entity.entityId, entityId));
            }

            if (par1Entity instanceof EntityArrow)
            {
                entitytracker.func_57593_a(par1Entity, new Packet22Collect(par1Entity.entityId, entityId));
            }

            if (par1Entity instanceof EntityXPOrb)
            {
                entitytracker.func_57593_a(par1Entity, new Packet22Collect(par1Entity.entityId, entityId));
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
            func_56260_H().func_56859_K().func_57593_a(this, new Packet18Animation(this, 1));
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
            func_56260_H().func_56859_K().func_57593_a(this, packet17sleep);
            field_56268_a.func_56719_a(posX, posY, posZ, rotationYaw, rotationPitch);
            field_56268_a.func_56717_b(packet17sleep);
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
            func_56260_H().func_56859_K().func_57596_b(this, new Packet18Animation(this, 3));
        }

        super.wakeUpPlayer(par1, par2, par3);

        if (field_56268_a != null)
        {
            field_56268_a.func_56719_a(posX, posY, posZ, rotationYaw, rotationPitch);
        }
    }

    /**
     * Called when a player mounts an entity. e.g. mounts a pig, mounts a boat.
     */
    public void mountEntity(Entity par1Entity)
    {
        super.mountEntity(par1Entity);
        field_56268_a.func_56717_b(new Packet39AttachEntity(this, ridingEntity));
        field_56268_a.func_56719_a(posX, posY, posZ, rotationYaw, rotationPitch);
    }

    /**
     * Takes in the distance the entity has fallen this tick and whether its on the ground to update the fall distance
     * and deal fall damage if landing on the ground.  Args: distanceFallenThisTick, onGround
     */
    protected void updateFallState(double d, boolean flag)
    {
    }

    public void func_56255_b(double par1, boolean par3)
    {
        super.updateFallState(par1, par3);
    }

    private void func_56252_bn()
    {
        field_56269_cx = field_56269_cx % 100 + 1;
    }

    /**
     * Displays the crafting GUI for a workbench.
     */
    public void displayWorkbenchGUI(int par1, int par2, int par3)
    {
        func_56252_bn();
        field_56268_a.func_56717_b(new Packet100OpenWindow(field_56269_cx, 1, "Crafting", 9));
        craftingInventory = new ContainerWorkbench(inventory, worldObj, par1, par2, par3);
        craftingInventory.windowId = field_56269_cx;
        craftingInventory.func_56981_a(this);
    }

    public void displayGUIEnchantment(int par1, int par2, int par3)
    {
        func_56252_bn();
        field_56268_a.func_56717_b(new Packet100OpenWindow(field_56269_cx, 4, "Enchanting", 9));
        craftingInventory = new ContainerEnchantment(inventory, worldObj, par1, par2, par3);
        craftingInventory.windowId = field_56269_cx;
        craftingInventory.func_56981_a(this);
    }

    /**
     * Displays the GUI for interacting with a chest inventory. Args: chestInventory
     */
    public void displayGUIChest(IInventory par1IInventory)
    {
        func_56252_bn();
        field_56268_a.func_56717_b(new Packet100OpenWindow(field_56269_cx, 0, par1IInventory.getInvName(), par1IInventory.getSizeInventory()));
        craftingInventory = new ContainerChest(inventory, par1IInventory);
        craftingInventory.windowId = field_56269_cx;
        craftingInventory.func_56981_a(this);
    }

    /**
     * Displays the furnace GUI for the passed in furnace entity. Args: tileEntityFurnace
     */
    public void displayGUIFurnace(TileEntityFurnace par1TileEntityFurnace)
    {
        func_56252_bn();
        field_56268_a.func_56717_b(new Packet100OpenWindow(field_56269_cx, 2, par1TileEntityFurnace.getInvName(), par1TileEntityFurnace.getSizeInventory()));
        craftingInventory = new ContainerFurnace(inventory, par1TileEntityFurnace);
        craftingInventory.windowId = field_56269_cx;
        craftingInventory.func_56981_a(this);
    }

    /**
     * Displays the dipsenser GUI for the passed in dispenser entity. Args: TileEntityDispenser
     */
    public void displayGUIDispenser(TileEntityDispenser par1TileEntityDispenser)
    {
        func_56252_bn();
        field_56268_a.func_56717_b(new Packet100OpenWindow(field_56269_cx, 3, par1TileEntityDispenser.getInvName(), par1TileEntityDispenser.getSizeInventory()));
        craftingInventory = new ContainerDispenser(inventory, par1TileEntityDispenser);
        craftingInventory.windowId = field_56269_cx;
        craftingInventory.func_56981_a(this);
    }

    /**
     * Displays the GUI for interacting with a brewing stand.
     */
    public void displayGUIBrewingStand(TileEntityBrewingStand par1TileEntityBrewingStand)
    {
        func_56252_bn();
        field_56268_a.func_56717_b(new Packet100OpenWindow(field_56269_cx, 5, par1TileEntityBrewingStand.getInvName(), par1TileEntityBrewingStand.getSizeInventory()));
        craftingInventory = new ContainerBrewingStand(inventory, par1TileEntityBrewingStand);
        craftingInventory.windowId = field_56269_cx;
        craftingInventory.func_56981_a(this);
    }

    public void func_56241_a(IMerchant par1IMerchant)
    {
        func_56252_bn();
        craftingInventory = new ContainerMerchant(inventory, par1IMerchant, worldObj);
        craftingInventory.windowId = field_56269_cx;
        craftingInventory.func_56981_a(this);
        InventoryMerchant inventorymerchant = ((ContainerMerchant)craftingInventory).func_56983_c();
        field_56268_a.func_56717_b(new Packet100OpenWindow(field_56269_cx, 6, inventorymerchant.getInvName(), inventorymerchant.getSizeInventory()));
        MerchantRecipeList merchantrecipelist = par1IMerchant.func_56220_b(this);

        if (merchantrecipelist != null)
        {
            try
            {
                ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
                DataOutputStream dataoutputstream = new DataOutputStream(bytearrayoutputstream);
                dataoutputstream.writeInt(field_56269_cx);
                merchantrecipelist.func_57491_a(dataoutputstream);
                field_56268_a.func_56717_b(new Packet250CustomPayload("MC|TrList", bytearrayoutputstream.toByteArray()));
            }
            catch (IOException ioexception)
            {
                ioexception.printStackTrace();
            }
        }
    }
    /*@autor : MysterHyde
     * methode :func_56241_a2
     * @see net.minecraft.src.EntityPlayer#func_56241_a2(net.minecraft.TradeToulonais.IMerchantToulonais)
     */
    public void func_56241_a2(IMerchantToulonais par1IMerchant2)
    {
        func_56252_bn();
        craftingInventory = new ContainerMerchantToulonais(inventory, par1IMerchant2, worldObj);
        craftingInventory.windowId = field_56269_cx;
        craftingInventory.func_56981_a(this);
        InventoryMerchantToulonais inventorymerchant = ((ContainerMerchantToulonais)craftingInventory).func_56983_c();
        field_56268_a.func_56717_b(new Packet100OpenWindow(field_56269_cx, 7, inventorymerchant.getInvName(), inventorymerchant.getSizeInventory())); // switch 7 pour le paque HandleWindow
        MerchantRecipeListToulonais merchantrecipelist = par1IMerchant2.func_56220_b(this);

        if (merchantrecipelist != null)
        {
            try
            {
                ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
                DataOutputStream dataoutputstream = new DataOutputStream(bytearrayoutputstream);
                dataoutputstream.writeInt(field_56269_cx);
                merchantrecipelist.func_57491_a(dataoutputstream);
                field_56268_a.func_56717_b(new Packet250CustomPayload("MC|TrList", bytearrayoutputstream.toByteArray()));
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

        if (field_56263_g)
        {
            return;
        }
        else
        {
            field_56268_a.func_56717_b(new Packet103SetSlot(par1Container.windowId, par2, par3ItemStack));
            return;
        }
    }

    public void func_56259_a(Container par1Container)
    {
        func_56245_a(par1Container, par1Container.func_56980_a());
    }

    public void func_56245_a(Container par1Container, List par2List)
    {
        field_56268_a.func_56717_b(new Packet104WindowItems(par1Container.windowId, par2List));
        field_56268_a.func_56717_b(new Packet103SetSlot(-1, -1, inventory.getItemStack()));
    }

    /**
     * send information about the crafting inventory to the client(currently only for furnace times)
     */
    public void updateCraftingInventoryInfo(Container par1Container, int par2, int par3)
    {
        field_56268_a.func_56717_b(new Packet105UpdateProgressbar(par1Container.windowId, par2, par3));
    }

    /**
     * sets current screen to null (used on escape buttons of GUIs)
     */
    public void closeScreen()
    {
        field_56268_a.func_56717_b(new Packet101CloseWindow(craftingInventory.windowId));
        func_56251_C();
    }

    public void func_56248_B()
    {
        if (field_56263_g)
        {
            return;
        }
        else
        {
            field_56268_a.func_56717_b(new Packet103SetSlot(-1, -1, inventory.getItemStack()));
            return;
        }
    }

    public void func_56251_C()
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
                field_56268_a.func_56717_b(new Packet200Statistic(par1StatBase.statId, 100));
            }

            field_56268_a.func_56717_b(new Packet200Statistic(par1StatBase.statId, par2));
        }
    }

    public void func_56249_D()
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

    public void func_56254_C_()
    {
        field_56279_co = 0xfa0a1f01;
    }

    /**
     * Add a chat message to the player
     */
    public void addChatMessage(String par1Str)
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        String s = stringtranslate.translateKey(par1Str);
        field_56268_a.func_56717_b(new Packet3Chat(s));
    }

    /**
     * Used for when item use count runs out, ie: eating completed
     */
    protected void onItemUseFinish()
    {
        field_56268_a.func_56717_b(new Packet38EntityStatus(entityId, (byte)9));
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
            func_56260_H().func_56859_K().func_57596_b(this, new Packet18Animation(this, 5));
        }
    }

    protected void onNewPotionEffect(PotionEffect par1PotionEffect)
    {
        super.onNewPotionEffect(par1PotionEffect);
        field_56268_a.func_56717_b(new Packet41EntityEffect(entityId, par1PotionEffect));
    }

    protected void onChangedPotionEffect(PotionEffect par1PotionEffect)
    {
        super.onChangedPotionEffect(par1PotionEffect);
        field_56268_a.func_56717_b(new Packet41EntityEffect(entityId, par1PotionEffect));
    }

    protected void onFinishedPotionEffect(PotionEffect par1PotionEffect)
    {
        super.onFinishedPotionEffect(par1PotionEffect);
        field_56268_a.func_56717_b(new Packet42RemoveEntityEffect(entityId, par1PotionEffect));
    }

    /**
     * Move the entity to the coordinates informed, but keep yaw/pitch values.
     */
    public void setPositionAndUpdate(double par1, double par3, double par5)
    {
        field_56268_a.func_56719_a(par1, par3, par5, rotationYaw, rotationPitch);
    }

    /**
     * Called when the player performs a critical hit on the Entity. Args: entity that was hit critically
     */
    public void onCriticalHit(Entity par1Entity)
    {
        func_56260_H().func_56859_K().func_57596_b(this, new Packet18Animation(par1Entity, 6));
    }

    public void onEnchantmentCritical(Entity par1Entity)
    {
        func_56260_H().func_56859_K().func_57596_b(this, new Packet18Animation(par1Entity, 7));
    }

    public void func_50009_aI()
    {
        if (field_56268_a == null)
        {
            return;
        }
        else
        {
            field_56268_a.func_56717_b(new Packet202PlayerAbilities(capabilities));
            return;
        }
    }

    public WorldServer func_56260_H()
    {
        return (WorldServer)worldObj;
    }

    public void func_56240_a(EnumGameType par1EnumGameType)
    {
        field_56267_c.func_57359_a(par1EnumGameType);
        field_56268_a.func_56717_b(new Packet70GameEvent(3, par1EnumGameType.func_57538_a()));
    }

    public void func_55086_a(String par1Str)
    {
        field_56268_a.func_56717_b(new Packet3Chat(par1Str));
    }

    public boolean func_55084_b(String par1Str)
    {
        if ("seed".equals(par1Str) && !field_56266_b.func_56371_Q())
        {
            return true;
        }
        else
        {
            return field_56266_b.func_56339_Z().func_57087_e(username);
        }
    }

    public String func_56258_I()
    {
        String s = field_56268_a.field_56726_b.func_57272_c().toString();
        s = s.substring(s.indexOf("/") + 1);
        s = s.substring(0, s.indexOf(":"));
        return s;
    }

    public void func_56246_a(Packet204ClientInfo par1Packet204ClientInfo)
    {
        if (field_56281_cn.getLanguageList().containsKey(par1Packet204ClientInfo.func_56541_b()))
        {
            field_56281_cn.setLanguage(par1Packet204ClientInfo.func_56541_b());
        }

        int i = 256 >> par1Packet204ClientInfo.func_56542_c();

        if (i > 3 && i < 15)
        {
            field_56274_ct = i;
        }

        field_56272_cu = par1Packet204ClientInfo.func_56540_d();
        field_56271_cv = par1Packet204ClientInfo.func_56539_e();

        if (field_56266_b.func_56307_I() && field_56266_b.func_56296_H().equals(username))
        {
            field_56266_b.func_56364_c(par1Packet204ClientInfo.func_56538_f());
        }
    }

    public StringTranslate func_55089_aK()
    {
        return field_56281_cn;
    }

    public int func_56257_K()
    {
        return field_56272_cu;
    }

    public void func_56250_a(String par1Str, int par2)
    {
        String s = (new StringBuilder()).append(par1Str).append("\0").append(par2).toString();
        field_56268_a.func_56717_b(new Packet250CustomPayload("MC|TPack", s.getBytes()));
    }
}
