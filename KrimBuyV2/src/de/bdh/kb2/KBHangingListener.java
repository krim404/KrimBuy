package de.bdh.kb2;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.hanging.HangingBreakEvent.RemoveCause;

import de.bdh.kb.util.configManager;

public class KBHangingListener implements Listener
{

	Main p;
	KBHelper helper;
	
	public KBHangingListener(Main m)
	{
		this.p = m;
		this.helper = Main.helper;
		
		for (Player player: Bukkit.getServer().getOnlinePlayers()) 
        {
	        this.helper.loadPlayerAreas(player);
	        this.helper.updateLastOnline(player);
        }
	}
	
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPaintingBreakGarbageCollector(HangingBreakEvent event)
    {
    	this.garbageCollector(event);
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPaintingPlaceGarbageCollector(HangingPlaceEvent event)
    {
    	this.garbageCollector(event);
    }
    
    public void garbageCollector(Event event)
    {
    	this.p.playerListener.garbageCollector(event);
    }
	
    //Painting - Anti Guest
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onGuestPaintingPlace(HangingPlaceEvent event)
    {
    	if(!(event.getPlayer() instanceof Player))
			return;
    	Player player = event.getPlayer();
        if(!player.hasPermission("kab.build"))
        {
            event.setCancelled(true);
            this.helper.blockedEvent.put(event.hashCode(), true);
            if(configManager.lang.equalsIgnoreCase("de"))
            	player.sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("Du hast noch keine Berechtigung zum Bauen.").toString());
            else
        		player.sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("You dont have permissions to build").toString());

            return;
        }
    }
    
    //Painting2 - Anti Guest 
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onGuestPaintingBreak(HangingBreakEvent event)
    {
    	if(event instanceof HangingBreakByEntityEvent)
        {
            org.bukkit.entity.Entity remover = ((HangingBreakByEntityEvent)event).getRemover();
		    if(remover instanceof Player) 
		    {
		    	Player player = (Player)remover;
	    		if(!player.hasPermission("kab.build"))
	    		{
	    			event.setCancelled(true);
	    			this.helper.blockedEvent.put(event.hashCode(), true);
	    			if(configManager.lang.equalsIgnoreCase("de"))
	    				player.sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("Du hast noch keine Berechtigung zum Bauen.").toString());
	    			else
		        		player.sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("You dont have permissions to build").toString());

	    			return;
	    		}
		    }
        }
    }
    
    //DEFAULT Paint Build
    @EventHandler(priority = EventPriority.LOW)
    public void onPaintingPlace(HangingPlaceEvent event)
    {
    	if(!(event.getPlayer() instanceof Player))
			return;
    	
    	Player player = event.getPlayer();

        if(!this.helper.canBuildHere(player, event.getBlock().getWorld().getBlockAt(event.getEntity().getLocation())))
        {
        	this.helper.blockedEvent.put(event.hashCode(), true);
        	event.setCancelled(true);
        	return;
        }
    }
    
    //DEFAULT Paint Build
    @EventHandler(priority = EventPriority.LOW)
    public void onPaintingBreak(HangingBreakEvent event)
    {
    	if(event instanceof HangingBreakByEntityEvent)
        {
            org.bukkit.entity.Entity remover = ((HangingBreakByEntityEvent)event).getRemover();
		    if(remover instanceof Player) 
		    {
		    	Player player = (Player)remover;
	    		
	    		if(!this.helper.canBuildHere(player, event.getEntity().getWorld().getBlockAt(event.getEntity().getLocation())))
	            {
	    			this.helper.blockedEvent.put(event.hashCode(), true);
	            	event.setCancelled(true);
	            	return;
	            }
		    } else if(configManager.doProtectPicsTNT == 1 && event.getCause() == RemoveCause.EXPLOSION)
		    {
		    	this.helper.blockedEvent.put(event.hashCode(), true);
            	event.setCancelled(true);
            	return;
		    }
        }
    }
    
}
