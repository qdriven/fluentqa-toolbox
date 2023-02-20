package io.fluentqa.builtin.validators;

import cn.hutool.core.util.StrUtil;
import io.fluentqa.builtin.strings.MatcherUtils;
import io.fluentqa.builtin.constant.RegexpConstant;
import io.fluentqa.builtin.validators.annotation.IsMobile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 校验是否为合法的手机号码
 *
 */
public class MobileValidator implements ConstraintValidator<IsMobile, String> {

    @Override
    public void initialize(IsMobile isMobile) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (StrUtil.isBlank(s)) {
                return true;
            } else {
                String regex = RegexpConstant.MOBILE_REG;
                return MatcherUtils.match(regex, s);
            }
        } catch (Exception e) {
            return false;
        }
    }
}
