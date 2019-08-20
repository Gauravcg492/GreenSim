package org.cloudbus.cloudsim.infrastructure;

import java.util.ArrayList;
import java.util.List;

import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;

public class ExtDatacenterCharacteristics extends DatacenterCharacteristics {
	
	private List<? extends Zone> zoneList;

	public ExtDatacenterCharacteristics(
			String architecture,
			String os,
			String vmm,
			List<? extends Zone> zoneList,
			double timeZone,
			double costPerSec,
			double costPerMem,
			double costPerStorage,
			double costPerBw) {
		super(architecture, os, vmm, new ArrayList<Host>(), timeZone,
				costPerSec, costPerMem, costPerStorage, costPerBw);
		// TODO Auto-generated constructor stub
		setZoneList(zoneList);
	}	

	/**
	 * Gets the host list.
	 * 
	 * @param <T> the generic type
	 * @return the host list
         * @todo check this warning below
	 */
	@SuppressWarnings("unchecked")
	public <T extends Host> List<T> getHostList() {
		List<Host> hostList = new ArrayList<Host>();
		for (Zone zone : zoneList) {
			for(Aisle aisle : zone.getAisleList())
			{
				for(Rack rack : aisle.getRackList())
				{
					hostList.addAll(rack.getHostList());
				}
			}			
		}
		return (List<T>) hostList;
	}
	
	/**
	 * Sets the zone list.
	 * 
	 * @param <T> the generic type
	 * @param zoneList the new zone list
	 */
	protected <T extends Zone> void setZoneList(List<T> zoneList) {
		this.zoneList = zoneList;
	}
	
	/**
	 * Gets the zone list.
	 * 
	 * @param <T> the generic type
	 * @return the zone list
         * @todo check this warning below
	 */
	@SuppressWarnings("unchecked")
	public <T extends Zone> List<T> getZoneList() {
		return (List<T>) zoneList;
	}

}
