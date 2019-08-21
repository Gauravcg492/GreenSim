package org.cloudbus.cloudsim.examples.infrastructure;

/*
 * Title:        CloudSim Toolkit
 * Description:  CloudSim (Cloud Simulation) Toolkit for Modeling and Simulation
 *               of Clouds
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2009, The University of Melbourne, Australia
 */


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.infrastructure.ExtVm;
import org.cloudbus.cloudsim.lists.VmList;

/**
 * A simple example showing how to create
 * a datacenter with one host and run two
 * cloudlets on it. The cloudlets run in
 * VMs with the same MIPS requirements.
 * The cloudlets will take the same time to
 * complete the execution.
 */
public class Example2 {

	/** The cloudlet list. */
	private static List<Cloudlet> cloudletList;

	/** The vmlist. */
	private static List<ExtVm> vmlist;

	/**
	 * Creates main() to run this example
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Log.printLine("Starting CloudSimExample2...");

	        try {
	        	// First step: Initialize the CloudSim package. It should be called
	            	// before creating any entities.
	            	int num_user = 1;   // number of cloud users
	            	Calendar calendar = Calendar.getInstance();
	            	boolean trace_flag = false;  // mean trace events

	            	// Initialize the CloudSim library
	            	CloudSim.init(num_user, calendar, trace_flag);

	            	// Second step: Create Datacenters
	            	//Datacenters are the resource providers in CloudSim. We need at list one of them to run a CloudSim simulation
	            	//@SuppressWarnings("unused")
					//Conf_reader r0 = new Conf_reader();
	            	CreateInfrastructure r0 = new CreateInfrastructure();
					//Datacenter dc = r0.createInfrastructure("/home/ubuntu/Documents/GreenSim/CloudSim_Granularity/modules/cloudsim-examples/src/main/java/org/cloudbus/cloudsim/examples/GreenSim.conf");
					Datacenter dc = r0.createInfrastructure("C:\\Users\\user\\Desktop\\Work\\GreenSim\\cloudsim-3.0.3\\examples\\org\\cloudbus\\cloudsim\\examples\\infrastructure\\Greensim.conf");

	            	//Third step: Create Broker
	            	DatacenterBroker broker = createBroker();
	            	int brokerId = broker.getId();

	            	//Fourth step: Create one virtual machine
	            	vmlist = new ArrayList<ExtVm>();

	            	//VM description
	            	int vmid = 0;
	            	int mips = 100;
	            	long size = 10000; //image size (MB)
	            	int ram = 512; //vm memory (MB)
	            	long bw = 1000;
	            	int pesNumber = 1; //number of cpus
	            	String vmm = "Xen"; //VMM name

	            	for(int i=0;i<10;i++) {
	            		ExtVm vm = new ExtVm(vmid, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
		            	vmlist.add(vm);
		            	vmid++;
	            	}      	
	       
	            	//submit vm list to the broker
	            	broker.submitVmList(vmlist);


	            	//Fifth step: Create two Cloudlets
	            	cloudletList = new ArrayList<Cloudlet>();

	            	//Cloudlet properties
	            	int id = 0;
	            	pesNumber=1;
	            	long length = 250000;
	            	long fileSize = 300;
	            	long outputSize = 300;
	            	UtilizationModel utilizationModel = new UtilizationModelFull();

	            	for(int i=0; i<20; i++) {
	            		Cloudlet cloudlet = new Cloudlet(id, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
		            	cloudlet.setUserId(brokerId);
		            	cloudletList.add(cloudlet);
		            	id++;
	            	}	            	
	            	
	            	//submit cloudlet list to the broker
	            	broker.submitCloudletList(cloudletList);

	            	// Sixth step: Starts the simulation
	            	CloudSim.startSimulation();


	            	// Final step: Print results when simulation is over
	            	List<Cloudlet> newList = broker.getCloudletReceivedList();
	            	List<Cloudlet> cloudletlist = broker.getCloudletList();
	            	for (Cloudlet cloudlet : cloudletlist) {
	            		int cloudlet_vmid = cloudlet.getVmId();
	            		Log.printLine(cloudlet_vmid);
	            		Log.printLine("imple"+VmList.getById(broker.getVmsCreatedList(), cloudlet_vmid).getHost().getId());
	            	}

	            	CloudSim.stopSimulation();

	            	printCloudletList(newList);

	            	Log.printLine("Example2 finished!");
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	            Log.printLine("The simulation has been terminated due to an unexpected error");
	        }
	    }

	    //We strongly encourage users to develop their own broker policies, to submit vms and cloudlets according
	    //to the specific rules of the simulated scenario
	    private static DatacenterBroker createBroker(){

	    	DatacenterBroker broker = null;
	        try {
			broker = new DatacenterBroker("Broker");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	    	return broker;
	    }

	    /**
	     * Prints the Cloudlet objects
	     * @param list  list of Cloudlets
	     */
	    private static void printCloudletList(List<Cloudlet> list) {
	        int size = list.size();
	        Cloudlet cloudlet;

	        String indent = "    ";
	        Log.printLine();
	        Log.printLine("========== OUTPUT ==========");
	        Log.printLine("Cloudlet ID" + indent + "STATUS" + indent +
	                "Data center ID" + indent + "VM ID" + indent + "Time" + indent + "Start Time" + indent + "Finish Time");

	        DecimalFormat dft = new DecimalFormat("###.##");
	        for (int i = 0; i < size; i++) {
	            cloudlet = list.get(i);
	            Log.print(indent + cloudlet.getCloudletId() + indent + indent);

	            if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS){
	                Log.print("SUCCESS");

	            	Log.printLine( indent + indent + cloudlet.getResourceId() + indent + indent + indent + cloudlet.getVmId() +
	                     indent + indent + dft.format(cloudlet.getActualCPUTime()) + indent + indent + dft.format(cloudlet.getExecStartTime())+
                             indent + indent + dft.format(cloudlet.getFinishTime()));
	            }
	        }

	    }
}
