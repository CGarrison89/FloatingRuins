package bspkrs.floatingruins.fml.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import bspkrs.floatingruins.fml.Reference;
import cpw.mods.fml.client.config.GuiConfig;

public class GuiFRConfig extends GuiConfig
{
    @SuppressWarnings("rawtypes")
    public GuiFRConfig(GuiScreen parent)
    {
        super(parent, (new ConfigElement(Reference.config.getCategory(Configuration.CATEGORY_GENERAL))).getChildElements(),
                Reference.MODID, false, false, GuiConfig.getAbridgedConfigPath(Reference.config.toString()));
    }
}
