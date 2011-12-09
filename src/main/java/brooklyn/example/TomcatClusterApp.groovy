package brooklyn.example

import brooklyn.entity.basic.AbstractApplication
import brooklyn.entity.webapp.DynamicWebAppCluster
import brooklyn.entity.webapp.tomcat.TomcatServer
import brooklyn.launcher.BrooklynLauncher
import brooklyn.location.basic.jclouds.JcloudsLocation
import brooklyn.location.basic.jclouds.JcloudsLocationFactory

class TomcatClusterApp extends AbstractApplication {
	
	//For this to successfully run on AWS you will need to replace the placeholder credentials; indentity, credential,
	//sshPrivateKey and sshPublicKey. You will also need to specify your AMI image ID and a security group.

	DynamicWebAppCluster cluster = new DynamicWebAppCluster(
		owner : this,
		initialSize: 2,
		newEntity: { properties -> new TomcatServer(properties) },
		httpPort: 8080, 
		war: "/path/to/booking-mvc.war")

	public static void main(String[] argv) {
		TomcatClusterApp demo = new TomcatClusterApp(displayName : "tomcat cluster example")
		BrooklynLauncher.manage(demo)

		JcloudsLocationFactory locFactory = new JcloudsLocationFactory([
					provider : "aws-ec2",
					identity : "xxxxxxxxxxxxxxxxxxxxxxxxxxx",
                    credential : "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
                    sshPrivateKey : new File("/home/bob/.ssh/id_rsa.private"),
                    sshPublicKey : new File("/home/bob/.ssh/id_rsa.pub"),
					securityGroups:["brooklyn-all"]
				])

		JcloudsLocation loc = locFactory.newLocation("us-west-1")
		demo.start([loc])
	}
}
