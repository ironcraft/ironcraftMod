package ironcraft.doubi125.town.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiButton;

import org.lwjgl.opengl.GL11;

public class GuiButtonSpell extends GuiButton {
	public boolean activated;
	public GuiButtonSpell(int par1, int par2, int par3, String par4Str)
    {
        this(par1, par2, par3, 200, 20, par4Str);
    }
	public GuiButtonSpell(int par1, int par2, int par3, int par4, int par5, String par6Str) {
		super(par1, par2, par3, par4, par5, par6Str);
	}
	
	public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        if (!drawButton)
        {
            return;
        }

        FontRenderer fontrenderer = par1Minecraft.fontRenderer;
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, par1Minecraft.renderEngine.getTexture("/gui/gui.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        boolean flag = par2 >= xPosition && par3 >= yPosition && par2 < xPosition + field_52008_a && par3 < yPosition + field_52007_b;
        int i = getHoverState(flag);
        if(activated)
        	i=2;
        drawTexturedModalRect(xPosition, yPosition, 1, 127 + i * 20, field_52008_a / 2, field_52007_b-2);
        drawTexturedModalRect(xPosition + field_52008_a / 2, yPosition, 200 - field_52008_a / 2, 127 + i * 20, field_52008_a / 2-1, field_52007_b-2);
        mouseDragged(par1Minecraft, par2, par3);
        int j = 0xe0e0e0;

        if (!enabled)
        {
            j = 0xffa0a0a0;
        }
        else if (flag)
        {
            j = 0xffffa0;
        }
//        updateTimer();
      drawCenteredString(fontrenderer,displayString, xPosition + field_52008_a / 2, yPosition + (field_52007_b - 8) / 2, j);
        //drawCenteredString(fontrenderer, xPosition+" "+yPosition+" "+displayString+" "+time, xPosition + field_52008_a / 2, yPosition + (field_52007_b - 8) / 2, j);
    }

}
