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
	public List<Map<String, Object>> selectSampleList(Map<String, String> searchInfo) {
		return sampleMapper.selectSampleList(searchInfo);
	}

	// 데이터 추가/수정/삭제
	@Override
	public void updateSampleList(List<Map<String, Object>> sampleList) {
		int size = sampleList.size();

		for (int i = 0; i < size; i++) {
			Map<String, Object> sample = sampleList.get(i);

			int dataRowType = Integer.parseInt(String.valueOf(sample.get(DataSetRowTypeAccessor.NAME)));

			if (dataRowType == DataSet.ROW_TYPE_INSERTED) {
				String id = "";

				try {
					id = egovIdGnrService.getNextStringId();

					sample.replace("ID", id);

					sampleMapper.insertSampleList(sample);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (dataRowType == DataSet.ROW_TYPE_UPDATED) {
				sampleMapper.updateSampleList(sample);

			} else if (dataRowType == DataSet.ROW_TYPE_DELETED) {
				sampleMapper.deleteSampleList(sample);

			}
		}
	}

}
