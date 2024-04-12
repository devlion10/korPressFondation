package kr.or.kpf.lms.biz.servicecenter.notice.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.Data;

@Schema(name="NoticeApiResponseVO", description="공지사항 API 관련 응답 객체")
@Data
public class NoticeApiResponseVO extends CSResponseVOSupport {}
