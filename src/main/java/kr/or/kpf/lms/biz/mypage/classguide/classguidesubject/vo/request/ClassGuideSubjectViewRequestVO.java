
package kr.or.kpf.lms.biz.mypage.classguide.classguidesubject.vo.request;

import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

/**
 * 수업지도안 관련 요청 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ClassGuideSubjectViewRequestVO extends CSViewVOSupport {
    /** 일련 번호 */
    private Long sequenceNo;

}