package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.vo.PaginationVo;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.dao.ActivityRemarkDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {

    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    public Boolean save(Activity activity) {

        Boolean success = false;
        int count = activityDao.save(activity);
        if(count == 1){
            success = true;
        }

        return success;


    }

    public PaginationVo<Activity> pageList(Map<String, Object> map) {

        int total = activityDao.getTotalByCondition(map);

        List<Activity> list = activityDao.getActivityListByCondition(map);

        PaginationVo<Activity> vo = new PaginationVo<Activity>();

        vo.setTotal(total);
        vo.setDataList(list);

        return vo;
    }

    //目的：删除市场活动表中的记录时，同时也要删除市场活动备注表中的想关联的记录
    public boolean delete(String[] ids) {
        boolean flag = true;
        //查询出需要删除的备注的数量
        int count1 = activityRemarkDao.getCountByAids(ids);
        //删除备注，返回受影响的条数
        int count2 = activityRemarkDao.deleteByAids(ids);
        //删除市场活动
        if(count1 != count2){
            flag = false;
        }
        int count3 = activityDao.delete(ids);
       if(ids.length != count3){
           flag = false;
       }
        System.out.println("删除成功了吗？"+flag);
        return flag;

    }

    public Map<String, Object> getUserListAndActivity(String id) {
        Map<String,Object> map = new HashMap<String, Object>();
        List<User> userList = userDao.queryAll();

        Activity activity = activityDao.getActivityById(id);

        map.put("userList",userList);
        map.put("activity",activity);
        return map;
    }

    public Boolean update(Activity activity) {
        boolean flag = true;

        int count = activityDao.update(activity);

        if(count != 1){
            flag = false;
        }

        return flag;
    }

    public Activity detail(String id) {

        Activity activity = activityDao.detail(id);

        return activity;
    }

    public List<ActivityRemark> getRemarkListByAid(String activityId) {

        List<ActivityRemark> activityRemarks = activityRemarkDao.getRemarkListByAid(activityId);

        return activityRemarks;
    }

    public boolean deleteRemarkById(String remarkId) {
        boolean flag = true;
        int count = activityRemarkDao.deleteRemarkById(remarkId);
        if(count != 1){
            flag = false;
        }

        return flag;
    }

    public boolean addRemark(ActivityRemark remark) {
        boolean flag = true;
        int count = activityRemarkDao.addRemark(remark);
        if(count == 0){
            flag = false;
        }

        return flag;
    }

    public ActivityRemark getNoteContent(String id) {

        ActivityRemark remark = activityRemarkDao.getNoteContent(id);

        return remark;
    }

    public Map<String,Object> updateRemark(ActivityRemark remark) {
        boolean flag = true;
        int count = activityRemarkDao.updateRemark(remark);
        ActivityRemark activityRemark = activityRemarkDao.getNoteContent(remark.getId());

        if(count == 0){
            flag = false;
        }
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("success",flag);
        map.put("activityRemark",activityRemark);

        return map;
    }

    public List<Activity> getActivityListByClueId(String id) {

        List<Activity> activityList = activityDao.getActivityListByClueId(id);

        return activityList;
    }

    public List<Activity> getActivityListForConvert(String aname) {
        List<Activity> activityList = activityDao.getActivityListForConvert(aname);

        return activityList;
    }

    public List<Activity> getActivityListByActivityName(Map map) {

        List<Activity> aList = activityDao.getActivityListByActivityName(map);

        return aList;
    }


}
