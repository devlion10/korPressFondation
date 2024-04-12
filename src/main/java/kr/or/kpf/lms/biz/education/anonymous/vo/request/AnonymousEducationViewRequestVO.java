package kr.or.kpf.lms.biz.education.anonymous.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

/**
 * 비로그인 이러닝 교육 관련 요청 객체
 */
@Schema(name="AnonymousEducationViewResponseVO", description="비로그인 이러닝 교육 관련 요청 객체")
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class AnonymousEducationViewRequestVO extends CSViewVOSupport {

    /** 콘텐츠 코드 */
    private String contentsCode;
    /** 차시 코드 */
    private String chapterCode;
}
