package com.mrkl21full.api;

import java.util.HashMap;
import java.util.UUID;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class BossBarAPI extends JavaPlugin implements Listener {
	
	private static BossBarAPI instance;
	
	private static HashMap<UUID, BossBar> playerBar = new HashMap<>();
	private static HashMap<UUID, Integer> playerTimers = new HashMap<>();
	
	public static enum Color { PINK, BLUE, RED, GREEN, YELLOW, PURPLE, WHITE }
	public static enum Style { SOLID, SEGMENTED_6, SEGMENTED_10, SEGMENTED_12, SEGMENTED_20; }
	public static enum Flag { DARKEN_SKY,  PLAY_MUSIC,  CREATE_FOG; }
	
	public void onEnable() { 
		instance = this; 
	}
	public void onDisable() { 
		instance = this;
		
		for (Player p : Bukkit.getOnlinePlayers())
			playerQuit(p);
		
		playerBar.clear();
		
		for (int timerID : playerTimers.values())
			Bukkit.getScheduler().cancelTask(timerID);
		
		playerTimers.clear();
	}
	
	public static void createBar(String message) {
		Validate.isTrue(message.length() > 0, "Message must contain at least 1 letter");
		
		for (Player p : Bukkit.getOnlinePlayers())
			createBar(p, message);
	}
	
	public static void createBar(Player player, String message) {
		Validate.isTrue(message.length() > 0, "Message must contain at least 1 letter");
		
		if(hasBar(player)) 
			removeBar(player);
		else
			clearTimer(player);
		
		BossBar bossBar = Bukkit.createBossBar(shortMessage(message), BarColor.PURPLE, BarStyle.SOLID);
		bossBar.addPlayer(player);
		
		playerBar.put(player.getUniqueId(), bossBar);
	}
	
	public static void createBar(Player player, String message, float percent) {
		Validate.isTrue(0F <= percent && percent <= 100F, "Percent must be between 0F and 100F, but was: ", percent);
		
		if(hasBar(player)) 
			removeBar(player);
		else
			clearTimer(player);
		
		BossBar bossBar = Bukkit.createBossBar(shortMessage(message), BarColor.PURPLE, BarStyle.SOLID);
		bossBar.addPlayer(player);
		bossBar.setProgress(percent / 100f);
		
		playerBar.put(player.getUniqueId(), bossBar);
	}
	
	public static void createBar(Player player, String message, int seconds) {
		Validate.isTrue(seconds > 0, "Seconds must be above 1 but was: ", seconds);
		
		if(hasBar(player)) 
			removeBar(player);
		else
			clearTimer(player);
		
		BossBar bossBar = Bukkit.createBossBar(shortMessage(message), BarColor.PURPLE, BarStyle.SOLID);
		bossBar.addPlayer(player);
		
		playerBar.put(player.getUniqueId(), bossBar);
		
		final float percentMinus = 100 / seconds;
		
		int timer = Bukkit.getScheduler().runTaskTimer(instance, new Runnable() {			
			@Override
			public void run() {
				float percent = (float)getProgress(player) - percentMinus;
				
				if (percent <= 0) {
					removeBar(player);
				} else {
					setProgress(player, percent);
				}
			}
		}, 20L, 20L).getTaskId();
		
		playerTimers.put(player.getUniqueId(), timer);
	}
	
	public static boolean hasBar(Player p) {
		return playerBar.get(p.getUniqueId()) != null;
	}
	
	public static void setMessage(Player p, String message) {
		if (!hasBar(p))
			return;
		
		if(message.length() <= 0) {
			removeBar(p);
		} else {
			BossBar bossBar = playerBar.get(p.getUniqueId());
			bossBar.setTitle(message);
		}
	}
	
	public static String getMessage(Player p) {
		if (!hasBar(p))
			return null;
		
		BossBar bossBar = playerBar.get(p.getUniqueId());
		return bossBar.getTitle();
	}
	
	public static void setProgress(Player p, float progress) {
		if (!hasBar(p))
			return;
		
		if(progress <= 0) {
			removeBar(p);
		} else {
			BossBar bossBar = playerBar.get(p.getUniqueId());
			bossBar.setProgress(progress / 100f);
		}
	}
	
	public static double getProgress(Player p) {
		if (!hasBar(p))
			return 0;
		
		BossBar bossBar = playerBar.get(p.getUniqueId());
		return bossBar.getProgress() * 100f;
	}
	
	public static void setColor(Player p, Color color) {
		if (!hasBar(p))
			return;
		
		BossBar bossBar = playerBar.get(p.getUniqueId());
		bossBar.setColor(BarColor.valueOf(color.toString()));
	}
	
	public static Color getColor(Player p) {
		if (!hasBar(p))
			return null;
		
		BossBar bossBar = playerBar.get(p.getUniqueId());
		return Color.valueOf(bossBar.getColor().toString());
	}
	
	public static void setStyle(Player p, Style style) {
		if (!hasBar(p))
			return;
		
		BossBar bossBar = playerBar.get(p.getUniqueId());
		bossBar.setStyle(BarStyle.valueOf(style.toString()));
	}
	
	public static Style getStyle(Player p) {
		if (!hasBar(p))
			return null;
		
		BossBar bossBar = playerBar.get(p.getUniqueId());
		return Style.valueOf(bossBar.getStyle().toString());
	}
	
	public static void addFlag(Player p, Flag flag) {
		if (!hasBar(p))
			return;
		
		BossBar bossBar = playerBar.get(p.getUniqueId());
		bossBar.addFlag(BarFlag.valueOf(flag.toString()));
	}
	
	public static void removeFlag(Player p, Flag flag) {
		if (!hasBar(p))
			return;
		
		BossBar bossBar = playerBar.get(p.getUniqueId());
		bossBar.removeFlag(BarFlag.valueOf(flag.toString()));
	}
	
	public static boolean hasFlag(Player p, Flag flag) {
		if (!hasBar(p))
			return false;
		
		BossBar bossBar = playerBar.get(p.getUniqueId());
		return bossBar.hasFlag(BarFlag.valueOf(flag.toString()));
	}
	
	private static String shortMessage(String message) {
		if (message.length() > 64)
			message = message.substring(0, 63);
		
		return message;
	}
	
	public static void removeBar(Player p) {
		if (!hasBar(p))
			return;
		
		BossBar bossBar = playerBar.get(p.getUniqueId());
		bossBar.removeAll();
		
		playerBar.remove(p.getUniqueId());
		
		clearTimer(p);
	}
	
	private static void clearTimer(Player p) {
		if(playerTimers.containsKey(p.getUniqueId())) Bukkit.getScheduler().cancelTask((int) playerTimers.get(p.getUniqueId()));
	}
	
	private void playerQuit(Player p) {
		removeBar(p);
	}
	
	public void onPlayerQuit(PlayerQuitEvent e) {
		playerQuit(e.getPlayer());
	}
	
	public void onPlayerKick(PlayerKickEvent e) {
		playerQuit(e.getPlayer());
	}
}
