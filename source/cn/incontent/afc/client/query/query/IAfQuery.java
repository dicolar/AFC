package cn.incontent.afc.client.query.query;

import java.util.Locale;

import cn.incontent.afc.client.IAfSession;
import cn.incontent.afc.client.query.IAfQueryCondition;
import cn.incontent.afc.client.query.res.IAfCollection;
import cn.incontent.afc.entries.model.exception.AfException;

/**
 *@author Val.(Valentine Vincent) E-mail:valer@126.com
 *@version 1.0
 *@date 2011-10-21
 *Instruction : 
 **/
public interface IAfQuery {
	
	public String getQuery();

	public void setQuery(String query);
	
	public IAfCollection execute(IAfSession afSession) throws AfException;

	public void setQueryCondition(IAfQueryCondition queryCond);

	public void appendQuery(String query);

	public void setResultRange(int min, int resultLength);

	/**
	 *Instruction : cm:content.size cm:content.mimetype are also supported!
	 *
	 * @param attrName
	 * @param ascend
	 *		
	 */
	public void addOrderByAttr(String attrName, boolean ascend);

	public void addLocale(Locale locale);

	public void addOrderBy(String fieldName, boolean ascend);

	void setMaxItems(int limit);

}
