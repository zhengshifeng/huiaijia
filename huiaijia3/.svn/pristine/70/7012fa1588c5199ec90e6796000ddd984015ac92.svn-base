package com.flf.resolver;

/**
 * 用于事务回滚的自定义异常，回滚后事务将不会提交
 *
 * Created by SevenWong on 2016/12/16 14:14.
 */
public class RollbackException extends Exception{

	public RollbackException(String message) {
		super(message);
	}

	public static void main(String[] args) {
		try {
			throw new RollbackException("事务回滚");
		} catch (RollbackException e) {
			e.printStackTrace();
		}
	}
}
