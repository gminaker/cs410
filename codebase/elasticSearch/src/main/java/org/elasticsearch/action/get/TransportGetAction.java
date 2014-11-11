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

package org.elasticsearch.action.get;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.RoutingMissingException;
import org.elasticsearch.action.support.ActionFilters;
import org.elasticsearch.action.support.single.shard.TransportShardSingleOperationAction;
import org.elasticsearch.cluster.ClusterService;
import org.elasticsearch.cluster.ClusterState;
import org.elasticsearch.cluster.routing.ShardIterator;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.engine.Engine;
import org.elasticsearch.index.get.GetResult;
import org.elasticsearch.index.service.IndexService;
import org.elasticsearch.index.shard.ShardId;
import org.elasticsearch.index.shard.service.IndexShard;
import org.elasticsearch.indices.IndicesService;
import org.elasticsearch.threadpool.ThreadPool;
import org.elasticsearch.transport.TransportService;

/**
 * Performs the get operation.
 */
public class TransportGetAction extends TransportShardSingleOperationAction<GetRequest, GetResponse> {

    public static final boolean REFRESH_FORCE = false;

    private final IndicesService indicesService;
    private final boolean realtime;

    @Inject
    public TransportGetAction(Settings settings, ClusterService clusterService, TransportService transportService,
                              IndicesService indicesService, ThreadPool threadPool, ActionFilters actionFilters) {
        super(settings, GetAction.NAME, threadPool, clusterService, transportService, actionFilters);
        this.indicesService = indicesService;

        this.realtime = settings.getAsBoolean("action.get.realtime", true);
    }

    @Override
    protected String executor() {
        return ThreadPool.Names.GET;
    }

    @Override
    protected boolean resolveIndex() {
        return true;
    }

    @Override
    protected ShardIterator shards(ClusterState state, InternalRequest request) {
        return clusterService.operationRouting()
                .getShards(clusterService.state(), request.concreteIndex(), request.request().type(), request.request().id(), request.request().routing(), request.request().preference());
    }

    @Override
    protected void resolveRequest(ClusterState state, InternalRequest request) {
        if (request.request().realtime == null) {
            request.request().realtime = this.realtime;
        }
        // update the routing (request#index here is possibly an alias)
        request.request().routing(state.metaData().resolveIndexRouting(request.request().routing(), request.request().index()));
        // Fail fast on the node that received the request.
        if (request.request().routing() == null && state.getMetaData().routingRequired(request.concreteIndex(), request.request().type())) {
            throw new RoutingMissingException(request.concreteIndex(), request.request().type(), request.request().id());
        }
    }

    @Override
    protected GetResponse shardOperation(GetRequest request, ShardId shardId) throws ElasticsearchException {
        IndexService indexService = indicesService.indexServiceSafe(shardId.getIndex());
        IndexShard indexShard = indexService.shardSafe(shardId.id());

        if (request.refresh() && !request.realtime()) {
            indexShard.refresh(new Engine.Refresh("refresh_flag_get").force(REFRESH_FORCE));
        }

        GetResult result = indexShard.getService().get(request.type(), request.id(), request.fields(),
                request.realtime(), request.version(), request.versionType(), request.fetchSourceContext(), request.ignoreErrorsOnGeneratedFields());
        return new GetResponse(result);
    }

    @Override
    protected GetRequest newRequest() {
        return new GetRequest();
    }

    @Override
    protected GetResponse newResponse() {
        return new GetResponse();
    }
}
