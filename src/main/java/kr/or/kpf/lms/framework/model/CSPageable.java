package kr.or.kpf.lms.framework.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class CSPageable {

    @ApiModelProperty(value="페이지 번호(0..N)")
    private Integer page;

    @ApiModelProperty(value="페이지 크기", allowableValues="range[0, 100]")
    private Integer size;

//    @ApiModelProperty(value="정렬(사용법: 컬럼명, ASC | DESC)")
    @ApiModelProperty(value="정렬")
    private List<String> sort;
}
