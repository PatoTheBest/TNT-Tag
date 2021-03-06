package com.minebone.tnttag.commands.user;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minebone.tnttag.core.TNTTag;
import com.minebone.tnttag.files.Messages;
import com.minebone.tnttag.util.AbstractTagCommands;
import com.minebone.tnttag.util.Message;
import com.minebone.tnttag.util.Permissions;

public class transfer extends AbstractTagCommands {

	int amount;

	public transfer(TNTTag plugin) {
		super(plugin, "transfer", Messages.getMessage(Message.transfer), "coins <player> <amount>", new Permissions().transferCoins, true, "pay");
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (args.length != 2) {
			getMessageManager().sendInsuficientArgs(sender, "transfer", "coins <player> <amount>");
			return;
		}
		
		String playerName = args[1];
		if (getPlayerData().getString(playerName) == null) {
			getMessageManager().sendErrorMessage(sender, playerName + " does not exist!");
			return;
		}
		
		Player player = (Player) sender;
		try {
			this.amount = Integer.parseInt(args[2]);
		} catch (NumberFormatException e) {
			getMessageManager().sendErrorMessage(sender, Messages.getMessage(Message.invalidNumber));
			return;
		}
		
		if (getPlayerData().getInt(player.getName() + ".money") >= this.amount) {
			int giverMoney = getPlayerData().getInt(player.getName() + ".money");
			int recieverMoney = getPlayerData().getInt(playerName + ".money");

			getPlayerData().set(playerName, Integer.valueOf(recieverMoney + this.amount));
			getPlayerData().set(playerName, Integer.valueOf(giverMoney - this.amount));
			sendMessage(sender, ChatColor.GREEN + "Successfully transfered " + this.amount + " to " + playerName + ".");
		}
	}
}
