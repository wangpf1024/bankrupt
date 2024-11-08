package cn.bankrupt.workflow.enums;


public enum BaseExceptionEnum {



    EC200("200", "success"),
    EC201("201", "唯一ID关联数据已添加"),
    EC500("500", "系统异常"),
    EC404("404", "数据不存在"),

    NOT_LOGIN("401", "token已失效，请重新登录！"),
    NOT_PERMISSION("401", "您无该权限"),
    EC401("401", "token鉴权失败！"),
    EC000("000", "无法加载切点对象"),
    EC001("001", "链接点参数为空"),
    EC002("002", "类或对象访问权限限制"),
    EC003("003", "I/O异常"),
    EC004("004", "obj to json 失败"),
    EC005("005", "不支持字符编码"),
    EC006("006", "没有此算法"),
    EC007("007", "缺少算法配置参数"),
    EC008("008", "读取私钥失败"),
    EC009("009", "加载私钥失败"),
    EC010("010", "密文数据已损坏"),
    EC011("011", "私钥长度非法"),
    EC012("012", "私钥非法"),
    EC013("013", "读取公钥失败"),
    EC014("014", "加载公钥失败"),
    EC015("015", "明文数据已损坏"),
    EC016("016", "公钥非法"),
    EC017("017", "公钥长度非法"),
    EC018("018", "签名失败"),
    EC019("019", "验签失败");

    private String code;

    private String message;

    BaseExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
