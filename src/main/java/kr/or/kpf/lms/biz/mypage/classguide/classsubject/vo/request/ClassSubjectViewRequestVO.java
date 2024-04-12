package kr.or.kpf.lms.biz.mypage.classguide.classsubject.vo.request;

import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

/**
 * 수업지도안 교과 관련 요청 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ClassSubjectViewRequestVO extends CSViewVOSupport {
    /** 검색어 범위 */
    private String containTextType;
    /** 검색에 포함할 단어 */
    private String containText;

    /** 내용 */
    private String content;
    /** 등록자 id */
    private String registUserId;
}