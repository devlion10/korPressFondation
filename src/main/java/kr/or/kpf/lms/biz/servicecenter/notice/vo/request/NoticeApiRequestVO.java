package kr.or.kpf.lms.biz.servicecenter.notice.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name="NoticeApiRequestVO", description="공지사항 API 관련 요청 객체")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NoticeApiRequestVO {}
