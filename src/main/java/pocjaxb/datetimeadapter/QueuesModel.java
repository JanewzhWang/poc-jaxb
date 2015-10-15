package pocjaxb.datetimeadapter;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Queues")
@XmlAccessorType(XmlAccessType.FIELD)
public class QueuesModel {
	
	@XmlElement(name="Queue")
	private List<QueueModel> queues;
	@XmlElement(name="NextMarker")
	private String nextMarker;

	public List<QueueModel> getQueues() {
		return queues;
	}
	public void setQueues(List<QueueModel> list) {
		this.queues = list;
	}
	public String getNextMarker() {
		return nextMarker;
	}
	public void setNextMarker(String nextMarker) {
		this.nextMarker = nextMarker;
	}
	
}
