package com.cofixer.mf.mfcontentapi.dto.req;

import com.cofixer.mf.mfcontentapi.constant.MissionStatus;
import com.cofixer.mf.mfcontentapi.constant.MissionType;
import com.cofixer.mf.mfcontentapi.util.StringUtil;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ChangeMissionReq {
    Integer type;
    String title;
    String description;
    Integer status;
    Long deadline;
    Long categoryId;

    Long stateId;
    Long startStamp;

    public boolean needChangeDescription() {
        return StringUtil.isNotEmpty(description);
    }

    public boolean needChangeType() {
        return type != null && MissionType.has(type);
    }

    public boolean needChangeTitle() {
        return title != null;
    }

    public boolean needChangeStatus() {
        return status != null && MissionStatus.has(status);
    }

    public boolean needChangeDeadline() {
        return deadline != null && deadline > 0;
    }

    public boolean needChangeCategoryId() {
        return categoryId != null && categoryId > 0;
    }

    public boolean hasNotChanged() {
        return !needChangeDescription() && !needChangeType() && !needChangeTitle() && !needChangeStatus() && !needChangeDeadline() && !needChangeCategoryId();
    }
}
