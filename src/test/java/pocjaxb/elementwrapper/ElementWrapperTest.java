package pocjaxb.elementwrapper;

import static org.junit.Assert.assertEquals;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import pocjaxb.namespace.MessageModel;
import common.utils.CommonUtils;

public class ElementWrapperTest {

	private String TEST_BLOCKLIST_STREAM;
	private BlockListModel testBlockList;
	
	@Before
	public void initialize() {
		
		final String COMMITTED_BLOCK_ID = UUID.randomUUID().toString();
		final String UNCOMMITTED_BLOCK_ID = UUID.randomUUID().toString();
		
		TEST_BLOCKLIST_STREAM = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<BlockList>"
				+ "<CommittedBlocks>"
				+ "<Block>"
				+ "<Name>" + COMMITTED_BLOCK_ID + "</Name>"
				+ "<Size>100</Size>"
				+ "</Block>"
				+ "</CommittedBlocks>"
				+ "<UncommittedBlocks>"
				+ "<Block>"
				+ "<Name>" + UNCOMMITTED_BLOCK_ID + "</Name>"
				+ "<Size>200</Size>"
				+ "</Block>"
				+ "</UncommittedBlocks>"
				+ "</BlockList>";
		
		testBlockList = new BlockListModel();
		BlockModel blockModel = new BlockModel();
		blockModel.setName(COMMITTED_BLOCK_ID);
		blockModel.setSize(100);
		testBlockList.setCommittedBlocks(Arrays.asList(blockModel));
		blockModel = new BlockModel();
		blockModel.setName(UNCOMMITTED_BLOCK_ID);
		blockModel.setSize(200);
		testBlockList.setUncommittedBlocks(Arrays.asList(blockModel));
	}
	
	@Test
	public void testMarshalBlockList() {
		assertEquals(TEST_BLOCKLIST_STREAM, CommonUtils.marshal(testBlockList));
	}
	
	@Test
	public void testUnmarshalBlockList() {
		assertReflectionEquals(
				testBlockList, 
				CommonUtils.unmarshal(new ByteArrayInputStream(TEST_BLOCKLIST_STREAM.getBytes()), BlockListModel.class));
	}
	
}
