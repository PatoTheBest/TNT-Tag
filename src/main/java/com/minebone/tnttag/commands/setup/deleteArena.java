package com.minebone.tnttag.commands.setup;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.minebone.tnttag.core.TNTTag;
import com.minebone.tnttag.files.GameData;
import com.minebone.tnttag.files.Messages;
import com.minebone.tnttag.util.AbstractTagSetupCommands;
import com.minebone.tnttag.util.Arena;
import com.minebone.tnttag.util.Message;
import com.minebone.tnttag.util.Permissions;

public class deleteArena extends AbstractTagSetupCommands {

	public deleteArena(TNTTag plugin) {
		super(plugin, "deletearena", Messages.getMessage(Message.deleteArena), "<ArenaName|confirm>", new Permissions().deleteArena, true);
	}

	private static HashMap<String, String> strings = new HashMap<String, String>();

	public void onCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		if (args.length == 0) {
			getMessageManager().sendInsuficientArgs(sender, "deletearena", "<ArenaName>");
			StringBuilder arenaNames = new StringBuilder(Messages.getMessage(Message.availableArenas));

			int x = 0;
			for (Arena arena : Arena.arenaObjects) {
				x++;
				arenaNames.append(arena.getName() + (x != Arena.arenaObjects.size() ? ", " : "."));
			}

			getMessageManager().sendMessage(sender, arenaNames.toString());
			return;
		}

		if (args[0].equalsIgnoreCase("confirm")) {
			if (strings.get(player.getName()) != null) {
				FileConfiguration fc = GameData.getGameData();
				fc.set("arenas." + (String) strings.get(player.getName()), null);
				Arena.arenaObjects.remove(getArenaManager().getArena((String) strings.get(player.getName())));
				getMessageManager().sendMessage(sender, Messages.getMessage(Message.arenaDeleted).replace("{arena}", (CharSequence) strings.get(player.getName())));
				strings.remove(player.getName());
				return;
			}
			getMessageManager().sendErrorMessage(sender, Messages.getMessage(Message.unspecifiedArena));
			return;
		}

		String arenaName = args[0];
		if (getArenaManager().getArena(arenaName) != null) {
			getMessageManager().sendMessage(sender, Messages.getMessage(Message.confirmationMessage));
			strings.put(player.getName(), arenaName);
			addPlayer(player.getName());
		} else {
			getMessageManager().sendErrorMessage(sender, Messages.getMessage(Message.invalidArena));
		}
	}

	private void addPlayer(final String name) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(getPlugin(), new Runnable() {
			public void run() {
				if (deleteArena.strings.get(name) != null) {
					deleteArena.strings.remove(name);
				}
			}
		}, 100L);
	}
}
