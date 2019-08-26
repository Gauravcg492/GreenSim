package org.cloudbus.cloudsim.examples.infrastructure;

import java.util.ArrayList;
import java.util.List;

public class Console {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		List<String> conf;
		String workload;

		if (args.length >= 1 && args[0] != null && !args[0].isEmpty()) {
			conf = getConf(args[0]);
			if (args.length >= 2 && args[1] != null && !args[1].isEmpty()) {
				workload = args[1];
			}
			else
			{
				workload = "";
			}
		}
		//Runner runner = new Runner();
		//runner.start(conf,workload);
		//new ReadConf("/home/ubuntu/Downloads/My downloads/web/GreenSim.conf");
		new ReadConf("C:\\Users\\user\\Downloads\\Testing\\GreenSim.conf");

	}

	/**
	 * Function to split the conf argument given
	 */
	public static List<String> getConf(String arg){
		List<String> conf = new ArrayList<String>();
		String[] temp = arg.split(",");
		for (String string : temp) {
			if(string != "[start" && string!="end]") {
				conf.add(string);
			}
		}
		return conf;
		
	}
}

