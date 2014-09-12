package com.gs.pos.util;


import org.apache.ibatis.session.SqlSession;

public abstract class DAOTransaccion {

    protected SqlSession sqlSession = null;

    public abstract void openConnection() throws Exception;

    public void commit() throws Exception {
        sqlSession.commit(true);
        sqlSession.close();
    }

    public void rollback() throws Exception {
        sqlSession.rollback(true);
        sqlSession.close();
    }

    protected SqlSession getSqlSession() {
        return sqlSession;
    }
}
