package cn.mooding.modules.system.controller;

import cn.mooding.common.model.constant.CacheConstant;
import cn.mooding.common.model.constant.CommonConstant;
import cn.mooding.common.model.dto.ResponseResult;
import cn.mooding.common.model.enums.HttpCodeEnum;
import cn.mooding.common.utils.Base64;
import cn.mooding.common.utils.IdUtils;
import cn.mooding.common.utils.redis.RedisCache;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 验证码操作处理
 *
 * @Author BlueFire
 * @Date 28/4/2021 -下午8:30
 */
@RestController
@Api(tags = "验证码操作处理")
public class CaptchaController {
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private RedisCache redisCache;

    // 验证码类型
//    @Value("${mooding.captchaType}")
    private String captchaType = "math";

    @GetMapping("/captchaImage")
    @ApiOperation(value = "获取图形验证码", notes = "")
    public ResponseResult getCode(HttpServletResponse response) throws IOException {
        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = CacheConstant.CAPTCHA_CODE_KEY + uuid;

        String capStr = null;
        String code = null;
        BufferedImage image = null;

        // 生成验证码
        if ("math".equals(captchaType)) {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        } else if ("char".equals(captchaType)) {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        redisCache.setCacheObject(verifyKey, code, CacheConstant.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            return ResponseResult.errorResult(HttpCodeEnum.SERVER_ERROR.getCode(), e.getMessage());
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("uuid", uuid);
        result.put("img", Base64.encode(os.toByteArray()));
        return ResponseResult.okResult(result);
    }
}
