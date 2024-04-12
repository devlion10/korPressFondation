package kr.or.kpf.lms.biz.mypage.instructorstate.vo.request;

import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class FormeBizlecinfoApiRequestVO extends CSViewVOSupport {

    private Long blciId;

    private String blciUserId;
}
