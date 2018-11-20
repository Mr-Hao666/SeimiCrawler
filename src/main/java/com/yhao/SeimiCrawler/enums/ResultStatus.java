package com.yhao.SeimiCrawler.enums;

/**
 * @author long.hua
 * @since 1.0.0
 * Created on 2017-01-15 11:19
 */
public enum ResultStatus {

    FAILURE(-1,"-1"),

    SUCCESS(0,"0"),

    INSERT_FAILURE(1,"1"),

    UPDATE_FAILURE(2,"2"),

    DELETE_FAILURE(3,"3"),

    SELECT_FAILURE(4,"4"),

    LOGIN_FAILURE(5,"5"),

    NOT_LOGIN(6,"6"),

    NOT_EXIST(7,"7"),

    DELETE_FAILURE_INLINE(111,"111"),
    DELETE_FAILURE_TARGET_ERROR(112,"112"),
    UPDATE_FAILURE_INLINE(113,"113"),


    /**
     * 活动
     */
    ACTIVITY_OUT(21,"21"),

    /**
     * 再次购买
     */
    NOT_ORDER(8,"8"),

    NOT_COMPLETED(9,"9"),
    GOODS_ACTIVITING(10,"10"),

    GOODS_OUT(11,"11"),

    GOODS_PART_OUT(12,"12"),

    SHOP_OUT(13,"13"),
    NOT_MARKET_OUT(14,"14"),
    /**
     * 用戶操作
     */
    USER_MUST_LOGIN(101,"101"),

    USER_IS_NOT_AGENT(102,"102"),

    USER_HAS_NO_TRIAL_TIMES(103,"103"),

    USER_HAS_NO_TRIAL_DATE(103,"103"),

    USER_IS_FROZEN(104,"104"),
    USER_IS_UNACTIVATION(105,"105"),
    USER_OUT_OF_TIME(106,"106"),

    /**
     * 店铺操作
     */
    SHOP_BUSINESS_STATUS_ON(201,"201"),
    SHOP_BUSINESS_STATUS_OFF(202,"202"),
    SHOP_NOT_EXIST(203,"203"),
    SHOP_IS_SUPPLIERS(204,"204"),
    SHOP_NOT_CANG_MALL(205,"205"),

    /**
     * 参数错误
     */
    PARAM_ERROR(610,"610"),

    PARAM_FORMAT_ERROR(611,"611"),

    PARAM_TYPE_ERROR(612,"612"),

    PARAM_VALUE_RANGE_OUT(613,"613"),

    PARAM_EMPTY(614,"614"),

    PARAM_IDENTITY(615,"615"),
    PARAM_UPDATE(616,"616"),

    /**
     * 文件操作
     */
    FILE_IS_EMPTY(820,"820"),

    FILE_FORMAT_ERROR(821,"821"),

    MAKE_DIR_ERROR(822,"822"),

    FILE_UPLOAD_FAIL(823,"823"),


    /**
     * 调用第三方失败
     */
    CALL_API_ERROR(700,"700"),

    /*购物车操作*/
    CART_AMOUNT_TOO_LITTER(901,"901"),
    CART_GOODS_SALE_OUT(902,"902"),
    GOODS_STATUS_OUT(903,"903"),
    COUPON_MONE_ONE(904,"904"),
    COUPON_INVALID(905,"905"),
    COUPON_NOT_BELONG(906,"906"),

    //金额错误
    ORDER_AMOUNT_ERROR(907,"907"),

    /**
     * 客户类型删除操作
     */
    CUSTOMER_IS_NOT_EMPTY(830,"830"),
    HAD_CONLLECTION(840,"840"),
    CONLLECTION_SUCCESS(850,"850"),
    //登录成功！
    LOGIN_SUCCESS(100001,"100001"),
    //用户不存在！
    USER_NOT_EXIST(100002,"100002"),
    //验证码错误！
    CHECK_CODE_ERROP(100003,"100003"),
    //收藏成功
    COLLECTION_SUCCESS(100004,"100004"),
    //补水成功
    WATER_SUCCESS(100005,"100005"),
    //补水失败
    HAD_WATER(100006,"100006"),
    //美白成功
    WHITE_SUCCESS(100007,"100007"),
    //美白失败
    HAD_WHITE(100008,"100008"),
    //紧致成功
    COMPACT_SUCCESS(100009,"100009"),
    //紧致失败
    HAD_COMPACT(100010,"100010"),
    //滋润成功
    MOIST_SUCCESS(100011,"100011"),
    //滋润失败
    HAD_MOIST(100012,"100012"),
    //音乐闹钟加分成功
    ALARM_SUCCESS(100013,"100013"),
    //已经听过音乐了
    HAD_ALARM(100014,"100014"),
    //继续保持
    CONTINUE_SUCCESS(100015,"100015");

    private int code;
    private String message;

    ResultStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultStatus setMessage(String message) {
        this.message = message;
        return this;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
