package service;


import static org.junit.Assert.*;


import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eegApp.model.ArtefactType;
import eegApp.model.EEGSegment;
import eegApp.service.EEGSegmentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/application-context.xml" })
public class EEGSegmentServiceTest {
	
	private static final String ID_SEGMENT="SOME_ID2";
	
	@Autowired
	private EEGSegmentService service;
	
	private EEGSegment segment;
	private EEGSegment segment2;
	
	@Before
	public void setUp(){
		
		segment=new EEGSegment();
		segment2=new EEGSegment();
		segment.setArtefactType(ArtefactType.MUSCULAR);
		segment.setId(ID_SEGMENT);
		segment2.setId(ID_SEGMENT);
		segment.setValues(Arrays.asList(0.2,0.4,0.6));
		
	}
	@Test
	@Rollback
	public void testFindAll(){
		
		service.save(segment);
		service.save(segment2);
		List<EEGSegment> segmentsFromDB=service.findAll();
		System.out.println(segmentsFromDB);
		
	}
	@Test
	@Rollback
	public void testSave(){
		service.save(segment);
		segment.setArtefactType(ArtefactType.OCULAR);
		service.save(segment);
		List<EEGSegment> segmentsFromDB=service.findAll();
		System.out.println("-----------"+segmentsFromDB);
		
	}
	
	@Test
	@Rollback
	public void testfindById(){
		
		service.save(segment);
		
		EEGSegment segmentFromDB=service.findById(ID_SEGMENT);
		System.out.println("++++"+segmentFromDB);
		assertEquals(segmentFromDB,segment);
	
	}

}
