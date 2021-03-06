package com.bacon.demo.utils;
/*
 * Copyright (c) 2017 Selva.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.util.AttributeSet;

/**
 * Created by serajam on 10/15/2017.
 */

public class HtmlTextView extends AppCompatTextView {

    public HtmlTextView(Context context,  @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setHtmlText(String text) {
        if (text != null) {
            setText(Html.fromHtml(text));
        } else {
            setText("");
        }
    }


}
