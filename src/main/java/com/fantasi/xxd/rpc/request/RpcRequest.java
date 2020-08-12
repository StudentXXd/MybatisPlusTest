package com.fantasi.xxd.rpc.request;

import java.io.Serializable;

/**
 * @author xxd
 * @date 2020/7/26 19:14
 */
public class RpcRequest implements Serializable {

    private static final long serialVersionUID = -4762891269913479609L;

    private String className;

    private String methodName;

    private Object[] parameters;

    private String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
