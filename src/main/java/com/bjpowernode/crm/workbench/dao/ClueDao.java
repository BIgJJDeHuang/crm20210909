package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;

import java.util.List;
import java.util.Map;

public interface ClueDao {
    int save(Clue clue);

    int getTotalCount(Map<String, Object> map);

    List<Clue> getClueListByCondition(Map<String, Object> map);

    Clue getClueById(String id);

    int deleteRelationById(String id);

    int bund(ClueActivityRelation car);

    Clue getById(String clueId);

    int delete(String clueId);
}
