package org.cloudbus.cloudsim.infrastructure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.core.CloudSimTags;
import org.cloudbus.cloudsim.core.SimEvent;
import org.cloudbus.cloudsim.lists.VmList;
import org.cloudbus.cloudsim.lists.infrastructure.ExtVmList;

public class ExtDatacenterBroker extends DatacenterBroker {
	
	
	protected void processVmCreate(SimEvent ev) {
		int[] data = (int[]) ev.getData();
		int datacenterId = data[0];
		int vmId = data[1];
		int result = data[2];

		if (result == CloudSimTags.TRUE) {
			getVmsToDatacentersMap().put(vmId, datacenterId);
			getVmsCreatedList().add(ExtVmList.getById(getVmList(), vmId));
			Log.printLine(CloudSim.clock() + ": " + getName() + ": VM #" + vmId
					+ " has been created in Datacenter #" + datacenterId + ", Host #"
					+ ExtVmList.getById(getVmsCreatedList(), vmId).getHost().getId());
		} else {
			Log.printLine(CloudSim.clock() + ": " + getName() + ": Creation of VM #" + vmId
					+ " failed in Datacenter #" + datacenterId);
		}

		incrementVmsAcks();

		// all the requested VMs have been created
		if (getVmsCreatedList().size() == getVmList().size() - getVmsDestroyed()) {
			submitCloudlets();
		} else {
			// all the acks received, but some VMs were not created
			if (getVmsRequested() == getVmsAcks()) {
				// find id of the next datacenter that has not been tried
				for (int nextDatacenterId : getDatacenterIdsList()) {
					if (!getDatacenterRequestedIdsList().contains(nextDatacenterId)) {
						createVmsInDatacenter(nextDatacenterId);
						return;
					}
				}

				// all datacenters already queried
				if (getVmsCreatedList().size() > 0) { // if some vm were created
					submitCloudlets();
				} else { // no vms created. abort
					Log.printLine(CloudSim.clock() + ": " + getName()
							+ ": none of the required VMs could be created. Aborting");
					finishExecution();
				}
			}
		}
	}
	
	/** The list of VM Ids for each Cloudlet*/
	public List <Integer> cloudlet_vmid = new ArrayList<Integer>();

	public ExtDatacenterBroker(String name) throws Exception {
		super(name);
	}
	
