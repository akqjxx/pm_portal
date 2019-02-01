/**
 * 
 */
package cn.etcom.dao.log;

import org.apache.ibatis.annotations.Insert;

import cn.etcom.entity.SystemLog;

/**
 * @author admin
 *
 */

public interface SystemLogDao {
	
	@Insert(value="insert into SYSTEMLOG(requestip, url,httpmethod,type, userid, description, actiondate, exceptioncode, exceptiondetail, actionmethod, params)"
			+ "values "
			+ "(#{requestip},#{url},#{httpmethod},#{type},#{userid},#{description},#{actiondate},#{exceptioncode},#{exceptiondetail},#{actionmethod},#{params})")
	public int addLog(SystemLog log);

}
