package eegApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import eegApp.model.EEGSegment;

@Repository
public interface EEGSegmentRepository   extends MongoRepository<EEGSegment,String>{

}
