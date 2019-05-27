package net.asbyth.oldanimations;

import net.asbyth.oldanimations.animations.HealthFlashingAnimation;
import net.asbyth.oldanimations.command.OldAnimationsCommand;
import net.asbyth.oldanimations.gui.AnimationsGui;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

import static net.asbyth.oldanimations.config.Settings.*;

@Mod(modid = OldAnimations.MODID, name = OldAnimations.NAME, version = OldAnimations.VERSION)
public class OldAnimations {

    static final String MODID = "asbyth_oldanimations";
    static final String NAME = "Old Animations";
    static final String VERSION = "1.0";

    public static Logger LOGGER = LogManager.getLogger(MODID);
    private boolean gui = false;
    private final File configFile = new File(Minecraft.getMinecraft().mcDataDir, "config/" + MODID + ".cfg");

    @Mod.Instance(MODID)
    public static OldAnimations instance;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        LOGGER.info("Loading Configuration");
        loadConfig();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        LOGGER.info("Initialized Old Animations");

        ClientCommandHandler.instance.registerCommand(new OldAnimationsCommand());
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new HealthFlashingAnimation());
    }

    public void saveConfig() {
        Configuration configuration = new Configuration(configFile);
        configuration.save();
        updateConfig(configuration, false);
    }

    private void loadConfig() {
        Configuration configuration = new Configuration(configFile);
        configuration.load();
        updateConfig(configuration, true);
    }

    private void updateConfig(Configuration configuration, boolean load) {
        Property prop = configuration.get("General", "OLD_SNEAKING_ANIMATION", false);
        if (load) OLD_SNEAKING_ANIMATION = prop.getBoolean();
        else prop.setValue(OLD_SNEAKING_ANIMATION);

        prop = configuration.get("General", "OLD_HEALTH_FLASHING", false);
        if (load) OLD_HEALTH_FLASHING = prop.getBoolean();
        else prop.setValue(OLD_HEALTH_FLASHING);

        prop = configuration.get("General", "OLD_PLAYER_DAMAGE_FLASH", false);
        if (load) OLD_PLAYER_DAMAGE_FLASH = prop.getBoolean();
        else prop.setValue(OLD_PLAYER_DAMAGE_FLASH);

        prop = configuration.get("General", "OLD_BLOCKHITTING_ANIMATION", false);
        if (load) OLD_BLOCKHITTING_ANIMATION = prop.getBoolean();
        else prop.setValue(OLD_BLOCKHITTING_ANIMATION);

        prop = configuration.get("General", "OLD_ENCHANTMENT_GLINT", false);
        if (load) OLD_ENCHANTMENT_GLINT = prop.getBoolean();
        else prop.setValue(OLD_ENCHANTMENT_GLINT);

        prop = configuration.get("General", "OLD_BLOCKING_POSITION", false);
        if (load) OLD_BLOCKING_POSITION = prop.getBoolean();
        else prop.setValue(OLD_BLOCKING_POSITION);

        prop = configuration.get("General", "OLD_ROD_POSITION", false);
        if (load) OLD_ROD_POSITION = prop.getBoolean();
        else prop.setValue(OLD_ROD_POSITION);

        prop = configuration.get("General", "OLD_BOW_POSITION", false);
        if (load) OLD_BOW_POSITION = prop.getBoolean();
        else prop.setValue(OLD_BOW_POSITION);

        prop = configuration.get("General", "OLD_BOW_SCALE", false);
        if (load) OLD_BOW_SCALE = prop.getBoolean();
        else prop.setValue(OLD_BOW_SCALE);

        prop = configuration.get("General", "OLD_ROD_SCALE", false);
        if (load) OLD_ROD_SCALE = prop.getBoolean();
        else prop.setValue(OLD_ROD_SCALE);

        prop = configuration.get("General", "HIT_GROUND_WHILE_EATING", false);
        if (load) HIT_GROUND_WHILE_EATING = prop.getBoolean();
        else prop.setValue(HIT_GROUND_WHILE_EATING);

        prop = configuration.get("General", "HIT_GROUND_WHILE_AIMING", false);
        if (load) HIT_GROUND_WHILE_AIMING = prop.getBoolean();
        else prop.setValue(HIT_GROUND_WHILE_AIMING);

        prop = configuration.get("General", "OLD_TAB_LIST", false);
        if (load) OLD_TAB_LIST = prop.getBoolean();
        else prop.setValue(OLD_TAB_LIST);

        prop = configuration.get("General", "OLD_DEBUG_MENU", false);
        if (load) OLD_DEBUG_MENU = prop.getBoolean();
        else prop.setValue(OLD_DEBUG_MENU);

        prop = configuration.get("General", "OLD_ITEMS_POSITION", false);
        if (load) OLD_ITEMS_POSITION = prop.getBoolean();
        else prop.setValue(OLD_ITEMS_POSITION);
    }


    @SubscribeEvent
    public void renderTick(TickEvent.RenderTickEvent event) {
        if (gui) {
            Minecraft.getMinecraft().displayGuiScreen(new AnimationsGui());
            gui = false;
        }
    }

    public void openGui() {
        gui = true;
    }
}
