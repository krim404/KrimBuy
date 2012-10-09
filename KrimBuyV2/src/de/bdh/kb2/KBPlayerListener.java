package de.bdh.kb2;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.painting.PaintingBreakByEntityEvent;
import org.bukkit.event.painting.PaintingBreakEvent;
import org.bukkit.event.painting.PaintingPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import de.bdh.kb.util.configManager;
import de.bdh.kb2.Main;

public class KBPlayerListener implements Listener
{
	Main p;
	KBHelper helper;
	public Map<Player,Long> lastclick = new HashMap<Player,Long>();
	
	public KBPlayerListener(Main m)
	{
		this.p = m;
		this.helper = Main.helper;
		
		for (Player player: Bukkit.getServer().getOnlinePlayers()) 
        {
	        this.helper.loadPlayerAreas(player);
	        this.helper.updateLastOnline(player);
        }
	}
	
	@EventHandler
    public void onQuit(PlayerQuitEvent event)
    {
		this.helper.lastBlock.remove(event.getPlayer());
		this.helper.pass.remove(event.getPlayer());
		this.helper.userarea.remove(event.getPlayer());
    }
	
	@EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        this.helper.loadPlayerAreas(player);
        this.helper.updateLastOnline(player);
    }
	
	//BlockBreak - Anti Guest 
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockDamage(BlockDamageEvent event)
    {
    	if(!(event.getPlayer() instanceof Player))
			return;
    	
        Player player = event.getPlayer();
        if(!player.hasPermission("kab.build"))
        {
            event.setCancelled(true);
            if(configManager.lang.equalsIgnoreCase("de"))
            	player.sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("Du hast noch keine Berechtigung zum Bauen.").toString());
            else
            	player.sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("You dont have permissions to build.").toString());

            return;
        }
    }
    
    //Inventory Click - Anti Guest
    @EventHandler(priority = EventPriority.HIGHEST)
	public void onDisc(InventoryClickEvent event)
    {
		if(event.getWhoClicked() instanceof Player)
		{
			Player player = (Player) event.getWhoClicked();
			if(!player.hasPermission("kab.build"))
	        {
	        	event.setCancelled(true);
	        	if(configManager.lang.equalsIgnoreCase("de"))
	        		player.sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("Du hast noch keine Berechtigung.").toString());
	        	else
	        		player.sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("You dont have permissions to do that").toString());

	        	return;
	        }
		}
    }
    
    //Painting - Anti Guest
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onGuestPaintingPlace(PaintingPlaceEvent event)
    {
    	if(!(event.getPlayer() instanceof Player))
			return;
    	
    	Player player = event.getPlayer();
        if(!player.hasPermission("kab.build"))
        {
            event.setCancelled(true);
            if(configManager.lang.equalsIgnoreCase("de"))
            	player.sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("Du hast noch keine Berechtigung zum Bauen.").toString());
            else
        		player.sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("You dont have permissions to build").toString());

            return;
        }
    }
    
    //Painting2 - Anti Guest 
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onGuestPaintingBreak(PaintingBreakEvent event)
    {
    	if(event instanceof PaintingBreakByEntityEvent)
        {
            org.bukkit.entity.Entity remover = ((PaintingBreakByEntityEvent)event).getRemover();
		    if(remover instanceof Player) 
		    {
		    	Player player = (Player)remover;
	    		if(!player.hasPermission("kab.build"))
	    		{
	    			event.setCancelled(true);
	    			if(configManager.lang.equalsIgnoreCase("de"))
	    				player.sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("Du hast noch keine Berechtigung zum Bauen.").toString());
	    			else
		        		player.sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("You dont have permissions to build").toString());

	    			return;
	    		}
		    }
        }
    }
    
    //BlockPlace - Anti Guest
    public void onBlockPlaceGuest(BlockPlaceEvent blockplaceevent)
    {
		if(!(blockplaceevent.getPlayer() instanceof Player))
			return;
		
        Player player = blockplaceevent.getPlayer();
        if(!player.hasPermission("kab.build"))
        {
        	blockplaceevent.setCancelled(true);
        	if(configManager.lang.equalsIgnoreCase("de"))
        		player.sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("Du hast noch keine Berechtigung zum Bauen.").toString());
        	else
        		player.sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("You dont have permissions to do build").toString());

        	return;
        }
    }
    
    //BlockBreak - Anti Guest
    @EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreakGuest(BlockBreakEvent blockbreakevent)
	{
		if(!(blockbreakevent.getPlayer() instanceof Player))
			return;
		
        Player player = blockbreakevent.getPlayer();
        if(!player.hasPermission("kab.build"))
        {
        	blockbreakevent.setCancelled(true);
        	if(configManager.lang.equalsIgnoreCase("de"))
        		player.sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("Du hast noch keine Berechtigung zum Bauen.").toString());
        	else
        		player.sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("You dont have permissions to build").toString());

        	return;
        }
	}
    
    //Interact - Anti Guest
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerInteractEventGuest(PlayerInteractEvent event)
    {
		if(!(event.getPlayer() instanceof Player))
			return;
		
    	Player player = event.getPlayer();
        if(!player.hasPermission("kab.interact"))
        {
            event.setCancelled(true);
            if(configManager.lang.equalsIgnoreCase("de"))
            	player.sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("Du hast noch keine Berechtigung zum Interagieren.").toString());
            else
        		player.sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("You dont have permissions to interact with the world").toString());

            return;
        }
    }
    
    //Item Trade
    @EventHandler
	public void onClickPlayer(PlayerInteractEntityEvent event)
    {
        if(!(event.getRightClicked() instanceof Player))
        	return;
       
        Player giver = event.getPlayer();
        Player reciever = (Player)event.getRightClicked();
        
        if(event.getPlayer().isSneaking() && event.isCancelled() == false)
        {
        	Long lc = lastclick.get(giver);
        	if(lc != null && Math.abs(System.currentTimeMillis() - lc) < 500)
				event.setCancelled(true);
			else
			{
				int itemid = giver.getItemInHand().getTypeId();
				if(reciever.getInventory().firstEmpty() == -1)
				{
					if(configManager.lang.equalsIgnoreCase("de"))
						giver.sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("Das Inventar des anderen Spielers ist voll.").toString());
					else
		        		giver.sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("The inventory of the other player is full").toString());

				}
				else if(giver.getInventory().contains(itemid))
	            {
	                reciever.getInventory().addItem(new ItemStack[] {
	                	event.getPlayer().getItemInHand()
	                });
	                giver.getInventory().removeItem(new ItemStack[] {
		                	event.getPlayer().getItemInHand()
		            });
	            }
	        	event.setCancelled(true);
	        	lastclick.put(giver, System.currentTimeMillis());
	        	return;
			}
        }
    }
    
    //DEFAULT Paint Build
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPaintingPlace(PaintingPlaceEvent event)
    {
    	if(!(event.getPlayer() instanceof Player))
			return;
    	
    	Player player = event.getPlayer();

        if(!this.helper.canBuildHere(player, event.getBlock().getWorld().getBlockAt(event.getPainting().getLocation())))
        {
        	event.setCancelled(true);
        	return;
        }
    }
    
    //DEFAULT Paint Build
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPaintingBreak(PaintingBreakEvent event)
    {
    	if(event instanceof PaintingBreakByEntityEvent)
        {
            org.bukkit.entity.Entity remover = ((PaintingBreakByEntityEvent)event).getRemover();
		    if(remover instanceof Player) 
		    {
		    	Player player = (Player)remover;
	    		
	    		if(!this.helper.canBuildHere(player, event.getPainting().getWorld().getBlockAt(event.getPainting().getLocation())))
	            {
	            	event.setCancelled(true);
	            	return;
	            }
		    }
        }
    }
    
    //DEFAULT Block Place
    @EventHandler(priority = EventPriority.LOWEST)
	public void onBlockPlace(BlockPlaceEvent blockplaceevent)
    {
		if(!(blockplaceevent.getPlayer() instanceof Player))
			return;
		
        Player player = blockplaceevent.getPlayer();
        if(!this.helper.canBuildHere(player, blockplaceevent.getBlock()))
        {
    		blockplaceevent.setCancelled(true);
        	return;
        }
       
		int i = blockplaceevent.getBlock().getTypeId();
		Player p = blockplaceevent.getPlayer();
		
		if(p.hasPermission("kb.create") && (i == 7))
		{
			this.helper.lastBlock.put(p, blockplaceevent.getBlock());
		}
    }
    
    //DEFAULT Block Break
    @EventHandler(priority = EventPriority.LOWEST)
	public void onBlockBreak(BlockBreakEvent blockbreakevent)
	{
		if(!(blockbreakevent.getPlayer() instanceof Player))
			return;
		
        Player player = blockbreakevent.getPlayer();
      
        
        if(!this.helper.canBuildHere(player, blockbreakevent.getBlock()))
        {
    		blockbreakevent.setCancelled(true);
        	return;
        }
        
        if(blockbreakevent.getBlock().getTypeId() == Material.SPONGE.getId() && blockbreakevent.getBlock().getRelative(BlockFace.DOWN).getTypeId() == 7)
        {
    		blockbreakevent.setCancelled(true);
        	return;
        }
       
        
		Block b = blockbreakevent.getBlock();
		int i = b.getTypeId();
		Player p = blockbreakevent.getPlayer();
		if(p.hasPermission("kb.create") && (i == 7))
		{
			Long lc = lastclick.get(blockbreakevent.getPlayer());
			lastclick.put(blockbreakevent.getPlayer(), System.currentTimeMillis());
			
        	if(lc != null && Math.abs(System.currentTimeMillis() - lc) > 500)
        		blockbreakevent.setCancelled(true);
        	else
        	{	
        		int id = this.helper.getIDbyBlock(b);
        		if(id != 0)
        		{
        			this.helper.killGS(id);
        			if(configManager.lang.equalsIgnoreCase("de"))
        				player.sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("Das Grundstück wurde zerstört").toString());
        			else
    	        		player.sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("The lot has been destroyed").toString());

        		}
        	}
		}
	}
    
    //DEFAULT Interact
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerInteractEvent(PlayerInteractEvent event)
    {
		if(!(event.getPlayer() instanceof Player))
			return;
		
    	Player player = event.getPlayer();
    	
        if((event.getClickedBlock() instanceof Block))
		{
			Block b = event.getClickedBlock();
			int gt = b.getTypeId();

			
			if((gt == Material.DISPENSER.getId() || gt == Material.FURNACE.getId() || gt == Material.CHEST.getId()) && player.hasPermission("kb.nochest") && !player.hasPermission("kb.chest"))
			{
				if(!this.helper.canBuildHereData(player, b))
				{
					if(configManager.lang.equalsIgnoreCase("de"))
						player.sendMessage("Dein Rang verbietet das Öffnen von Truhen / Dispensern / Öfen");
					else
		        		player.sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("You're not allowed to open chests, dispensers and furnaces").toString());

					event.setCancelled(true);
				}
			}
			
			/*
			//Ehemals für BrauTec
			//Verhindere manipulation an Pipes - BrauTec Mod
			else if(event.getAction() == Action.RIGHT_CLICK_BLOCK && (gt == 166 || gt == 187) && configManager.BrauTec.equalsIgnoreCase("1"))
	        {
	        	if(!this.helper.canBuildHere(player, b))
				{
					player.kickPlayer("Du darfst keine fremden Objekte editieren");
					event.setCancelled(true);
				}
	        }
	        */
			
	        //Schilder dürfen immer geklickt werden - sowie minecarts immer auf rails gesetzt werden dürfen
	        else if(event.getAction() == Action.RIGHT_CLICK_BLOCK && ((gt == 63 || gt == 68 || gt == 323) || (player.getItemInHand().getTypeId() == 328 && (gt == 27 || gt == 28 || gt == 66))))
	        {
	        	event.setCancelled(false);
	        	//alles OK
	        } 
	        	
	        //Steinknöpfe gehen immer genauso wie 225 ( BrauTec ) und Bedrock
	        else if((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK) && (event.getClickedBlock().getTypeId() != 7 && event.getClickedBlock().getTypeId() != 225 && event.getClickedBlock().getTypeId() != Material.STONE_BUTTON.getId()))
			{
				if(!(this.helper.canBuildHere(player, b.getRelative(BlockFace.UP))) && !this.helper.canBuildHere(player, b) && !event.getPlayer().hasPermission("kb.interact"))
				{
	        		event.setCancelled(true);
	        		
	        		if(configManager.interactMessage == 1)
	        		{
	        			if(configManager.lang.equalsIgnoreCase("de"))
	        				player.sendMessage("Du kannst nicht auf X:"+b.getX() +"+Y:"+ b.getY()+" Z:"+b.getZ()+" interagieren");
	        			else
	    	        		player.sendMessage("You can't interact on X:"+b.getX() +"+Y:"+ b.getY()+" Z:"+b.getZ());

	        		}
	        	} else if(!(this.helper.canBuildHere(player, b.getRelative(BlockFace.UP))) && !this.helper.canBuildHere(player, b) && event.getPlayer().hasPermission("kb.interact") && (gt == Material.DISPENSER.getId() || gt == Material.FURNACE.getId() || gt == Material.CHEST.getId()))
				{
					//Truhen / Dispenser / Ofen sind trotz allem verboten (gilt nur mit Vanilla Blocks)
					event.setCancelled(true);
					if(configManager.lang.equalsIgnoreCase("de"))
						player.sendMessage("Du darfst keine fremden Truhen öffnen");
					else
		        		player.sendMessage("You're not allowed to open chests from other players");

				}
			}
	        
	        //Interaktionsblock
	        else if(b.getTypeId() == 7 && !event.getPlayer().isSneaking())
			{
				if(event.getPlayer().hasPermission("kb.create"))
					this.helper.lastBlock.put(event.getPlayer(), b);
				
				event.setCancelled(true);
				
				int id = this.helper.getIDbyBlock(b);
        		if(id != 0)
        		{
        			KBArea a = this.helper.getArea(id);
        			if(a != null)
        			{
        				this.helper.lastBlock.put(event.getPlayer(), b);
        				
        				//Region String
        				StringBuilder s = (new StringBuilder());
        				if(a.ruleset.length() > 0)
        				{
        					if(configManager.lang.equalsIgnoreCase("de"))
        						s.append(ChatColor.YELLOW).append("Bauhöhe: ").append(a.height).append(" Blöcke, Keller: ").append(a.deep).append(" Blöcke");
        					else
        						s.append(ChatColor.YELLOW).append("Build height: ").append(a.height).append(" Blocks, basement: ").append(a.deep).append(" Blocks");
	
        					if(a.miet > 0)
							{
								int miete;
								if(a.miet == 1) miete = a.price; else miete = a.miet;
								if(configManager.lang.equalsIgnoreCase("de"))
									s.append(", Miete pro Tag: ").append(miete);
								else
									s.append(", rental fee per day: ").append(miete);
							}
        				}
        				
        				//Typbezeichnung
        				String typ = "";
        				if(a.gruppe.length() > 0)
        					typ = a.gruppe;
        				else if(a.ruleset.length() == 0)
        					typ = "OnlyBlock";
        				else
        					typ = a.ruleset;
        				
        				//GS noch nicht verkauft
        				if(a.sold == 0)
        				{
        					if(configManager.lang.equalsIgnoreCase("de"))
        						event.getPlayer().sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("Dieser Bauplatz vom Typ ").append(typ).append(" steht zum Verkauf").toString());
        					else
        						event.getPlayer().sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("This lot of the type  ").append(typ).append(" is for sale").toString());
        					if(a.price > 0)
        					{
        						if(configManager.lang.equalsIgnoreCase("de"))
        							event.getPlayer().sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("Der Preis beträgt ").append(a.price).append("BM").toString());
        						else
        							event.getPlayer().sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("It cost ").append(a.price).append("BM").toString());

        					}
        					else
        					{
        						if(configManager.lang.equalsIgnoreCase("de"))
        							event.getPlayer().sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("Das Grundstück ist kostenlos").toString());
        						else
        							event.getPlayer().sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("It's free").toString());

        					}
							if(s.length() > 1)
								event.getPlayer().sendMessage(s.toString());
							
							if(configManager.lang.equalsIgnoreCase("de"))
								event.getPlayer().sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("Wenn du dies kaufen willst, gib /buyGS ein").toString());
							else
								event.getPlayer().sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("If you want to buy this - enter /buyGS").toString());

        				} else if(a.owner.equalsIgnoreCase(event.getPlayer().getName()))
        				{
							StringBuilder sndm = (new StringBuilder()).append(ChatColor.YELLOW).append("Level: '").append(a.level);
							if(a.cansell != 0)
							{
								if(configManager.lang.equalsIgnoreCase("de"))
									sndm.append(". Du kannst es mit /sellGS verkaufen");
								else
									sndm.append(". You can sell it by entering /sellGS");

							}
							if(configManager.lang.equalsIgnoreCase("de"))
								event.getPlayer().sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("Dieses Grundstück gehört dir. Es ist vom Typ '").append(typ).toString());
							else
								event.getPlayer().sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("This lot is yours. It's type is '").append(typ).toString());
	
							event.getPlayer().sendMessage(sndm.toString());
							int cu = this.helper.canUpgradeArea(event.getPlayer(), b);
							if(cu != 0)
							{
								if(configManager.lang.equalsIgnoreCase("de"))
									event.getPlayer().sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("Dieses Grundstück kann erweitert werden für ").append(cu).append("BM. Gib dazu /upgradeGS ein").toString());
								else
									event.getPlayer().sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("This lot can be upgraded for ").append(cu).append("BM. Just enter /upgradeGS").toString());

							}        				
						} else
        				{
							if(configManager.lang.equalsIgnoreCase("de"))
								event.getPlayer().sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("Das Grundstück im Wert von ").append(a.paid).append("BM des Typs '").append(typ).append("' - Level ").append(a.level).append(" gehoert ").append(a.owner).toString());
							else
								event.getPlayer().sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("This lot with the value ").append(a.paid).append("BM and the type '").append(typ).append("' - Level ").append(a.level).append(" owns ").append(a.owner).toString());

							long tmp = 1000l * a.lastonline;
							String date = DateFormat.getDateInstance().format(tmp);
							tmp = 1000l * a.kaufzeit;
							String date2 = DateFormat.getDateInstance().format(tmp);
							if(configManager.lang.equalsIgnoreCase("de"))
								event.getPlayer().sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("Letztes mal Online: ").append(date).append(", Kaufzeitpunkt: ").append(date2).toString());
							else
								event.getPlayer().sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("Last online: ").append(date).append(", Bought: ").append(date2).toString());

        				}
        			}
        		}
			}
		}
    }
	   
}
