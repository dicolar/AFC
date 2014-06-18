package test;

import org.alfresco.repo.security.authentication.AuthenticationComponent;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.util.ApplicationContextHelper;
import org.springframework.context.ApplicationContext;

import cn.incontent.afc.client.AFCSessionFactory;
import cn.incontent.afc.client.IAfSession;
import cn.incontent.afc.client.query.query.AfQuery;
import cn.incontent.afc.client.query.query.IAfQuery;
import cn.incontent.afc.client.query.querycond.PathCond;
import cn.incontent.afc.client.query.querycond.TypeCond;
import cn.incontent.afc.client.query.res.IAfCollection;
import cn.incontent.afc.entries.model.abs.IAfSysObject;
import cn.incontent.afc.entries.model.exception.AfException;

/**
 *@author Val.(Valentine Vincent) E-mail:valer@126.com
 *@version 1.0
 *@date 2013-10-11
 *Instruction : 
 **/
public class AFCTest {

	private static ServiceRegistry serviceRegistry;
	
	protected static ApplicationContext ctx;
	private static AuthenticationComponent authenticationComponent;
	
	static {
		ApplicationContext ctx = ApplicationContextHelper.getApplicationContext();
		
		serviceRegistry = (ServiceRegistry) ctx.getBean(ServiceRegistry.SERVICE_REGISTRY);
		
		authenticationComponent = ((AuthenticationComponent) ctx.getBean("authenticationComponent"));
	}
	
	public static IAfSession getAfSession(String userLoginId) throws AfException {
		try {
			authenticationComponent.setCurrentUser(userLoginId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AfException("user with login id " + userLoginId + " not exist!" + e);
		}
		return AFCSessionFactory.produceSession(serviceRegistry);
	}
	
	public static void main(String[] args) {
		
		try {
			doTest();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
		
	}
	
	public static void doTest() throws Exception {
		System.out.println("=============START================");
		
		//this snippet shows how 2 query all objects that type of cm:content and that in /app:company_home. 
		IAfSession afSession = getAfSession("admin");
		
		IAfQuery query = new AfQuery();
		query.setQueryCondition(new TypeCond("cm:content").appendAND(new PathCond("/")));
		
		IAfCollection coll = query.execute(afSession);
		while (coll.next()) {
			
			IAfSysObject doc = (IAfSysObject) afSession.getObject(coll.getID("sys:node-uuid"));
			
			System.out.println(doc.getObjectName());
		}
		
		coll.close();
		
	}
	
}
