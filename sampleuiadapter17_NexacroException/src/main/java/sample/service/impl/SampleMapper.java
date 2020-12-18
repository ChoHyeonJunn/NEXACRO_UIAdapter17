package sample.service.impl;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("sampleMapper")
public interface SampleMapper {
	List<Map<String, Object>> selectSampleList(Map<String, String> searchInfo) throws Exception;

	void insertSampleList(Map<String, Object> sampleData) throws Exception;
	void updateSampleList(Map<String, Object> sampleData) throws Exception;
	void deleteSampleList(Map<String, Object> sampleData) throws Exception;
}
