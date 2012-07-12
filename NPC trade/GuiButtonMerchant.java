package net.minecraft.src;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

class GuiButtonMerchant extends GuiButton
{
    private final boolean field_56463_j;

    public GuiButtonMerchant(int par1, int par2, int par3, boolean par4)
    {
        super(par1, par2, par3, 12, 19, "");
        field_56463_j = par4;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        if (!drawButton)
        {
            return;
        }

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, par1Minecraft.renderEngine.getTexture("/gui/trading.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        boolean flag = par2 >= xPosition && par3 >= yPosition && par2 < xPosition + field_52008_a && par3 < yPosition + field_52007_b;
        int i = 0;
        int j = 176;

        if (!enabled)
        {
            j += field_52008_a * 2;
        }
        else if (flag)
        {
            j += field_52008_a;
        }

        if (!field_56463_j)
        {
            i += field_52007_b;
        }

        drawTexturedModalRect(xPosition, yPosition, j, i, field_52008_a, field_52007_b);
    }
}
