package net.minecraft.TradeToulonais;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
/*@autor : MysterHyde
 * Class :GuiMerchantToulonais
 * note : non terminé
 */
public class GuiMerchantToulonais extends GuiContainer
{
    private IMerchantToulonais field_56488_h;
    private GuiButtonMerchantToulonais field_56489_i;
    private GuiButtonMerchantToulonais field_56486_j;
    private int field_56487_k;

    public GuiMerchantToulonais(InventoryPlayer par1InventoryPlayer, IMerchantToulonais par2IMerchant, World par3World)
    {
        super(new ContainerMerchantToulonais(par1InventoryPlayer, par2IMerchant, par3World));
        field_56487_k = 0;
        field_56488_h = par2IMerchant;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        int i = (width - xSize) / 2;
        int j = (height - ySize) / 2;
        controlList.add(field_56489_i = new GuiButtonMerchantToulonais(1, i + 120 + 27, (j + 24) - 1, true));
        controlList.add(field_56486_j = new GuiButtonMerchantToulonais(2, (i + 36) - 19, (j + 24) - 1, false));
        field_56489_i.enabled = false;
        field_56486_j.enabled = false;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everythin in front of the items)
     */
    protected void drawGuiContainerForegroundLayer()
    {
        fontRenderer.drawString(StatCollector.translateToLocal("entity.Toulonais.name"), 56, 6, 0x404040);
        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, (ySize - 96) + 2, 0x404040);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
        MerchantRecipeListToulonais merchantrecipelist = field_56488_h.func_56220_b(mc.field_56455_h);

        if (merchantrecipelist != null)
        {
            field_56489_i.enabled = field_56487_k < merchantrecipelist.size() - 1;
            field_56486_j.enabled = field_56487_k > 0;
        }
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        boolean flag = false;

        if (par1GuiButton == field_56489_i)
        {
            field_56487_k++;
            flag = true;
        }
        else if (par1GuiButton == field_56486_j)
        {
            field_56487_k--;
            flag = true;
        }

        if (flag)
        {
            ((ContainerMerchantToulonais)inventorySlots).func_56984_c(field_56487_k);
            ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
            DataOutputStream dataoutputstream = new DataOutputStream(bytearrayoutputstream);

            try
            {
                dataoutputstream.writeInt(field_56487_k);
                mc.getSendQueue().addToSendQueue(new Packet250CustomPayload("MC|TrSel", bytearrayoutputstream.toByteArray()));
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        int i = mc.renderEngine.getTexture("/gui/tradingWithToulonais.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        super.drawScreen(par1, par2, par3);
        MerchantRecipeListToulonais merchantrecipelist = field_56488_h.func_56220_b(mc.field_56455_h);

        if (merchantrecipelist != null && !merchantrecipelist.isEmpty())
        {
            int i = (width - xSize) / 2;
            int j = (height - ySize) / 2;
            GL11.glPushMatrix();
            int k = field_56487_k;
            MerchantRecipeToulonais merchantrecipe = (MerchantRecipeToulonais)merchantrecipelist.get(k);
            ItemStack itemstack = merchantrecipe.func_57067_a();
            ItemStack itemstack1 = merchantrecipe.func_57065_b();
            ItemStack itemstack2 = merchantrecipe.func_57071_d();
            RenderHelper.enableGUIStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glEnable(GL11.GL_COLOR_MATERIAL);
            GL11.glEnable(GL11.GL_LIGHTING);
            itemRenderer.zLevel = 100F;
            itemRenderer.renderItemIntoGUI(fontRenderer, mc.renderEngine, itemstack, i + 36, j + 24);
            itemRenderer.renderItemOverlayIntoGUI(fontRenderer, mc.renderEngine, itemstack, i + 36, j + 24);

            if (itemstack1 != null)
            {
                itemRenderer.renderItemIntoGUI(fontRenderer, mc.renderEngine, itemstack1, i + 62, j + 24);
                itemRenderer.renderItemOverlayIntoGUI(fontRenderer, mc.renderEngine, itemstack1, i + 62, j + 24);
            }

            itemRenderer.renderItemIntoGUI(fontRenderer, mc.renderEngine, itemstack2, i + 120, j + 24);
            itemRenderer.renderItemOverlayIntoGUI(fontRenderer, mc.renderEngine, itemstack2, i + 120, j + 24);
            itemRenderer.zLevel = 0.0F;
            GL11.glDisable(GL11.GL_LIGHTING);

            if (func_56484_a(36, 24, 16, 16, par1, par2))
            {
                func_56482_a(itemstack, par1, par2);
            }
            else if (itemstack1 != null && func_56484_a(62, 24, 16, 16, par1, par2))
            {
                func_56482_a(itemstack1, par1, par2);
            }
            else if (func_56484_a(120, 24, 16, 16, par1, par2))
            {
                func_56482_a(itemstack2, par1, par2);
            }

            GL11.glPopMatrix();
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            RenderHelper.enableStandardItemLighting();
        }
    }

    public IMerchantToulonais func_56485_g()
    {
        return field_56488_h;
    }
}
