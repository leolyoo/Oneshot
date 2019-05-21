/*
  Copyright 2014-2017 Kakao Corp.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */
package com.example.a82102.oneshot;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * 추가로 받고자 하는 사용자 정보를 나타내는 layout
 * 이름, 나이, 성별을 입력할 수 있다.
 * @author MJ
 */
public class ExtraUserPropertyLayout extends FrameLayout {
    // property key
    private  static final String NAME_KEY = "name";
    private  static final String AGE_KEY = "age";
    private  static final String GENDER_KEY = "gender";

    private EditText name;
    private EditText age;
    private RadioGroup gender;
    private RadioButton male;
    private RadioButton female;

    public ExtraUserPropertyLayout(Context context) {
        super(context);
        initView();
    }

    public ExtraUserPropertyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ExtraUserPropertyLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        final View view = inflate(getContext(), R.layout.layout_usermgmt_extra_user_property, this);
        name = view.findViewById(R.id.name);
        age = view.findViewById(R.id.age);
        gender = view.findViewById(R.id.gender);
        male = view.findViewById(R.id.male);
        female = view.findViewById(R.id.female);
    }

    public HashMap<String, String> getProperties(){
        final String nickNameValue = name.getText().toString();
        final String ageValue = age.getText().toString();
        final String genderValue = getGender();

        HashMap<String, String> properties = new HashMap<>();
        properties.put(NAME_KEY, nickNameValue);
        properties.put(AGE_KEY, ageValue);
        if(genderValue != null)
            properties.put(GENDER_KEY, genderValue);

        return properties;
    }

    private String getGender() {
        if (gender.getCheckedRadioButtonId() == male.getId())
            return male.getText().toString();
        else if (gender.getCheckedRadioButtonId() == female.getId())
            return female.getText().toString();
        else
            return null;
    }

    void showProperties(final Map<String, String> properties) {
        final String nameValue = properties.get(NAME_KEY);
        if (nameValue != null)
            name.setText(nameValue);

        final String ageValue = properties.get(AGE_KEY);
        if (ageValue != null)
            age.setText(ageValue);

        final String genderValue = properties.get(GENDER_KEY);
        if (genderValue != null) {
            if (genderValue.equalsIgnoreCase(male.getText().toString())) {
                gender.check(male.getId());
            } else if (genderValue.equalsIgnoreCase(female.getText().toString())) {
                gender.check(female.getId());
            }
        }
    }


}
