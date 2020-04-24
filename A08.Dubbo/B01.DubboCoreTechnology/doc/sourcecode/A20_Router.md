## Router

#### 1 简介

> 路由

#### 2 类关系

```
Router (com.alibaba.dubbo.rpc.cluster)
    AbstractRouter (org.apache.dubbo.rpc.cluster.router)
        ScriptRouter (org.apache.dubbo.rpc.cluster.router.script)
        TagRouter (org.apache.dubbo.rpc.cluster.router.tag)
        MockInvokersSelector (org.apache.dubbo.rpc.cluster.router.mock)
        ConditionRouter (org.apache.dubbo.rpc.cluster.router.condition)
        ListenableRouter (org.apache.dubbo.rpc.cluster.router.condition.config)
            ServiceRouter (org.apache.dubbo.rpc.cluster.router.condition.config)
            AppRouter (org.apache.dubbo.rpc.cluster.router.condition.config)

```

#### 3 MockInvokersSelector

> mock路由选择

```

public class MockInvokersSelector extends AbstractRouter {

    public static final String NAME = "MOCK_ROUTER";
    private static final int MOCK_INVOKERS_DEFAULT_PRIORITY = Integer.MIN_VALUE;

    public MockInvokersSelector() {
        this.priority = MOCK_INVOKERS_DEFAULT_PRIORITY;
    }

    @Override
    public <T> List<Invoker<T>> route(final List<Invoker<T>> invokers,
                                      URL url, final Invocation invocation) throws RpcException {
        if (CollectionUtils.isEmpty(invokers)) {
            return invokers;
        }

        // 如果隐式参数为空，则选择非Mock的Invoker
        if (invocation.getAttachments() == null) {
            return getNormalInvokers(invokers);
        } else {
            String value = invocation.getAttachments().get(Constants.INVOCATION_NEED_MOCK);
            if (value == null) {
                return getNormalInvokers(invokers);
            } else if (Boolean.TRUE.toString().equalsIgnoreCase(value)) {
                // 过滤出Mock的Invoker
                return getMockedInvokers(invokers);
            }
        }
        return invokers;
    }

    private <T> List<Invoker<T>> getMockedInvokers(final List<Invoker<T>> invokers) {
        if (!hasMockProviders(invokers)) {
            return null;
        }
        List<Invoker<T>> sInvokers = new ArrayList<Invoker<T>>(1);
        for (Invoker<T> invoker : invokers) {
            if (invoker.getUrl().getProtocol().equals(Constants.MOCK_PROTOCOL)) {
                sInvokers.add(invoker);
            }
        }
        return sInvokers;
    }

    private <T> List<Invoker<T>> getNormalInvokers(final List<Invoker<T>> invokers) {
        if (!hasMockProviders(invokers)) {
            return invokers;
        } else {
            List<Invoker<T>> sInvokers = new ArrayList<Invoker<T>>(invokers.size());
            for (Invoker<T> invoker : invokers) {
                if (!invoker.getUrl().getProtocol().equals(Constants.MOCK_PROTOCOL)) {
                    sInvokers.add(invoker);
                }
            }
            return sInvokers;
        }
    }

    private <T> boolean hasMockProviders(final List<Invoker<T>> invokers) {
        boolean hasMockProvider = false;
        for (Invoker<T> invoker : invokers) {
            if (invoker.getUrl().getProtocol().equals(Constants.MOCK_PROTOCOL)) {
                hasMockProvider = true;
                break;
            }
        }
        return hasMockProvider;
    }

}


```


#### 4 TagRouter

> 标签路由

