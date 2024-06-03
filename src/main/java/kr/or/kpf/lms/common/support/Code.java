package kr.or.kpf.lms.common.support;

import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.framework.exception.KPFException;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class Code {
    /**
     * 공통 사용 상태
     */
    public enum STATE {
        UNUSED("0", "미사용"),
        USE("1", "사용");

        public String enumCode;
        public String codeName;

        STATE(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(STATE.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 사용 상태")).enumCode)
                    .orElse(null);
        }

        public static STATE enumOfCode(String imsiCode) {
            return Arrays.stream(STATE.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 사용 상태"));
        }
    }

    /**
     * 검색어 범위
     */
    public enum CON_TEXT_TYPE {
        ALL("1", "제목 + 내용"),
        TITLE("2", "제목"),
        CONTENTS("3", "내용");

        public String enumCode;
        public String codeName;

        CON_TEXT_TYPE(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(CON_TEXT_TYPE.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 검색어 범위 코드")).enumCode)
                    .orElse(null);
        }

        public static CON_TEXT_TYPE enumOfCode(String imsiCode) {
            return Arrays.stream(CON_TEXT_TYPE.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 검색어 범위 코드"));
        }
    }

    /**
     * 팀 구분 코드
     */
    public enum TEAM_CTGR {
        ETC("0", "기타"),
        MEDIA("1", "미디어교육"),
        JOURNAL("2", "언론인연수"),
        SUPPORT("3", "미디어지원");

        public String enumCode;
        public String codeName;

        TEAM_CTGR(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(TEAM_CTGR.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 팀 구분 코드")).enumCode)
                    .orElse(null);
        }

        public static TEAM_CTGR enumOfCode(String imsiCode) {
            return Arrays.stream(TEAM_CTGR.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 팀 구분 코드"));
        }
    }

    /**
     * 미디어아카데미(KPF 미카) 지사
     */
    public enum PROVINCE_CD {
        HEAD("HEAD", "본사"),
        SEDA("SEDA", "세종대전지사"),
        BU("BU", "부산지사"),
        GW("GW", "광주지사"),
        DA("DA", "대구지사");

        public String enumCode;
        public String codeName;

        PROVINCE_CD(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(PROVINCE_CD.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 지점 코드")).enumCode)
                    .orElse(null);
        }

        public static PROVINCE_CD enumOfCode(String imsiCode) {
            return Arrays.stream(PROVINCE_CD.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 지점 코드"));
        }
    }

    /**
     * 간편 로그인
     */
    public enum EASY_LGN_CD {
        KAKAO("kakao", "카카오"),
        NAVER("naver", "네이버"),
        GOOGLE("google", "구글");

        public String enumCode;
        public String codeName;

        EASY_LGN_CD(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(EASY_LGN_CD.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 간편 로그인 코드")).enumCode)
                    .orElse(null);
        }

        public static EASY_LGN_CD enumOfCode(String imsiCode) {
            return Arrays.stream(EASY_LGN_CD.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 간편 로그인 코드"));
        }
    }

    public static void main(String[] args) {
        System.out.println(EASY_LGN_CD.enumOfCode("kakao").name());
        System.out.println(EASY_LGN_CD.enumOfCode("kakao").enumCode);
        System.out.println(EASY_LGN_CD.enumOfCode("kakao").codeName);
        System.out.println(EASY_LGN_CD.enumOfCode("kakao").toString());
    }

    /**
     * 웹 회원 유형
     */
    public enum WEB_USER_ROLE {
        JOURNALIST("JOURNALIST", "언론인"),
        TEACHER("TEACHER", "교원"),
        STUDENT("STUDENT", "학생"),
        PARENTS("PARENTS", "학부모"),
        GENERAL("GENERAL", "일반인"),
        SUPER("SUPER", "관리자");

        public String enumCode;
        public String codeName;

        WEB_USER_ROLE(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(WEB_USER_ROLE.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 웹 회원 유형")).enumCode)
                    .orElse(null);
        }

        public static WEB_USER_ROLE enumOfCode(String imsiCode) {
            return Arrays.stream(WEB_USER_ROLE.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 웹 회원 유형"));
        }
    }

    /**
     * 성별 코드
     */
    public enum GNDR_CD {
        MAN("1", "남성"),
        WOMAN("2", "여성");

        public String enumCode;
        public String codeName;

        GNDR_CD(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(GNDR_CD.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 성별 코드")).enumCode)
                    .orElse(null);
        }

        public static GNDR_CD enumOfCode(String imsiCode) {
            return Arrays.stream(GNDR_CD.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 성별 코드"));
        }
    }

    /**
     * 회원 상태
     */
    public enum USER_STATE {
        NONE("0", "미사용"),
        GENERAL("1", "일반"),
        DORMANCY("2", "휴면"),
        LOCK("3", "잠김"),
        WITHDRAWAL("4", "탈퇴");

        public String enumCode;
        public String codeName;

        USER_STATE(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(USER_STATE.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 회원 상태")).enumCode)
                    .orElse(null);
        }

        public static USER_STATE enumOfCode(String imsiCode) {
            return Arrays.stream(USER_STATE.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 회원 상태"));
        }
    }

    public enum JOURNALIST_STTS {
        Y("Y", "승인"),
        N("N", "미승인"),
        W("W", "승인 대기");

        public String enumCode;
        public String codeName;

        JOURNALIST_STTS(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(JOURNALIST_STTS.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 언론인 상태 코드")).enumCode)
                    .orElse(null);
        }

        public static JOURNALIST_STTS enumOfCode(String imsiCode) {
            return Arrays.stream(JOURNALIST_STTS.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 언론인 상태 코드"));
        }
    }

    /**
     * 기관 타입
     */
    public enum ORG_TYPE {
        MEDIA("1", "매체사"),
        AGENCY("2", "기관"),
        SCHOOL("3", "학교");

        public String enumCode;
        public String codeName;

        ORG_TYPE(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(ORG_TYPE.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 성별 코드")).enumCode)
                    .orElse(null);
        }

        public static ORG_TYPE enumOfCode(String imsiCode) {
            return Arrays.stream(ORG_TYPE.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 성별 코드"));
        }
    }

    /**
     * 사업 참여 권한
     */
    public enum BIZ_AUTH {
        SCHOOL("SCHOOL", "학교 담당자"),
        AGENCY("AGENCY", "기관 담당자"),
        INSTR("INSTR", "미디어 강사");

        public String enumCode;
        public String codeName;

        BIZ_AUTH(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(BIZ_AUTH.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 사업 참여 권한")).enumCode)
                    .orElse(null);
        }

        public static BIZ_AUTH enumOfCode(String imsiCode) {
            return Arrays.stream(BIZ_AUTH.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 사업 참여 권한"));
        }
    }

    /**
     * 사업 참여 권한 신청 상태
     */
    public enum BIZ_AUTH_STATE {
        APPLY("1", "권한 요청"),
        APPROVAL("2", "권한 승인"),
        CANCEL_APPLY("3", "권한 해지 요청"),
        CANCEL_APPROVAL("4", "권한 해지"),
        APPLY_CANCEL("5", "권한 요청 반려");

        public String enumCode;
        public String codeName;

        BIZ_AUTH_STATE(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(BIZ_AUTH_STATE.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 사업 참여 권한 신청 상태")).enumCode)
                    .orElse(null);
        }

        public static BIZ_AUTH_STATE enumOfCode(String imsiCode) {
            return Arrays.stream(BIZ_AUTH_STATE.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 사업 참여 권한 신청 상태"));
        }
    }

    /**
     * 강사 구분
     */
    public enum INSTR_CTGR {
        INSTR("INSTR", "미디어 강사"),
        LECTURER("LECTURER", "미디어교육 강사"),
        READER("READER", "언론인연수 강사");

        public String enumCode;
        public String codeName;

        INSTR_CTGR(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(INSTR_CTGR.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 강사 구분")).enumCode)
                    .orElse(null);
        }

        public static INSTR_CTGR enumOfCode(String imsiCode) {
            return Arrays.stream(INSTR_CTGR.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 강사 구분"));
        }
    }

    /**
     * 강사 유형
     */
    public enum INSTR_TYPE {
        ETC("00", "기타"),
        aREADER("30", "기사작성"),
        bREADER("31", "디지털저널리즘"),
        cREADER("32", "미디어경영"),
        dREADER("33", "방송"),
        eREADER("34", "사회"),
        fREADER("35", "윤리"),
        gREADER("36", "저널리즘"),
        hREADER("37", "정치"),
        iREADER("38", "편집");

        public String enumCode;
        public String codeName;

        INSTR_TYPE(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(INSTR_TYPE.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 강사 유형")).enumCode)
                    .orElse(null);
        }

        public static INSTR_TYPE enumOfCode(String imsiCode) {
            return Arrays.stream(INSTR_TYPE.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 강사 유형"));
        }
    }

    /**
     * 강사 세부 유형
     */
    public enum INSTR_TYPE_SUB {
        ETC("000", "기타"),
        aREADERa("300", "기획탐사"),
        aREADERb("301", "내러티브"),
        aREADERc("302", "문장론/어법"),
        aREADERd("303", "스트레이트"),
        aREADERe("304", "인터뷰"),
        aREADERf("305", "칼럼"),
        bREADERa("310", "AI"),
        bREADERb("311", "디지털기술"),
        bREADERc("312", "데이터 분석/활용"),
        bREADERd("313", "정보공개청구"),
        bREADERe("314", "콘텐츠기획"),
        cREADERa("320", "경영"),
        cREADERb("321", "광고/마케팅"),
        cREADERc("322", "인사/노무"),
        cREADERd("323", "재무회계"),
        dREADERa("330", "기획탐사"),
        dREADERb("331", "리포팅"),
        dREADERc("332", "영상콘텐츠 제작"),
        eREADERa("340", "경제/금융"),
        eREADERb("341", "과학"),
        eREADERc("342", "문화/예술"),
        eREADERd("343", "보건복지"),
        eREADERe("344", "사회"),
        eREADERf("345", "사건사고/수사"),
        eREADERg("346", "산업/기술"),
        eREADERh("347", "지역"),
        eREADERi("348", "통계"),
        eREADERj("349", "환경/ESG"),
        fREADERa("350", "법"),
        fREADERb("351", "언론중재/취재윤리"),
        fREADERc("352", "인권/젠더"),
        gREADERa("360", "저널리즘학"),
        gREADERb("361", "취재방법"),
        gREADERc("362", "팩트체크"),
        hREADERa("370", "국제/외교"),
        hREADERb("371", "정치/선거"),
        hREADERc("372", "예산"),
        hREADERd("373", "정책"),
        iREADERa("380", "사진/촬영"),
        iREADERb("381", "신문편집");

        public String enumCode;
        public String codeName;

        INSTR_TYPE_SUB(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(INSTR_TYPE_SUB.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 강사 유형 세부")).enumCode)
                    .orElse(null);
        }

        public static INSTR_TYPE_SUB enumOfCode(String imsiCode) {
            return Arrays.stream(INSTR_TYPE_SUB.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 강사 유형 세부"));
        }
    }

    /**
     * 과정 구분
     */
    public enum CTS_CTGR {
        JOURNAL("1", "언론인연수"),
        MEDIA("2", "미디어교육"),
        ETC("3", "공통");

        public String enumCode;
        public String codeName;

        CTS_CTGR(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(CTS_CTGR.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 콘텐츠 카테고리 코드")).enumCode)
                    .orElse(null);
        }

        public static CTS_CTGR enumOfCode(String imsiCode) {
            return Arrays.stream(CTS_CTGR.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 콘텐츠 카테고리 코드"));
        }
    }

    /**
     * 교육 대상
     */
    public enum EDU_TARGET {
        GENERAL("1", "일반인"),
        JOURNALIST("2", "언론인"),
        TEACHER("3", "교원"),
        STUDENT("4", "학생"),
        PARENTS("5", "학부모");

        public String enumCode;
        public String codeName;

        EDU_TARGET(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(EDU_TARGET.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 교육 대상 코드")).enumCode)
                    .orElse(null);
        }

        public static EDU_TARGET enumOfCode(String imsiCode) {
            return Arrays.stream(EDU_TARGET.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 교육 대상 코드"));
        }
    }

    /**
     * 교육 운영 타입
     */
    public enum OPER_TYPE {
        CARDINAL("1", "기수 운영"),
        ALWAYS("2", "상시 운영");

        public String enumCode;
        public String codeName;

        OPER_TYPE(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(OPER_TYPE.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 교육 운영 타입")).enumCode)
                    .orElse(null);
        }

        public static OPER_TYPE enumOfCode(String imsiCode) {
            return Arrays.stream(OPER_TYPE.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 교육 운영 타입"));
        }
    }

    /**
     * 교육 과정 유형
     */
    public enum EDU_TYPE {
        VIDEO("1", "화상 교육"),
        CONVOCATION("2", "집합 교육"),
        E_LEARNING("3", "이러닝"),
        LECTURE("4", "병행(화상+집합) 교육");

        public String enumCode;
        public String codeName;

        EDU_TYPE(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(EDU_TYPE.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 교육 과정 구분")).enumCode)
                    .orElse(null);
        }

        public static EDU_TYPE enumOfCode(String imsiCode) {
            return Arrays.stream(EDU_TYPE.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 교육 과정 구분"));
        }
    }
    /**
     * 교육 과정 유형
     */
    public enum SET_EDU_TYPE {
        VIDEO("1", "화상 교육"),
        CONVOCATION("2", "집합 교육"),
        E_LEARNING("3", "이러닝"),
        LECTURE("4", "병행(화상+집합) 교육");

        public String enumCode;
        public String codeName;

        SET_EDU_TYPE(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(SET_EDU_TYPE.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 교육 과정 구분")).enumCode)
                    .orElse(null);
        }

        public static SET_EDU_TYPE enumOfCode(String imsiCode) {
            return Arrays.stream(SET_EDU_TYPE.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 교육 과정 구분"));
        }
    }

    /**
     * 교육 과정 구분
     */
    public enum EDU_CTGR {
        ETC("30", "기타"),
        aJOURNALIST("10", "국제교류"),
        bJOURNALIST("11", "디플로마"),
        cJOURNALIST("12", "수습기자"),
        dJOURNALIST("13", "전문과정"),
        eJOURNALIST("14", "해외과정");

        public String enumCode;
        public String codeName;

        EDU_CTGR(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(EDU_CTGR.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 교육 유형 구분")).enumCode)
                    .orElse(null);
        }

        public static EDU_CTGR enumOfCode(String imsiCode) {
            return Arrays.stream(EDU_CTGR.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 교육 유형 구분"));
        }
    }

    /**
     * 교육 과정 구분 세부
     */
    public enum EDU_CTGR_SUB {
        ETC("300", "기타"),
        aJOURNALISTa("100", "국제회의참가"),
        bJOURNALISTa("110", "디플로마"),
        cJOURNALISTa("120", "수습기자"),
        cJOURNALISTb("121", "신입기자"),
        dJOURNALISTa("130", "디지털저널리즘"),
        dJOURNALISTb("131", "미디어경영"),
        dJOURNALISTc("132", "전문일반"),
        dJOURNALISTd("133", "지역"),
        eJOURNALISTa("140", "해외장기연수");

        public String enumCode;
        public String codeName;

        EDU_CTGR_SUB(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(EDU_CTGR_SUB.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 교육 유형 구분")).enumCode)
                    .orElse(null);
        }

        public static EDU_CTGR_SUB enumOfCode(String imsiCode) {
            return Arrays.stream(EDU_CTGR_SUB.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 교육 유형 구분"));
        }
    }

    /**
     * 신청 가능 여부 타입
     */
    public enum APLYABLE_TYPE {
        NOT("1", "신청 불가"),
        CLOSED("2", "신청 마감"),
        AVAILABLE("3", "신청 가능"),
        APPLIED("4", "신청 완료");

        public String enumCode;
        public String codeName;

        APLYABLE_TYPE(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(APLYABLE_TYPE.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 신청 가능 여부 타입 코드")).enumCode)
                    .orElse(null);
        }

        public static APLYABLE_TYPE enumOfCode(String imsiCode) {
            return Arrays.stream(APLYABLE_TYPE.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 신청 가능 여부 타입 코드"));
        }
    }

    /**
     * 교육 신청 승인 유형
     */
    public enum APLY_APPR_TYPE {
        IMMEDIATELY("1", "신청 즉시 승인"),
        ADMIN("2", "관리자 승인");

        public String enumCode;
        public String codeName;

        APLY_APPR_TYPE(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(APLY_APPR_TYPE.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 교육 신청 승인 유형")).enumCode)
                    .orElse(null);
        }

        public static APLY_APPR_TYPE enumOfCode(String imsiCode) {
            return Arrays.stream(APLY_APPR_TYPE.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 교육 신청 승인 유형"));
        }
    }

    /**
     * 교육 신청 승인 상태
     */
    public enum ADM_APL_STATE {
        WAIT("1", "승인대기"),
        APPROVAL("2", "승인"),
        REFUSE("3", "승인거절"),
        CANCEL("4", "사용자 취소");

        public String enumCode;
        public String codeName;

        ADM_APL_STATE(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(ADM_APL_STATE.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 교육 신청 승인 상태")).enumCode)
                    .orElse(null);
        }

        public static ADM_APL_STATE enumOfCode(String imsiCode) {
            return Arrays.stream(ADM_APL_STATE.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 교육 신청 승인 상태"));
        }
    }

    /**
     * 교육 진행 상태
     */
    public enum EDU_STATE {
        WAIT("1", "심사 신청 중"),
        PROCEEDING("2", "교육 진행 중"),
        END("3", "교육 완료");

        public String enumCode;
        public String codeName;

        EDU_STATE(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(EDU_STATE.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 교육 진행 상태")).enumCode)
                    .orElse(null);
        }

        public static EDU_STATE enumOfCode(String imsiCode) {
            return Arrays.stream(EDU_STATE.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 교육 진행 상태"));
        }
    }

    /**
     * 교육 수료 유형
     */
    public enum EUD_FNSH_TYPE {
        SUCCESS("1", "수료 기준 달성"),
        ENDED("2", "교육 종료일 이후");

        public String enumCode;
        public String codeName;

        EUD_FNSH_TYPE(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(EUD_FNSH_TYPE.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 교육 수료 유형")).enumCode)
                    .orElse(null);
        }

        public static EUD_FNSH_TYPE enumOfCode(String imsiCode) {
            return Arrays.stream(EUD_FNSH_TYPE.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 교육 수료 유형"));
        }
    }

    /**
     * 시험 문제 유형 코드
     */
    public enum QUES_TYPE {
        ONE("1", "단일선택"),
        MANY("2", "다중선택"),
        SHORT("3", "단답형"),
        LONG("4", "서술형");

        public String enumCode;
        public String codeName;

        QUES_TYPE(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(QUES_TYPE.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 시험 문제(설문 질문) 유형 코드")).enumCode)
                    .orElse(null);
        }

        public static QUES_TYPE enumOfCode(String imsiCode) {
            return Arrays.stream(QUES_TYPE.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 시험 문제(설문 질문) 유형 코드"));
        }
    }

    /**
     * 설문 유형
     */
    public enum EVAL_TYPE {
        MEDIA("1", "미디어교육팀"),
        JOURNAL("2", "언론인연수팀");

        public String enumCode;
        public String codeName;

        EVAL_TYPE(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(EVAL_TYPE.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 설문 유형 코드")).enumCode)
                    .orElse(null);
        }

        public static EVAL_TYPE enumOfCode(String imsiCode) {
            return Arrays.stream(EVAL_TYPE.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 설문 유형 코드"));
        }
    }

    /**
     * 설문 문항 유형
     */
    public enum EVAL_QUES_CTGR {
        ETC("1", "문항 카테고리1"),
        JOURNAL_ALL("20", "언론인연수팀 공통"),
        JOURNAL_CONVOCATION("21", "언론인연수팀 집합강의"),
        JOURNAL_VIDEO("22", "언론인연수팀 화상강의");

        public String enumCode;
        public String codeName;

        EVAL_QUES_CTGR(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(EVAL_QUES_CTGR.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 설문 문항 유형 코드")).enumCode)
                    .orElse(null);
        }

        public static EVAL_QUES_CTGR enumOfCode(String imsiCode) {
            return Arrays.stream(EVAL_QUES_CTGR.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 설문 문항 유형 코드"));
        }
    }

    /**
     * 문제 난이도
     */
    public enum QUE_LEVEL {
        HIGH("1", "상", 3),
        MEDIUM("2", "중", 2),
        LOW("3", "하", 1);

        public String enumCode;
        public String codeName;
        public Integer score;

        QUE_LEVEL(String enumCode, String codeName, Integer score) {
            this.enumCode = enumCode;
            this.codeName = codeName;
            this.score = score;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(QUE_LEVEL.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 문제 난이도 타입")).enumCode)
                    .orElse(null);
        }

        public static QUE_LEVEL enumOfCode(String imsiCode) {
            return Arrays.stream(QUE_LEVEL.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 문제 난이도 타입"));
        }
    }

    /**
     * 공모 세부 구분
     */
    public enum BIZ_PBANC_CTGR_SUB {
        SOCIAL("1", "사회미디어"),
        MEDIA("2", "학교미디어");

        public String enumCode;
        public String codeName;

        BIZ_PBANC_CTGR_SUB(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(BIZ_PBANC_CTGR_SUB.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 공모 세부 구분 코드")).enumCode)
                    .orElse(null);
        }

        public static BIZ_PBANC_CTGR_SUB enumOfCode(String imsiCode) {
            return Arrays.stream(BIZ_PBANC_CTGR_SUB.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 공모 세부 구분 코드"));
        }
    }

    /**
     * 공모 템플릿 타입
     */
    public enum BIZ_PBANC_TYPE {
        A("0", "기본형"),
        B("1", "미디어교육 평생교실"),
        C("2", "미디어교육 운영학교"),
        D("3", "자유학기제 미디어교육지원"),
        E("4", "팩트체크 교실"),
        F("5", "자유형");

        public String enumCode;
        public String codeName;

        BIZ_PBANC_TYPE(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(BIZ_PBANC_TYPE.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 공모 템플릿 타입 코드")).enumCode)
                    .orElse(null);
        }

        public static BIZ_PBANC_TYPE enumOfCode(String imsiCode) {
            return Arrays.stream(BIZ_PBANC_TYPE.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 공모 템플릿 타입 코드"));
        }
    }

    /**
     * 공모 상태
     */
    public enum BIZ_PBANC_STTS {
        RECIPT("0", "모집중"),
        ENDED("1", "모집 마감"),
        SAVE("9", "임시 저장");

        public String enumCode;
        public String codeName;

        BIZ_PBANC_STTS(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(BIZ_PBANC_STTS.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 공모 상태 코드")).enumCode)
                    .orElse(null);
        }

        public static BIZ_PBANC_STTS enumOfCode(String imsiCode) {
            return Arrays.stream(BIZ_PBANC_STTS.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 공모 상태 코드"));
        }
    }

    /**
     * 강사 배정 방식
     */
    public enum BIZ_PBANC_INSTR_SLCTN_METH {
        APRV("0", "승인"),
        FCFS("1", "선착순"),
        NONE("9", "없음");

        public String enumCode;
        public String codeName;

        BIZ_PBANC_INSTR_SLCTN_METH(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(BIZ_PBANC_INSTR_SLCTN_METH.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 강사 배정 방식 코드")).enumCode)
                    .orElse(null);
        }

        public static BIZ_PBANC_INSTR_SLCTN_METH enumOfCode(String imsiCode) {
            return Arrays.stream(BIZ_PBANC_INSTR_SLCTN_METH.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 강사 배정 방식 코드"));
        }
    }

    /**
     * 사업공고 기본형 템플릿 대상
     */
    public enum BIZ_PBANC_TMPL0_TRGT {
        ALL("ALL", "전체"),
        A("0", "언론인"),
        B("1", "교원(일반)"),
        C("2", "일반(학생)"),
        D("3", "일반(학부모)"),
        E("4", "일반(일반인)"),
        F("5", "기관담당자"),
        G("6", "학교담당자"),
        H("7", "미디어강사");

        public String enumCode;
        public String codeName;

        BIZ_PBANC_TMPL0_TRGT(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(BIZ_PBANC_TMPL0_TRGT.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 사업공고 기본형 템플릿 대상 코드")).enumCode)
                    .orElse(null);
        }

        public static BIZ_PBANC_TMPL0_TRGT enumOfCode(String imsiCode) {
            return Arrays.stream(BIZ_PBANC_TMPL0_TRGT.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 사업공고 기본형 템플릿 대상 코드"));
        }
    }

    /**
     * 사업공고 미디어교육 평생교실 템플릿 대상
     */
    public enum BIZ_PBANC_TMPL1_TRGT {
        ALL("ALL", "전체"),
        A("0", "유아"),
        B("1", "초등학생"),
        C("2", "중학생"),
        D("3", "고등학생"),
        E("4", "성인"),
        F("5", "학부모"),
        G("6", "노년층"),
        H("7", "소외계층(장애인, 다문화 등)"),
        I("8", "북한이탈주민");

        public String enumCode;
        public String codeName;

        BIZ_PBANC_TMPL1_TRGT(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(BIZ_PBANC_TMPL1_TRGT.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 사업공고 미디어교육 평생교실 템플릿 대상 코드")).enumCode)
                    .orElse(null);
        }

        public static BIZ_PBANC_TMPL1_TRGT enumOfCode(String imsiCode) {
            return Arrays.stream(BIZ_PBANC_TMPL1_TRGT.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 사업공고 미디어교육 평생교실 템플릿 대상 코드"));
        }
    }

    /**
     * 사업 신청 공고 상태
     */
    public enum BIZ_ORG_APLY_STTS {
        A("0", "임시 저장"),
        B("1", "신청"),
        C("2", "승인"),
        D("3", "반려"),
        E("7", "종료"),
        F("9", "가승인");

        public String enumCode;
        public String codeName;

        BIZ_ORG_APLY_STTS(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(BIZ_ORG_APLY_STTS.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 사업 신청 공고 상태 코드")).enumCode)
                    .orElse(null);
        }

        public static BIZ_ORG_APLY_STTS enumOfCode(String imsiCode) {
            return Arrays.stream(BIZ_ORG_APLY_STTS.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 사업 신청 공고 상태 코드"));
        }
    }

    /**
     * 강의확인서 상태
     */
    public enum BIZ_INSTR_IDNTY_STTS {
        A("0", "미승인"),
        B("1", "승인 대기"),
        C("2", "기관 승인"),
        D("3", "정산 대기"),
        E("7", "정산 완료"),
        F("9", "반려");

        public String enumCode;
        public String codeName;

        BIZ_INSTR_IDNTY_STTS(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(BIZ_INSTR_IDNTY_STTS.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 강의확인서 상태 코드")).enumCode)
                    .orElse(null);
        }

        public static BIZ_INSTR_IDNTY_STTS enumOfCode(String imsiCode) {
            return Arrays.stream(BIZ_INSTR_IDNTY_STTS.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 강의확인서 상태 코드"));
        }
    }

    /**
     * 상호평가 구분
     */
    public enum BIZ_SURVEY_CTGR {
        INSTR("0", "강사평가"),
        ORG("1", "기관평가");

        public String enumCode;
        public String codeName;

        BIZ_SURVEY_CTGR(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(BIZ_SURVEY_CTGR.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 상호평가 구분 코드")).enumCode)
                    .orElse(null);
        }

        public static BIZ_SURVEY_CTGR enumOfCode(String imsiCode) {
            return Arrays.stream(BIZ_SURVEY_CTGR.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 상호평가 구분 코드"));
        }
    }

    /**
     * 상호평가 문항 유형
     */
    public enum BIZ_SURVEY_QITEM_TYPE {
        ONE("0", "단일선택"),
        MANY("1", "다중선택"),
        SHORT("2", "단답형"),
        LONG("3", "서술형");

        public String enumCode;
        public String codeName;

        BIZ_SURVEY_QITEM_TYPE(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(BIZ_SURVEY_QITEM_TYPE.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 상호평가 문항 유형 코드")).enumCode)
                    .orElse(null);
        }

        public static BIZ_SURVEY_QITEM_TYPE enumOfCode(String imsiCode) {
            return Arrays.stream(BIZ_SURVEY_QITEM_TYPE.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 상호평가 문항 유형 코드"));
        }
    }

    /**
     * 교육 후기방 유형
     */
    public enum EDU_REVIEW_TYPE {
        ETC("00", "기타"),
        bA("21", "수습기자 기본교육"),  // 언론인
        bB("22", "인터넷신문 신입기자 기본교육"),  // 언론인
        bC("23", "케이블TV방송(SO) 주니어기자연수"),  // 언론인
        bD("24", "KPF 디플로마 과정"),  // 언론인
        bE("25", "경력기자 전문연수");  // 언론인

        public String enumCode;
        public String codeName;

        EDU_REVIEW_TYPE(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(EDU_REVIEW_TYPE.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 교육 후기방 유형 코드")).enumCode)
                    .orElse(null);
        }

        public static EDU_REVIEW_TYPE enumOfCode(String imsiCode) {
            return Arrays.stream(EDU_REVIEW_TYPE.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 교육 후기방 유형 코드"));
        }
    }

    /**
     * 수업지도안 유형
     */
    public enum GUI_TYPE {
        TEACHER("1", "교사"),
        PARENT("2", "학부모"),
        ETC("3", "기타(다문화/유아/일반)");

        public String enumCode;
        public String codeName;

        GUI_TYPE(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(GUI_TYPE.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 수업지도안 유형 코드")).enumCode)
                    .orElse(null);
        }

        public static GUI_TYPE enumOfCode(String imsiCode) {
            return Arrays.stream(GUI_TYPE.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 수업지도안 유형 코드"));
        }
    }

    /**
     * 수업지도안 파일 유형
     */
    public enum GUI_FILE_TYPE {
        TEACH("TEACH", "수업지도안/길라잡이"),
        ACTIVITY("ACTIVITY", "활동지"),
        ANSWER("ANSWER", "예시답안"),
        NIE("NIE", "10분 NIE");

        public String enumCode;
        public String codeName;

        GUI_FILE_TYPE(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(GUI_FILE_TYPE.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 수업지도안 파일 유형 코드")).enumCode)
                    .orElse(null);
        }

        public static GUI_FILE_TYPE enumOfCode(String imsiCode) {
            return Arrays.stream(GUI_FILE_TYPE.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 수업지도안 파일 유형 코드"));
        }
    }

    /**
     * 자료실 자료 구분
     */
    public enum MTRL_CTGR {
        ETC("0", "기타 자료"),
        A("1", "교재 자료"),
        B("2", "연구/통계 자료"),
        C("3", "영상 자료"),
        D("4", "사업결과물"),
        E("5", "미디어리터러시");

        public String enumCode;
        public String codeName;

        MTRL_CTGR(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(MTRL_CTGR.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 자료실 구분 코드")).enumCode)
                    .orElse(null);
        }

        public static MTRL_CTGR enumOfCode(String imsiCode) {
            return Arrays.stream(MTRL_CTGR.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 자료실 구분 코드"));
        }
    }

    /**
     * 자료실 자료 유형
     */
    public enum MTRL_TYPE {
        ETC("00", "기타 자료(기타)"),
        ETCA("01", "기타 자료(아카이브)"),
        ETCB("02", "기타 자료(NIE공모전)"),
        ETCC("03", "기타 자료(신문제작갤러리)"),
        ETCD("04", "기타 자료(외부자료)"),
        aETC("10", "교재 자료(기타)"),
        aA("11", "교재 자료(초등)"),
        aB("12", "교재 자료(중등)"),
        aC("13", "교재 자료(고등)"),
        bETC("20", "연구/통계 자료(기타)"),
        bA("21", "연구/통계 자료(결과보고)"),
        bB("22", "연구/통계 자료(사례/자료집)"),
        bC("23", "연구/통계 자료(연구자료)"),
        bD("24", "연구/통계 자료(통계자료)"),
        bE("25", "연구/통계 자료(교육자료)"),
        cETC("30", "영상 자료(기타)"),
        cA("31", "영상 자료(수업 자료)"),
        cB("32", "영상 자료(행사 영상)"),
        cC("33", "영상 자료(협업 영상)"),
        dETC("40", "사업결과물(기타)"),
        dA("41", "사업결과물(국제교류)"), // 미디어
        dB("42", "사업결과물(연구및포럼)"), // 미디어
        dC("43", "사업결과물(해외자료)"), // 미디어
        dD("46", "사업결과물(해외장기)"), // 언론인
        dE("47", "사업결과물(연수)"), // 언론인
        dF("48", "사업결과물(KPF 디플로마)"), // 언론인
        dG("49", "국제회의"); // 언론인

        public String enumCode;
        public String codeName;

        MTRL_TYPE(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(MTRL_TYPE.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 자료실 유형 코드")).enumCode)
                    .orElse(null);
        }

        public static MTRL_TYPE enumOfCode(String imsiCode) {
            return Arrays.stream(MTRL_TYPE.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 자료실 유형 코드"));
        }
    }

    /**
     * 교육 주제 제안 타입
     */
    public enum SGST_TYPE {

        JOURNALIST("1", "언론인"),
        CITIZEN("2", "시민");

        public String enumCode;
        public String codeName;

        SGST_TYPE(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(SGST_TYPE.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 교육 주제 제안 타입 코드")).enumCode)
                    .orElse(null);
        }

        public static SGST_TYPE enumOfCode(String imsiCode) {
            return Arrays.stream(SGST_TYPE.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 교육 주제 제안 타입 코드"));
        }
    }

    /**
     * 1:1 문의 타입
     */
    public enum QNA_TYPE {

        APLY("1", "공모 문의"),
        EDUCATION("2", "교육 문의"),
        SITE("3", "사이트 이용 문의");

        public String enumCode;
        public String codeName;

        QNA_TYPE(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(QNA_TYPE.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 1:1 문의 타입 코드")).enumCode)
                    .orElse(null);
        }

        public static QNA_TYPE enumOfCode(String imsiCode) {
            return Arrays.stream(QNA_TYPE.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 1:1 문의 타입 코드"));
        }
    }

    /**
     * 1:1 문의 상태
     */
    public enum QNA_STATE {

        YET("1", "미확인"),
        PROCESS("2", "처리중"),
        SUCCESS("3", "완료");

        public String enumCode;
        public String codeName;

        QNA_STATE(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(QNA_STATE.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 1:1 문의 상태 코드")).enumCode)
                    .orElse(null);
        }

        public static QNA_STATE enumOfCode(String imsiCode) {
            return Arrays.stream(QNA_STATE.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 1:1 문의 상태 코드"));
        }
    }

    /**
     * 문서 유형
     */
    public enum DCMNT_TYPE {
        GENERAL("0", "일반"),
        TERMS("1", "이용약관"),
        PRIVACY_POLICY("2", "개인정보 처리 방침"),
        PRIVACY_AGREEMENT("3", "개인정보 동의서(필수)"),
        AUTHORIZE_AGREEMENT("4", "개인정보 동의서(선택)");

        public String enumCode;
        public String codeName;

        DCMNT_TYPE(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(DCMNT_TYPE.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 문서 유형")).enumCode)
                    .orElse(null);
        }

        public static DCMNT_TYPE enumOfCode(String imsiCode) {
            return Arrays.stream(DCMNT_TYPE.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 문서 유형"));
        }
    }


    // 미사용
    /**
     * 검색어 범위
     */
    public enum LMS_DATA_CON_TEXT_TYPE {
        ALL("1", "전체"),
        TITLE("2", "제목"),
        CONTENTS("3", "내용"),
        AUTHOR("4", "저자");

        public String enumCode;
        public String codeName;

        LMS_DATA_CON_TEXT_TYPE(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static String valueOfEnum(String imsiCode) {
            return Optional.ofNullable(imsiCode)
                    .filter(Objects::nonNull)
                    .map(value -> Arrays.stream(LMS_DATA_CON_TEXT_TYPE.values()).filter(data -> data.enumCode.equals(value))
                            .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 검색어 범위 코드")).enumCode)
                    .orElse(null);
        }

        public static LMS_DATA_CON_TEXT_TYPE enumOfCode(String imsiCode) {
            return Arrays.stream(LMS_DATA_CON_TEXT_TYPE.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 검색어 범위 코드"));
        }
    }

    /**
     * 검색 날짜 범위
     */
    public enum CON_DATE_TYPE {
        REGIST("1", "등록 일자"),
        UPDATE("2", "수정 일자");

        public String enumCode;
        public String codeName;

        CON_DATE_TYPE(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static CON_DATE_TYPE enumOfCode(String imsiCode) {
            return Arrays.stream(CON_DATE_TYPE.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 검색 날짜 범위"));
        }
    }

    /**
     * 권한 조회 타입
     */
    public enum ROLE_SEARCH_TYPE {
        ROLE_GROUP("1", "권한 그룹 기준"),
        ROLE("2", "권한 기준"),
        ROLE_MAPPING("3", "매핑 정보 기준");

        public String enumCode;
        public String codeName;

        ROLE_SEARCH_TYPE(String enumCode, String codeName) {
            this.enumCode = enumCode;
            this.codeName = codeName;
        }

        public static ROLE_SEARCH_TYPE enumOfCode(String imsiCode) {
            return Arrays.stream(ROLE_SEARCH_TYPE.values()).filter(data -> data.enumCode.equals(imsiCode))
                    .findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9998, "유효하지 않은 권한 조회 타입"));
        }
    }
}
