package kr.or.kpf.lms.biz.common.main.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class EducationPlanMainDTO {
    /** 교육 계획 코드 */
    private String educationPlanCode;
    /** 과정 코드 */
    private String curriculumCode;
    /** 교육 과정 신청 가능 여부(1: 신청 불가, 2: 신청 마감, 3: 신청 가능, 4: 신청 완료) */
    private String availableApplicationType;
    /** 썸네일 파일 경로 */
    private String thumbnailFilePath;
    /** 썸네일 파일 너비 */
    private Integer width;
    /** 썸네일 파일 높이 */
    private Integer height;
    /** 교육 타입 */
    @JsonIgnore
    private String educationType;
    /** 교육 대상 */
    private String educationTarget;
    /** 과정 명 */
    private String curriculumName;
    /** 교육 과정 내용 */
    @JsonIgnore
    private String educationContent;
    /** 운영 시작 일시 */
    private String operationBeginDateTime;
    /** 운영 종료 일시 */
    private String operationEndDateTime;
    /** 신청 시작 일시 */
    private String applyBeginDateTime;
    /** 신청 종료 일시 */
    private String applyEndDateTime;
    /** 교육 장소 */
    private String educationPlace;
    /** 정원 */
    private Integer numberOfPeople;
    /** 정원(병행교육시)*/
/*    private Integer numberOfPeopleParallel;*/
    /** 생성 일시 */
    @JsonIgnore
    private String createDateTime;
}
