package com.wxh.swing.utils;

import java.util.Arrays;

public class CharaccterOperate {
	/** 归并排序 从字符串的第四个字符开始排序 */
	public static String[] sortMerge(String[] array) {
		if (array.length < 2) return array;
		int mid = array.length / 2;
		String[] left = Arrays.copyOfRange(array, 0, mid);
		String[] right = Arrays.copyOfRange(array, mid, array.length);
		return merge(sortMerge(left), sortMerge(right));
	}

	public static String[] merge(String[] left, String[] right) {
		String[] result = new String[left.length + right.length];
		for (int index = 0, i = 0, j = 0; index < result.length; index++) {
			if (i >= left.length) {
				result[index] = right[j++];
			} else if (j >= right.length) {
				result[index] = left[i++];
			} else if (left[i].substring(3).compareTo(right[j].substring(3)) <= 0) {
				result[index] = left[i++];
			} else {
				result[index] = right[j++];
			}
		}
		return result;
	}
}
