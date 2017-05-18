package com.pizzaboxcore.constants;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.pizzabox.common.constants.Constants;

/**
 * Categorizes address into zones
 * 
 * @author rupalip
 *
 */
public enum Zone {

	NORTH_ZONE(Constants.NORTH_ZONE_CHANNEL),
	SOUTH_ZONE(Constants.SOUTH_ZONE_CHANNEL),
	EAST_ZONE(Constants.EAST_ZONE_CHANNEL),
	WEST_ZONE(Constants.WEST_ZONE_CHANNEL),
	UNKNOWN_ZONE(Constants.ERROR_CHANNEL);
	
	private static final Logger LOG = Logger.getLogger(Zone.class);
	
	private static final List<String> NORTH_ZONE_LIST = Arrays.asList("Khadki", "Alandi", "Yerwada", "Bund Garden");
	private static final List<String> SOUTH_ZONE_LIST = Arrays.asList("Sarasbaug", "Bibvewadi", "Katraj", "Dhanakwadi");
	private static final List<String> EAST_ZONE_LIST = Arrays.asList("FatimaNagar", "Hadapsar", "Magarpatta", "Camp");
	private static final List<String> WEST_ZONE_LIST = Arrays.asList("Kothrud", "KarveNagar", "Bavdhan", "Warje");
		
	private String channel;
	
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	private Zone(String channel) {
		this.channel = channel;
	}
	
	/**
	 * This method returns a zone to which the given address belongs
	 * 
	 * @param address
	 * 			Address for which the zone is to be returned
	 * @return Zone
	 * 			Zone belonging to the given address
	 */
	public static Zone getZone(final String address){
		if(NORTH_ZONE_LIST.contains(address)){
			return Zone.NORTH_ZONE;
		}
		else if(SOUTH_ZONE_LIST.contains(address)){
			return Zone.SOUTH_ZONE;
		}
		else if(EAST_ZONE_LIST.contains(address)){
			return Zone.EAST_ZONE;
		}
		else if(WEST_ZONE_LIST.contains(address)){
			return Zone.WEST_ZONE;
		}
		
		LOG.error("Invalid address");
		return Zone.UNKNOWN_ZONE;
	}
}