	/**
	 * Process a cloudlet return event.
	 * 
	 * @param ev a SimEvent object
	 * @pre ev != $null
	 * @post $none
	 */
	protected void processCloudletReturn(SimEvent ev) {
		Cloudlet cloudlet = (Cloudlet) ev.getData();
		getCloudletReceivedList().add(cloudlet);
		Log.printLine(CloudSim.clock() + ": " + getName() + ": Cloudlet " + cloudlet.getCloudletId()
				+ " received");
		
		cloudlet_vmid.add(cloudlet.getVmId());
		
		cloudletsSubmitted--;
		if (getCloudletList().size() == 0 && cloudletsSubmitted == 0) { // all cloudlets executed
			
//			Log.printLine(getVmsCreatedList().size());
//			Log.printLine(cloudlet_vmid);
			
			int count_vmid[]= new int[getVmsCreatedList().size()];
			
			int hostid[] = new int[cloudlet_vmid.size()];
			List <Integer> host = new ArrayList<Integer>();
			Set<Integer> host_hash_Set = new HashSet<Integer>();
			
			int rackid[] = new int[cloudlet_vmid.size()];
			List <Integer> rack = new ArrayList<Integer>();
			Set<Integer> rack_hash_Set = new HashSet<Integer>();
			
			int aisleid[] = new int[cloudlet_vmid.size()];
			List <Integer> aisle = new ArrayList<Integer>();
			Set<Integer> aisle_hash_Set = new HashSet<Integer>();
			
			int zoneid[] = new int[cloudlet_vmid.size()];
			List <Integer> zone = new ArrayList<Integer>();
			Set<Integer> zone_hash_Set = new HashSet<Integer>();
			
			int dcid[] = new int[cloudlet_vmid.size()];
			List <Integer> dc = new ArrayList<Integer>();
			Set<Integer> dc_hash_Set = new HashSet<Integer>();
			
			for(int i = 0;i < cloudlet_vmid.size();i++) {
				count_vmid[cloudlet_vmid.get(i)]++;
				hostid[i] = ExtVmList.getById(getVmsCreatedList(), cloudlet_vmid.get(i)).getHost().getId();
				host_hash_Set.add(hostid[i]);
				host.add(hostid[i]);
				rackid[i] = ExtVmList.getById(getVmsCreatedList(), cloudlet_vmid.get(i)).getHost().getRack().getId();
				rack_hash_Set.add(rackid[i]);
				rack.add(rackid[i]);
				aisleid[i] = ExtVmList.getById(getVmsCreatedList(), cloudlet_vmid.get(i)).getHost().getRack().getAisle().getId();
				aisle_hash_Set.add(aisleid[i]);
				aisle.add(aisleid[i]);
				zoneid[i] = ExtVmList.getById(getVmsCreatedList(), cloudlet_vmid.get(i)).getHost().getRack().getAisle().getZone().getId();
				zone_hash_Set.add(zoneid[i]);
				zone.add(zoneid[i]);
				dcid[i] = ExtVmList.getById(getVmsCreatedList(), cloudlet_vmid.get(i)).getHost().getRack().getAisle().getZone().getDatacenter().getId();
				dc_hash_Set.add(dcid[i]);
				dc.add(dcid[i]);
			}
			
//			Log.printLine("The array host is: "+ host);
//			Log.printLine("The set host hashset is: " + host_hash_Set);
//			Log.printLine("The array rack is: "+ rack);
//			Log.printLine("The set rack hashset is: " + rack_hash_Set);
//			Log.printLine("The array aisle is: "+ aisle);
//			Log.printLine("The set aisle hashset is: " + aisle_hash_Set);
//			Log.printLine("The array zone is: "+ zone);
//			Log.printLine("The set zone hashset is: " + zone_hash_Set);
//			Log.printLine("The array Datacenter is: "+ dc);
//			Log.printLine("The set Datacenter hashset is: " + dc_hash_Set);
			
			int count_hostid[] = new int[host_hash_Set.size()];
			int count_rackid[] = new int[rack_hash_Set.size()];
			int count_aisleid[] = new int[aisle_hash_Set.size()];
			int count_zoneid[] = new int[zone_hash_Set.size()];
			int count_dcid[] = new int[dc_hash_Set.size()];
			
			for (int i = 0; i < count_vmid.length; i++) {
				Log.printLine("Vm "+ i + " has " + count_vmid[i]+ " cloudlets");
			}
			
			for (int i = 0; i<hostid.length; i++) {
				count_hostid[hostid[i]]++;
			}
			
			for(int i= 0;i<count_hostid.length;i++) {
				Log.printLine("Host "+ i + " has " + count_hostid[i]+ " cloudlets");
			}
			
			for (int i = 0; i<rackid.length; i++) {
				count_rackid[rackid[i]]++;
			}
			
			for(int i= 0;i<count_rackid.length;i++) {
				Log.printLine("Rack "+ i + " has " + count_rackid[i]+ " cloudlets");
			}
			
			for (int i = 0; i<aisleid.length; i++) {
				count_aisleid[aisleid[i]]++;
			}
			
			for(int i= 0;i<count_aisleid.length;i++) {
				Log.printLine("Aisle "+ i + " has " + count_aisleid[i]+ " cloudlets");
			}
			
			for (int i = 0; i<zoneid.length; i++) {
				count_zoneid[zoneid[i]]++;
			}
			
			for(int i= 0;i<count_zoneid.length;i++) {
				Log.printLine("Zone "+ i + " has " + count_zoneid[i]+ " cloudlets");
			}
			
			for (int i = 0; i<dcid.length; i++) {
				count_dcid[dcid[i]-2]++;
			}
			
			for(int i= 0;i<count_dcid.length;i++) {
				int j = i + 2;
				Log.printLine("Datacenter "+ j + " has " + count_dcid[i]+ " cloudlets");
			}
			
			Log.printLine(CloudSim.clock() + ": " + getName() + ": All Cloudlets executed. Finishing...");
			clearDatacenters();
			finishExecution();
		} else { // some cloudlets haven't finished yet
			if (getCloudletList().size() > 0 && cloudletsSubmitted == 0) {
				// all the cloudlets sent finished. It means that some bount
				// cloudlet is waiting its VM be created
				clearDatacenters();
				createVmsInDatacenter(0);
			}

		}
	}
	
