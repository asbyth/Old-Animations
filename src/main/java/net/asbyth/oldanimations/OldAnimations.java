package net.asbyth.oldanimations;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.asbyth.oldanimations.command.OldAnimationsCommand;
import net.asbyth.oldanimations.gui.AnimationsGui;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

import static net.asbyth.oldanimations.config.Settings.*;

@Mod(modid = OldAnimations.MODID, name = OldAnimations.NAME, version = OldAnimations.VERSION)
public class OldAnimations {

    static final String MODID = "asbyth_oldanimations";
    static final String NAME = "Old Animations";
    static final String VERSION = "1.0";

    public static Logger LOGGER = LogManager.getLogger(MODID);
    private boolean gui = false;
    private File configFile;

    @Mod.Instance(MODID)
    public static OldAnimations instance;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        configFile = event.getSuggestedConfigurationFile();
        loadConfig();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        LOGGER.info("Initialized Old Animations");

        ClientCommandHandler.instance.registerCommand(new OldAnimationsCommand());
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void saveConfig() {
        try {
            JsonObject object = new JsonObject();

            object.addProperty("OLD_SNEAKING_ANIMATION", OLD_SNEAKING_ANIMATION);
            object.addProperty("OLD_HEALTH_FLASHING", OLD_HEALTH_FLASHING);
            object.addProperty("OLD_PLAYER_DAMAGE_FLASH", OLD_PLAYER_DAMAGE_FLASH);
            object.addProperty("OLD_BLOCKHITTING_ANIMATION", OLD_BLOCKHITTING_ANIMATION);
            object.addProperty("OLD_ENCHANTMENT_GLINT", OLD_ENCHANTMENT_GLINT);

            object.addProperty("OLD_BLOCKING_POSITION", OLD_BLOCKING_POSITION);
            object.addProperty("OLD_ROD_POSITION", OLD_ROD_POSITION);
            object.addProperty("OLD_BOW_POSITION", OLD_BOW_POSITION);

            object.addProperty("OLD_BOW_SCALE", OLD_BOW_SCALE);
            object.addProperty("OLD_ROD_SCALE", OLD_ROD_SCALE);

            object.addProperty("HIT_GROUND_WHILE_EATING", HIT_GROUND_WHILE_EATING);
            object.addProperty("HIT_GROUND_WHILE_AIMING", HIT_GROUND_WHILE_AIMING);

            object.addProperty("OLD_TAB_LIST", OLD_TAB_LIST);
            object.addProperty("OLD_DEBUG_MENU", OLD_DEBUG_MENU);

            FileUtils.writeStringToFile(configFile, object.toString(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadConfig() { // the sk1er route because fuck Property and that extra shit :-)
        try {
            if (!configFile.exists()) configFile.createNewFile();
            JsonObject object = new JsonParser().parse(FileUtils.readFileToString(configFile, "UTF-8")).getAsJsonObject();

            if (object.has("OLD_SNEAKING_ANIMATION")) OLD_SNEAKING_ANIMATION = object.get("OLD_SNEAKING_ANIMATION").getAsBoolean();
            if (object.has("OLD_HEALTH_FLASHING")) OLD_HEALTH_FLASHING = object.get("OLD_HEALTH_FLASHING").getAsBoolean();
            if (object.has("OLD_PLAYER_DAMAGE_FLASH")) OLD_PLAYER_DAMAGE_FLASH = object.get("OLD_PLAYER_DAMAGE_FLASH").getAsBoolean();
            if (object.has("OLD_BLOCKHITTING_ANIMATION")) OLD_BLOCKHITTING_ANIMATION = object.get("OLD_BLOCKHITTING_ANIMATION").getAsBoolean();
            if (object.has("OLD_ENCHANTMENT_GLINT")) OLD_ENCHANTMENT_GLINT = object.get("OLD_ENCHANTMENT_GLINT").getAsBoolean();

            if (object.has("OLD_BLOCKING_POSITION")) OLD_BLOCKING_POSITION = object.get("OLD_BLOCKING_POSITION").getAsBoolean();
            if (object.has("OLD_ROD_POSITION")) OLD_ROD_POSITION = object.get("OLD_ROD_POSITION").getAsBoolean();
            if (object.has("OLD_BOW_POSITION")) OLD_BOW_POSITION = object.get("OLD_BOW_POSITION").getAsBoolean();

            if (object.has("OLD_BOW_SCALE")) OLD_BOW_SCALE = object.get("OLD_BOW_SCALE").getAsBoolean();
            if (object.has("OLD_ROD_SCALE")) OLD_ROD_SCALE = object.get("OLD_ROD_SCALE").getAsBoolean();

            if (object.has("HIT_GROUND_WHILE_EATING")) HIT_GROUND_WHILE_EATING = object.get("HIT_GROUND_WHILE_EATING").getAsBoolean();
            if (object.has("HIT_GROUND_WHILE_AIMING")) HIT_GROUND_WHILE_AIMING = object.get("HIT_GROUND_WHILE_AIMING").getAsBoolean();

            if (object.has("OLD_TAB_LIST")) OLD_TAB_LIST = object.get("OLD_TAB_LIST").getAsBoolean();
            if (object.has("OLD_DEBUG_MENU")) OLD_DEBUG_MENU = object.get("OLD_DEBUG_MENU").getAsBoolean();

        } catch (IOException e) {
            e.printStackTrace();
        }
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
