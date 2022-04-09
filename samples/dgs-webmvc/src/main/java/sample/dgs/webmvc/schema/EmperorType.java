/*
 * Copyright 2017 Alexey Zhokhov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sample.dgs.webmvc.schema;

import java.time.LocalDate;

/**
 * @author Alexey Zhokhov
 */
public class EmperorType {

    private final String givenName;
    private final String title;
    private final LocalDate reignStart;
    private final LocalDate reignStop;

    public EmperorType(
            String givenName,
            String title,
            LocalDate reignStart,
            LocalDate reignStop
    ) {
        this.givenName = givenName;
        this.title = title;
        this.reignStart = reignStart;
        this.reignStop = reignStop;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getReignStart() {
        return reignStart;
    }

    public LocalDate getReignStop() {
        return reignStop;
    }

    @Deprecated
    public String getName() {
        return getGivenName();
    }

}
