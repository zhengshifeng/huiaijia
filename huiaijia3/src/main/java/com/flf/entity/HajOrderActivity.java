package com.flf.entity;

import com.base.entity.BaseEntity;
import java.math.BigDecimal;
/**
 * 
 * <br>
 * <b>功能：</b>HajOrderActivityEntity<br>
 */
public class HajOrderActivity extends BaseEntity {
	
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private java.lang.Integer id;//   	private java.lang.Integer orderId;//   订单id（订单表外键）	private java.lang.Integer activityId;//   活动表id	private BigDecimal discountAmount;//   活动优惠金额	private java.lang.String remark1;//   	private java.lang.String remark2;//   	private java.lang.String remark3;//   	public java.lang.Integer getId() {	    return this.id;	}	public void setId(java.lang.Integer id) {	    this.id=id;	}	public java.lang.Integer getOrderId() {	    return this.orderId;	}	public void setOrderId(java.lang.Integer orderId) {	    this.orderId=orderId;	}	public java.lang.Integer getActivityId() {	    return this.activityId;	}	public void setActivityId(java.lang.Integer activityId) {	    this.activityId=activityId;	}	public BigDecimal getDiscountAmount() {	    return this.discountAmount;	}	public void setDiscountAmount(BigDecimal discountAmount) {	    this.discountAmount=discountAmount;	}	public java.lang.String getRemark1() {	    return this.remark1;	}	public void setRemark1(java.lang.String remark1) {	    this.remark1=remark1;	}	public java.lang.String getRemark2() {	    return this.remark2;	}	public void setRemark2(java.lang.String remark2) {	    this.remark2=remark2;	}	public java.lang.String getRemark3() {	    return this.remark3;	}	public void setRemark3(java.lang.String remark3) {	    this.remark3=remark3;	}
}

