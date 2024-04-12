package kr.or.kpf.lms.biz.search.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 통합 검색 관련 응답 객체
 */
@Schema(name="SearchResponseVO", description="통합검색 API 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponseVO {

    private String menuName; // 공모/자격, 참여/소통, 고객센터, 연수원 소개
    private String menuSubName; // 공모사업, 교육후기, 자료실, 이벤트/설문, 공지사항, 행사소개(보도자료)
    private String menuSubSubName; // 0: 수업지도안, 0: 기타 자료, 1: 교재 자료, 2: 연구/통계 자료, 3: 영상 자료, 4: 사업결과물, 5: 미디어리터러시

    private String contentNo; // 컨텐츠 키

    private String title; // 사업명, 게시물 제목
    private String contents; // 지원개요, 게시물 내용

    private String bizPbancRcptBgng;
    private String bizPbancRcptEnd;

    private String bizPbancSprtBgng;
    private String bizPbancSprtEnd;

    private String regDt;

    private String registerId;
    private String registerName;

}
