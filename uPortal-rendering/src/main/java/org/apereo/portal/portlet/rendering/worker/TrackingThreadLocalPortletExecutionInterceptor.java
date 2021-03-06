/**
 * Licensed to Apereo under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright ownership. Apereo
 * licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at the
 * following location:
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apereo.portal.portlet.rendering.worker;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apereo.portal.utils.threading.TrackingThreadLocal;
import org.springframework.stereotype.Service;

/**
 * Copies {@link TrackingThreadLocal} data from the portal request thread into the portlet execution
 * thread and cleans up afterwards.
 */
@Service("TrackingThreadLocalPortletExecutionInterceptor")
public class TrackingThreadLocalPortletExecutionInterceptor
        extends ThreadLocalPortletExecutionInterceptor<Map<TrackingThreadLocal<Object>, Object>> {

    @Override
    protected Map<TrackingThreadLocal<Object>, Object> getThreadLocalValue(
            HttpServletRequest request,
            HttpServletResponse response,
            IPortletExecutionContext context) {
        return TrackingThreadLocal.getCurrentData();
    }

    @Override
    protected void setThreadLocalValue(
            HttpServletRequest request,
            HttpServletResponse response,
            IPortletExecutionContext context,
            Map<TrackingThreadLocal<Object>, Object> value) {
        if (value == null || value.size() == 0) {
            TrackingThreadLocal.clearCurrentData();
        } else {
            TrackingThreadLocal.setCurrentData(value);
        }
    }
}
