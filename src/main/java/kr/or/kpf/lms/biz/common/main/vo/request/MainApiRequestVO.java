package kr.or.kpf.lms.biz.common.main.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(name="MainApiRequestVO", description="메인 통합 관련 요청 객체")
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MainApiRequestVO {}
