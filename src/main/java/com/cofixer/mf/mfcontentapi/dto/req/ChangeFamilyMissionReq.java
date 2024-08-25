package com.cofixer.mf.mfcontentapi.dto.req;

import com.cofixer.mf.mfcontentapi.constant.MissionStatus;
import com.cofixer.mf.mfcontentapi.constant.MissionType;
import com.cofixer.mf.mfcontentapi.util.StringUtil;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class ChangeFamilyMissionReq {
    Integer type;
    Long assignee;
    String title;
    String description;
    Integer status;
    Long deadline;
    Long categoryId;

    Long stateId;
    Long startStamp;

    public boolean needChangeType() {
        return type != null && MissionType.has(type);
    }

    public boolean needChangeDescription() {
        return StringUtil.isNotEmpty(description);
    }

    public boolean needChangeAssignee() {
        return assignee != null && assignee > 0;
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
        return !needChangeType() && !needChangeAssignee() && !needChangeTitle() && !needChangeStatus();
    }
}
