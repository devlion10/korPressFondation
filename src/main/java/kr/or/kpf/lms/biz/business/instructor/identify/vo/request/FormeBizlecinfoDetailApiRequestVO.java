package kr.or.kpf.lms.biz.business.instructor.identify.vo.request;

import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class FormeBizlecinfoDetailApiRequestVO extends CSViewVOSupport {

    private String blciUserId;
    private Long blciId;


}
