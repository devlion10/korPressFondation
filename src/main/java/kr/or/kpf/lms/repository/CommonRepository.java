package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.biz.common.code.vo.request.CommonCodeViewRequestVO;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.entity.CommonCodeMaster;

import java.util.List;

/**
 * 공통 Repository
 */
public interface CommonRepository {

    List<CommonCodeMaster> findTopCommonCode(CommonCodeViewRequestVO requestObject);

    <T extends CSViewVOSupport> CSPageImpl<?> findEntityList(T requestObject);
}
