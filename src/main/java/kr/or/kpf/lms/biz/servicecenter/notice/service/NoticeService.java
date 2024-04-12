package kr.or.kpf.lms.biz.servicecenter.notice.service;

import kr.or.kpf.lms.biz.servicecenter.notice.vo.request.NoticeViewRequestVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.CommonServiceCenterRepository;
import kr.or.kpf.lms.repository.NoticeRepository;
import kr.or.kpf.lms.repository.entity.Notice;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

/**
 * 공지사항 관련 Service
 */
@Service
@RequiredArgsConstructor
public class NoticeService extends CSServiceSupport {

    private static final String NOTICE_IMG_TAG = "NOTICE";
    /** 고객센터 공통 Repository */
    private final CommonServiceCenterRepository commonServiceCenterRepository;
    /** 공지사항 Repository */
    private final NoticeRepository noticeRepository;

    /**
     * 공지사항 조회
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    public <T> Page<T> getNotice(NoticeViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonServiceCenterRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     * 공지사항 조회수 업데이트
     *
     * @param noticeSerialNo
     * @return
     */
    public CSResponseVOSupport updateViewCount(String noticeSerialNo) {

        Notice notice = noticeRepository.findOne(Example.of(Notice.builder().noticeSerialNo(noticeSerialNo).build()))
                            .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR7041, "존재하지 않는 공지사항"));

        /** 조회수 + 1 */
        notice.setViewCount(notice.getViewCount().add(BigInteger.ONE));
        noticeRepository.saveAndFlush(notice);

        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }
}
