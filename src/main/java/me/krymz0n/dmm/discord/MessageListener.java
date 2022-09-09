package me.krymz0n.dmm.discord;

import me.krymz0n.dmm.Main;
import net.dv8tion.jda.api.entities.Channel;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class MessageListener extends ListenerAdapter {
    private final Main plugin;

    private ArrayList<User> users = new ArrayList<>();

    public MessageListener(Main plugin) {
        this.plugin = plugin;
    }


    @Override
    public void onMessageReceived(MessageReceivedEvent evt) {
        if (evt.getChannel().getId().equals(plugin.getConfig().getString("Channel1Id"))) {
            Message msg = evt.getMessage();
            String[] args = parseStringCom(msg.getContentRaw());
            Channel chan1 = evt.getChannel();
            String chan = plugin.getConfig().getString("Channel2Id");

            TextChannel chan2 = plugin.jda.getTextChannelById(chan);

            if (msg.getContentRaw().startsWith("-reset")) {
                if (args.length >= 1) {
                    User author = evt.getAuthor();

                    int occurrences = count(users, author);

                    if (occurrences <= 4) {
///
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                                Bukkit.dispatchCommand(console, "authme unregister " + args[1]);
                                evt.getChannel().sendMessage("<@" + author.getId() + "> " + "Successfully reset the password of " + args[1]).queue();
                                chan2.sendMessage("<@" + author.getId() + "> " + "reset the password of " + args[1]).queue();
                                users.add(author);
                                cancel();
                            }
                        }.runTask(plugin);
                    } else {
                        evt.getChannel().sendMessage("You can no longer perform this action!").queue();
                    }



                }
            }
        }
    }

    private int count(ArrayList<User> set, User u ) {
        int count = 0;
        for (User author : set) {
            if (author.equals(u)) {
                count++;
            }
        }
        return count;
    }

    private String[] parseStringCom(String s) { // Parsing through a string using a space as the delimeter. then adding to array
        String[] newS =  s.split(" ");


        return newS;
    }
}
