package com.cofixer.mf.mfcontentapi.dto.req;

import com.cofixer.mf.mfcontentapi.constant.MissionStatus;
import com.cofixer.mf.mfcontentapi.constant.MissionType;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ChangeMissionReq {
    Integer type;
    String title;
    Integer status;
    Long deadline;

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

    public boolean hasNotChanged() {
        return !needChangeType() && !needChangeTitle() && !needChangeStatus() && !needChangeDeadline();
    }
}
