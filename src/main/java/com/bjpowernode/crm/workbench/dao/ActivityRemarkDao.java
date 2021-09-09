package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {

    int getCountByAids(String[] ids);

    int deleteByAids(String[] ids);

    int addRemark(ActivityRemark remark);

    List<ActivityRemark> getRemarkListByAid(String activityId);

    int deleteRemarkById(String remarkId);

    ActivityRemark getNoteContent(String id);

    int updateRemark(ActivityRemark remark);
}
