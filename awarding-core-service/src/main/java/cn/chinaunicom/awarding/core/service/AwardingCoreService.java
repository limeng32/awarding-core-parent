package cn.chinaunicom.awarding.core.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import cn.chinaunicom.awarding.core.exception.AwardingCoreException;
import cn.chinaunicom.awarding.core.exception.AwardingCoreExceptionEnum;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

@Service
public class AwardingCoreService implements InitializingBean {

	private DefaultKaptcha producer;

	private Map<String, String> tokenMap = new ConcurrentHashMap<String, String>();

	private List<String> preDefinedTexts;

	private int textCount = 0;

	@Override
	public void afterPropertiesSet() throws Exception {

		producer = new DefaultKaptcha();

		producer.setConfig(new Config(new Properties()));

	}

	public byte[] generateCaptchaImageNew(String captchaText)
			throws AwardingCoreException {

		if (captchaText == null) {
			throw new AwardingCoreException(
					AwardingCoreExceptionEnum.CaptchaTextIsNull.toString());
		}
		BufferedImage image = producer.createImage(captchaText);

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			ImageIO.write(image, "jpg", out);
		} catch (IOException e) {
			throw new AwardingCoreException(
					AwardingCoreExceptionEnum.FailWriteCaptchaStream.toString(),
					e);
		}

		return out.toByteArray();
	}

	private String getCaptchaText() {
		if (preDefinedTexts != null && !preDefinedTexts.isEmpty()) {
			String text = preDefinedTexts.get(textCount);
			textCount = (textCount + 1) % preDefinedTexts.size();
			return text;
		} else {
			return RandomGenerator.getRandomString(4);
		}
	}

	public String generateCaptchaKeyNew(String token, String oldToken)
			throws AwardingCoreException {
		String value = getCaptchaText();
		if (oldToken != null && !"".equals(oldToken)) {
			tokenMap.remove(oldToken);
		}
		tokenMap.put(token, value);
		return value;
	}

	public boolean testCaptcha(String value, String token)
			throws AwardingCoreException {
		String text = tokenMap.get(token);
		if (text == null) {
			throw new AwardingCoreException(
					AwardingCoreExceptionEnum.ConnotFindToken.toString());
		}
		return text.equals(value);
	}

	public void clearToken(String token) throws AwardingCoreException {
		if (tokenMap.get(token) == null) {
			throw new AwardingCoreException(
					AwardingCoreExceptionEnum.ConnotFindToken.toString());
		} else {
			tokenMap.remove(token);
		}
	}
}
