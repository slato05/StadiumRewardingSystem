package com.itccloud.mapper;

import com.itccloud.model.Fan;
import com.itccloud.model.Preference;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PreferenceMapper {
    void insertFan(Fan fan);
    void insertPreference(Preference preference);
    int findDuplicateFan(String firstName, String lastName);
    void clearPreferences();
}
