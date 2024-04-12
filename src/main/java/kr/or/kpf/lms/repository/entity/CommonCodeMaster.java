package kr.or.kpf.lms.repository.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import kr.or.kpf.lms.repository.entity.key.KeyOfCommonCodeMaster;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 공통 코드 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "COMMON_CODE")
@Access(value = AccessType.FIELD)
@IdClass(KeyOfCommonCodeMaster.class)
public class CommonCodeMaster extends CSEntitySupport implements Serializable {

    /** 개별 코드 */
    @Id
    @Column(name="INDIV_CD")
    private String individualCode;

    /** 코드 */
    @Id
    @Column(name="CD")
    private String code;

    /** 상위 개별 코드 */
    @Column(name="UP_INDIV_CD")
    private String upIndividualCode;

    /** 코드 명 */
    @Column(name="CD_NM")
    private String codeName;

    /** 코드 관리 깊이 */
    @Column(name="CD_MNG_DPTH")
    private Integer codeDepth;

    /** 코드 정렬 번호 */
    @Column(name="CD_SORT_NO")
    private Integer codeSort;

    /** 코드 설명 */
    @Column(name="CD_DSCR")
    private String codeComments;

    @Transient
    private List<CommonCodeMaster> subCommonCodeMaster;
}
