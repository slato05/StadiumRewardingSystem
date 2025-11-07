package com.itccloud.mapper;

import com.itccloud.model.Reward;
import com.itccloud.model.RewardResult;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RewardMapper {

    void insertReward(Reward reward);

    void clearRewards();

    List<RewardResult> findRewardResults();
}
