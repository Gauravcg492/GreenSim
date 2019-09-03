package org.cloudbus.cloudsim.infrastructure.lists;

import java.util.List;

import org.cloudbus.cloudsim.infrastructure.ExtHost;
import org.cloudbus.cloudsim.lists.PeList;

public class ExtHostList {
	
	/**
	 * Gets the Machine object for a particular ID.
	 * 
	 * @param <T> the generic type
	 * @param hostList the host list
	 * @param id the host ID
	 * @return the Machine object or <tt>null</tt> if no machine exists
	 * @see gridsim.Machine
	 * @pre id >= 0
	 * @post $none
	 */
	public static <T extends ExtHost> T getById(List<T> hostList, int id) {
		for (T host : hostList) {
			if (host.getId() == id) {
				return host;
			}
		}
		return null;
	}

	/**
	 * Gets the total number of PEs for all Machines.
	 * 
	 * @param <T> the generic type
	 * @param hostList the host list
	 * @return number of PEs
	 * @pre $none
	 * @post $result >= 0
	 */
	public static <T extends ExtHost> int getNumberOfPes(List<T> hostList) {
		int numberOfPes = 0;
		for (T host : hostList) {
			numberOfPes += host.getPeList().size();
		}
		return numberOfPes;
	}

	/**
	 * Gets the total number of <tt>FREE</tt> or non-busy PEs for all Machines.
	 * 
	 * @param <T> the generic type
	 * @param hostList the host list
	 * @return number of PEs
	 * @pre $none
	 * @post $result >= 0
	 */
	public static <T extends ExtHost> int getNumberOfFreePes(List<T> hostList) {
		int numberOfFreePes = 0;
		for (T host : hostList) {
			numberOfFreePes += PeList.getNumberOfFreePes(host.getPeList());
		}
		return numberOfFreePes;
	}

	/**
	 * Gets the total number of <tt>BUSY</tt> PEs for all Machines.
	 * 
	 * @param <T> the generic type
	 * @param hostList the host list
	 * @return number of PEs
	 * @pre $none
	 * @post $result >= 0
	 */
	public static <T extends ExtHost> int getNumberOfBusyPes(List<T> hostList) {
		int numberOfBusyPes = 0;
		for (T host : hostList) {
			numberOfBusyPes += PeList.getNumberOfBusyPes(host.getPeList());
		}
		return numberOfBusyPes;
	}

	/**
	 * Gets a Machine with free Pe.
	 * 
	 * @param <T> the generic type
	 * @param hostList the host list
	 * @return a machine object or <tt>null</tt> if not found
	 * @pre $none
	 * @post $none
	 */
	public static <T extends ExtHost> T getHostWithFreePe(List<T> hostList) {
		return getHostWithFreePe(hostList, 1);
	}

	/**
	 * Gets a Machine with a specified number of free Pe.
	 * 
	 * @param <T> the generic type
	 * @param hostList the host list
	 * @param pesNumber the pes number
	 * @return a machine object or <tt>null</tt> if not found
	 * @pre $none
	 * @post $none
	 */
	public static <T extends ExtHost> T getHostWithFreePe(List<T> hostList, int pesNumber) {
		for (T host : hostList) {
			if (PeList.getNumberOfFreePes(host.getPeList()) >= pesNumber) {
				return host;
			}
		}
		return null;
	}

	/**
	 * Sets the particular Pe status on a Machine.
	 * 
	 * @param <T> the generic type
	 * @param hostList the host list
	 * @param status Pe status, either <tt>Pe.FREE</tt> or <tt>Pe.BUSY</tt>
	 * @param hostId the host id
	 * @param peId the pe id
	 * @return <tt>true</tt> if the Pe status has changed, <tt>false</tt> otherwise (Machine id or
	 *         Pe id might not be exist)
	 * @pre machineID >= 0
	 * @pre peID >= 0
	 * @post $none
	 */
	public static <T extends ExtHost> boolean setPeStatus(List<T> hostList, int status, int hostId, int peId) {
		T host = getById(hostList, hostId);
		if (host == null) {
			return false;
		}
		return host.setPeStatus(peId, status);
	}
	
	public static <T extends ExtHost> int size(List<T> hostList) {
		return hostList.size();
	}
	
//	/**
//	 * @param l_host the l_host to set
//	 */
//	public void setL_host(Host l_host) {
//		this.l_host = l_host;
//	}
	
//	public static int isFreeHost(Host host) {
//		return 
//	}
//	
//	/**
//	 * Gets the total number of <tt>FREE</tt> Hosts.
//	 * 
//	 * @param <T> the generic type
//	 * @param hostList the list of existing hosts
//	 * @return total number of free Hosts
//	 * @pre $none
//	 * @post $result >= 0
//	 */
//	public static int getNumberOfFreeHosts(List<T> hostList) {
//		int numberOfFreeHosts = 0;
//		for (T host : hostList) {
//			numberOfFreeHosts += PeList.getNumberOfFreePes(host.getPeList());
//		}
//		return numberOfFreePes;
//	}
//	/**
//	 * Sets the status of PEs of a host to FAILED or FREE.
//	 * 
//	 * @param peList the host's PE list to be set as failed or free
//	 * @param failed true if the host's PEs have to be set as FAILED, false
//         * if they have to be set as FREE.
//	 */
//	public static <T extends Host> void setStatusFailed(List<T> hostList, boolean failed) {
//		// a loop to set the status of all the Hosts in this machine
//		for (Host host : hostList) {
//			if (failed) {
//				host.setStatus(Host.FAILED);
//			} else {
//				host.setStatus(Host.FREE);
//			}
//		}
//	}
}
