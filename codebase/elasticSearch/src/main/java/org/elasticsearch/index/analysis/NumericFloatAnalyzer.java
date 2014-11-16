/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.index.analysis;

import com.carrotsearch.hppc.IntObjectOpenHashMap;

import java.io.IOException;

/**
 *
 */
public class NumericFloatAnalyzer extends NumericAnalyzer<NumericFloatTokenizer> {

    private final static IntObjectOpenHashMap<NamedAnalyzer> builtIn;

    static {
        builtIn = new IntObjectOpenHashMap<>();
        builtIn.put(Integer.MAX_VALUE, new NamedAnalyzer("_float/max", AnalyzerScope.GLOBAL, new NumericFloatAnalyzer(Integer.MAX_VALUE)));
        for (int i = 0; i <= 64; i += 4) {
            builtIn.put(i, new NamedAnalyzer("_float/" + i, AnalyzerScope.GLOBAL, new NumericFloatAnalyzer(i)));
        }
    }

    public static NamedAnalyzer buildNamedAnalyzer(int precisionStep) {
        NamedAnalyzer namedAnalyzer = builtIn.get(precisionStep);
        if (namedAnalyzer == null) {
            namedAnalyzer = new NamedAnalyzer("_float/" + precisionStep, AnalyzerScope.INDEX, new NumericFloatAnalyzer(precisionStep));
        }
        return namedAnalyzer;
    }

    private final int precisionStep;

    public NumericFloatAnalyzer(int precisionStep) {
        this.precisionStep = precisionStep;
    }

    @Override
    protected NumericFloatTokenizer createNumericTokenizer(char[] buffer) throws IOException {
        return new NumericFloatTokenizer(precisionStep, buffer);
    }
}