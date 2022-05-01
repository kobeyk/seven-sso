package com.appleyk.auth.core.dict;

import java.util.regex.Pattern;

/**
 * 正则表达式校验规则
 */
public enum ESeRegExp {

	/**用户名（邮箱）验证规则*/
	ACCOUNT("^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$", "用户名是2-16位的数字或字母或邮箱！"),
	USERNAME("^[a-zA-Z0-9]{6,16}$", "只允许输入字母和数字，电子邮箱,长度为6-16位！"),
	/**强密码验证规则*/
	STRONG_PASSWORD("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[\\s\\S]{8,16}$", "密码长度为8-20位，且必须至少包含一个数字、一个小写字母和一个大写字母。"),
	/**弱密码验证规则*/
	WEEK_PASSWORD("^(?![\\d]+$)(?![a-zA-Z]+$)(?![^\\da-zA-Z]+$).{6,20}$", "长度为6-20位的数字字母或特殊字符互相组合（不能是纯数字、纯字母、纯特殊符号）。"),;
	private final String regular;
	private final String description;

	ESeRegExp(String regular, String description) {
		this.regular = regular;
		this.description = description;
	}

	public String getRegular() {
		return regular;
	}

	public String getDescription() {
		return description;
	}

	public static void main(String[] args) {
		boolean matches = Pattern.matches(ESeRegExp.STRONG_PASSWORD.getRegular(), "123456a");
		if(!matches){
			System.out.println(ESeRegExp.STRONG_PASSWORD.getDescription());
		}
	}

}