	/**
	 * Submit cloudlets to the created VMs.
	 * 
	 * @pre $none
	 * @post $none
	 */
	protected void submitCloudlets() {
		int vmIndex = 0;
		List<Cloudlet> successfullySubmitted = new ArrayList<Cloudlet>();
		for (Cloudlet cloudlet : getCloudletList()) {
			Vm vm = null;
			// if user didn't bind this cloudlet and it has not been executed yet
			if (cloudlet.getVmId() == -1) {
				//Checking if enough number of processing elements and enough storage capacity is available in VMs.
				int count = 0;
				while(count<getVmsCreatedList().size()) {
					vm = getVmsCreatedList().get(vmIndex);
					if(cloudlet.getNumberOfPes() > vm.getNumberOfPes()) {
						cloudlet.setVmId(-1);
						count++; //To check if we have iterated through all possible vms.
						vmIndex = (vmIndex + 1) % getVmsCreatedList().size();
						
					}
					else if(cloudlet.getCloudletFileSize() > vm.getSize()) {
						cloudlet.setVmId(-1);
						count++;
						vmIndex = (vmIndex + 1) % getVmsCreatedList().size();
					}
					else {
						Log.printLine(CloudSim.clock() + ": " + getName() + ": cloudlet "+cloudlet.getCloudletId() + " is being sent to Vm "+ vm.getId() );
						vm.setSize(vm.getSize() - cloudlet.getCloudletFileSize());//setting the new size once cloudlet is assigned to this vm.
						vmIndex = (vmIndex + 1) % getVmsCreatedList().size();
						cloudlet.setVmId(vm.getId());//assigning the cloudlets to the vm.
						break;
					}
				}
				if(cloudlet.getVmId() == -1) {
					Log.printLine("Cloudlet "+cloudlet.getCloudletId()+" could not be assigned to any vm.");
					getCloudletList().remove(cloudlet.getCloudletId());
					break;
				}
			} else { // submit to the specific vm
				vm = VmList.getById(getVmsCreatedList(), cloudlet.getVmId());
				if (vm == null) { // vm was not created
					Log.printLine(CloudSim.clock() + ": " + getName() + ": Postponing execution of cloudlet "
							+ cloudlet.getCloudletId() + ": bount VM not available");
					continue;
				}
			}

			Log.printLine(CloudSim.clock() + ": " + getName() + ": Sending cloudlet "
					+ cloudlet.getCloudletId() + " to VM #" + vm.getId());
			cloudlet.setVmId(vm.getId());
			sendNow(getVmsToDatacentersMap().get(vm.getId()), CloudSimTags.CLOUDLET_SUBMIT, cloudlet);
			cloudletsSubmitted++;
			//vmIndex = (vmIndex + 1) % getVmsCreatedList().size();
			getCloudletSubmittedList().add(cloudlet);
			successfullySubmitted.add(cloudlet);
		}

		// remove submitted cloudlets from waiting list
		getCloudletList().removeAll(successfullySubmitted);
	}

}
