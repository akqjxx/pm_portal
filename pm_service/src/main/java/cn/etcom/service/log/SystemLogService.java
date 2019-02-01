/**
 * 
 */
package cn.etcom.service.log;

import java.util.List;

import cn.etcom.entity.SystemLog;

/**
 * @author admin
 *
 */
public interface SystemLogService {

    public List<SystemLog> findAll();

    public void saveLog(SystemLog log);

    public SystemLog findOne(long id);

    public void delete(long id);

}
