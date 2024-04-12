package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 본인 인증 내역 Entity
 */
@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "SELF_AUTH_HISTORY")
@Access(value = AccessType.FIELD)
public class SelfAuthenticationHistory extends CSEntitySupport implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="SEQ_NO", nullable=false)
	private Long sequenceNo;

	@Column(name="SESSION_ID")
	private String sessionId;
	
	@Column(name="SECURE_IV")
	private String secureIv;
	
	@Column(name="SECURE_KEY")
	private String secureKey;	

	@Column(name="REQ_ENCRYPT_DATA")
	private String requstEncryptData;
	
	@Column(name="RES_ENCRYPT_DATA")
	private String responseEncryptData;
}
