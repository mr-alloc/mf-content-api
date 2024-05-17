package com.cofixer.mf.mfcontentapi.dto.req;

import com.cofixer.mf.mfcontentapi.constant.MissionStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class ChangeFamilyMissionReq {
    Long assignee;
    String title;
    Integer status;

    public boolean needChangeAssignee() {
        return assignee != null && assignee > 0;
    }

    public boolean needChangeTitle() {
        return title != null;
    }

    public boolean needChangeStatus() {
        return status != null && MissionStatus.has(status);
    }

    public boolean hasNotChanged() {
        return !needChangeAssignee() && !needChangeTitle() && !needChangeStatus();
    }
}
