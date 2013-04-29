package de.bdh.kb2;

import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;

public class KBCArea 
{
	public int[][] map;
	Main m;
	String world = "", pass = "", owner = "", ruleset = "", perm = "", gruppe = "";
	boolean pvp = false;
	boolean invers = false;
	public int id,lastpay,bh=0,price,upgradeprice=0,paid,cansell=0,miet=0,autofree=0,onlyamount=0,nobuild=1,level,lastonline,noloose,kaufzeit,timestamp,sold;
	public List<Integer> boh = null;
	
	public KBCArea(Main m)
	{
		this.m = m;
	}
	
	public void load()
	{
		//TODO: Load Area from DB
	}
	
	public void save()
	{
		//TODO: save to DB
	}
	
	public void registerNewChunk(Chunk c)
	{
		
	}
	
	public void looseChunk(Chunk c)
	{
		//TODO: if last one loosed - delete from db
	}
	
	public boolean isIn(Location l)
	{
		if(l.getWorld().getName().equalsIgnoreCase(this.world))
		{
			if(this.map[l.getChunk().getX()][l.getChunk().getZ()] == 1)
				return true;
		}
		return false;
	}
	
	public boolean isCloseTo(Chunk c)
	{
		int x = c.getX();
		int z = c.getZ();
		if(map[x][z] == 1 || map[x-1][z] == 1 || map[x+1][z] == 1 || map[x][z-1] == 1 || map[x][z+1] == 1 || map[x+1][z+1] == 1 || map[x-1][z+1] == 1 || map[x+1][z-1] == 1 || map[x-1][z-1] == 1)
			return true;
		
		return false;
	}
	
	public int getMiet()
	{
		if(this.miet == 0) return 0;
		if(this.miet == 1) return this.price;
		else return this.miet;
	}
	
	public boolean canPlaceBlock(int id)
	{
		if(id == 0)
			return true;
		
		boolean ret;
		if(this.boh.contains(id))
			ret = false;
		else ret = true;
		
		
		if(this.invers)
			return !ret;
		else
			return ret;
	}
	
	public void unload()
	{
		//TODO: Unloading of this Area in the KBCHelper map array
	}
	
	public boolean canPlaceBlock(Block b)
	{
		return this.canPlaceBlock(b.getTypeId());
	}
}
