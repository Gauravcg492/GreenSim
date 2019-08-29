package org.cloudbus.cloudsim.examples.infrastructure;

import java.util.ArrayList;
import java.util.List;

import org.cloudbus.cloudsim.Log;

public class Console {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		List<String> conf = null;
		String workload = "";

		if (args.length >= 1 && args[0] != null && !args[0].isEmpty()) {
			conf = getConf(args[0]);
			for(int i=0;i<conf.size();i++) {
				Log.printLine(conf.get(i));
			}
			if (args.length >= 2 && args[1] != null && !args[1].isEmpty()) {
				workload = args[1];
			}
		}
		//Runner runner = new Runner();
		//runner.start(conf,workload);
		//new ReadConf("/home/ubuntu/Downloads/My downloads/web/GreenSim.conf");
		//new ReadConf("C:\\Users\\user\\Downloads\\Testing\\GreenSim.conf");
		Example3 ex = new Example3();
		ex.start(conf, workload);

	}

	/**
	 * Function to split the conf argument given
	 */
	public static List<String> getConf(String arg){
		List<String> conf = new ArrayList<String>();
		String[] temp = arg.split(",");
		for (String string : temp) {
			if(!string.equals("[start") && !string.equals("end]")) {
				conf.add(string);
			}
		}
		return conf;
		
	}
}

