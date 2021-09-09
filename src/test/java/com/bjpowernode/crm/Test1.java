package com.bjpowernode.crm;

import com.bjpowernode.crm.settings.web.controller.UserController;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ServiceFactory;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.sun.media.jfxmedia.events.PlayerTimeListener;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Test1 {

    @Test
    public void test1(){
        ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
        //Activity activity= activityDao.queryAll();
        Activity activity = activityDao.getActivityById("0260da0fee7e414eb80ef2ce614888c2");
        System.out.println(activity.toString());
    }



}
