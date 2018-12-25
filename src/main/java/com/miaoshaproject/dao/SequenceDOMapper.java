package com.miaoshaproject.dao;

import com.miaoshaproject.dataobject.SequenceDO;

public interface SequenceDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated Sun Dec 23 21:51:10 CST 2018
     */
    SequenceDO getSequenceByName(String name);
    int updateByPrimaryKeySelective(SequenceDO record);
    SequenceDO selectByPrimaryKey(String name);
    int insert(SequenceDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated Sun Dec 23 21:51:10 CST 2018
     */
    int insertSelective(SequenceDO record);


}