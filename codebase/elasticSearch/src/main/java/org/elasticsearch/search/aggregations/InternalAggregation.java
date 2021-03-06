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
package org.elasticsearch.search.aggregations;

import org.elasticsearch.Version;
import org.elasticsearch.common.bytes.BytesArray;
import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.io.stream.Streamable;
import org.elasticsearch.common.util.BigArrays;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentBuilderString;
import org.elasticsearch.script.ScriptService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * An internal implementation of {@link Aggregation}. Serves as a base class for all aggregation implementations.
 */
public abstract class InternalAggregation implements Aggregation, ToXContent, Streamable {


    /**
     * The aggregation type that holds all the string types that are associated with an aggregation:
     * <ul>
     *     <li>name - used as the parser type</li>
     *     <li>stream - used as the stream type</li>
     * </ul>
     */
    public static class Type {

        private String name;
        private BytesReference stream;

        public Type(String name) {
            this(name, new BytesArray(name));
        }

        public Type(String name, String stream) {
            this(name, new BytesArray(stream));
        }

        public Type(String name, BytesReference stream) {
            this.name = name;
            this.stream = stream;
        }

        /**
         * @return The name of the type (mainly used for registering the parser for the aggregator (see {@link org.elasticsearch.search.aggregations.Aggregator.Parser#type()}).
         */
        public String name() {
            return name;
        }

        /**
         * @return  The name of the stream type (used for registering the aggregation stream
         *          (see {@link AggregationStreams#registerStream(AggregationStreams.Stream, org.elasticsearch.common.bytes.BytesReference...)}).
         */
        public BytesReference stream() {
            return stream;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static class ReduceContext {

        private final List<InternalAggregation> aggregations;
        private final BigArrays bigArrays;
        private ScriptService scriptService;

        public ReduceContext(List<InternalAggregation> aggregations, BigArrays bigArrays, ScriptService scriptService) {
            this.aggregations = aggregations;
            this.bigArrays = bigArrays;
            this.scriptService = scriptService;
        }

        public List<InternalAggregation> aggregations() {
            return aggregations;
        }

        public BigArrays bigArrays() {
            return bigArrays;
        }
        
        public ScriptService scriptService() {
            return scriptService;
        }
    }


    protected String name;

    protected Map<String, Object> metaData;

    /** Constructs an un initialized addAggregation (used for serialization) **/
    protected InternalAggregation() {}

    /**
     * Constructs an get with a given name.
     *
     * @param name The name of the get.
     */
    protected InternalAggregation(String name, Map<String, Object> metaData) {
        this.name = name;
        this.metaData = metaData;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * @return The {@link Type} of this aggregation
     */
    public abstract Type type();

    /**
     * Reduces the given addAggregation to a single one and returns it. In <b>most</b> cases, the assumption will be the all given
     * addAggregation are of the same type (the same type as this aggregation). For best efficiency, when implementing,
     * try reusing an existing get instance (typically the first in the given list) to save on redundant object
     * construction.
     */
    public abstract InternalAggregation reduce(ReduceContext reduceContext);

    /**
     * Read a size under the assumption that a value of 0 means unlimited.
     */
    protected static int readSize(StreamInput in) throws IOException {
        final int size = in.readVInt();
        return size == 0 ? Integer.MAX_VALUE : size;
    }

    /**
     * Write a size under the assumption that a value of 0 means unlimited.
     */
    protected static void writeSize(int size, StreamOutput out) throws IOException {
        if (size == Integer.MAX_VALUE) {
            size = 0;
        }
        out.writeVInt(size);
    }

    public Map<String, Object> getMetaData() {
        return metaData;
    }

    @Override
    public final XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {
        builder.startObject(name);
        if (this.metaData != null) {
            builder.field(CommonFields.META);
            builder.map(this.metaData);
        }
        doXContentBody(builder, params);
        builder.endObject();
        return builder;
    }

    public abstract XContentBuilder doXContentBody(XContentBuilder builder, Params params) throws IOException;

    public final void writeTo(StreamOutput out) throws IOException {
        out.writeString(name);
        if (out.getVersion().onOrAfter(Version.V_1_5_0)) {
            out.writeGenericValue(metaData);
        }
        doWriteTo(out);
    }

    protected abstract void doWriteTo(StreamOutput out) throws IOException;

    public final void readFrom(StreamInput in) throws IOException {
        name = in.readString();
        if (in.getVersion().onOrAfter(Version.V_1_5_0)) {
            metaData = in.readMap();
        }
        doReadFrom(in);
    }

    protected abstract void doReadFrom(StreamInput in) throws IOException;

    /**
     * Common xcontent fields that are shared among addAggregation
     */
    public static final class CommonFields {
        public static final XContentBuilderString META = new XContentBuilderString("meta");
        public static final XContentBuilderString BUCKETS = new XContentBuilderString("buckets");
        public static final XContentBuilderString VALUE = new XContentBuilderString("value");
        public static final XContentBuilderString VALUES = new XContentBuilderString("values");
        public static final XContentBuilderString VALUE_AS_STRING = new XContentBuilderString("value_as_string");
        public static final XContentBuilderString DOC_COUNT = new XContentBuilderString("doc_count");
        public static final XContentBuilderString KEY = new XContentBuilderString("key");
        public static final XContentBuilderString KEY_AS_STRING = new XContentBuilderString("key_as_string");
        public static final XContentBuilderString FROM = new XContentBuilderString("from");
        public static final XContentBuilderString FROM_AS_STRING = new XContentBuilderString("from_as_string");
        public static final XContentBuilderString TO = new XContentBuilderString("to");
        public static final XContentBuilderString TO_AS_STRING = new XContentBuilderString("to_as_string");
    }

}
