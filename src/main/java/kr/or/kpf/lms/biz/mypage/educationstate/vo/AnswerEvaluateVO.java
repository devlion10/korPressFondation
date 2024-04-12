package kr.or.kpf.lms.biz.mypage.educationstate.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerEvaluateVO {
    /** 강의 평가 질문 일련 번호 */
    private String questionSerialNo;
    /** 강의 평가 질문 문항 일련 번호 */
    private List<String> questionItemSerialNo;
}
