package kr.or.kpf.lms.common.support;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.framework.model.CSSearchMap;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Data
public abstract class CSViewVOSupport {

    /** 검색 시작일 */
    @Schema(name="검색 시작일", example="2022-09-01")
    @JsonAlias(value={"startDate"})
    private String startDate;

    /**검색 종료일 */
    @Schema(name="검색 종료일", example="2022-09-30")
    @JsonAlias(value={"endDate"})
    private String endDate;

    @Schema(hidden = true)
    @JsonIgnore
    private Pageable pageable;
    public void setPageable(PageRequest pageRequest) { this.pageable = pageRequest; }
    public void setPageable(Pageable pageRequest) { this.pageable = pageRequest; }

    /** 페이지 번호 */
    @Schema(hidden = true)
    @JsonIgnore
    private Integer pageNum;
    public int getPageNum() { return this.pageable.getPageNumber(); }

    /** 페이지 당 데이터 수 */
    @Schema(hidden = true)
    @JsonIgnore
    private Integer pageSize;
    public int getPageSize() { return this.pageable.getPageSize(); }

    /**
     * Map 객체 -> VO 객체 변환
     *
     * @param searchMap
     * @param pageable
     * @return
     */
    public static <T extends CSViewVOSupport> Object requestParameterSetting(Class<?> object, CSSearchMap searchMap, Pageable pageable) {
        Map<String, Object> convertMap = new HashMap<>();
        searchMap.entrySet().stream().filter(entry -> StringUtils.isNotEmpty(String.valueOf(entry.getValue()))).forEach(entry -> convertMap.put(entry.getKey(), entry.getValue()));
        T params = new Gson().fromJson(new Gson().toJson(convertMap), (Type) object);
        params.setPageable(pageable);
        return params;
    }

}
