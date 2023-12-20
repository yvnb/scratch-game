package com.teetov.scratch.in.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public class WinCombination {

    public WinCombination() {
    }

    public WinCombination(
            BigDecimal rewardMultiplier,
            String when,
            Integer count,
            String group,
            List<List<String>> coveredAreas
    ) {
        this.rewardMultiplier = rewardMultiplier;
        this.when = when;
        this.count = count;
        this.group = group;
        this.coveredAreas = coveredAreas;
    }

    @JsonProperty("reward_multiplier")
    private BigDecimal rewardMultiplier;
    private String when;
    private Integer count;
    private String group;
    @JsonProperty("covered_areas")
    private List<List<String>> coveredAreas;

    public BigDecimal getRewardMultiplier() {
        return rewardMultiplier;
    }

    public void setRewardMultiplier(BigDecimal rewardMultiplier) {
        this.rewardMultiplier = rewardMultiplier;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<List<String>> getCoveredAreas() {
        return coveredAreas;
    }

    public void setCoveredAreas(List<List<String>> coveredAreas) {
        this.coveredAreas = coveredAreas;
    }

    @Override
    public String toString() {
        return "WinCombination{" +
                "rewardMultiplier=" + rewardMultiplier +
                ", when='" + when + '\'' +
                ", count=" + count +
                ", group='" + group + '\'' +
                ", coveredAreas=" + coveredAreas +
                '}';
    }
}
