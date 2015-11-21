package com.minebone.tnttag.commands.setup;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minebone.tnttag.files.Messages;
import com.minebone.tnttag.util.AbstractTagSetupCommands;
import com.minebone.tnttag.util.CreateArenaData;
import com.minebone.tnttag.util.Message;
import com.minebone.tnttag.util.Permissions;

public class setSpectatorsPoint extends AbstractTagSetupCommands {
	public setSpectatorsPoint() {
		super("setspec", Messages.getMessage(Message.setSpectatorsPoint), null, new Permissions().setSpec, true);
	}

	public void onCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		CreateArenaData.setSpectatorsLocation(player);
	}
}
