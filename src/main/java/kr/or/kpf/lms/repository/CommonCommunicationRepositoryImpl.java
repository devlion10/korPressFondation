package kr.or.kpf.lms.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.CollectionExpression;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.or.kpf.lms.biz.common.main.vo.request.MainViewRequestVO;
import kr.or.kpf.lms.biz.communication.archive.data.vo.request.ArchiveViewRequestVO;
import kr.or.kpf.lms.biz.communication.archive.data.vo.response.ArchiveCustomApiResponseVO;
import kr.or.kpf.lms.biz.communication.archive.guide.vo.request.ArchiveClassGuideViewRequestVO;
import kr.or.kpf.lms.biz.communication.event.vo.request.EventViewRequestVO;
import kr.or.kpf.lms.biz.communication.review.vo.request.ReviewViewRequestVO;
import kr.or.kpf.lms.biz.communication.suggestion.vo.request.SuggestionViewRequestVO;
import kr.or.kpf.lms.biz.mypage.classguide.vo.request.ClassGuideViewRequestVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSRepositorySupport;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import kr.or.kpf.lms.common.support.Code;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.framework.model.ResponseSummary;
import kr.or.kpf.lms.repository.entity.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static kr.or.kpf.lms.repository.entity.QAdminUser.adminUser;
import static kr.or.kpf.lms.repository.entity.QClassGuide.classGuide;
import static kr.or.kpf.lms.repository.entity.QClassSubject.classSubject;
import static kr.or.kpf.lms.repository.entity.QClassGuideSubject.classGuideSubject;
import static kr.or.kpf.lms.repository.entity.QClassGuideFile.classGuideFile;
import static kr.or.kpf.lms.repository.entity.QEducationReview.educationReview;
import static kr.or.kpf.lms.repository.entity.QEducationSuggestion.educationSuggestion;
import static kr.or.kpf.lms.repository.entity.QLmsData.lmsData;
import static kr.or.kpf.lms.repository.entity.QEventInfo.eventInfo;
import static kr.or.kpf.lms.repository.entity.QLmsUser.lmsUser;
import static org.springframework.util.StringUtils.hasText;

/**
 * 참여 / 소통 공통 Repository 구현체
 */
