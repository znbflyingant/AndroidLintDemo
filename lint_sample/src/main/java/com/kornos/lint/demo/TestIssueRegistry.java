/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kornos.lint.demo;

import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.ApiKt;
import com.android.tools.lint.detector.api.Issue;
import com.kornos.lint.demo.other.AttrPrefixDetector;

import java.util.Arrays;
import java.util.List;

/*
 * The list of issues that will be checked when running <code>lint</code>.
 */
public class TestIssueRegistry extends IssueRegistry {

    @Override
    public List<Issue> getIssues() {
        return Arrays.asList(
//                RouteDetector.ISSUE,
//                RouteDetector.CALL_ISSUE,
//                LogDetector.ISSUE,
//                GlideUnusedDetector.ISSUE,
//                ThreadDetector.ISSUE,
//                PngResourceDetector.ISSUE,
//                EventSpaceDetector.ISSUE,
//                SerializableDetector.ISSUE,
                AttrPrefixDetector.ISSUE
        );
    }

    @Override
    public int getApi() {
        return ApiKt.CURRENT_API;
    }
}

