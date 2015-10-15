package pocjaxb.datetimeadapter;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.unitils.reflectionassert.ReflectionAssert.*;

import org.junit.Before;
import org.junit.Test;

import common.utils.CommonUtils;

public class DatetimeAdapterTest {

	private final String QUEUE_ID = UUID.randomUUID().toString();
	private String TEST_QUEUES_STREAM;
	private QueuesModel testQueues;
	
	@Before
	public void initialize() {
		
		Date datetime = new Date();
		
		TEST_QUEUES_STREAM = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<Queues>"
				+ 	"<Queue>"
				+ 		"<QueueName>" + QUEUE_ID + "</QueueName>"
				+ 		"<CreateTime>" + datetime.getTime() + "</CreateTime>"
				+ 		"<LastModifyTime>" + datetime.getTime() + "</LastModifyTime>"
				+ 		"<InactiveMessages>1</InactiveMessages>"
				+ 		"<ActiveMessages>10</ActiveMessages>"
				+ 		"<DelayMessages>2</DelayMessages>"
				+ 		"<MaximumMessageSize>20</MaximumMessageSize>"
				+ 		"<MessageRetentionPeriod>0</MessageRetentionPeriod>"
				+ 		"<PollingWaitSeconds>0</PollingWaitSeconds>"
				+ 	"</Queue>"
				+ 	"<NextMarker>nextmarker</NextMarker>"
				+ "</Queues>";
		
		testQueues = new QueuesModel();
		testQueues.setNextMarker("nextmarker");
		QueueModel queue = new QueueModel();
		queue.setActiveMessages(10);
		queue.setCreateTime(datetime);
		queue.setDelayMessages(2);
		queue.setInactiveMessages(1);
		queue.setLastModifyTime(datetime);
		queue.setMaximumMessageSize(20);
		queue.setMessageRetentionPeriod(0);
		queue.setPollingWaitSeconds(0);
		queue.setQueueName(QUEUE_ID);
		testQueues.setQueues(Arrays.asList(queue));
	}
	
	@Test
	public void testMarshalQueues() {
		assertEquals(
				TEST_QUEUES_STREAM, 
				CommonUtils.marshal(testQueues));
	}
	
	@Test
	public void testUnmarshalQueues() {
		assertReflectionEquals(
				testQueues,
				CommonUtils.unmarshal(new ByteArrayInputStream(TEST_QUEUES_STREAM.getBytes()), QueuesModel.class));
	}
	
}
