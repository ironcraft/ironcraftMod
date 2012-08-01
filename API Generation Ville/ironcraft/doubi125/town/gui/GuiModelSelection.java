package ironcraft.doubi125.town.gui;

import ironcraft.doubi125.town.TownGenerator;
import ironcraft.doubi125.town.home.HomeModel;
import ironcraft.doubi125.town.home.model.HomeTheme;

import java.util.ArrayList;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.OpenGlHelper;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.Slot;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiModelSelection extends GuiScreen
{
    protected int xSize;
    protected int ySize;
    protected int guiLeft;
    protected int guiTop;
    protected int modelSelected, themeSelected;
    protected HomeModel[] models;
    protected ArrayList<HomeTheme> themes = new ArrayList<HomeTheme>();
    private int oro;
    private int ara;
    
    /** Amount scrolled in Creative mode inventory (0 = top, 1 = bottom) */
    private float currentScroll;
    private float currentScroll2;

    /** True if the scrollbar is being dragged */
    private boolean isScrolling;
    private boolean isScrolling2;
    /**
     * True if the left mouse button was held down last time drawScreen was called.
     */
    private boolean wasClicking;
    

    public GuiModelSelection(HomeModel[] models)
    {
    	xSize = 176*2;
        ySize = 166;
        currentScroll = 0.0F;
        currentScroll2 = 0.0F;
        isScrolling = false;
        allowUserInput = true;
        ySize = 208;
        this.models = models;
        themes = models[0].getThemes();
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();

        if (!mc.thePlayer.isEntityAlive() || mc.thePlayer.isDead)
        {
            mc.thePlayer.closeScreen();
        }
    }
    
    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        if (par2 == 1 || par2 == mc.gameSettings.keyBindInventory.keyCode)
        {
            mc.thePlayer.closeScreen();
        }
    }
    
    

    protected void handleMouseClick(Slot par1Slot, int par2, int par3, boolean par4)
    {
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
            super.initGui();
            guiLeft = (width - xSize) / 2;
            guiTop = (height - ySize) / 2;
            int j = guiLeft;
            int k = guiTop;
            controlList.clear();
            controlList.add(new GuiButton(1, guiLeft + 127, guiTop+139, 130, 20, "Generate!"));
    }

    /**
     * Draw the foreground layer for the GuiContainer (everythin in front of the items)
     */
    protected void drawGuiContainerForegroundLayer()
    {
    	
        //fontRenderer.drawString(StatCollector.translateToLocal("container.spell"), 8, 6, 0x404040);
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput()
    {
        super.handleMouseInput();
    }
    
    public void scrollTo(float par1)
    {
        
    }
    
    public void scrollTo2(float par1)
    {
        
    }
    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        boolean flag = Mouse.isButtonDown(0);
        int i = guiLeft;
        int j = guiTop;
        int k = i + 156;
        int k2 = i + 156 + 178;
        int l = j + 17;
        int i1 = k + 14;
        int i2 = k2 + 14;
        int j1 = l + 71 + 2;

        if (!wasClicking && flag && par1 >= k && par2 >= l && par1 < i1 && par2 < j1)
        {
            isScrolling = true;
        }
        
        if (!wasClicking && flag && par1 >= k2 && par2 >= l && par1 < i2 && par2 < j1)
        {
            isScrolling2 = true;
        }

        if (!flag)
        {
            isScrolling = false;
            isScrolling2=false;
        }

        wasClicking = flag;

        if (isScrolling)
        {
            currentScroll = (float)(par2 - (l + 8)) / ((float)(j1 - l) - 16F);

            if (currentScroll < 0.0F)
            {
                currentScroll = 0.0F;
            }

            if (currentScroll > 1.0F)
            {
                currentScroll = 1.0F;
            }

           scrollTo(currentScroll);
        }
        if (isScrolling2)
        {
            currentScroll2 = (float)(par2 - (l + 8)) / ((float)(j1 - l) - 16F);

            if (currentScroll2 < 0.0F)
            {
                currentScroll2 = 0.0F;
            }

            if (currentScroll2 > 1.0F)
            {
                currentScroll2 = 1.0F;
            }

           scrollTo2(currentScroll2);
        }

        drawDefaultBackground();
i = guiLeft;
j = guiTop;
        drawGuiContainerBackgroundLayer(par3, par1, par2);
        for(int n=0; n<4; n++)
        {
        	if(getFirstModelId() + n < models.length)
        		drawCenteredString(mc.fontRenderer, models[getFirstModelId()+n].getName(), guiLeft + 79, guiTop + 22+18*n, -1);
        	if(getFirstModelId() + n == modelSelected)
        		drawRect(guiLeft + 8, guiTop + 18*(n+1), guiLeft+150, guiTop + 18*(n+1) + 16, 0x5541bcf6);

        	if(getFirstThemeId() + n < themes.size())
        		drawCenteredString(mc.fontRenderer, themes.get(getFirstThemeId()+n).getName(), guiLeft + 79 + 178, guiTop + 22+18*n, -1);
        	if(getFirstThemeId() + n == themeSelected)
        		drawRect(guiLeft + 8 + 178, guiTop + 18*(n+1), guiLeft+150 + 178, guiTop + 18*(n+1) + 16, 0x5541bcf6);
        }
        drawString(mc.fontRenderer, "dimensions :", guiLeft + 133, guiTop + 115, -1);
        drawCenteredString(mc.fontRenderer, ""+TownGenerator.generator.getW(), guiLeft + 224, guiTop + 116, -1);
        drawCenteredString(mc.fontRenderer, ""+TownGenerator.generator.getH(), guiLeft + 245, guiTop + 116, -1);
        
        super.drawScreen(par1, par2, par3);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glPushMatrix();
        GL11.glTranslatef(i, j, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        Slot slot = null;
        k = 240;
        i1 = 240;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)k / 1.0F, (float)i1 / 1.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        drawGuiContainerForegroundLayer();

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        GL11.glPopMatrix();
    }
    
    private HomeModel getModelSelected()
    {
    	return models[modelSelected];
    }
    
    private HomeTheme getThemeSelected()
    {
    	return themes.get(themeSelected);
    }
    

    /**
     * Returns the slot at the given coordinates or null if there is none.
     */
    private Slot getSlotAtPosition(int par1, int par2)
    {
        return null;
    }
    
    private int getFirstModelId()
    {
    	return Math.max(0, (int)((models.length-4)*currentScroll));
    }
    
    private int getFirstThemeId()
    {
    	return Math.max(0, (int)((themes.size()-4)*currentScroll2));
    }
    
    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int x, int y, int button)
    {
        super.mouseClicked(x, y, button);
        
        if(y > guiTop + 17 && y < guiTop + 88)
        {
        	if(x > guiLeft + 8 && x < guiLeft + 150 && getFirstModelId() + (y-guiTop - 17)/18 < models.length)
        	{
        		modelSelected = getFirstModelId() + (y-guiTop - 17)/18;
        		
        		themes = getModelSelected().getThemes();
        		themeSelected = 0;
        		
        		TownGenerator.generator.updateDims(getModelSelected());
        	}
        	
        	if(x > guiLeft + 8 + 178 && x < guiLeft + 150 + 178 && getFirstThemeId() + (y-guiTop - 17)/18 < themes.size())
        		themeSelected = getFirstThemeId() + (y-guiTop - 17)/18;
        	
        }
        if(x >= guiLeft + 220 && x <= guiLeft + 228 && y <= guiTop + 109 && y >= guiTop + 105)
        	TownGenerator.generator.incrW();
        
        if(x >= guiLeft + 242 && x <= guiLeft + 249 && y <= guiTop + 109 && y >= guiTop + 105)
        	TownGenerator.generator.incrH();

        if(x >= guiLeft + 220 && x <= guiLeft + 228 && y <= guiTop + 134 && y >= guiTop + 130)
        	TownGenerator.generator.decrW(getModelSelected().getMinSize());
        

        if(x >= guiLeft + 242 && x <= guiLeft + 249 && y <= guiTop + 134 && y >= guiTop + 130)
        	TownGenerator.generator.decrH(getModelSelected().getMinSize());

        if (button == 0 || button == 1)
        {
            int i = guiLeft;
            int j = guiTop;
            boolean flag = x < i || y < j || x >= i + xSize || y >= j + ySize;
            int k = -1;

            if (flag)
            {
                k = -999;
            }

            if (k != -1)
            {
                boolean flag1 = k != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
            }
        }
    }

    /**
     * Returns if the passed mouse position is over the specified slot.
     */
    private boolean isMouseOverSlot(Slot par1Slot, int par2, int par3)
    {
        int i = guiLeft;
        int j = guiTop;
        par2 -= i;
        par3 -= j;
        return par2 >= par1Slot.xDisplayPosition - 1 && par2 < par1Slot.xDisplayPosition + 16 + 1 && par3 >= par1Slot.yDisplayPosition - 1 && par3 < par1Slot.yDisplayPosition + 16 + 1;
    }
    
    
    
    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int i = mc.renderEngine.getTexture("/gui/GuiDoubi.png");
        mc.renderEngine.bindTexture(i);
        int j = guiLeft;
        int k = guiTop;
        
        drawTexturedModalRect(j, k, 0, 0, 176, 97);
        drawTexturedModalRect(j+178, k, 0, 0, 176, 97);
        drawTexturedModalRect(j+123, k + 100, 0, 97, 138, 63);
        
        int l = j + 57;
        int i1 = k + 17;
        int j1 = i1 + 71 + 2;
        if(!isScrolling)
        drawTexturedModalRect(j +154,( k + 17 + (int)((float)(j1 - i1 - 17) * currentScroll)), 176, 0, 16, 16);
        if(isScrolling)
        	drawTexturedModalRect(j +154,( k + 17 + (int)((float)(j1 - i1 - 17) * currentScroll)), 176, 16, 16, 16);
        if(!isScrolling2)
            drawTexturedModalRect(j +332,( k + 17 + (int)((float)(j1 - i1 - 17) * currentScroll2)), 176, 0, 16, 16);
            if(isScrolling2)
            	drawTexturedModalRect(j +332,( k + 17 + (int)((float)(j1 - i1 - 17) * currentScroll2)), 176, 16, 16, 16);
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton button)
    {
    	if(button.id == 1)
    	{
    		TownGenerator.generator.genTest(mc.theWorld, (int)mc.thePlayer.posX, (int)mc.thePlayer.posY, (int)mc.thePlayer.posZ, getModelSelected(), getThemeSelected());
    		mc.thePlayer.closeScreen();
    	}
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
    }
    
    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}
