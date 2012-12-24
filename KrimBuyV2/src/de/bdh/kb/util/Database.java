package de.bdh.kb.util;

import de.bdh.kb.util.configManager;

import java.sql.*;

public class Database
{
	private Connection con = null;
    public Database()
    {
        if(configManager.DatabaseType.equalsIgnoreCase("mySQL"))
        {
            driver = "com.mysql.jdbc.Driver";
            dsn = (new StringBuilder()).append("jdbc:mysql://").append(configManager.SQLHostname).append(":").append(configManager.SQLPort).append("/").append(configManager.SQLDatabase).toString();
            username = configManager.SQLUsername;
            password = configManager.SQLPassword;
        }
        try
        {
            Class.forName(driver).newInstance();
        }
        catch(Exception e)
        {
            System.out.println((new StringBuilder()).append("[KB] Driver error: ").append(e).toString());
        }
	}

    public Connection getConnection()
    {
    	try
    	{
    		if(this.con == null || this.con.isClosed())
	    	{
	    		this.con = this.makeConnection();
	    	}
	    } catch(SQLException e)
        {
            System.out.println((new StringBuilder()).append("[KB] Could not create connection: ").append(e).toString());
        }
    	
    	return this.con;
    }
    
    public Connection makeConnection()
    {
        if(username.equalsIgnoreCase("") && password.equalsIgnoreCase(""))
			try {
				return DriverManager.getConnection(dsn);
			} catch (SQLException e1) { }
        
        try
        {
            return DriverManager.getConnection(dsn, username, password);
        }
        catch(SQLException e)
        {
            System.out.println((new StringBuilder()).append("[KB] Could not create connection: ").append(e).toString());
        }
        return null;
    }
    

    public void close(Connection connection)
    {
        if(connection != null)
        try
        {
            connection.close();
        }
        catch(SQLException ex) { }
    }

    public void setupTable() throws Exception
    {
        Connection conn = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        DatabaseMetaData dbm = conn.getMetaData();
        rs = dbm.getTables(null, null, (new StringBuilder()).append(configManager.SQLTable).append("_krimbuy").toString(), null);
        if(!rs.next())
        {
            System.out.println((new StringBuilder()).append("[KB] Creating table: ").append(configManager.SQLTable).append("_krimbuy").toString());
            ps = conn.prepareStatement((new StringBuilder()).append("CREATE TABLE ").append(configManager.SQLTable).append("_krimbuy(").append("`id` int(12) NOT NULL AUTO_INCREMENT, `world` VARCHAR( 128 ) NOT NULL DEFAULT 'world',").append("`price` INT(10) NULL DEFAULT  '0', `pass` VARCHAR( 50 ) NOT NULL DEFAULT '',").append("`blockx` INT(10) NULL DEFAULT  '0',").append("`blocky` INT(10) NULL DEFAULT  '0',").append("`blockz` INT(10) NULL DEFAULT  '0',").append("`bx` INT(10) NULL DEFAULT  '0',").append("`by` INT(10) NULL DEFAULT  '0',").append("`bz` INT(10) NULL DEFAULT  '0',").append("`tx` INT(10) NULL DEFAULT  '0',").append("`ty` INT(10) NULL DEFAULT  '0',").append("`tz` INT(10) NULL DEFAULT  '0',").append("`sold` INT(1) NULL DEFAULT  '0',").append("`buyer` varchar(128) NULL DEFAULT  '0',").append("`ruleset` varchar(100) NULL DEFAULT  '0',").append("`lastpay` INT(10) NULL DEFAULT  '0',").append("`kaufzeit` INT(10) NULL DEFAULT  '0',").append("`lastonline` INT(10) NULL DEFAULT  '0',").append("`noloose` int(1) NULL DEFAULT  '0',").append("`floor` TEXT,").append("`paid` int(10) NULL DEFAULT  '0',").append("`level` int(10) NULL DEFAULT  '0',").append("PRIMARY KEY (`id`), UNIQUE KEY `location` (`world`,`blockx`,`blocky`,`blockz`), KEY `top` (`world`,`tx`,`ty`,`tz`), KEY `bot` (`world`,`bx`,`by`,`bz`)").append(")").toString());
            ps.executeUpdate();
            System.out.println("[KB] Table buy Created.");
            
            
        }
        
        rs = dbm.getTables(null, null, (new StringBuilder()).append(configManager.SQLTable).append("_krimbuy_rules").toString(), null);
        if(!rs.next())
        {
	        System.out.println((new StringBuilder()).append("[KB] Creating table: ").append(configManager.SQLTable).append("_krimbuy_rules").toString());
	        ps = conn.prepareStatement((new StringBuilder()).append("CREATE TABLE ").append(configManager.SQLTable).append("_krimbuy_rules(").append("`id` int(12) NOT NULL AUTO_INCREMENT,").append("`ruleset` varchar(100) NULL DEFAULT  '',").append("`level` INT(3) NULL DEFAULT  '0',").append("`permissionnode` VARCHAR(25) NOT NULL DEFAULT  '',").append("`controlblockheight` INT(10) NULL DEFAULT  '1',").append("`autofree` INT(10) NULL DEFAULT  '0',").append("`miet` INT(10) NULL DEFAULT  '0',").append("`cansell` INT(3) NULL DEFAULT  '70',").append("`nobuy` INT(3) NULL DEFAULT  '0',").append("`onlyamount` INT(3) NULL DEFAULT  '0',").append("`clear` INT(2) NULL DEFAULT  '1',").append("`nobuild` INT(1) NULL DEFAULT  '0',").append("`height` INT(10) NULL DEFAULT  '0',").append("`deep` INT(10) NULL DEFAULT  '0', `price` INT NOT NULL DEFAULT '0',").append("`bottom` TEXT,").append("`blocks` TEXT,").append("`gruppe` VARCHAR(25) NOT NULL DEFAULT  '',").append("PRIMARY KEY (`id`), UNIQUE KEY `rulelvl` (`level`,`ruleset`)").append(")").toString());
	        ps.executeUpdate();
	        System.out.println("[KB] Table buy_rules Created.");
        }
        
        if(ps != null)
            try
            {
                ps.close();
            }
            catch(SQLException ex) { }
        if(rs != null)
            try
            {
                rs.close();
            }
            catch(SQLException ex) { }
    }
    
    public void setupTableMutex() throws Exception
    {
        Connection conn = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        DatabaseMetaData dbm = conn.getMetaData();
        rs = dbm.getTables(null, null, (new StringBuilder()).append("mutex").toString(), null);
        if(!rs.next())
        {
            System.out.println((new StringBuilder()).append("[KB] Creating table: ").append("mutex").toString());
            ps = conn.prepareStatement((new StringBuilder()).append("CREATE TABLE `mutex` (`i` int(11) NOT NULL,PRIMARY KEY (`i`))").toString());
            ps.executeUpdate();
            
            if(ps != null)
                try
                {
                    ps.close();
                }
                catch(SQLException ex) { }
            
            ps = conn.prepareStatement((new StringBuilder()).append("INSERT INTO `mutex` (`i`) VALUES (0),(1)").toString());
            ps.executeUpdate();
            
            System.out.println("[KB] Table mutex Created.");
        }
        
        if(ps != null)
            try
            {
                ps.close();
            }
            catch(SQLException ex) { }
        if(rs != null)
            try
            {
                rs.close();
            }
            catch(SQLException ex) { }
    }
    
    private String driver;
    private String dsn;
    private String username;
    private String password;
}
