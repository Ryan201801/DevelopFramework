package com.ryan.model.system;
/**
 * Description:短信类型
 * User: Ryan
 * Time: 2018/2/23 10:52
 */
public enum SMSType {
    //发送登录用的随机码
    LOGIN("LOGIN"),
    //客户发送推荐短信给朋友
    MemberToFriendSMS("MemberToFriendSMS"),
    //销售给新客户发短信
    SellerToMemberSMS("SellerToMemberSMS"),
    //销售给新的下级销售发短信
    SellerToJuniorSMS("SellerToJuniorSMS");

    private String name;

    /**
     * @Description: 私有构造方法，防止被外部调用
     * @author Ryan
     * @date 2018/2/23 15:34
     */
    private SMSType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }

    public static SMSType fromString(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }
        for (SMSType component : SMSType.values()) {
            if (component.getName().equalsIgnoreCase(s.trim())) {
                return component;
            }
        }

        throw  new IllegalArgumentException("Unrecognized SMSType name");
    }
}