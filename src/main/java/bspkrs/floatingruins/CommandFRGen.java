package bspkrs.floatingruins;

import java.util.Random;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.village.Village;
import net.minecraft.world.World;

public class CommandFRGen extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "frgen";
    }
    
    @Override
    public String getCommandUsage(ICommandSender icommandsender)
    {
        return "commands.frgen.usage";
    }
    
    @Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }
    
    /*
     *  /frgen <x> <y> <z> [[<radius> <depth>] <shape>]
     *  /frgen random
     */
	@Override
    @SuppressWarnings("unchecked")
    public void processCommand(ICommandSender sender, String[] args)
    {
        if (sender instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) sender;
            int x = (int) player.posX;
            int z = (int) player.posZ;
            int tgtY = -1;
            int radius = -1;
            int depth = -1;
            int islandType = -1;
            boolean tryGenerateDungeon = true;
            int noDungeonWeight = -1;
            
            for (int i = 0; i < args.length - 1; i += 2)
            {
            	int j = i + 1;
            	
            	String param = args[i];
            	String value = args[j];
            	
            	if (param.equalsIgnoreCase("x"))
            	{
            		try
            		{
            			x = CommandBase.parseInt(sender, value);
            		}
            		catch (Exception ex)
            		{
            			x = (int) player.posX;
            		}
            		finally
            		{
            		}
            	}
            	else if (param.equalsIgnoreCase("z"))
            	{
            		try
            		{
                		z = CommandBase.parseInt(sender, value);
            		}
            		catch (Exception ex)
            		{
            			z = (int) player.posZ;
            		}
            		finally
            		{
            		}
            	}
            	else if (param.equalsIgnoreCase("height"))
            	{
            		try
            		{
                		tgtY = CommandBase.parseInt(sender, value);
            		}
            		catch (Exception ex)
            		{
            			tgtY = -1;
            		}
            		finally
            		{
            		}
            	}
            	else if (param.equalsIgnoreCase("radius"))
            	{
            		try
            		{
            			boolean foundVillage = false;
            			
            			if (value.equalsIgnoreCase("village"))
            			{
            				World world = player.worldObj;
            		        if (world.villageCollectionObj != null)
            		            for (Village village : (List<Village>) world.villageCollectionObj.getVillageList())
            		            {
            		                if (Math.sqrt(village.getCenter().getDistanceSquared((int) player.posX, (int) player.posY, (int) player.posZ)) < village.getVillageRadius())
            		                {
            		                    foundVillage = true;
            		                    x = village.getCenter().posX;
            		                    z = village.getCenter().posZ;
            		                    islandType = WorldGenFloatingIsland.SPHEROID;
            		                    radius = village.getVillageRadius() + 5;
            		                    tryGenerateDungeon = false;
            		                    
            		                    break;
            		                }
            		            }
            		        
            		        if (!foundVillage)
            		        {
            		        	radius = -1;
            		        }
            		        
        		        	break;
            			}
            			else
            				radius = CommandBase.parseIntWithMin(sender, value, 1);
            		}
            		catch (Exception ex)
            		{
            			radius = -1;
            		}
            		finally
            		{
            		}
            	}
            	else if (param.equalsIgnoreCase("depth"))
            	{
            		try
            		{
                		depth = CommandBase.parseIntWithMin(sender, value, 1);
            		}
            		catch (Exception ex)
            		{
            			depth = -1;
            		}
            		finally
            		{
            		}
            	}
            	else if (param.equalsIgnoreCase("type"))
            	{
            		if (value.equalsIgnoreCase("spheroid") || value.equalsIgnoreCase("sphere") || value.equalsIgnoreCase("bowl") || value.equalsIgnoreCase("0"))
            			islandType = 0;
            		else if (value.equalsIgnoreCase("cone") || value.equalsIgnoreCase("1"))
            			islandType = 1;
            		else if (value.equalsIgnoreCase("jetsons") || value.equalsIgnoreCase("2"))
            			islandType = 2;
            		else if (value.equalsIgnoreCase("stalactite") || value.equalsIgnoreCase("3"))
            			islandType = 3;
            		else
            			islandType = -1;
            	}
            	else if (param.equalsIgnoreCase("tryGenerateDungeon"))
            	{
            		if (value.equalsIgnoreCase("0") || value.equalsIgnoreCase("false"))
            			tryGenerateDungeon = false;
            		else
            			tryGenerateDungeon = true;
            	}
            	else if (param.equalsIgnoreCase("dungeonRarity"))
            	{
            		try
            		{
                		noDungeonWeight = CommandBase.parseIntWithMin(sender, value, 0);
            		}
            		catch (Exception ex)
            		{
            			noDungeonWeight = -1;
            		}
            		finally
            		{
            		}
            	}
            }

            FloatingRuins.generateSurface(player.worldObj, null, x, z, false, tgtY, radius, depth, islandType, tryGenerateDungeon, noDungeonWeight);
        }
    }
    
    @Override
    public int compareTo(Object o)
    {
        // TODO Auto-generated method stub
        return 0;
    }
}
