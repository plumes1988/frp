package com.figure.op.duty.domain.bo;

import lombok.Data;

@Data
public class ReportTemplateQueryBo {

    private Integer templateId;

    private String templateName;

    private String templateType;

    private String reportType;

}
