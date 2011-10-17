package brooklyn.demo


import java.util.List
import java.util.Map

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import brooklyn.entity.basic.AbstractApplication
import brooklyn.entity.group.Cluster
import brooklyn.entity.nosql.gemfire.GemfireFabric
import brooklyn.entity.nosql.gemfire.GemfireServer
import brooklyn.launcher.BrooklynLauncher
import brooklyn.location.Location

class GemfireExample extends AbstractApplication {
    
    // FIXME Work in progress; gemfire entities not yet completed...
    
    // FIXME Change GEMFIRE_INSTALL_DIR
    // FIXME Change GemfireSetup's start (should it be "&"), and isRunning
    // FIXME Change GemfireServer.waitForEntityStart, to wait for service to really be up? Rather than just process
    
    public static final Logger LOG = LoggerFactory.getLogger(GemfireExample)
    
    private static final File GEMFIRE_CONF_FILE = new File("src/main/resources/gemfire/server-conf.xml")
    private static final File GEMFIRE_JAR_FILE = new File("src/main/resources/gemfire/springtravel-datamodel.jar")
    private static final String GEMFIRE_INSTALL_DIR = "/Users/aled/temp/gemfire"//"/gemfire"
    
    public static final List<String> DEFAULT_LOCATIONS = [ Locations.LOCALHOST, Locations.LOCALHOST ]

    final GemfireFabric fabric
    
    GemfireExample(Map props=[:]) {
        super(props)
        
        fabric = new GemfireFabric(owner:this)
        fabric.setConfig(GemfireServer.INSTALL_DIR, GEMFIRE_INSTALL_DIR)
        fabric.setConfig(GemfireServer.CONFIG_FILE, GEMFIRE_CONF_FILE)
        fabric.setConfig(GemfireServer.JAR_FILE, GEMFIRE_JAR_FILE)
        fabric.setConfig(GemfireServer.SUGGESTED_HUB_PORT, 11111)
        fabric.setConfig(Cluster.INITIAL_SIZE, 1)
        //fabric.setConfig(LICENSE, GEMFIRE_LICENSE_FILE)
    }
    
    public static void main(String[] argv) {
        // Parse arguments for location ids and resolve each into a location
        List<Location> locations = Locations.getLocationsById(Arrays.asList(argv) ?: DEFAULT_LOCATIONS)

        // Initialize the Spring Travel application entity
        GemfireExample app = new GemfireExample(name:'gemfire-wide-area-example',
                displayName:'Gemfire Wide-Area Example')

        BrooklynLauncher.manage(app)
        app.start(locations)
    }
}
