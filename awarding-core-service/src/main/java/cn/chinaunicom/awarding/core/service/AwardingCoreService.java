package cn.chinaunicom.awarding.core.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

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

}
