package org.cloudbus.cloudsim.infrastructure;

import java.util.List;
import java.util.ArrayList;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.VmScheduler;
import org.cloudbus.cloudsim.provisioners.BwProvisioner;
import org.cloudbus.cloudsim.provisioners.RamProvisioner;

public class ExtHost extends Host {
	
	/** The rack where the host is placed. */
	private Rack rack;

	public ExtHost(int id, RamProvisioner ramProvisioner, BwProvisioner bwProvisioner, long storage,
			List<? extends Pe> peList, VmScheduler vmScheduler) {
		super(id, ramProvisioner, bwProvisioner, storage, peList, vmScheduler);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Gets the rack of the host.
	 * 
	 * @return the rack where the host runs
	 */
	public Rack getRack() {
		return rack;
	}

	/**
	 * Sets the rack of the host.
	 * 
	 * @param rack the data center from this host
	 */
	public void setRack(Rack rack) {
		this.rack = rack;
	}
	
	/**
	 * Gets the characteristics of the host.
	 */
	public List<String> getCharacteristics() {
		
		List<String> c= new ArrayList<String>();  
		c.add(Integer.toString(getId()));
		c.add(Long.toString(getBw()));
		c.add(Long.toString(getRam()));
		c.add(Long.toString(getStorage()));
		return c;
	}
	
	/**
	 * 
	 * @return the address of the host.
	 */
	
	public String getAddress() {
		int hostid = getId();
		int rackid = getRack().getId();
		int aisleid = getRack().getAisle().getId();
		int zoneid = getRack().getAisle().getZone().getId();
		int dcid = getRack().getAisle().getZone().getDatacenter().getId();
		String address = Integer.toString(dcid)+"_"+Integer.toString(zoneid)+"_"+Integer.toString(aisleid)+"_"+Integer.toString(rackid)+"_"+Integer.toString(hostid);

		return address;
	}

}
