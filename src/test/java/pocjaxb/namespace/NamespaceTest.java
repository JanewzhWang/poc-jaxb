package pocjaxb.namespace;

import java.io.ByteArrayInputStream;
import java.util.UUID;
import static org.junit.Assert.*;
import static org.unitils.reflectionassert.ReflectionAssert.*;
import javax.xml.bind.JAXBException;
import org.junit.Before;
import org.junit.Test;
import common.utils.CommonUtils;
import pocjaxb.namespace.MessageModel;

/*
 * change xmlns:ns2 to xmlns: add namespace for all the fields
 */
public class NamespaceTest  {

	private final String MESSAGE_ID = UUID.randomUUID().toString();
	private String TEST_MESSAGE_STREAM; 
	private MessageModel testMessage;
	
	@Before
	public void initialize() {
		
		TEST_MESSAGE_STREAM = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<Message xmlns=\"http://mqs.aliyuncs.com/doc/v1/\">"
				+ 	"<MessageId>" + MESSAGE_ID + "</MessageId>"
				+ 	"<MessageBody>message body encoded by BASE64</MessageBody>"
				+ 	"<MessageBodyMD5>message body encoded by MD5</MessageBodyMD5>"
				+ 	"<DelaySeconds>100</DelaySeconds>"
				+ 	"<Priority>1</Priority>"
				+ 	"<ReceiptHandle>handler</ReceiptHandle>"
				+ 	"<DequeueCount>1</DequeueCount>"
				+ "</Message>";
		
		testMessage = new MessageModel();
		testMessage.setDelaySeconds(100);
		testMessage.setDequeueCount(1);
		testMessage.setMessageBody("message body encoded by BASE64");
		testMessage.setMessageBodyMD5("message body encoded by MD5");
		testMessage.setMessageId(MESSAGE_ID);
		testMessage.setPriority(1);
		testMessage.setReceiptHandle("handler");
	}
	
	@Test
	public void marshalMessage() throws JAXBException {
		assertEquals(TEST_MESSAGE_STREAM, CommonUtils.marshal(testMessage));
	}
	
	@Test
	public void unmarshalMessage() {
		assertReflectionEquals(
				testMessage, 
				CommonUtils.unmarshal(new ByteArrayInputStream(TEST_MESSAGE_STREAM.getBytes()), MessageModel.class));
	}
	
}
