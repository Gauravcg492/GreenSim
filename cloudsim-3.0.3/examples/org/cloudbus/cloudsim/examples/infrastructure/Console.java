package org.cloudbus.cloudsim.examples.infrastructure;

public class Console {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		String conf;
		String workload;

		if (args.length >= 1 && args[0] != null && !args[0].isEmpty()) {
			conf = args[0];
			if (args.length >= 2 && args[1] != null && !args[1].isEmpty()) {
				workload = args[1];
			}
			else
			{
				workload = "";
			}
		}
		else {
			conf = "";
			workload = "";
		}
		//Runner runner = new Runner();
		//runner.start(conf,workload);
		//new ReadConf("/home/ubuntu/Downloads/My downloads/web/GreenSim.conf");
		new ReadConf("C:\\Users\\user\\Downloads\\Testing\\GreenSim.conf");

	}

}

