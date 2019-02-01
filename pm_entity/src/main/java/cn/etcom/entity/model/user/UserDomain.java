package cn.etcom.entity.model.user;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;

import lombok.Data; 

@Data
public class UserDomain implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 928914780982155718L;
	@Min(value = 18, message = "用户id必须大于18")
	private Integer userId;
	@NotBlank(message = "用户名不能为空")
	private String userName;

	private String password;
	@Pattern(regexp = "^[0-9]{11}$", message = "手機號碼不正确")
	private String phone;
	@Range(min=1,max=99,message="年齡不正常") 
	private Integer age;
	private Integer sex;
	// @JSONField(format="yyyy-MM-dd HH:mm:ss")
	/// @JSONField(serialize=false)
	// @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date ctm;

}