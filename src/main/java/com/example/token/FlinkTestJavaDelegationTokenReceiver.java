/*
 * Licensed to Cloudera, Inc. under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.token;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.security.token.DelegationTokenReceiver;
import org.apache.flink.util.InstantiationUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlinkTestJavaDelegationTokenReceiver implements DelegationTokenReceiver {

    private static final Logger LOG =
            LoggerFactory.getLogger(FlinkTestJavaDelegationTokenReceiver.class);

    private int counter = 0;

    @Override
    public String serviceName() {
        return FlinkTestJavaCustomDelegationToken.CLASS_NAME;
    }

    @Override
    public void init(Configuration configuration) {
        LOG.info("init");
    }

    @Override
    public void onNewTokensObtained(byte[] bytes) throws Exception {
        LOG.info("onNewTokensObtained");

        if (counter++ % 4 == 1) {
            throw new RuntimeException(
                    "Intended exception to simulate temporary token receive failure");
        }

        FlinkTestJavaCustomDelegationToken token =
                InstantiationUtil.deserializeObject(
                        bytes, FlinkTestJavaCustomDelegationToken.class.getClassLoader());
        LOG.info(token.toString());
    }
}
