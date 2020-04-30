package com.qian.gulimall.security.properties.contsant;

import com.qian.gulimall.common.security.SecurityConstants;

/**
 * Created by IntelliJ IDEA.
 * ValidateCodeTypeEnum is
 *
 * @author QIAN
 * Date 2020/04/18
 * Time 16:38
 */
public enum ValidateCodeTypeEnum {
    IMAGE {
        @Override
        public String getParameterNameOnValidate() {
            return SecurityConstants.DEFAULT_REQUEST_PARAMETER_IMAGECODE;
        }
    },
    SMS {
        @Override
        public String getParameterNameOnValidate() {
            return SecurityConstants.DEFAULT_REQUEST_PARAMETER_SMSCODE;
        }
    };

    public abstract String getParameterNameOnValidate();
}
