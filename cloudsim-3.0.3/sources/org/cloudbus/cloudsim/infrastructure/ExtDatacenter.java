package org.cloudbus.cloudsim.infrastructure;

import java.util.List;

import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.VmAllocationPolicy;

public class ExtDatacenter extends Datacenter {

	public ExtDatacenter(String name,
			ExtDatacenterCharacteristics characteristics,
			VmAllocationPolicy vmAllocationPolicy,
			List<Storage> storageList,
			double schedulingInterval) throws Exception {
		super(name, characteristics, vmAllocationPolicy, storageList, schedulingInterval);
		setCharacteristics(characteristics);
		
		for(Zone zone : getCharacteristics().getZoneList()) {
			zone.setDatacenter(this);
		}
	}

	/**
	 * Gets the characteristics.
	 * 
	 * @return the characteristics
	 */
	protected ExtDatacenterCharacteristics getCharacteristics() {
		return (ExtDatacenterCharacteristics) super.getCharacteristics();
	}

}
