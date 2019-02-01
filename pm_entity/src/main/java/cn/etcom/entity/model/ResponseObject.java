package cn.etcom.entity.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@Builderpublic class ResponseObject implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6868647105846125400L;

	private Integer total;
	private Object data;
	private Object rows;
	private String returnMsg;
	private Integer returnCode;
}
