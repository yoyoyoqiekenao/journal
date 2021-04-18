package com.dzt.journal.constant;

/**
 * Created on 2016/12/3.
 */

public class DateConst {
	public final static String[] sch_type = {"会议", "约会", "电话", "纪念日", "生日", "课程", "其他"}; // 日程类型
	public final static String[] remind = {"提醒一次", "隔10分钟", "隔30分钟", "隔一小时", "每天重复", "每周重复", "每月重复", "每年重复"};

	public final static String[] chineseNumber = {"一", "二", "三", "四", "五", "六", "七",
			"八", "九", "十", "十一", "十二"};

	public final static String[] animals = {"鼠", "牛", "虎", "兔", "龙", "蛇",
			"马", "羊", "猴", "鸡", "狗", "猪"};

	public final static String[] TIAN_GAN = {"甲", "乙", "丙", "丁", "戊", "己", "庚",
			"辛", "壬", "癸"};

	public final static String[] DI_ZHI = {"子", "丑", "寅", "卯", "辰", "巳", "午",
			"未", "申", "酉", "戌", "亥"};

	//农历部分假日
	public final static String[] lunarHoliday = new String[]{
			"0101 春节",
			"0115 元宵",
			"0505 端午",
			"0707 七夕情人",
			"0715 中元",
			"0815 中秋",
			"0909 重阳",
			"1208 腊八",
			"1224 小年",
			"0100 除夕"
	};

	//公历部分节假日
	public final static String[] solarHoliday = new String[]{
			"0101 元旦",
			"0214 情人",
			"0308 妇女",
			"0312 植树",
			"0315 消费者权益日",
			"0401 愚人",
			"0501 劳动",
			"0504 青年",
			"0512 护士",
			"0601 儿童",
			"0701 建党",
			"0801 建军",
			"0808 父亲",
			"0909 毛泽东逝世纪念",
			"0910 教师",
			"0928 孔子诞辰",
			"1001 国庆",
			"1006 老人",
			"1024 联合国日",
			"1112 孙中山诞辰纪念",
			"1220 澳门回归纪念",
			"1225 圣诞",
			"1226 毛泽东诞辰纪念"
	};

	public final static long[] lunarInfo = new long[]{0x04bd8, 0x04ae0, 0x0a570,
			0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2,
			0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0,
			0x0ada2, 0x095b0, 0x14977, 0x04970, 0x0a4b0, 0x0b4b5, 0x06a50,
			0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970, 0x06566,
			0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0,
			0x1c8d7, 0x0c950, 0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4,
			0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557, 0x06ca0, 0x0b550,
			0x15355, 0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8, 0x0e950,
			0x06aa0, 0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260,
			0x0f263, 0x0d950, 0x05b57, 0x056a0, 0x096d0, 0x04dd5, 0x04ad0,
			0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6,
			0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40,
			0x0af46, 0x0ab60, 0x09570, 0x04af5, 0x04970, 0x064b0, 0x074a3,
			0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0, 0x0c960,
			0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0,
			0x092d0, 0x0cab5, 0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9,
			0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930, 0x07954, 0x06aa0,
			0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65,
			0x0d530, 0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0,
			0x1d0b6, 0x0d250, 0x0d520, 0x0dd45, 0x0b5a0, 0x056d0, 0x055b2,
			0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0};
}
