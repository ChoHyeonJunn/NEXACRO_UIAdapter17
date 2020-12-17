package sample.service;

import java.util.List;
import java.util.Map;

public interface SampleService {
	// 데이터 조회
	List<Map<String, Object>> selectSampleList(Map<String, String> searchInfo);

	// 데이터 추가/수정/삭제
	void updateSampleList(List<Map<String, Object>> sampleList);
}
