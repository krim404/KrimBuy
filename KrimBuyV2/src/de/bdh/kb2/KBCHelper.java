package de.bdh.kb2;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;

import de.bdh.kb.util.configManager;

public class KBCHelper 
{
	public Main m;
	public HashMap<World,KBCArea[][]> maps = new HashMap<World,KBCArea[][]>();
	public KBCHelper(Main m)
	{
		this.m = m;
		String w = configManager.cworlds;
		if(w != null && w.length() > 0)
		{
			String[] tmpBoh = w.split(",");
			for (String bl: tmpBoh) {
				if(Bukkit.getWorld(bl) != null)
				{
					System.out.println("[KB] Chunkbuy Enabled for World: "+bl);
					this.maps.put(Bukkit.getWorld(bl),null);
				}
			}
		}
	}
	
	
	public void obtainChunk(Chunk c, Player p)
	{
		//TODO: if player has permission + player has permission for ruleset
	}
	
	
	public void loadAreas()
	{
		//TODO: load areas from db
	}

	
	public KBCArea getArea(Chunk c)
	{
		if(this.maps.get(c.getWorld()) == null)
			return null;
		
		return this.maps.get(c.getWorld())[c.getX()][c.getZ()];
	}
	
	public KBCArea isCloseTo(Chunk c)
	{
		
		if(this.maps.get(c.getWorld()) == null)
			return null;
		
		KBCArea[][] map = this.maps.get(c.getWorld());
		int x = c.getX();
		int z = c.getZ();
		if(map[x][z] != null)
			return map[x][z];
		else if(map[x-1][z] != null)
			return map[x-1][z];
		else if(map[x+1][z] != null)
			return map[x+1][z];
		else if(map[x][z-1] != null)
			return map[x][z-1];
		else if(map[x][z+1] != null)
			return map[x][z+1];
		else if(map[x+1][z+1] != null)
			return map[x+1][z+1];
		else if(map[x-1][z+1] != null)
			return map[x-1][z+1];
		else if(map[x+1][z-1] != null)
			return map[x+1][z-1];
		else if(map[x-1][z-1] != null)
			return map[x-1][z-1];
		
		return null;
	}
}
