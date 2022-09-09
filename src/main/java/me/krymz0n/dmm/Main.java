package me.krymz0n.dmm;

import me.krymz0n.dmm.discord.MessageListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.util.Objects;

public final class Main extends JavaPlugin implements Listener {
    public JDA jda;

    @Override
    public void onEnable()  {
        try {
            createJda();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }
        saveDefaultConfig();
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(this, this);

    }

    public void createJda() throws LoginException, InterruptedException {
        jda = JDABuilder.createDefault(this.getConfig().getString("BotToken")).addEventListeners(new MessageListener(this)).build();

        jda.awaitReady();
    }
}