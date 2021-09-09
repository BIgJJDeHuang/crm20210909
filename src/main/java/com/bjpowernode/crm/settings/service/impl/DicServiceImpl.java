package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.dao.DicTypeDao;
import com.bjpowernode.crm.settings.dao.DicValueDao;
import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DicService;
import com.bjpowernode.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DicServiceImpl implements DicService {

    private DicTypeDao dicTypeDao = SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);
    private DicValueDao dicValueDao = SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);


    public Map<String, List<DicValue>> getAll() {
        Map<String,List<DicValue>> map = new HashMap<String, List<DicValue>>();

        List<DicType> dicTypeList = dicTypeDao.getTypeList();
        for(DicType dt : dicTypeList){

            String code = dt.getCode();
            List<DicValue> dicValueList= dicValueDao.getDicValueListByCode(code);
            map.put(code,dicValueList);
        }


        return map;
    }
}
