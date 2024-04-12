package kr.or.kpf.lms.biz.business.instructor.identify.vo.request;

import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class FormeBizlecinfoApiRequestVO extends CSViewVOSupport {

    private String containText;

    private String containTextType;

    private Long blciId;


    private String rgn;
    private String year;

    private String blciUserId;
}
