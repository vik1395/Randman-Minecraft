package me.vik1395.randman;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.wimbli.WorldBorder.*;

public class RandMan extends JavaPlugin
{

    public RandMan()
    {
        wb = null;
    }

    public void onDisable()
    {
        wb = null;
        PluginDescriptionFile pdfFile = getDescription();
        System.out.println(String.valueOf(pdfFile.getName()) + " says Goodbye!");
    }

    public void onEnable()
    {
    	String wgh = getConfig().getString("World Guard Hook");
    	stimer = getConfig().getString("Teleport Timer");
    	String ntsp = getConfig().getString("Nether Support");
    	if(ntsp.equalsIgnoreCase("True"))
    	{
    		nsp = true;
    	}
        Plugin wbd = getServer().getPluginManager().getPlugin("WorldBorder");
        if(wbd != null && wbd.getClass().getName().equals("com.wimbli.WorldBorder.WorldBorder"))
        {
            wb = (WorldBorder)wbd;
            System.out.println("[RandMan] hooked into WorldBorder.");
        }
        
        if(wgh.equalsIgnoreCase("True"))
        {
        	wgd = getServer().getPluginManager().getPlugin("WorldGuard");
	        
	        if ((wgd != null))
	        {
	            System.out.println("[RandMan] hooked into WorldGuard.");
	            wgc = true;
	        }
        }
        getLogger().info("RandMan has successfully started!");
		getLogger().info("Created by Vik1395");
		saveDefaultConfig();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[])
    {
        if(cmd.getName().equalsIgnoreCase("randman"))
        {
            if(wb != null && (sender instanceof Player))
            {
                Player p = (Player)sender;
                if(getCords(p))
                	return true;
            } 
            else
            {
                return true;
            }
        } 
        else
        {
            return false;
        }
        return false;
    }
    
    public boolean getCords(Player p)
    {
    	if(!p.hasPermission("randman.use"))
            return false;
    	
        World w = p.getWorld();
        
        
        if(!p.hasPermission("randman.world."+w.getName())||!p.hasPermission("randman.world.*"))
        {
        	p.sendMessage(ChatColor.RED + "You are not allowed to use RandMan in " + w.getName());
        	return true;
        }
        
        if(nsp==false&&w.equals(Environment.NETHER))
        {
            p.sendMessage(ChatColor.RED + "Nether Support of RandMan has been disabled.");
            return true;
        }
        
        @SuppressWarnings("deprecation")
		BorderData bd = wb.GetWorldBorder(w.getName());
        if(bd == null)
        {
        	p.sendMessage(ChatColor.RED + "Please set up World Border");
            return false;
        }
        int getrx = bd.getRadiusX();
        int getrz = bd.getRadiusZ();
        int getrad = (getrx + getrz)/2;
        int r = getrad - 1;
        boolean shape;
        if(bd.getShape() == null)
            shape = Config.ShapeRound();
        else
            shape = bd.getShape().booleanValue();
        if(shape)
        {
            for(short i = 0; i < 256; i++)
            {
                double rrel = rand.nextDouble() * (double)r;
                double trel = rand.nextDouble() * 2D * 3.1415926535897931D;
                int x = Location.locToBlock(bd.getX() + rrel * Math.cos(trel));
                int z = Location.locToBlock(bd.getZ() + rrel * Math.sin(trel));
                for(int j = 0; j < 5; j++)
                {
	                if(grabRand(p, w, x, z))
	                {
	                    return true;
	                }
	                else
	                {
	                	rrel = rand.nextDouble() * (double)r;
	                    trel = rand.nextDouble() * 2D * 3.1415926535897931D;
	                    x = Location.locToBlock(bd.getX() + rrel * Math.cos(trel));
	                    z = Location.locToBlock(bd.getZ() + rrel * Math.sin(trel));
	                    
	                    if(grabRand(p, w, x, z))
	                    	return true;
	                }
                }
            }

        } else
        {
            for(short i = 0; i < 256; i++)
            {
                int xrel = rand.nextInt(r * 2) - r;
                int zrel = rand.nextInt(r * 2) - r;
                int x = Location.locToBlock(bd.getX() + (double)xrel);
                int z = Location.locToBlock(bd.getZ() + (double)zrel);
                for(int j = 0; j < 5; j++)
                {
	                if(grabRand(p, w, x, z))
	                {
	                    return true;
	                }
	                else
	                {
	                	xrel = rand.nextInt(r * 2) - r;
	                    zrel = rand.nextInt(r * 2) - r;
	                    x = Location.locToBlock(bd.getX() + (double)xrel);
	                    z = Location.locToBlock(bd.getZ() + (double)zrel);
	                    
	                    if(grabRand(p, w, x, z))
	                    	return true;
	                }
                }
            }

        }
        p.sendMessage(ChatColor.DARK_RED + "Error, teleport timed out. Please try again");
        getServer().getLogger().log(Level.WARNING, "[RandMan] teleport timed out");
        return true;
    }

