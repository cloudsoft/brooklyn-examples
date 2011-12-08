package brooklyn.example

import brooklyn.entity.Entity
import brooklyn.entity.basic.AbstractApplication
import brooklyn.entity.group.Cluster
import brooklyn.entity.group.DynamicFabric
import brooklyn.entity.webapp.DynamicWebAppCluster
import brooklyn.entity.webapp.tomcat.TomcatServer
import brooklyn.launcher.BrooklynLauncher
import brooklyn.location.Location
import brooklyn.location.basic.LocalhostMachineProvisioningLocation


class TomcatFabricApp extends AbstractApplication {
	
	Closure webClusterFactory = { Map flags, Entity owner ->
		return new DynamicWebAppCluster(
				newEntity: { properties -> new TomcatServer(properties) },
				owner : owner
		)
	}

	DynamicFabric fabric = new DynamicFabric(
			displayName : "WebFabric",
			displayNamePrefix : "",
			displayNameSuffix : " web cluster",
			newEntity : webClusterFactory,
			this,
			initialSize: 2,
			httpPort: 8080, war: "/path/to/booking-mvc.war")
	
    public static void main(String[] argv) {
        TomcatFabricApp demo = new TomcatFabricApp(displayName : "tomcat example")
        BrooklynLauncher.manage(demo)
        
        Location loc = new LocalhostMachineProvisioningLocation(count: 3)
        Location loc2 = new LocalhostMachineProvisioningLocation(count: 3)
        demo.start([loc, loc2])
    }
}
