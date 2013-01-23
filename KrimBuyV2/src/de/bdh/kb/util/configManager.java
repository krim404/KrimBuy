package de.bdh.kb.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import de.bdh.kb2.Main;

public class configManager {
	
	protected static Main plugin;
    protected static YamlConfiguration conf;
    
    // features 
    public static String DatabaseType = "mySQL";
    public static String SQLHostname = "localhost";
    public static String SQLPort = "3306";
    public static String SQLUsername = "root";
    public static String SQLPassword = "";
    public static String SQLDatabase = "minecraft";
    public static String SQLTable = "minecraft";
    public static String BrauTec = "0";
    public static Integer interactMessage = 0;
    public static String worlds = "0";
    public static String lang = "de";
    public static Integer doSponge = 1;
    public static Integer doPiston = 1;
    public static Integer doProtectPicsTNT = 1;
    public static Integer interactBlock = 7;
    public static String permOutBorder = "";
    public static Integer fromx = 0,fromy = 0,fromz = 0,tox = 0,toy = 0,toz = 0;
    public static HashMap<World,kbWorld> worldLimit = new HashMap<World,kbWorld>();
    private static File confFile;
    
	
    public configManager(Main main) {
    	plugin = main;
    	
    	File theDir = new File(plugin.getDataFolder(),"");
		if (!theDir.exists())
		{
			theDir.mkdir();
		}
		
    	setupconf();
    	load();
    }
    
	private static void load() 
	{
		try {
			conf.load(confFile);
		} catch (Exception e) {
			e.printStackTrace();		
		}
		
    	DatabaseType = conf.getString("System.Database.Type", DatabaseType);
        SQLDatabase = conf.getString("System.Database.Settings.Name", SQLDatabase);
        SQLTable = conf.getString("System.Database.Settings.Table", SQLTable);
        SQLHostname = conf.getString("System.Database.Settings.MySQL.Hostname", SQLHostname);
        SQLPort = conf.getString("System.Database.Settings.MySQL.Port", SQLPort);
        SQLUsername = conf.getString("System.Database.Settings.MySQL.Username", SQLUsername);
        SQLPassword = conf.getString("System.Database.Settings.MySQL.Password", SQLPassword);
        BrauTec = conf.getString("System.BrauTec",BrauTec);
        interactMessage = conf.getInt("System.interactMessage",interactMessage);
        lang = conf.getString("System.lang",lang);
        doSponge = conf.getInt("System.sponge",doSponge);
        doPiston = conf.getInt("System.hookPistonEvent",doPiston);
        doProtectPicsTNT = conf.getInt("System.protectPicturesFromTNT",doProtectPicsTNT);
        worlds = conf.getString("System.worlds",worlds);
        interactBlock = conf.getInt("System.interactBlock",interactBlock);
        
        fromx = conf.getInt("System.worldLimit.default.protect.from.x",fromx);
        fromy = conf.getInt("System.worldLimit.default.protect.from.y",fromy);
        fromz = conf.getInt("System.worldLimit.default.protect.from.z",fromz);
        tox = conf.getInt("System.worldLimit.default.protect.to.x",tox);
        toy = conf.getInt("System.worldLimit.default.protect.to.y",toy);
        toz = conf.getInt("System.worldLimit.default.protect.to.z",toz);
        permOutBorder = conf.getString("System.worldLimit.default.protect.permOut",permOutBorder);
        
        Integer tx,ty,tz,fx,fy,fz;
        String pr;
        
		if(worlds != null && worlds.length() > 0)
		{
			String[] tmpBoh = worlds.split(",");
			for (String bl: tmpBoh) 
			{
			   if(Bukkit.getWorld(bl) != null)
			   {
				   	tx = conf.getInt("System.worldLimit."+bl+".protect.to.x",0);
			        ty = conf.getInt("System.worldLimit."+bl+".protect.to.y",0);
			        tz = conf.getInt("System.worldLimit."+bl+".protect.to.z",0);
			        fx = conf.getInt("System.worldLimit."+bl+".protect.from.x",0);
			        fy = conf.getInt("System.worldLimit."+bl+".protect.from.y",0);
			        fz = conf.getInt("System.worldLimit."+bl+".protect.from.z",0);
			        pr = conf.getString("System.worldLimit."+bl+".protect.permOut","");
			        
			        if(!(tx == fx && ty == fy && tz == fz))
			        {
			        	worldLimit.put(Bukkit.getWorld(bl) , new kbWorld(bl,fx,fy,fz,tx,ty,tz,pr));
			        } else if(!(fromx == tox && fromy == toy && fromz == toz))
		            {
			        	worldLimit.put(Bukkit.getWorld(bl) , new kbWorld(bl,fromx,fromy,fromz,tox,toy,toz,permOutBorder));
		            } 
			   }
			}
		}
		
        
        
        try {
        	if (!confFile.exists())
        		confFile.createNewFile();
			conf.save(confFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	public static void reload() {
		load();
	}
	
    private static void setupconf() {
        confFile = new File(plugin.getDataFolder(), "config.yml");
        conf = null;
        
        if (confFile.exists())
        {
            conf = new YamlConfiguration();
            try {
				conf.load(confFile);
			} catch (Exception e) {
				e.printStackTrace();
			}       
        }
        else {
            File confFile;
            confFile = new File(plugin.getDataFolder(), "config.yml");
            conf = new YamlConfiguration();

            conf.set("System.Database.Type", DatabaseType);
            conf.set("System.Database.Settings.Name", SQLDatabase);
            conf.set("System.Database.Settings.Table", SQLTable);
            conf.set("System.Database.Settings.MySQL.Hostname", SQLHostname);
            conf.set("System.Database.Settings.MySQL.Port", SQLPort);
            conf.set("System.Database.Settings.MySQL.Username", SQLUsername);
            conf.set("System.Database.Settings.MySQL.Password", SQLPassword);
            conf.set("System.interactMessage", interactMessage);
            //conf.set("System.BrauTec", BrauTec);
            conf.set("System.interactBlock",interactBlock);
            conf.set("System.worlds", worlds);
            conf.set("System.lang", lang);
            conf.set("System.sponge", doSponge);
            conf.set("System.hookPistonEvent", doPiston);
            conf.set("System.protectPicturesFromTNT", doProtectPicsTNT);

            conf.set("System.worldLimit.default.protect.from.x",fromx);
            conf.set("System.worldLimit.default.protect.from.y",fromy);
            conf.set("System.worldLimit.default.protect.from.z",fromz);
       
            conf.set("System.worldLimit.default.protect.to.x",tox);
            conf.set("System.worldLimit.default.protect.to.y",toy);
            conf.set("System.worldLimit.default.protect.to.z",toz);
            conf.set("System.worldLimit.default.protect.permOut","");
          
            
            try {
                confFile.createNewFile();
				conf.save(confFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}
}
