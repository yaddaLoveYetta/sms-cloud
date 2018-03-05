package com.kingdee.hrp.sms.common.pojo;

/**
 * @author yadda
 */

public enum DataTypeEnum {

	NUMBER(1, "数字"), TEXT(2, "文本"), TIME(3, "日期时间"), BOOLEAN(4, "布尔值");

	private int number;
	private String name;

	private DataTypeEnum(int number, String name) {
		this.number = number;
		this.name = name;
	}

	public static DataTypeEnum getTypeEnum(int number) {

		for (DataTypeEnum d : DataTypeEnum.values()) {
			if (d.number == number) {
				return d;
			}
		}
		return DataTypeEnum.TEXT;
	}
}
