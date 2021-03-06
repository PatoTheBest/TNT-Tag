package com.minebone.tnttag.managers;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minebone.tnttag.files.Messages;
import com.minebone.tnttag.util.Arena;
import com.minebone.tnttag.util.Message;

public class MessageManager {
	
	private String prefix;

	public MessageManager() {
		prefix = ChatColor.GRAY + "[" + ChatColor.RED + "TNT Tag" + ChatColor.GRAY + "]: ";
	}
	
	public void sendErrorMessage(CommandSender sender, String errormsg) {
		sender.sendMessage(ChatColor.DARK_RED + "Error:" + ChatColor.RED + " " + errormsg);
	}

	public void sendInsuficientArgs(CommandSender sender, String command, String args) {
		sendErrorMessage(sender, "Insufficient args.");
		sender.sendMessage(ChatColor.RED + "Usage: /tag " + command + " " + args);
	}

	public void sendInvalidArgs(CommandSender sender, String command, String args) {
		sendErrorMessage(sender, "Invalid args.");
		sender.sendMessage(ChatColor.RED + "Usage: /tag " + command + " " + args);
	}

	public void sendMessage(CommandSender sender, String s) {
		sender.sendMessage(this.prefix + ChatColor.GRAY + s);
	}

	public void sendInGamePlayersMessage(String s, Arena arena) {
		arena.sendMessage(s);
	}

	public void sendWinMessage(Player player2, String s, Arena arena) {
		for (Player player : arena.getPlayers()) {
			player.sendMessage(Messages.getMessage(Message.lineBreak));
			player.sendMessage(Messages.getMessage(Message.winMessage).replace("{player}", s));
			player.sendMessage(Messages.getMessage(Message.lineBreak));
		}
		player2.sendMessage(Messages.getMessage(Message.lineBreak));
		player2.sendMessage(Messages.getMessage(Message.winMessage).replace("{player}", s));
		player2.sendMessage(Messages.getMessage(Message.lineBreak));
	}

	public void isConsole(CommandSender sender) {
		sender.sendMessage(this.prefix + ChatColor.RED + "This command can only be used by an in-game player!");
	}

	public void noperm(CommandSender sender) {
		sender.sendMessage(this.prefix + ChatColor.GRAY + "You do not have permission to perform this command!");
	}

	public void sendNoPrefixMessage(CommandSender sender, String s) {
		sender.sendMessage(ChatColor.GRAY + s);
	}
}
