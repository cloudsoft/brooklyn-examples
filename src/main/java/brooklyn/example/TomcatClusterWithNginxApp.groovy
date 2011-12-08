package brooklyn.example

import brooklyn.entity.basic.AbstractApplication
import brooklyn.entity.basic.Attributes
import brooklyn.entity.group.Cluster
import brooklyn.entity.proxy.nginx.NginxController
import brooklyn.entity.webapp.ControlledDynamicWebAppCluster
import brooklyn.entity.webapp.tomcat.TomcatServer
import brooklyn.launcher.BrooklynLauncher
import brooklyn.location.Location
import brooklyn.location.basic.LocalhostMachineProvisioningLocation

class TomcatClusterWithNginxApp extends AbstractApplication {
	
	NginxController nginxController = new NginxController(
		domain : "brooklyn.geopaas.org",
		port : 8000,
		portNumberSensor : Attributes.HTTP_PORT)

	ControlledDynamicWebAppCluster cluster = new ControlledDynamicWebAppCluster(
		controller : nginxController,
		webServerFactory : { properties -> new TomcatServer(properties) },
		owner : this,
		initialSize: 2,
		newEntity: { properties -> new TomcatServer(properties) },
		httpPort: 8080, war: "/path/to/booking-mvc.war")

    public static void main(String[] argv) {
        TomcatClusterWithNginxApp demo = new TomcatClusterWithNginxApp(displayName : "tomcat cluster with nginx example")
        BrooklynLauncher.manage(demo)
        
        Location loc = new LocalhostMachineProvisioningLocation(count: 3)
        demo.start([loc])
    }
}


