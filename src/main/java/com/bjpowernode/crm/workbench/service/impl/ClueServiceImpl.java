package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.vo.PaginationVo;
import com.bjpowernode.crm.workbench.dao.*;
import com.bjpowernode.crm.workbench.domain.*;
import com.bjpowernode.crm.workbench.service.ClueService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ClueServiceImpl implements ClueService {

    ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    ClueActivityRelationDao clueActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);
    ClueRemarkDao clueRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ClueRemarkDao.class);

    TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);

    CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
    CustomerRemarkDao customerRemarkDao = SqlSessionUtil.getSqlSession().getMapper(CustomerRemarkDao.class);

    ContactsDao contactsDao = SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);
    ContactsRemarkDao contactsRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ContactsRemarkDao.class);
    ContactsActivityRelationDao contactsActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ContactsActivityRelationDao.class);


    public boolean save(Clue clue) {
        boolean flag = true;
        int count = clueDao.save(clue);
        if(count == 0){
            flag = false;
        }

        return true;
    }

    public PaginationVo pageList(Map<String, Object> map) {
        PaginationVo vo = new PaginationVo();

        int total = clueDao.getTotalCount(map);
        List<Clue> dataList = clueDao.getClueListByCondition(map);

        vo.setTotal(total);
        vo.setDataList(dataList);

        return vo;
    }

    public Clue detail(String id) {

        Clue clue = clueDao.getClueById(id);

        return clue;
    }

    public boolean unbund(String id) {
        boolean flag = true;
        int count = clueDao.deleteRelationById(id);
        if(count == 0){
            flag = false;
        }

        return flag;
    }

    public boolean bund(String[] aids, String cid) {
        boolean flag = true;
        for(String aid:aids){
            String id = UUIDUtil.getUUID();
            ClueActivityRelation car = new ClueActivityRelation();
            car.setId(id);
            car.setClueId(cid);
            car.setActivityId(aid);
            int count = clueDao.bund(car);
            if(count != 1){
                flag = false;
            }

        }
        return flag;
    }

    public Boolean convert(String clueId, Tran t, String createBy) {
        Boolean flag = true;
        String createTime = DateTimeUtil.getSysTime();


        //(1) ???????????????id???????????????id??????????????????????????????????????????????????????????????????
        Clue clue = clueDao.getById(clueId);

        //(2) ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        String company = clue.getCompany();
        Customer customer = customerDao.getCustomerByName(company);

        if(customer == null){

            customer  = new Customer();

            customer.setName(company);
            customer.setAddress(clue.getAddress());
            customer.setCreateBy(createBy);
            customer.setId(UUIDUtil.getUUID());
            customer.setContactSummary(clue.getContactSummary());
            customer.setDescription(clue.getDescription());
            customer.setNextContactTime(clue.getNextContactTime());
            customer.setOwner(clue.getOwner());
            customer.setPhone(clue.getPhone());

            int count1 = customerDao.save(customer);
            if(count1 != 1){
                flag = false;
            }
        }

        //(3) ?????????????????????????????????????????????????????????

        Contacts contacts = new Contacts();
        contacts.setId(UUIDUtil.getUUID());
        contacts.setAddress(clue.getAddress());
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setCreateBy(createBy);
        contacts.setAppellation(clue.getAppellation());
        contacts.setDescription(clue.getDescription());
        contacts.setCreateTime(createTime);
        contacts.setCustomerId(customer.getId());
        contacts.setEmail(clue.getEmail());
        contacts.setFullname(clue.getFullname());
        contacts.setSource(clue.getSource());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setOwner(clue.getOwner());

        int count2 = contactsDao.save(contacts);
        if(count2 != 1){
            flag = false;
        }

        //(4) ??????????????????????????????????????????????????????
        List<ClueRemark> clueRemarkList = clueRemarkDao.getRemarkByClueId(clueId);

        for(ClueRemark clueRemark : clueRemarkList){
            String noteContent = clueRemark.getNoteContent();

            //?????????????????????
            CustomerRemark customerRemark = new CustomerRemark();
            customerRemark.setNoteContent(noteContent);
            customerRemark.setCustomerId(customer.getId());
            customerRemark.setCreateBy(createBy);
            customerRemark.setCreateTime(createTime);
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setEditFlag("0");
            int count3 = customerRemarkDao.save(customerRemark);
            if(count3 != 1){
                flag = false;
            }

            //????????????????????????
            ContactsRemark contactsRemark = new ContactsRemark();
            contactsRemark.setNoteContent(noteContent);
            contactsRemark.setContactsId(contacts.getId());
            contactsRemark.setCreateBy(createBy);
            contactsRemark.setCreateTime(createTime);
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setEditFlag("0");
            int count4 = contactsRemarkDao.save(contactsRemark);
            if(count4 != 1){
                flag = false;
            }
        }

        //(5) ????????????????????????????????????????????????????????????????????????????????????
        List<ClueActivityRelation> clueActivityRelations = clueActivityRelationDao.getListByClueId(clueId);

        for(ClueActivityRelation clueActivityRelation : clueActivityRelations){
            String activityId = clueActivityRelation.getActivityId();

            ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setActivityId(activityId);
            contactsActivityRelation.setContactsId(contacts.getId());

            int count5 = contactsActivityRelationDao.save(contactsActivityRelation);
            if(count5 != 1){
                flag = false;
            }
        }

        //(6) ????????????????????????????????????????????????
        if(t != null){
            t.setSource(clue.getSource());
            t.setOwner(clue.getOwner());
            t.setNextContactTime(clue.getNextContactTime());
            t.setDescription(clue.getDescription());
            t.setCustomerId(customer.getId());
            t.setContactsId(contacts.getId());
            t.setContactSummary(clue.getContactSummary());

            int count6 = tranDao.save(t);
            if(count6 != 1){
                flag = false;
            }

            //(7) ??????????????????????????????????????????????????????????????????
            TranHistory tranHistory = new TranHistory();
            tranHistory.setCreateBy(createBy);
            tranHistory.setCreateTime(createTime);
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setTranId(t.getId());
            tranHistory.setMoney(t.getMoney());
            tranHistory.setExpectedDate(t.getExpectedDate());
            tranHistory.setStage(t.getStage());

            int count7 = tranHistoryDao.save(tranHistory);
            if(count7 != 1){
                flag = false;
            }

        }

        //(8) ??????????????????
        for(ClueRemark clueRemark:clueRemarkList){
            int count8 = clueRemarkDao.delete(clueRemark);
            if(count8 != 1){
                flag = false;
            }
        }

        //(9) ????????????????????????????????????
        for(ClueActivityRelation clueActivityRelation : clueActivityRelations){
            int count9 = clueActivityRelationDao.delete(clueActivityRelation);
            if(count9 != 1){
                flag = false;
            }
        }

        //(10) ????????????
        int count10 = clueDao.delete(clueId);
        if(count10 != 1){
            flag = false;
        }

        return flag;
    }


}
