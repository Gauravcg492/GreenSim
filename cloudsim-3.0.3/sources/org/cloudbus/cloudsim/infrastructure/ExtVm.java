package org.cloudbus.cloudsim.infrastructure;

import org.cloudbus.cloudsim.CloudletScheduler;
import org.cloudbus.cloudsim.Vm;

/**
 * Extended version of the Vm.java class to get the address of the VM
 * @author user
 *
 */

public class ExtVm extends Vm{

	public ExtVm(int id, int userId, double mips, int numberOfPes, int ram, long bw, long size, String vmm,
			CloudletScheduler cloudletScheduler) {
		super(id, userId, mips, numberOfPes, ram, bw, size, vmm, cloudletScheduler);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @return the address of the vm.
	 */
	
	public String getAddress() {
		
		int vmid = getId();
		int hostid = getHost().getId();
		int rackid = getHost().getRack().getId();
		int aisleid = getHost().getRack().getAisle().getId();
		int zoneid = getHost().getRack().getAisle().getZone().getId();
		int dcid = getHost().getRack().getAisle().getZone().getDatacenter().getId();
		String address = Integer.toString(dcid)+"_"+Integer.toString(zoneid)+"_"+Integer.toString(aisleid)+"_"+Integer.toString(rackid)+"_"+Integer.toString(hostid)+"_"+Integer.toString(vmid);

		return address;
	}

}
