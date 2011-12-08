package brooklyn.example

import brooklyn.entity.basic.AbstractApplication
import brooklyn.entity.webapp.tomcat.TomcatServer
import brooklyn.launcher.BrooklynLauncher
import brooklyn.location.Location
import brooklyn.location.basic.LocalhostMachineProvisioningLocation


class TomcatServerApp extends AbstractApplication {

	def tomcat = new TomcatServer(this, http: 8080, war: "/path/to/booking.war")

	public static void main(String... args) {
		TomcatServerApp demo = new TomcatServerApp(displayName : "tomcat server example")
		BrooklynLauncher.manage(demo)
		demo.start([new LocalhostMachineProvisioningLocation(count: 1)])
	}

}