    public boolean grabRand(final Player p, World w, int x, int z)
    {
        Chunk c = w.getChunkAt(new Location(w, x, 0.0D, z));
        w.loadChunk(c);
        int y= 0;
        if(nsp==true&&w.getEnvironment().equals(Environment.NETHER))
        {
	        for(short tr = 1; tr<128; tr++)
	        {
	        	Material tt = w.getBlockAt(x,tr,z).getType();
	        	if(tt.toString().equalsIgnoreCase("AIR"))
	        	{
	        		Material tt1 = w.getBlockAt(x,tr+1,z).getType();
	            	if(tt1.toString().equalsIgnoreCase("AIR"))
	            	{
	            		y = tr;
	            		break;
	            	}
	        	}
	        }
        }
        else
        {
        	y = w.getHighestBlockYAt(x, z);
        }
        
        if(y == 0)
        {
            getServer().getLogger().log(Level.WARNING, "[RandMan] Unable to find the ground");
            w.unloadChunk(c.getX(), c.getZ());
            return false;
        }
		Block typ = w.getBlockAt(x, y - 1, z);
		Material mtr = typ.getType();
        if(mtr.equals(Material.STATIONARY_WATER) || mtr.equals(Material.STATIONARY_LAVA) || mtr.equals(Material.WATER) || mtr.equals(Material.LAVA) || mtr.equals(Material.FIRE))
        {
            w.unloadChunk(c.getX(), c.getZ());
            return false;
        }
        
        else if(mtr.equals(Material.LONG_GRASS)||mtr.equals(Material.YELLOW_FLOWER)||mtr.equals(Material.RED_ROSE))
        {
        	Block lav = w.getBlockAt(x, y - 2, z);
        	if(lav.getType().equals(Material.LAVA)||lav.getType().equals(Material.STATIONARY_LAVA))
        	{
        		w.unloadChunk(c.getX(), c.getZ());
                return false;
        	}
        }
        
        else if(wgc==true)
        {
        	WorldGuardPlugin wg = (WorldGuardPlugin) wgd;
        	RegionManager rgm = wg.getRegionManager(w);
        	Map<String, ProtectedRegion> mp = rgm.getRegions();
            Iterator<Entry<String, ProtectedRegion>> it = mp.entrySet().iterator();
            while (it.hasNext()) 
            {
                @SuppressWarnings("rawtypes")
				Map.Entry pairs = (Map.Entry)it.next();
                String rgname = (String) pairs.getKey();
                ProtectedRegion ptr = rgm.getRegion(rgname);
	        	if(ptr.contains(x, y, z)==true)
	        	{
	        		w.unloadChunk(c.getX(), c.getZ());
	                return false;
	        	}
            }
            sendPlayer(p, w, x, z, y);
        }
        
        else
        {
        	sendPlayer(p, w, x, z, y);
        }
            
        return true;
    }
    
	private void sendPlayer(final Player p, World w, int x, int z, int y)
	{
		newloc = new Location(w, (double)x + 0.5D, y, (double)z + 0.5D);
	    poloc = p.getLocation();
	    int timer = Integer.parseInt(stimer);
	    if(p.hasPermission("randman.admin"))
	    {
	    	timer = 0;
	    }
	    long timertps = timer*20;
	    
	    p.sendMessage(ChatColor.GOLD + "Teleportation will begin in " + ChatColor.RED + timer + ChatColor.GOLD + " seconds.");
	    
	    getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
			public void run()
			{
				long ox = (long) poloc.getX();
				long oy = (long) poloc.getY();
				long oz = (long) poloc.getZ();
				pnloc = p.getLocation();
				long nx = (long) pnloc.getX();
				long ny = (long) pnloc.getY();
				long nz = (long) pnloc.getZ();
				
				boolean xCheck = nx<=ox+1 && nx>=ox-1;
				boolean yCheck = ny<=oy+1 && ny>=oy-1;
				boolean zCheck = nz<=oz+1 && nz>=oz-1;
				
				if(xCheck && yCheck && zCheck)
				{
					p.teleport(newloc);
					p.sendMessage(ChatColor.GOLD + "You have successfully been teleported to a random area.");
				}
				else
				{
					p.sendMessage(ChatColor.DARK_RED + "You have moved! The teleportation has failed.");
				}
			}
		}, timertps);
	}

    private final Random rand = new Random();
    private String stimer = "";
    private Plugin wgd;
    private boolean wgc = false;
    private boolean nsp = false;
    private WorldBorder wb;
    private Location poloc;
    private Location pnloc;
    private Location newloc;
}

