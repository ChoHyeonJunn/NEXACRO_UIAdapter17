package sample.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nexacro.uiadapter17.spring.core.data.DataSetRowTypeAccessor;
import com.nexacro17.xapi.data.DataSet;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import sample.service.SampleService;

@Service("sampleService")
public class SampleServiceImpl extends EgovAbstractServiceImpl implements SampleService {
	private Logger logger = LoggerFactory.getLogger(SampleServiceImpl.class);

	/** ID Generation **/
	@Resource(name = "egovIdGnrService")
	private EgovIdGnrService egovIdGnrService;

	@Resource(name = "sampleMapper")
	private SampleMapper sampleMapper;

	// 데이터 조회
	@Override
	public List<Map<String, Object>> selectSampleList(Map<String, String> searchInfo) throws Exception {
		return sampleMapper.selectSampleList(searchInfo);
	}

	// 데이터 추가/수정/삭제
	@Override
	public void updateSampleList(List<Map<String, Object>> sampleList) throws Exception {
		int size = sampleList.size();

		for (int i = 0; i < size; i++) {
			Map<String, Object> sample = sampleList.get(i);

			int dataRowType = Integer.parseInt(String.valueOf(sample.get(DataSetRowTypeAccessor.NAME)));

			if (dataRowType == DataSet.ROW_TYPE_INSERTED) {

				/*
				 * 일부러 Exception 발생시키기 ([ 예외상황 2 ] Controller에서 catch 된 Exception 종류 중
				 * NexacroException, FdlException 을 제외한 예외일 경우 화면 콜백 메시지 확인)
				 */
				String id = null;
				sample.replace("ID", id);
				sampleMapper.insertSampleList(sample);

				/*
				 * 일부러 Exception 발생시키기 ([ 예외상황 1 ] Controller에서 catch 된 Exception 종류 중
				 * NexacroException일 경우 화면 콜백 메시지 확인)
				 */
				// String id = null;
				//
				// if (id == null) {
				// throw new NexacroException("id");
				// }
				//
				// id = egovIdGnrService.getNextStringId();
				//
				// sample.replace("ID", id);
				// sampleMapper.insertSampleList(sample);

				/* Execption 테스트용 */
				// String id = null;
				// sample.replace("ID", id);
				// sampleMapper.insertSampleList(sample);

				/* 원문 */
				// try {
				// id = egovIdGnrService.getNextStringId();
				//
				// sample.replace("ID", id);
				//
				// sampleMapper.insertSampleList(sample);
				// } catch (Exception e) {
				// e.printStackTrace();
				// }
			} else if (dataRowType == DataSet.ROW_TYPE_UPDATED) {
				sampleMapper.updateSampleList(sample);

			} else if (dataRowType == DataSet.ROW_TYPE_DELETED) {
				sampleMapper.deleteSampleList(sample);

			}
		}
	}

}
