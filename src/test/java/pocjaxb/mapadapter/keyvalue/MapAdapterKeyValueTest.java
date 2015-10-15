package pocjaxb.mapadapter.keyvalue;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.unitils.reflectionassert.ReflectionAssert.*;
import common.utils.CommonUtils;

public class MapAdapterKeyValueTest {

	private final String CONTAINER_ID = UUID.randomUUID().toString();
	private String TEST_CONTAINER_STREAM;
	private ContainerModel testContainer;
	
	@Before
	public void initialize() {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		Date datetime = new Date();
		
		TEST_CONTAINER_STREAM = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<Container>"
				+ 	"<Name>" + CONTAINER_ID + "</Name>"
				+ 	"<Properties>"
				+ 		"<Last-Modified>" + format.format(datetime) + "</Last-Modified>"
				+ 		"<Etag>backup</Etag>"
				+ 		"<LeaseStatus>Released</LeaseStatus>"
				+ 		"<LeaseState>Normal</LeaseState>"
				+ 		"<LeaseDuration>10</LeaseDuration>"
				+ 	"</Properties>"
				+ 	"<metadata>"
				+ 		"<owner>johnson</owner>"
				+		"<category>test</category>"
				+ 	"</metadata>"
				+ "</Container>";
		
		testContainer = new ContainerModel();
		Map<String, Object> metadataMap = new HashMap<String, Object>();
		metadataMap.put("category", "test");
		metadataMap.put("owner", "johnson");
		testContainer.setMetadata(metadataMap);
		testContainer.setName(CONTAINER_ID);
		BasePropertiesModel properties = new BasePropertiesModel();
		properties.seteTag("backup");
		properties.setLastModified(format.format(datetime));
		properties.setLeaseDuration("10");
		properties.setLeaseState("Normal");
		properties.setLeaseStatus("Released");
		testContainer.setProperties(properties);
	}
	
	@Test
	public void testMarshalQueues() {
		assertEquals(
				TEST_CONTAINER_STREAM,
				CommonUtils.marshal(testContainer));
	}
	
	@Test
	public void testUnmarshalQueues() {
		assertReflectionEquals(
				testContainer,
				CommonUtils.unmarshal(new ByteArrayInputStream(TEST_CONTAINER_STREAM.getBytes()), ContainerModel.class));
	}
	
}
