package com.itccloud.mapper;

import com.itccloud.model.Fan;
import com.itccloud.model.Preference;
import com.itccloud.model.PreferenceEntry;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PreferenceMapper {
    int countPreferencesByStand(String standId);
    void insertFan(Fan fan);
    void insertPreference(Preference preference);
    int findDuplicateFan(String firstName, String lastName);
    void clearPreferences();
    List<PreferenceEntry> findAllPreferences();
}