@Repository
public class CommonCommunicationRepositoryImpl extends CSRepositorySupport implements  CommonCommunicationRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public CommonCommunicationRepositoryImpl(JPAQueryFactory jpaQueryFactory){ this.jpaQueryFactory = jpaQueryFactory; }
    @Autowired private LmsDataFileRepository lmsDataFileRepository;
    @Autowired private LmsUserRepository lmsUserRepository;
    @Autowired private AdminUserRepository adminUserRepository;

    /**
     * 공통 리스트 조회
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    @Override
    public <T extends CSViewVOSupport> CSPageImpl<?> findEntityList(T requestObject) {
        /** Entity 총 갯수 */
        Long count = getEntityCount(requestObject);
        /** Entity 리스트 */
        List<?> content = getEntityList(requestObject);

        ResponseSummary summary = ResponseSummary.builder()
                .count(content.size())
                .offset(requestObject.getPageNum())
                .limit(requestObject.getPageSize())
                .build();
        Pageable pageableToApply = summary.ensureValidPageable(requestObject.getPageable());

        return CSPageImpl.of(content, pageableToApply, count);
    }

    /**
     * Entity 총 갯수
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    private <T extends CSViewVOSupport> Long getEntityCount(T requestObject) {
        if(requestObject instanceof SuggestionViewRequestVO) { /** 교육 주제 제안 */
            return jpaQueryFactory.select(educationSuggestion.count())
                    .from(educationSuggestion)
                    .where(getQuery(requestObject))
                    .orderBy(educationSuggestion.createDateTime.desc())
                    .fetchOne();
        } else if(requestObject instanceof ReviewViewRequestVO) { /** 교육 후기 */
            return jpaQueryFactory.select(educationReview.count())
                    .from(educationReview)
                    .where(getQuery(requestObject))
                    .orderBy(educationReview.createDateTime.desc())
                    .fetchOne();
        } else if(requestObject instanceof ArchiveClassGuideViewRequestVO) { /** 자료실(수업지도안) */
            return jpaQueryFactory.select(classGuide.count())
                    .from(classGuide)
                    .leftJoin(lmsUser).on(lmsUser.userId.eq(classGuide.registUserId))
                    .where(getQuery(requestObject))
                    .orderBy(classGuide.createDateTime.desc())
                    .fetchOne();
        } else if(requestObject instanceof ArchiveViewRequestVO) { /** 자료실(교육 자료, 연구/통계 자료, 영상 자료) */
            return jpaQueryFactory.select(lmsData.count())
                    .from(lmsData)
                    .where(getQuery(requestObject))
                    .orderBy(lmsData.createDateTime.desc())
                    .fetchOne();
        } else if(requestObject instanceof EventViewRequestVO) { /** 이벤트/설문 */
            return jpaQueryFactory.select(eventInfo.count())
                    .from(eventInfo)
                    .where(getQuery(requestObject))
                    .orderBy(eventInfo.createDateTime.desc())
                    .fetchOne();
        } else {
            return NumberUtils.LONG_ZERO;
        }
    }

    /**
     * Entity 리스트
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    private <T extends CSViewVOSupport> List<?> getEntityList(T requestObject) {
        if(requestObject instanceof SuggestionViewRequestVO) { /** 교육 주제 제안 */
            return jpaQueryFactory.selectFrom(educationSuggestion)
                    .where(getQuery(requestObject))
                    .orderBy(educationSuggestion.createDateTime.desc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch().stream().peek(data -> {
                        if (lmsUserRepository.findOne(Example.of(LmsUser.builder().userId(data.getRegistUserId()).build())).isPresent()) {
                            data.setSuggestUser(lmsUserRepository.findOne(Example.of(LmsUser.builder().userId(data.getRegistUserId()).build())).get().getUserName());
                        } else {
                            if (adminUserRepository.findOne(Example.of(AdminUser.builder().userId(data.getRegistUserId()).build())).isPresent()) {
                                data.setSuggestUser(adminUserRepository.findOne(Example.of(AdminUser.builder().userId(data.getRegistUserId()).build())).get().getUserName());
                            } else {
                                data.setSuggestUser("-");
                            }
                        }

                        if (data.getCommentUserId() != null) {
                            if (lmsUserRepository.findOne(Example.of(LmsUser.builder().userId(data.getCommentUserId()).build())).isPresent()) {
                                data.setCommentUser(lmsUserRepository.findOne(Example.of(LmsUser.builder().userId(data.getCommentUserId()).build())).get().getUserName());
                            } else {
                                if (adminUserRepository.findOne(Example.of(AdminUser.builder().userId(data.getCommentUserId()).build())).isPresent()) {
                                    data.setCommentUser(adminUserRepository.findOne(Example.of(AdminUser.builder().userId(data.getCommentUserId()).build())).get().getUserName());
                                } else {
                                    data.setCommentUser("-");
                                }
                            }
                        }
                    }).collect(Collectors.toList());
        } else if(requestObject instanceof ReviewViewRequestVO) { /** 교육 후기 */
            return jpaQueryFactory.selectFrom(educationReview)
                    .where(getQuery(requestObject))
                    .orderBy(educationReview.createDateTime.desc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch().stream().peek(data -> {
                        AdminUser user = jpaQueryFactory.selectFrom(adminUser)
                                .where(adminUser.userId.eq(data.getRegistUserId()))
                                .fetchOne();
                        if (user != null) {
                            data.setAdminUser(user);
                        } else {
                            AdminUser admin = jpaQueryFactory.selectFrom(adminUser)
                                    .where(adminUser.userId.eq("admin"))
                                    .fetchOne();
                            data.setAdminUser(admin);
                        }
                    }).collect(Collectors.toList());
        } else if(requestObject instanceof ArchiveClassGuideViewRequestVO) { /** 자료실(수업지도안) */
            List<ClassGuide> entities = jpaQueryFactory.selectFrom(classGuide)
                    .leftJoin(lmsUser).on(lmsUser.userId.eq(classGuide.registUserId))
                    .where(getQuery(requestObject))
                    .orderBy(classGuide.createDateTime.desc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch();

            if (!entities.isEmpty() && entities.size() > 0) {
                for (ClassGuide entity : entities) {
                    List<ClassGuideFile> guideFileList = jpaQueryFactory.selectFrom(classGuideFile)
                            .where(classGuideFile.classGuideCode.eq(entity.getClassGuideCode()), classGuideFile.fileType.eq(Code.GUI_FILE_TYPE.TEACH.enumCode))
                            .fetch();
                    if (guideFileList.size() > 0 && !guideFileList.isEmpty())
                        entity.setGuideFileList(guideFileList);

                    List<ClassGuideFile> activityFileList = jpaQueryFactory.selectFrom(classGuideFile)
                            .where(classGuideFile.classGuideCode.eq(entity.getClassGuideCode()), classGuideFile.fileType.eq(Code.GUI_FILE_TYPE.ACTIVITY.enumCode))
                            .fetch();
                    if (activityFileList.size() > 0 && !activityFileList.isEmpty())
                        entity.setActivityFileList(activityFileList);

                    List<ClassGuideFile> answerFileList = jpaQueryFactory.selectFrom(classGuideFile)
                            .where(classGuideFile.classGuideCode.eq(entity.getClassGuideCode()), classGuideFile.fileType.eq(Code.GUI_FILE_TYPE.ANSWER.enumCode))
                            .fetch();
                    if (answerFileList.size() > 0 && !answerFileList.isEmpty())
                        entity.setAnswerFileList(answerFileList);

                    List<ClassGuideFile> nieFileList = jpaQueryFactory.selectFrom(classGuideFile)
                            .where(classGuideFile.classGuideCode.eq(entity.getClassGuideCode()), classGuideFile.fileType.eq(Code.GUI_FILE_TYPE.NIE.enumCode))
                            .fetch();
                    if (nieFileList.size() > 0 && !nieFileList.isEmpty())
                        entity.setNieFileList(nieFileList);

                    LmsUser user = jpaQueryFactory.selectFrom(lmsUser)
                            .where(lmsUser.userId.eq(entity.getRegistUserId()))
                            .fetchOne();
                    if (user != null && !user.equals(null))
                        entity.setUserName(user.getUserName());
                    else
                        entity.setUserName("(사용자)");

                    if(entity.getReferenceSubject() != null) {
                        List<ClassSubject> subjects = new ArrayList<>();
                        String[] list = entity.getReferenceSubject().split(",");

                        for (String str : list) {
                            subjects.add(jpaQueryFactory.selectFrom(classSubject)
                                    .where(classSubject.individualCode.eq(str))
                                    .fetchOne());
                        }

                        if (subjects.size() > 0 && !subjects.isEmpty()) {
                            int n = 0;
                            for (ClassSubject subject : subjects) {
                                List<String> subjectStrList = new ArrayList<>();
                                String code = subject.getIndividualCode();

                                for (int i=0; i < subject.getDepth(); i++) {
                                    ClassSubject subjectSub = jpaQueryFactory.selectFrom(classSubject)
                                            .where(classSubject.individualCode.eq(code))
                                            .orderBy(classSubject.individualCode.desc())
                                            .fetchOne();

                                    if (subjectSub != null && !subjectSub.equals(null)) {
                                        code = subjectSub.getUpIndividualCode();
                                        subjectStrList.add(subjectSub.getContent());
                                    }
                                }

                                if (subjectStrList.size() > 0 && !subjectStrList.isEmpty()) {
                                    for (int i = subjectStrList.size() - 1; i >= 0; i--) {
                                        if (i == subjectStrList.size()-1) {
                                            code = new StringBuilder("").append(subjectStrList.get(i)).toString();
                                        } else {
                                            code = new StringBuilder(code).append(">").append(subjectStrList.get(i)).toString();
                                        }
                                    }
                                }
                                subjects.get(n).setCodeInfo(code);
                                n++;
                            }
                            entity.setClassSubjectList(subjects);
                        }
                    }
                }
            }
            return entities;

        } else if(requestObject instanceof ArchiveViewRequestVO) { /** 자료실(교육 자료, 연구/통계 자료, 영상 자료) */
            List<ArchiveCustomApiResponseVO> dtos = new ArrayList<>();
            dtos = jpaQueryFactory.select(Projections.fields(ArchiveCustomApiResponseVO.class,
                            lmsData.sequenceNo,
                            lmsData.teamCategory,
                            lmsData.materialCategory,
                            lmsData.materialType,
                            lmsData.title,
                            lmsData.contents,
                            lmsData.author,
                            lmsData.mediaFilePath,
                            lmsData.thumbFilePath,
                            lmsData.viewCount,
                            lmsData.createDateTime,
                            lmsData.registUserId,
                            lmsUser.userName
                    ))
                    .from(lmsData)
                    .leftJoin(lmsUser).on(lmsUser.userId.eq(lmsData.registUserId))
                    .where(getQuery(requestObject))
                    .orderBy(lmsData.createDateTime.desc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch()
                    .stream()
                    .peek(data -> data.setIsNew(new DateTime().minusDays(15).compareTo(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
                            .parseDateTime(data.getCreateDateTime())) < 0)).collect(Collectors.toList());

            if (dtos != null && dtos.size() > 0) {
                for (ArchiveCustomApiResponseVO dto : dtos) {
                    List<LmsDataFile> lmsDataFiles = lmsDataFileRepository.findAll(Example.of(LmsDataFile.builder()
                            .sequenceNo(dto.getSequenceNo())
                            .build()));
                    if (lmsDataFiles != null && lmsDataFiles.size() > 0)
                        dto.setLmsDataFiles(lmsDataFiles);

                    File filePath = new File(new StringBuilder(appConfig.getUploadFile().getUploadContextPath()).append(appConfig.getUploadFile().getLmsDataFolder())
                            .append("/")
                            .append(dto.getSequenceNo()).toString());
                    if (filePath.exists()) { /** 요청 폴더가 존재할 경우에만 파일 경로 저장 */
                        dto.setPublicationFile(FileUtils.listFiles(filePath, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE).stream()
                                .filter(data -> String.valueOf(data.getParent()).equals(String.valueOf(filePath)))
                                .map(data -> {
                                    if (data.getName().endsWith(".html")) {
                                        return data.getName();
                                    } else return null;
                                }).collect(Collectors.toList()).get(0));
                    }
                }
            }
            return dtos;

        } else if(requestObject instanceof EventViewRequestVO) { /** 이벤트/설문 */
            return jpaQueryFactory.selectFrom(eventInfo)
                    .where(getQuery(requestObject))
                    .orderBy(eventInfo.createDateTime.desc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch();
        } else if(requestObject instanceof MainViewRequestVO) { /** 메인(수업지도안) */
            return jpaQueryFactory.selectFrom(classGuide)
                    .where(getQuery(requestObject))
                    .orderBy(classGuide.createDateTime.desc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch();
        } else {
            return null;
        }
    }

    /**
     * where 절
     */
    private <T extends CSViewVOSupport> Predicate[] getQuery(T requestObject) {
        if(requestObject instanceof SuggestionViewRequestVO) {/** 교육 주제 제안 */
            return new Predicate[] { betweenTime(requestObject),
                    condition(((SuggestionViewRequestVO) requestObject).getSuggestionType(), educationSuggestion.suggestionType::eq)};
        } else if(requestObject instanceof ReviewViewRequestVO) { /** 교육 후기 */
            String[] types = null;
            if (((ReviewViewRequestVO) requestObject).getTypes() != null && !((ReviewViewRequestVO) requestObject).getTypes().isEmpty()) {
                types = ((ReviewViewRequestVO) requestObject).getTypes().split(",");
            }

            return new Predicate[] { betweenTime(requestObject),
                    condition(((ReviewViewRequestVO) requestObject).getSequenceNo(), educationReview.sequenceNo::eq),
                    condition(((ReviewViewRequestVO) requestObject).getCategory(), educationReview.category::eq),
                    condition(types, educationReview.type::in),
                    searchContainText(requestObject, ((ReviewViewRequestVO) requestObject).getContainText())};
        } else if(requestObject instanceof ArchiveClassGuideViewRequestVO) { /** 자료실(수업지도안) */
            String[] targets = null;
            if (((ArchiveClassGuideViewRequestVO) requestObject).getTargets() != null && !((ArchiveClassGuideViewRequestVO) requestObject).getTargets().isEmpty()) {
                targets = ((ArchiveClassGuideViewRequestVO) requestObject).getTargets().split(",");
            }

            String[] targetGrades = null;
            if (((ArchiveClassGuideViewRequestVO) requestObject).getTargetGrades() != null && !((ArchiveClassGuideViewRequestVO) requestObject).getTargetGrades().isEmpty()) {
                targetGrades = ((ArchiveClassGuideViewRequestVO) requestObject).getTargetGrades().split(",");
            }

            String[] years = null;
            if (((ArchiveClassGuideViewRequestVO) requestObject).getYears() != null && !((ArchiveClassGuideViewRequestVO) requestObject).getYears().isEmpty()) {
                years = ((ArchiveClassGuideViewRequestVO) requestObject).getYears().split(",");
            }

            String[] subjects = null;
            String[] classGuides = null;
            if (((ArchiveClassGuideViewRequestVO) requestObject).getSubjects() != null && !((ArchiveClassGuideViewRequestVO) requestObject).getSubjects().isEmpty()) {
                subjects = ((ArchiveClassGuideViewRequestVO) requestObject).getSubjects().split(",");

                List<String> classSubjectCodes = new ArrayList<>();
                for (int i=0; i<subjects.length; i++) {
                    classSubjectCodes.addAll(jpaQueryFactory.select(classSubject.individualCode)
                            .from(classSubject)
                            .where(classSubject.content.contains(subjects[i]))
                            .fetch());
                }
                String[] classSubjects = classSubjectCodes.toArray(new String[classSubjectCodes.size()]);
                List<String> classGuideCodes = jpaQueryFactory.select(classGuideSubject.classGuideCode)
                        .from(classGuideSubject)
                        .where(classGuideSubject.individualCode.in(classSubjects))
                        .fetch().stream().distinct().collect(Collectors.toList());
                classGuides = classGuideCodes.toArray(new String[classGuideCodes.size()]);
            }

            return new Predicate[] { betweenTime(requestObject),
                    condition(((ArchiveClassGuideViewRequestVO) requestObject).getClassGuideCode(), classGuide.classGuideCode::eq),
                    condition(((ArchiveClassGuideViewRequestVO) requestObject).getClassGuideType(), classGuide.classGuideType::eq),
                    condition(targets, classGuide.target::in),
                    condition(targetGrades, classGuide.targetGrade::in),
                    condition(years, classGuide.createDateTime.substring(0,4)::in),            // 생성 일자의 년도와 비교
                    condition(classGuides, classGuide.classGuideCode::in),            // 수업지도안의 관련교과 코드의 내용과 비교
                    condition("1", classGuide.classGuideApplyStatus::eq),            // 제출 상태
                    condition("1", classGuide.classGuideApprovalStatus::eq),            // 승인 상태
                    searchContainText(requestObject, ((ArchiveClassGuideViewRequestVO) requestObject).getContainText())};
        } else if(requestObject instanceof ArchiveViewRequestVO) { /** 자료실(기타 자료, 교재 자료, 연구/통계 자료, 영상 자료, 사업결과물) */
            String[] types = null;
            if (((ArchiveViewRequestVO) requestObject).getTypes() != null && !((ArchiveViewRequestVO) requestObject).getTypes().isEmpty()) {
                types = ((ArchiveViewRequestVO) requestObject).getTypes().split(",");
            }

            return new Predicate[] { betweenTime(requestObject),
                    condition(((ArchiveViewRequestVO) requestObject).getSequenceNo(), lmsData.sequenceNo::eq),
                    condition(((ArchiveViewRequestVO) requestObject).getTeamCategory(), lmsData.teamCategory::eq),
                    condition(((ArchiveViewRequestVO) requestObject).getMaterialCategory(), lmsData.materialCategory::eq),
                    condition(types, lmsData.materialType::in),
                    condition(true, lmsData.isUse::eq),
                    searchContainText(requestObject, ((ArchiveViewRequestVO) requestObject).getContainText())};
        } else if(requestObject instanceof EventViewRequestVO) { /** 이벤트/설문 */
            return new Predicate[] { betweenTime(requestObject),
                    condition(((EventViewRequestVO) requestObject).getSequenceNo(), eventInfo.sequenceNo::eq),
                    condition(((EventViewRequestVO) requestObject).getType(), eventInfo.type::eq),
                    searchContainText(requestObject, ((EventViewRequestVO) requestObject).getContainText())};
        } else if(requestObject instanceof MainViewRequestVO) { /** 메인(수업지도안) */
            return new Predicate[] {
                    condition("1", classGuide.classGuideApplyStatus::eq),   // 제출 상태
                    condition("1", classGuide.classGuideApprovalStatus::eq)};   // 승인 상태
        } else {
            return null;
        }
    }

    /** 조회 타입 별 포함 단어 조회 */
    private <T extends CSViewVOSupport> BooleanBuilder searchContainText(T requestObject, String containsText) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (hasText(containsText)) { /** 검색어가 존재할 경우 */
            if(requestObject instanceof SuggestionViewRequestVO) { /** 교육 주제 제안 */
                //To do: 포함 단어로 검색할 경우
            } else if(requestObject instanceof ReviewViewRequestVO) { /** 교육 후기 */
                switch (Code.CON_TEXT_TYPE.enumOfCode(((ReviewViewRequestVO) requestObject).getContainTextType())) {
                    case ALL:   /** 제목 + 내용 */
                        booleanBuilder.or(educationReview.title.contains(containsText));
                        booleanBuilder.or(educationReview.contents.contains(containsText));
                        break;
                    case TITLE: /** 제목 */
                        booleanBuilder.or(educationReview.title.contains(containsText));
                        break;
                    case CONTENTS:  /** 내용 */
                        booleanBuilder.or(educationReview.contents.contains(containsText));
                        break;
                }
            } else if(requestObject instanceof ArchiveClassGuideViewRequestVO) { /** 자료실(수업지도안) */
                switch (((ArchiveClassGuideViewRequestVO) requestObject).getContainTextType()) {
                    case "0":   /** 제목 + 내용 */
                        booleanBuilder.or(classGuide.title.contains(containsText));
                        booleanBuilder.or(classGuide.learningArea.contains(containsText));
                        booleanBuilder.or(classGuide.learningGoal.contains(containsText));
                        booleanBuilder.or(classGuide.learningMaterial.contains(containsText));
                        booleanBuilder.or(lmsUser.userName.contains(containsText));
                        break;
                    case "1": /** 제목 */
                        booleanBuilder.or(classGuide.title.contains(containsText));
                        break;
                    case "2":  /** 내용 */
                        booleanBuilder.or(classGuide.learningArea.contains(containsText));
                        booleanBuilder.or(classGuide.learningGoal.contains(containsText));
                        booleanBuilder.or(classGuide.learningMaterial.contains(containsText));
                        break;
                    case "3":  /** 작성자 */
                        booleanBuilder.or(lmsUser.userName.contains(containsText));
                        break;
                }
            } else if(requestObject instanceof ArchiveViewRequestVO) { /** 자료실(교육 자료, 연구/통계 자료, 영상 자료) */
                switch (Code.CON_TEXT_TYPE.enumOfCode(((ArchiveViewRequestVO) requestObject).getContainTextType())) {
                    case ALL:   /** 제목 + 내용 + 작성자 */
                        booleanBuilder.or(lmsData.title.contains(containsText));
                        booleanBuilder.or(lmsData.contents.contains(containsText));
                        booleanBuilder.or(lmsData.author.contains(containsText));
                        break;
                    case TITLE: /** 제목 */
                        booleanBuilder.or(lmsData.title.contains(containsText));
                        break;
                    case CONTENTS:  /** 내용 */
                        booleanBuilder.or(lmsData.contents.contains(containsText));
                        break;
                }
            } else if(requestObject instanceof EventViewRequestVO) { /** 이벤트/설문 */
                switch (Code.CON_TEXT_TYPE.enumOfCode(((EventViewRequestVO) requestObject).getContainTextType())) {
                    case ALL:   /** 제목 + 내용 */
                        booleanBuilder.or(eventInfo.title.contains(containsText));
                        booleanBuilder.or(eventInfo.contents.contains(containsText));
                        break;
                    case TITLE: /** 제목 */
                        booleanBuilder.or(eventInfo.title.contains(containsText));
                        break;
                    case CONTENTS:  /** 내용 */
                        booleanBuilder.or(eventInfo.contents.contains(containsText));
                        break;
                }
            }
        }
        return booleanBuilder;
    }

    /** 시간 대 검색 */
    private <T extends CSViewVOSupport> BooleanExpression betweenTime(T requestObject) {
        String startDateTime = Optional.ofNullable(requestObject.getStartDate())
                .map(date -> new StringBuilder(date).append(" 00:00:00").toString())
                .orElse("");
        String endDateTime = Optional.ofNullable(requestObject.getEndDate())
                .map(date -> new StringBuilder(date).append(" 23:59:59").toString())
                .orElse("");

        if((!StringUtils.isEmpty(startDateTime) && StringUtils.isEmpty(endDateTime))
                || (StringUtils.isEmpty(startDateTime) && !StringUtils.isEmpty(endDateTime))) {
            throw new KPFException(KPF_RESULT.ERROR9001, "조회 시작 일자 & 조회 종료 일자는 한가지만 존재할 수 없습니다.");
        } else if(!StringUtils.isEmpty(startDateTime) && !StringUtils.isEmpty(endDateTime)) {
            if(requestObject instanceof SuggestionViewRequestVO) {/** 교육 주제 제안 */
                return educationSuggestion.createDateTime.between(startDateTime, endDateTime);
            } else if(requestObject instanceof ArchiveClassGuideViewRequestVO) {/** 자료실(수업지도안) */
                return classGuide.createDateTime.between(startDateTime, endDateTime);
            } else if(requestObject instanceof ArchiveViewRequestVO) {/** 자료실(교육 자료, 연구/통계 자료, 영상 자료) */
                return lmsData.createDateTime.between(startDateTime, endDateTime);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
