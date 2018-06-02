package com.itheima.service;

import com.itheima.dao.DiscussDao;
import com.itheima.domain.Discuss;

import java.sql.SQLException;
import java.util.List;

public class DiscussService {
    public void inDiscuss(Discuss discuss,String username){
        DiscussDao dao=new DiscussDao();
        dao.inDiscuss(discuss,username);
    }

    public List<Discuss> FindAllDiscuss() throws SQLException {
        DiscussDao dao=new DiscussDao();
//        String sql="select * from discuss";
        List<Discuss> mapList=dao.FindAllDiscuss();
        return mapList;
    }

    public  void DelDiscuss(String did) throws SQLException {
        DiscussDao dao=new DiscussDao();
        dao.DelDiscuss(did);
    }

}
