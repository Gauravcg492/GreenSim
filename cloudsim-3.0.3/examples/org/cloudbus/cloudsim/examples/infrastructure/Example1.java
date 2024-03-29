package org.cloudbus.cloudsim.examples.infrastructure;

//////
///////*
//////* Title:        CloudSim Toolkit
//////* Description:  CloudSim (Cloud Simulation) Toolkit for Modeling and Simulation
//////*               of Clouds
//////* Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
//////*
//////* Copyright (c) 2009, The University of Melbourne, Australia

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.cloudbus.cloudsim.infrastructure.Aisle;
import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.infrastructure.ExtDatacenter;
import org.cloudbus.cloudsim.infrastructure.ExtDatacenterBroker;
import org.cloudbus.cloudsim.infrastructure.ExtDatacenterCharacteristics;
import org.cloudbus.cloudsim.infrastructure.ExtHost;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.infrastructure.Rack;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.infrastructure.ExtVm;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.infrastructure.Zone;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

/**
* A simple example showing how to create a data center with one host and run one cloudlet on it.
*/

public class Example1 {
/** The cloudlet list. */
private static List<Cloudlet> cloudletList;
/** The vmlist. */
private static List<ExtVm> vmlist;

/**
 * Creates main() to run this example.
 *
 * @param args the args
 */
@SuppressWarnings("unused")
public static void main(String[] args) {
	Log.printLine("Starting CloudSimExample1...");

	try {
		// First step: Initialize the CloudSim package. It should be called before creating any entities.
		int num_user = 1; // number of cloud users
		Calendar calendar = Calendar.getInstance(); // Calendar whose fields have been initialized with the current date and time.
		boolean trace_flag = true; // trace events
		CloudSim.init(num_user, calendar, trace_flag);

		// Second step: Create Datacenters
		// Datacenters are the resource providers in CloudSim. We need at
		// list one of them to run a CloudSim simulation
		ExtDatacenter datacenter0 = createDatacenter("Datacenter_0");

		// Third step: Create Broker
		ExtDatacenterBroker broker = createBroker();
		int brokerId = broker.getId();

		// Fourth step: Create one virtual machine
		vmlist = new ArrayList<ExtVm>();

		// VM description
		int vmid = 0;
		int mips = 1000;
		long size = 10000; // image size (MB)
		int ram = 512; // vm memory (MB)
		long bw = 1000;
		int pesNumber = 1; // number of cpus
		String vmm = "Xen"; // VMM name

		// create VM
		ExtVm vm = new ExtVm(vmid, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());

		// add the VM to the vmList
		vmlist.add(vm);
		
		// create VM
		ExtVm vm1 = new ExtVm(++vmid, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());

		// add the VM to the vmList
		vmlist.add(vm1);
					
		// create VM
		ExtVm vm2 = new ExtVm(++vmid, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());

		// add the VM to the vmList
		vmlist.add(vm2);

		// submit vm list to the broker
		broker.submitVmList(vmlist);

		// Fifth step: Create one Cloudlet
		cloudletList = new ArrayList<Cloudlet>();

		// Cloudlet properties
		int id = 0;
		long length = 400000;
		long fileSize = 300;
		long outputSize = 300;
		UtilizationModel utilizationModel = new UtilizationModelFull();

		Cloudlet cloudlet = 
                            new Cloudlet(id, length, pesNumber, fileSize, 
                                    outputSize, utilizationModel, utilizationModel, 
                                    utilizationModel);
		cloudlet.setUserId(brokerId);
		//cloudlet.setVmId(vmid);

		// add the cloudlet to the list
		cloudletList.add(cloudlet);
		
		Cloudlet cloudlet1 = 
                new Cloudlet(++id, length, pesNumber, fileSize, 
                        outputSize, utilizationModel, utilizationModel, 
                        utilizationModel);
		cloudlet1.setUserId(brokerId);
		//cloudlet.setVmId(vmid);
		
		
		// add the cloudlet to the list
		cloudletList.add(cloudlet1);
		
		Cloudlet cloudlet2 = 
                new Cloudlet(++id, length, pesNumber, fileSize, 
                        outputSize, utilizationModel, utilizationModel, 
                        utilizationModel);
		cloudlet2.setUserId(brokerId);
		//cloudlet.setVmId(vmid);
		
		// add the cloudlet to the list
		cloudletList.add(cloudlet2);

		// submit cloudlet list to the broker
		broker.submitCloudletList(cloudletList);

		// Sixth step: Starts the simulation
		CloudSim.startSimulation();

		CloudSim.stopSimulation();

		//Final step: Print results when simulation is over
		List<Cloudlet> newList = broker.getCloudletReceivedList();
		printCloudletList(newList);

		Log.printLine("CloudSimExample1 finished!");
	} catch (Exception e) {
		e.printStackTrace();
		Log.printLine("Unwanted errors happen");
	}
}

/**
 * Creates the datacenter.
 *
 * @param name the name
 *
 * @return the datacenter
 */
private static ExtDatacenter createDatacenter(String name) {

	// Here are the steps needed to create a PowerDatacenter:
	// 1. We need to create a list to store
	// our machine
	List<ExtHost> hostList = new ArrayList<ExtHost>();

	// 2. A Machine contains one or more PEs or CPUs/Cores.
	// In this example, it will have only one core.
	List<Pe> peList = new ArrayList<Pe>();
	List<Pe> peList1 = new ArrayList<Pe>();
	List<Pe> peList2 = new ArrayList<Pe>();
	List<Pe> peList3 = new ArrayList<Pe>();

	int mips = 1000;

	// 3. Create PEs and add these into a list.
	peList.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating
	peList1.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating
	peList2.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating
	peList3.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating
	
	// 4. Create Host with its id and list of PEs and add them to the list
	// of machines
	int hostId = 0;
	int ram = 2048; // host memory (MB)
	long storage = 1000000; // host storage
	int bw = 10000;

	hostList.add(
		new ExtHost(
			hostId,
			new RamProvisionerSimple(ram),
			new BwProvisionerSimple(bw),
			storage,
			peList,
			new VmSchedulerTimeShared(peList)
		)
	); 
	
	
	hostList.add(
			new ExtHost(
				hostId++,
				new RamProvisionerSimple(ram),
				new BwProvisionerSimple(bw),
				storage,
				peList,
				new VmSchedulerTimeShared(peList1)
			)
		);
	
	hostList.add(
			new ExtHost(
				hostId++,
				new RamProvisionerSimple(ram),
				new BwProvisionerSimple(bw),
				storage,
				peList,
				new VmSchedulerTimeShared(peList2)
			)
		);
	
	hostList.add(
			new ExtHost(
				hostId++,
				new RamProvisionerSimple(ram),
				new BwProvisionerSimple(bw),
				storage,
				peList,
				new VmSchedulerTimeShared(peList3)
			)
		);// This is our machine
	
	List<Rack> rackList = new ArrayList<Rack>();
    rackList.add(new Rack(0, hostList));

   
    List<Aisle> aisleList = new ArrayList<Aisle>();
    aisleList.add(new Aisle(0, rackList));

    
    List<Zone> zoneList = new ArrayList<Zone>();
    zoneList.add(new Zone(0, aisleList));


	// 5. Create a DatacenterCharacteristics object that stores the
	// properties of a data center: architecture, OS, list of
	// Machines, allocation policy: time- or space-shared, time zone
	// and its price (G$/Pe time unit).
	String arch = "x86"; // system architecture
	String os = "Linux"; // operating system
	String vmm = "Xen";
	double time_zone = 10.0; // time zone this resource located
	double cost = 3.0; // the cost of using processing in this resource
	double costPerMem = 0.05; // the cost of using memory in this resource
	double costPerStorage = 0.001; // the cost of using storage in this
									// resource
	double costPerBw = 0.0; // the cost of using bw in this resource
	LinkedList<Storage> storageList = new LinkedList<Storage>(); // we are not adding SAN
												// devices by now

	ExtDatacenterCharacteristics characteristics = new ExtDatacenterCharacteristics(
			arch, os, vmm, zoneList, time_zone, cost, costPerMem,
			costPerStorage, costPerBw);

	// 6. Finally, we need to create a PowerDatacenter object.
	ExtDatacenter datacenter = null;
	try {
		datacenter = new ExtDatacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
	} catch (Exception e) {
		e.printStackTrace();
	}

	return datacenter;
}

// We strongly encourage users to develop their own broker policies, to
// submit vms and cloudlets according
// to the specific rules of the simulated scenario
/**
 * Creates the broker.
 *
 * @return the datacenter broker
 */
private static ExtDatacenterBroker createBroker() {
	ExtDatacenterBroker broker = null;
	try {
		broker = new ExtDatacenterBroker("Broker");
	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}
	return broker;
}

/**
 * Prints the Cloudlet objects.
 *
 * @param list list of Cloudlets
 */
private static void printCloudletList(List<Cloudlet> list) {
	int size = list.size();
	Cloudlet cloudlet;

	String indent = "    ";
	Log.printLine();
	Log.printLine("========== OUTPUT ==========");
	Log.printLine("Cloudlet ID" + indent + "STATUS" + indent
			+ "Data center ID" + indent + "VM ID" + indent + "Time" + indent
			+ "Start Time" + indent + "Finish Time");

	DecimalFormat dft = new DecimalFormat("###.##");
	for (int i = 0; i < size; i++) {
		cloudlet = list.get(i);
		Log.print(indent + cloudlet.getCloudletId() + indent + indent);

		if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS) {
			Log.print("SUCCESS");

			Log.printLine(indent + indent + cloudlet.getResourceId()
					+ indent + indent + indent + cloudlet.getVmId()
					+ indent + indent
					+ dft.format(cloudlet.getActualCPUTime()) + indent
					+ indent + dft.format(cloudlet.getExecStartTime())
					+ indent + indent
					+ dft.format(cloudlet.getFinishTime()));
		}
	}
}
}