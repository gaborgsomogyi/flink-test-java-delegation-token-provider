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
import org.apache.flink.core.security.token.DelegationTokenProvider;
import org.apache.flink.util.InstantiationUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;

public class FlinkTestJavaDelegationTokenProvider implements DelegationTokenProvider {

    // This should normally come from the token itself
    private static final long TOKEN_RENEWAL_PERIOD = 60000;

    private static final Logger LOG =
            LoggerFactory.getLogger(FlinkTestJavaDelegationTokenProvider.class);

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
    public boolean delegationTokensRequired() {
        LOG.info("delegationTokensRequired");
        return true;
    }

    @Override
    public ObtainedDelegationTokens obtainDelegationTokens() throws Exception {
        LOG.info("obtainDelegationTokens");

        if (counter++ % 4 == 1) {
            throw new RuntimeException(
                    "Intended exception to simulate temporary token obtain failure");
        }

        String identifier =
                FlinkTestJavaCustomDelegationToken.CLASS_NAME + "_identifier_" + UUID.randomUUID();
        String password =
                FlinkTestJavaCustomDelegationToken.CLASS_NAME + "_password_" + UUID.randomUUID();
        FlinkTestJavaCustomDelegationToken token =
                new FlinkTestJavaCustomDelegationToken(identifier, password);

        return new ObtainedDelegationTokens(
                InstantiationUtil.serializeObject(token),
                Optional.of(System.currentTimeMillis() + TOKEN_RENEWAL_PERIOD));
    }
}
