package com.ryan.model.system;
/**
 * @Description: 订单状态
 * @author Ryan
 * @date 2018/2/24 11:29
 */
public enum OrderStatus {
    //初始化
    INIT("INIT"),
    //已支付
    PAYMENT("PAYMENT"),
    //等待退款
    WAIT_FOR_REFUNDING("WAIT FOR REFUNDING"),
    //已退款
    REFUNDED("REFUNDED"),
    //已关闭
    CLOSED("CLOSED");

    private String name;
    private OrderStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
