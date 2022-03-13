/*
 *  Copyright 2017 Alexey Zhokhov
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package graphql.datetime.sample.kickstart.webflux

import org.apache.commons.text.StringEscapeUtils
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KickstartWebFluxSampleApplicationTests {

    @Autowired TestRestTemplate restTemplate

    @Test
    void "basic usage"() {
        // GIVEN:
        String graphQlQuery = """
{
  emperors {
    givenName
    title
    reignStart
    reignStop
  }
  now
  yesterday
  tomorrowMidnight
  noonTime
}
""".trim()

        // WHEN:
        String json = """
{
    "query": "${StringEscapeUtils.escapeJson(graphQlQuery)}"
}
"""

        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)

        HttpEntity entity = new HttpEntity(json, headers)

        ResponseEntity<Map> response = restTemplate.postForEntity('/graphql', entity, Map.class)

        // THEN:
        assert response
        assert response.statusCode == HttpStatus.OK
        assert response.body
        assert response.body.data
        assert response.body.data.emperors.size() == 5

        def emperor = response.body.data.emperors[0]

        assert emperor.givenName == '旻寧'
        assert emperor.title == 'Daoguang Emperor'
        assert emperor.reignStart == '1820-10-03'
        assert emperor.reignStop == '1850-02-25'

        emperor = response.body.data.emperors[1]

        assert emperor.givenName == '奕詝'
        assert emperor.title == 'Xianfeng Emperor'
        assert emperor.reignStart == '1850-03-09'
        assert emperor.reignStop == '1861-08-22'

        emperor = response.body.data.emperors[2]

        assert emperor.givenName == '載淳'
        assert emperor.title == 'Tongzhi Emperor'
        assert emperor.reignStart == '1861-11-11'
        assert emperor.reignStop == '1875-01-12'

        emperor = response.body.data.emperors[3]

        assert emperor.givenName == '載湉'
        assert emperor.title == 'Guangxu Emperor'
        assert emperor.reignStart == '1875-02-25'
        assert emperor.reignStop == '1908-11-14'

        emperor = response.body.data.emperors[4]

        assert emperor.givenName == '溥儀'
        assert emperor.title == 'Xuantong Emperor'
        assert emperor.reignStart == '1908-11-14'
        assert emperor.reignStop == '1912-02-12'

        LocalDateTime.parse(response.body.data.now, DateTimeFormatter.ISO_DATE_TIME)

        assert response.body.data.yesterday == LocalDate.now().minusDays(1).toString()
        assert response.body.data.tomorrowMidnight == DateTimeFormatter.ISO_DATE_TIME.format(
                LocalDate.now().plusDays(1).atStartOfDay().atZone(ZoneOffset.UTC)
        )
        assert response.body.data.noonTime == "12:00:00"
    }

}
