package kr.or.kpf.lms.repository.entity.key;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KeyOfBizOrganizationAplyDtl implements Serializable {

    /** 사업 공고 신청 수업 계획서 일련번호 */
    @Column(name="BIZ_ORG_APLY_DTL_NO")
    private String bizOrgAplyDtlNo;

    /** 사업 공고 신청 일련번호 */
    @Column(name="BIZ_ORG_APLY_NO")
    private String bizOrgAplyNo;

}
