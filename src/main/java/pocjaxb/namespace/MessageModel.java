package pocjaxb.namespace;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Message", namespace="http://mqs.aliyuncs.com/doc/v1/")
@XmlAccessorType(XmlAccessType.FIELD)
public class MessageModel {

	@XmlElement(name="MessageId", namespace="http://mqs.aliyuncs.com/doc/v1/")
	private String messageId;
	@XmlElement(name="MessageBody", namespace="http://mqs.aliyuncs.com/doc/v1/")
	private String messageBody;		
	@XmlElement(name="MessageBodyMD5", namespace="http://mqs.aliyuncs.com/doc/v1/")
	private String messageBodyMD5;	
	@XmlElement(name="DelaySeconds", namespace="http://mqs.aliyuncs.com/doc/v1/")
	private Integer delaySeconds;
	@XmlElement(name="Priority", namespace="http://mqs.aliyuncs.com/doc/v1/")
	private Integer priority;
	@XmlElement(name="ReceiptHandle", namespace="http://mqs.aliyuncs.com/doc/v1/")
	private String receiptHandle;
	@XmlElement(name="DequeueCount", namespace="http://mqs.aliyuncs.com/doc/v1/")
	private Integer dequeueCount;
	
	public String getMessageBody() {
		return messageBody;
	}
	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}
	public Integer getDelaySeconds() {
		return delaySeconds;
	}
	public void setDelaySeconds(Integer delaySeconds) {
		this.delaySeconds = delaySeconds;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getMessageBodyMD5() {
		return messageBodyMD5;
	}
	public void setMessageBodyMD5(String messageBodyMD5) {
		this.messageBodyMD5 = messageBodyMD5;
	}
	public String getReceiptHandle() {
		return receiptHandle;
	}
	public void setReceiptHandle(String receiptHandle) {
		this.receiptHandle = receiptHandle;
	}
	public Integer getDequeueCount() {
		return dequeueCount;
	}
	public void setDequeueCount(Integer dequeueCount) {
		this.dequeueCount = dequeueCount;
	}
}
