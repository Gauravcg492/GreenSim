package org.cloudbus.cloudsim.infrastructure;

import java.util.List;

import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.VmAllocationPolicy;

public class ExtDatacenter extends Datacenter {
	private ExtDatacenterCharacteristics characteristics;

	public ExtDatacenter(String name,
			ExtDatacenterCharacteristics characteristics,
			VmAllocationPolicy vmAllocationPolicy,
			List<Storage> storageList,
			double schedulingInterval) throws Exception {
		super(name, characteristics, vmAllocationPolicy, storageList, schedulingInterval);
		// TODO Auto-generated constructor stub
		for(Zone zone : getCharacteristics().getZoneList()) {
			zone.setDatacenter(this);
		}
	}

	public ExtDatacenterCharacteristics getCharacteristics() {
		return characteristics;
	}

	public void setCharacteristics(ExtDatacenterCharacteristics characteristics) {
		this.characteristics = characteristics;
	}

}
