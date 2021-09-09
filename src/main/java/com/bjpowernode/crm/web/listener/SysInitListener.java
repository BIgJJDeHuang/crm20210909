package com.bjpowernode.crm.web.listener;

import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DicService;
import com.bjpowernode.crm.settings.service.impl.DicServiceImpl;
import com.bjpowernode.crm.utils.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

public class SysInitListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("服务器缓存处理数据字典开始");

        ServletContext application = sce.getServletContext();

        DicService dicService = (DicService) ServiceFactory.getService(new DicServiceImpl());

        Map<String, List<DicValue>> map = dicService.getAll();

        Set<String> set = map.keySet();
        for(String key : set){
            application.setAttribute(key,map.get(key));
        }
        System.out.println("服务器缓存处理数据字典结束");

        Map<String,String> pMap = new HashMap<String, String>();
        ResourceBundle bundle = ResourceBundle.getBundle("Stage2Possibility");
        Enumeration e = bundle.getKeys();
        while(e.hasMoreElements()){
            String key = (String) e.nextElement();
            String value = bundle.getString(key);
            pMap.put(key,value);
        }
        application.setAttribute("pMap",pMap);

    }
}
