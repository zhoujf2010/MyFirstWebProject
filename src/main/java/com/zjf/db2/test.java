package com.zjf.db2;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

//Hibernate Demo
public class test
{

    public static void main(String[] args) {
     // 默认读取hibernate.cfg.xml文件
        Configuration cfg = new Configuration().configure();
        ServiceRegistry sr = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();
        SessionFactory factory = cfg.buildSessionFactory(sr);


        Session session = null;
        try {
            session = factory.openSession();

            Query query = session.createQuery("from People");
            List<People> lst = query.list();

            for (People item : lst) {
                System.out.println(item.getId() + "  " + item.getName() + "  " + item.getAge());
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (session != null) {
                if (session.isOpen()) {
                    // 关闭session
                    session.close();
                }
            }
        }
    }

}
