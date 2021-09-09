package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.vo.PaginationVo;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityService {

    Boolean save(Activity activity);

    PaginationVo<Activity> pageList(Map<String, Object> map);

    boolean delete(String[] ids);

    Map<String, Object> getUserListAndActivity(String id);

    Boolean update(Activity activity);

    Activity detail(String id);

    List<ActivityRemark> getRemarkListByAid(String activityId);

    boolean deleteRemarkById(String remarkId);

    boolean addRemark(ActivityRemark remark);

    ActivityRemark getNoteContent(String id);

    Map<String,Object> updateRemark(ActivityRemark remark);

    List<Activity> getActivityListByClueId(String id);

    List<Activity> getActivityListByActivityName(Map<String, Object> map);

    List<Activity> getActivityListForConvert(String aname);
}
