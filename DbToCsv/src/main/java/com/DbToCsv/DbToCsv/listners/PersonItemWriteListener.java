//package com.DbToCsv.DbToCsv.listners;
//
//import com.DbToCsv.DbToCsv.model.Candidate;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.batch.core.step.item.Chunk;
//
//import javax.batch.api.chunk.listener.ItemWriteListener;
//import java.util.List;
//
//public class PersonItemWriteListener implements ItemWriteListener<Candidate> {
//
//    public static final Logger logger = LoggerFactory.getLogger(PersonItemReadListener.class);
//
//    @Override
//    public void beforeWrite(Chunk<? extends Candidate> items) {
//        logger.info("Writing started persons list : " + items);
//    }
//
//    @Override
//    public void afterWrite(Chunk<? extends Candidate> items) {
//        logger.info("Writing completed persons list : " + items);
//        ;
//    }
//
//    @Override
//    public void onWriteError(Exception e, Chunk<? extends Candidate> items) {
//        logger.error("Error in reading the person records " + items);
//        logger.error("Error in reading the person records " + e);
//    }
//
//    @Override
//    public void beforeWrite(List<Object> list) throws Exception {
//
//    }
//
//    @Override
//    public void afterWrite(List<Object> list) throws Exception {
//
//    }
//
//    @Override
//    public void onWriteError(List<Object> list, Exception e) throws Exception {
//
//    }
//}