package cn.chinaunicom.awarding.core.exception;

public enum AwardingCoreExceptionEnum {

	CaptchaTextIsNull("生成验证码图片的字符为null"), FailWriteCaptchaStream("写验证码图片的流失败"), ConnotFindToken(
			"Token失效，请稍后尝试");

	private final String description;

	private AwardingCoreExceptionEnum(String description) {
		this.description = description;
	}

	public String description() {
		return description;
	}
}
