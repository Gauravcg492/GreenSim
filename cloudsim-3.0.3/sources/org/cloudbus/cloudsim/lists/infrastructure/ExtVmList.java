package org.cloudbus.cloudsim.lists.infrastructure;

import java.util.List;

import org.cloudbus.cloudsim.infrastructure.ExtVm;

public class ExtVmList {

	/**
	 * Return a reference to a Vm object from its ID.
	 * 
	 * @param id ID of required VM
	 * @param vmList the vm list
	 * @return Vm with the given ID, $null if not found
	 * @pre $none
	 * @post $none
	 */
	public static <T extends ExtVm> T getById(List<T> vmList, int id) {
		for (T vm : vmList) {
			if (vm.getId() == id) {
				return vm;
			}
		}
		return null;
	}

	/**
	 * Return a reference to a Vm object from its ID and user ID.
	 * 
	 * @param id ID of required VM
	 * @param userId the user ID
	 * @param vmList the vm list
	 * @return Vm with the given ID, $null if not found
	 * @pre $none
	 * @post $none
	 */
	public static <T extends ExtVm> T getByIdAndUserId(List<T> vmList, int id, int userId) {
		for (T vm : vmList) {
			if (vm.getId() == id && vm.getUserId() == userId) {
				return vm;
			}
		}
		return null;
	}
	
}
