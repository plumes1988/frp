package com.figure.op.system.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 *
 * @author fsn
 */
@Data
public class KnowInfoQueryBo {

    private Integer knowId;

    private String topic;

    private String knowKeywords;

    private Integer deviceId;

    private String device;

    private String content;

    private Integer updateUserId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startUpdateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endUpdateTime;

}
