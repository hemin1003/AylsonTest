package com.aylson.aylsonProtest.util;

/**
 * Created by Administrator on 2016/8/10.
 * <p/>
 * 常量配置
 */
public final class Const {

    //<editor-fold desc="---------------------------------------------------------------------------Config文件Key">

    // 用户ID
    public static final String USER_ID = "user_id";
    // 用户生日
    public static final String USER_BIRTHDAY = "user_birthday";
    // 用户性别
    public static final String USER_SEX = "user_sex";
    // 用户手机号
    public static final String USER_PHONE = "user_phone";
    // 用户登录密码
    public static final String USER_PASS = "user_pass";
    // 用户名
    public static final String USER_NAME = "user_name";
    // 用户qq
    public static final String USER_QQ = "user_qq";
    // 用户微信号
    public static final String USER_WECHAT = "user_weChat";
    // 用户邮箱
    public static final String USER_EMAIL = "user_email";
    // 用户单位
    public static final String USER_UNIT = "user_unit";
    // 用户地址
    public static final String USER_ADDRESS = "user_address";
    // 用户头像
    public static final String USER_HEAD_IMG = "user_head";
    // 网关物理地址
    public static final String PHYSICAL_DEVICE_ID = "physical_device_id";
    // 网关名
    public static final String GATEWAY_NAME = "gateway_name";
    // 网关逻辑ID
    public static final String DEVICE_ID = "device_id";
    // 用户昵称
    public static final String NICK_NAME = "nick_name";
    // 是否管理员
    public static final String IS_OWNER = "is_owner";
    // 手势密码
    public static final String GESTURE_PAS = "gesture_pas";
    public static final String GESTURE_TYPE = "gesture_type";
    public static final String GESTURE_CLEAR = "gesture_clear";
    // 声纹记录
    public static final String ISV_PAS = "isv_pas";
    // 人脸记录
    public static final String FACE_PAS = "face_pas";
    // 切换登录
    public static final String LOGIN_TYPE = "login_type";
    // 省
    public static final String PROVINCE = "province";
    // 市
    public static final String CITY = "city";
    // 区
    public static final String COUNTY = "county";
    public static final String IS_FOREGROUND_KEY ="foreground_key";
    //</editor-fold>


    //<editor-fold desc="---------------------------------------------------------------------------配置程序常量">

    // 主域名
    public static final String MAJOR_DOMAIN_NAME = "foshanaichen";
    // 主域ID
    public static final long MAJOR_DOMAIN_ID = 1031L;
    // 子域名
    public static final String SUB_DOMAIN_NAME = "aylsongateway";
    // 子域ID
    public static final int SUB_DOMAIN_ID = 1120;
    // UDS服务名
    public static final String UDS_NAME = "aylsoncloud";
    // ShareCode前缀
    public static final String SHARE_CODE = "aylson";
    //分享请求的标识
    public static final int INDICATOR_SHARE = 1;
    //邀请请求的标识
    public static final int INDICATOR_INVITE = 2;

    //管理员解绑普通用户请求的标识
    public static final int INDICATOR_ADMIN = 1;
    //普通用户解绑请求的标识ADMIN
    public static final int INDICATOR_COMMON = 2;
    //</editor-fold>

    public static final String HCI_APP_ID = "57d8c757";
    public static final String IAT_LAN_PREF = "iat_lan_pref";
//    public static final String IAT_LAN_PREF = "iat_lan_pref";
//    public static final String IAT_LAN_PREF = "iat_lan_pref";


    /**
     * 手机号码
     */
    public static final String PARAM_PHONE_NUMBER = "PARAM_PHONE_NUMBER";
    /**
     * 意图
     */
    public static final String PARAM_INTENT_CODE = "PARAM_INTENT_CODE";


    //<editor-fold desc="---------------------------------------------------------------------------UDS服务接口名称">

    // 验证网关密码
    public static final String VERIFY_DEVICE_PWD = "verifyDevicePwd";
    // 通知UDS给硬件发送指令上报数据
    public static final String SEND_COMMAND_OF_INITIAL = "sendCommandOfInitial";
    // 解除设备绑定
    public static final String UN_BIND_GATEWAY = "unBindGateway";
    // 管理获取主设备列表
    public static final String QUERY_PORTAL_DATAS = "queryPortalDatas";
    // 设置环信好友昵称
    public static final String SETFRINDNICENAME = "setFrindNicename";
    // 获取环信好友昵称
    public static final String GETFRINDNICENAME = "getFrindNicename";
    // 主页置顶操作
    public static final String MOVE_TO_TOP = "moveToTop";
    // 获取监控设备列表
    public static final String QUERY_MONITOR_DATAS = "queryMonitorDatas";
    // 从监控列表删除添加数据
    public static final String ADJUST_MONITOR_STATUS = "adjustMonitorStatus";
    // 从设备列表添加到主界面
    public static final String ADD_DEVICE_TO_PORTAL = "addSubDeviceToPortal";
    // 获取子设备操作历史
    public static final String QUERY_DEVICE_HIS = "querySubDeviceHis";
    // 获取了设备状态
    public static final String QUERY_DEVICE_STATUS = "querySubDeviceStatus";
    // 把子设备从主界面移除
    public static final String DELETE_DEVICE_FROM_PORTAL = "deleteSubDeviceFromPortal";
    // 获取设备授权权限
    public static final String QUERY_GATEWAY_PERMISSION = "queryGatewayPermission";
    // 获取子设备授权记录
    public static final String GET_SUB_DEVICE_PERMISSION_HIS = "getSubDevicePermiHis";
    // 取消子设备授权记录
    public static final String ADJUST_SUB_DEVICE_PERMISSION_HIS = "adjustSubDevicePermiFromHis";
    // 对某一用户添加网关设备授权（分享或邀请）
    public static final String ADD_GATEWAY_PERMISSION = "addGatewayPermission";
    // 取消对某一用户的网关设备授权（分享或邀请）
    public static final String CANCEL_DEVICE_PERMISSION = "cancelGatewayPermission";
    // 语音控制设备
    public static final String VOICE_CONTROL_TO_DEVICE = "voiceControltoDevice";
    // AI语音控制设备，根据用户说的内容自动适配到具体设备
    public static final String AI_CONTROL_TO_DEVICE = "aiControltoDevice";
    //添加用户密码记录
    public static final String ADD_USER_PWD = "addUserPwd";
    //修改用户密码记录
    public static final String UPDATE_USER_PWD = "updateUserPwd";
    //添加用户密码记录
    public static final String QUERY_USER_PWD = "queryUserPwd";

    //</editor-fold>
    // 程序检测更新服务器地址  http://test.aylsonclub.com:8081/AylsonAppUpdate/upload?apkPassword=123456
    public static final String APP_UPDATE_SERVICE = "http://test.aylsonclub.com:8081/AylsonAppUpdate";

}