```

public <T> List<Invoker<T>> route(List<Invoker<T>> invokers, URL url, Invocation invocation) throws RpcException {
    if (CollectionUtils.isEmpty(invokers)) {
        return invokers;
    }

    // since the rule can be changed by config center, we should copy one to use.
    final TagRouterRule tagRouterRuleCopy = tagRouterRule;
    if (tagRouterRuleCopy == null || !tagRouterRuleCopy.isValid() || !tagRouterRuleCopy.isEnabled()) {
        return filterUsingStaticTag(invokers, url, invocation);
    }

    List<Invoker<T>> result = invokers;
    String tag = StringUtils.isEmpty(invocation.getAttachment(TAG_KEY)) ? url.getParameter(TAG_KEY) :
            invocation.getAttachment(TAG_KEY);

    // if we are requesting for a Provider with a specific tag
    if (StringUtils.isNotEmpty(tag)) {
        List<String> addresses = tagRouterRuleCopy.getTagnameToAddresses().get(tag);
        // filter by dynamic tag group first
        if (CollectionUtils.isNotEmpty(addresses)) {
            result = filterInvoker(invokers, invoker -> addressMatches(invoker.getUrl(), addresses));
            // if result is not null OR it's null but force=true, return result directly
            if (CollectionUtils.isNotEmpty(result) || tagRouterRuleCopy.isForce()) {
                return result;
            }
        } else {
            // dynamic tag group doesn't have any item about the requested app OR it's null after filtered by
            // dynamic tag group but force=false. check static tag
            result = filterInvoker(invokers, invoker -> tag.equals(invoker.getUrl().getParameter(TAG_KEY)));
        }
        // If there's no tagged providers that can match the current tagged request. force.tag is set by default
        // to false, which means it will invoke any providers without a tag unless it's explicitly disallowed.
        if (CollectionUtils.isNotEmpty(result) || isForceUseTag(invocation)) {
            return result;
        }
        // FAILOVER: return all Providers without any tags.
        else {
            List<Invoker<T>> tmp = filterInvoker(invokers, invoker -> addressNotMatches(invoker.getUrl(),
                    tagRouterRuleCopy.getAddresses()));
            return filterInvoker(tmp, invoker -> StringUtils.isEmpty(invoker.getUrl().getParameter(TAG_KEY)));
        }
    } else {
        // List<String> addresses = tagRouterRule.filter(providerApp);
        // return all addresses in dynamic tag group.
        List<String> addresses = tagRouterRuleCopy.getAddresses();
        if (CollectionUtils.isNotEmpty(addresses)) {
            result = filterInvoker(invokers, invoker -> addressNotMatches(invoker.getUrl(), addresses));
            // 1. all addresses are in dynamic tag group, return empty list.
            if (CollectionUtils.isEmpty(result)) {
                return result;
            }
            // 2. if there are some addresses that are not in any dynamic tag group, continue to filter using the
            // static tag group.
        }
        return filterInvoker(result, invoker -> {
            String localTag = invoker.getUrl().getParameter(TAG_KEY);
            return StringUtils.isEmpty(localTag) || !tagRouterRuleCopy.getTagNames().contains(localTag);
        });
    }
}


```



#### 5 ListenableRouter

```


public <T> List<Invoker<T>> route(List<Invoker<T>> invokers, URL url, Invocation invocation) throws RpcException {
    if (CollectionUtils.isEmpty(invokers) || conditionRouters.size() == 0) {
        return invokers;
    }

    // We will check enabled status inside each router.
    // Router是ConditionRouter
    for (Router router : conditionRouters) {
        invokers = router.route(invokers, url, invocation);
    }

    return invokers;
}





```


#### 6 ConditionRouter


```

public <T> List<Invoker<T>> route(List<Invoker<T>> invokers, URL url, Invocation invocation)
        throws RpcException {
    if (!enabled) {
        return invokers;
    }

    if (CollectionUtils.isEmpty(invokers)) {
        return invokers;
    }
    try {
        if (!matchWhen(url, invocation)) {
            return invokers;
        }
        List<Invoker<T>> result = new ArrayList<Invoker<T>>();
        if (thenCondition == null) {
            logger.warn("The current consumer in the service blacklist. consumer: " + NetUtils.getLocalHost() + ", service: " + url.getServiceKey());
            return result;
        }
        for (Invoker<T> invoker : invokers) {
            if (matchThen(invoker.getUrl(), url)) {
                result.add(invoker);
            }
        }
        if (!result.isEmpty()) {
            return result;
        } else if (force) {
            logger.warn("The route result is empty and force execute. consumer: " + NetUtils.getLocalHost() + ", service: " + url.getServiceKey() + ", router: " + url.getParameterAndDecoded(Constants.RULE_KEY));
            return result;
        }
    } catch (Throwable t) {
        logger.error("Failed to execute condition router rule: " + getUrl() + ", invokers: " + invokers + ", cause: " + t.getMessage(), t);
    }
    return invokers;
}


```
















