package com.minebone.tnttag.util;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.minebone.tnttag.core.TNTTag;
import com.minebone.tnttag.managers.CountdownManager;
import com.minebone.tnttag.managers.InventoryManager;

public class Arena {

	public static ArrayList<Arena> arenaObjects = new ArrayList<Arena>();
	private TNTTag plugin;
	private CountdownManager countdownManager;
	private Location lobbyLocation;
	private Location arenaLocation;
	private Location spectatorLocation;
	private String name;
	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<Player> TNTPlayers = new ArrayList<Player>();
	private ArrayList<Player> AlivePlayers = new ArrayList<Player>();
	private int maxPlayers;
	private int minPlayers;
	private int taskID;
	private int seconds;
	private boolean inGame = false;
	private boolean runningCountdown = false;

	public Arena(TNTTag plugin, String arenaName, Location joinLocation, Location startLocation, Location endLocation, int maxPlayers, int minPlayers) {
		this.plugin = plugin;
		this.name = arenaName;
		this.lobbyLocation = joinLocation;
		this.arenaLocation = startLocation;
		this.spectatorLocation = endLocation;
		this.maxPlayers = maxPlayers;
		this.minPlayers = minPlayers;
		this.countdownManager = new CountdownManager(plugin, this);
		
		arenaObjects.add(this);
	}

	public Location getLobbyLocation() {
		return this.lobbyLocation;
	}

	public void setLobbyLocation(Location joinLocation) {
		this.lobbyLocation = joinLocation;
	}

	public Location getArenaLocation() {
		return this.arenaLocation;
	}

	public void setArenaLocation(Location startLocation) {
		this.arenaLocation = startLocation;
	}

	public Location getSpectatorLocation() {
		return this.spectatorLocation;
	}

	public void setSpectatorLocation(Location endLocation) {
		this.spectatorLocation = endLocation;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMaxPlayers() {
		return this.maxPlayers;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public int getMinPlayers() {
		return this.minPlayers;
	}

	public void setMinPlayers(int minPlayers) {
		this.minPlayers = minPlayers;
	}

	public ArrayList<Player> getPlayers() {
		return this.players;
	}

	public ArrayList<Player> getTNTPlayers() {
		return this.TNTPlayers;
	}

	public ArrayList<Player> getAlivePlayers() {
		return this.AlivePlayers;
	}

	public boolean isFull() {
		if (this.players.size() >= this.maxPlayers) {
			return true;
		}
		return false;
	}

	public boolean isInGame() {
		return this.inGame;
	}

	public boolean runningCountdown() {
		return this.runningCountdown;
	}

	public void setRunningCountdown(Boolean b) {
		this.runningCountdown = b.booleanValue();
	}

	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}

	public void sendMessage(String message) {
		for (Player player : this.players) {
			plugin.getMessageManager().sendMessage(player, message);
		}
	}

	public void endArena() {
		setInGame(false);
		for (Player player : this.players) {
			InventoryManager.restoreInventory(player);

			getPlayers().remove(player.getName());
			if (getTNTPlayers().contains(player.getName())) {
				getTNTPlayers().remove(player.getName());
			}
			if (getAlivePlayers().contains(player.getName())) {
				getAlivePlayers().remove(player.getName());
			}
			removeBoard(player);
			if (this.players.size() == 0) {
				this.players.clear();
				return;
			}
		}
	}

	public int getTaskID() {
		return this.taskID;
	}

	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}

	public int getSeconds() {
		return this.seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
	
	public CountdownManager getCountdownManager() {
		return countdownManager;
	}

	ScoreboardManager manager = Bukkit.getScoreboardManager();
	Scoreboard board = this.manager.getNewScoreboard();
	Objective objective = this.board.registerNewObjective("lives", "dummy");

	public void setBoard(Player player, int time) {
		this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		this.objective.setDisplayName("TNT Tag");

		Score money = this.objective.getScore("Players:");
		money.setScore(this.players.size());

		Score Tags = this.objective.getScore("Time:");
		Tags.setScore(time);
		player.setScoreboard(this.board);
	}

	public void removeBoard(Player player) {
		player.setScoreboard(this.manager.getNewScoreboard());
	}

	public void updateBoard(Player player, int time) {
		setBoard(player, time);
	}
}
