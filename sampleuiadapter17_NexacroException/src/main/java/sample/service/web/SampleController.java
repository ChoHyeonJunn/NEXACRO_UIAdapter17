package sample.service.web;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nexacro.uiadapter17.spring.core.NexacroException;
import com.nexacro.uiadapter17.spring.core.annotation.ParamDataSet;
import com.nexacro.uiadapter17.spring.core.data.NexacroResult;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import sample.service.SampleService;
import sample.service.impl.SampleServiceImpl;

@Controller
public class SampleController {
	private Logger logger = LoggerFactory.getLogger(SampleServiceImpl.class);

	@Autowired
	private MessageSource messageSource;

	@Resource(name = "sampleService")
	private SampleService sampleService;

	/************************************************************************/
	/************************************************************************/
	/************************************************************************/
	@RequestMapping(value = "/selectSampleList.do")
	public NexacroResult selectSampleList(
			@ParamDataSet(name = "input1", required = false) Map<String, String> ds_search) throws Exception {
		logger.debug("ds_search >>>" + ds_search);
		List<Map<String, Object>> sampleList = null;

		sampleList = sampleService.selectSampleList(ds_search);

		NexacroResult result = new NexacroResult();
		result.addDataSet("output1", sampleList);

		return result;
	}

	@RequestMapping(name = "/updateSampleList.do")
	public NexacroResult updateSampleList(@ParamDataSet(name = "input1") List<Map<String, Object>> updateSampleList,
			Locale locale) throws Exception {

		/*
		 * 일부러 Exception 발생시키기 (SampleServiceImple 에서 강제로 exception을 발생시켜 throws한 상태임!)
		 */
		try {
			sampleService.updateSampleList(updateSampleList);

		} catch (Exception e) {
			String msg = "";
			String exceptionName = "";
			String causeMsg = "";

			if (e instanceof NexacroException) { // id 값에 null 셋팅 후 강제 발생

				// ServiceImpl 에서 NexacroException 인자값 메시지
				String sMsg = ((NexacroException) e).getMessage();

				if (sMsg.equals("id")) {
					// {0}는 필수입니다_TEST.
					msg = messageSource.getMessage("nx.valid.required", new String[] { sMsg }, locale);

					((NexacroException) e).setErrorCode(-1000);
					((NexacroException) e).setErrorMsg(msg);

					throw e;
				}
			} else if (e instanceof FdlException) { // 신규 id Generating 오류

				exceptionName = e.getCause().getClass().getName();
				causeMsg = e.getCause().getLocalizedMessage();

				// 신규데이터 id 생성시 오류가 발생했습니다.
				throw new NexacroException("[ " + exceptionName + " ]" + causeMsg, e, -1, "nx.error.idgnr");
			} else {

				exceptionName = e.getCause().getClass().getName();
				causeMsg = e.getCause().getLocalizedMessage();

				// 저장중 에러가 발생했습니다.
				throw new NexacroException("[ " + exceptionName + " ]" + causeMsg, e, -1, "nx.error.save");
			}

		}

		/* 원문 */
		// sampleService.updateSampleList(updateSampleList);

		return new NexacroResult();
	}
}
