package com.njust.roftwaremanage.LabManagement.dao;

import com.njust.roftwaremanage.LabManagement.entity.Table;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TableDAO {

    /**
     * 根据tableId和arrangeId删除对应的table
     * 输入:tableId,arrangeId
     */
    public static void dropTable(int tableId,String arrangeId){
        List<Table> tableList = new ArrayList<>();
        String resource = "mybatis-config.xml";
        SqlSession sqlSession = null;
        try {
            InputStream is = Resources.getResourceAsStream(resource);
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
            sqlSession = factory.openSession();
            Table table = new Table();
            table.setTable_id(tableId);
            table.setArrange_id(arrangeId);
            tableList = sqlSession.selectList("dropTable", table);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (sqlSession != null) {
                sqlSession.rollback();
            }
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    /**
     * 根据学生id返回对应的排课信息
     * 输入:studentId
     * 输出:id对应的List<Table>
     */
    public static List<Table> findTableByStudentId(String student_id) {
        List<Table> tableList = new ArrayList<>();
        String resource = "mybatis-config.xml";
        SqlSession sqlSession = null;
        try {
            InputStream is = Resources.getResourceAsStream(resource);
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
            sqlSession = factory.openSession();
            tableList = sqlSession.selectList("findTablebyid", student_id);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (sqlSession != null) {
                sqlSession.rollback();
            }
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        return tableList;
    }

    /**
     * 根据学生id和实验id返回对应的排课信息
     * 输入:studentId,arrangeId
     * 输出:Table
     */
    public static Table findTableByStudentIdAndArrangeId(String student_id, String arrange_id) {
        String resource = "mybatis-config.xml";
        SqlSession sqlSession = null;
        Table table = new Table();
        try {
            InputStream is = Resources.getResourceAsStream(resource);
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
            sqlSession = factory.openSession();
            Table temp = new Table();
            temp.setStudent_id(student_id);
            temp.setArrange_id(arrange_id);
            table = sqlSession.selectOne("findTableByStudentIdAndArrangeId", temp);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (sqlSession != null) {
                sqlSession.rollback();
            }
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        return table;
    }

    /**
     * 根据学生座位号和实验id返回对应的排课信息
     * 输入:tableId,arrangeId
     * 输出:Table
     */
    public static Table findTableByTableIdAndArrangeId(int table_id, String arrange_id) {
        String resource = "mybatis-config.xml";
        SqlSession sqlSession = null;
        Table table = new Table();
        try {
            InputStream is = Resources.getResourceAsStream(resource);
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
            sqlSession = factory.openSession();
            Table temp = new Table();
            temp.setTable_id(table_id);
            temp.setArrange_id(arrange_id);
            table = sqlSession.selectOne("findTableByTableIdAndArrangeId", temp);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (sqlSession != null) {
                sqlSession.rollback();
            }
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        return table;
    }

    /**
     * 将学生实验的座位信息写入数据库
     * 输入:Table
     * 注意:Table_id由数据库生成
     */
    public static void InsertTable(Table table) {
        String resource = "mybatis-config.xml";
        SqlSession openSession = null;
        try {
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            openSession = sqlSessionFactory.openSession();
            openSession.insert("InsertTable", table);
            openSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (openSession != null) {
                openSession.rollback();
            }
        } finally {
            if (openSession != null) {
                openSession.close();
            }
        }
    }

    public static void main(String[] args) {
        TableDAO t = new TableDAO();
        System.out.println("------------------------");
        System.out.println("根据学生id查找：");
        List<Table> tableList = t.findTableByStudentId("2");
        for (Table t2 : tableList) {
            System.out.println(t2.getTable_id());
        }
        System.out.println("------------------------");
        System.out.println("根据学生id和实验id：");
        Table t3 = t.findTableByStudentIdAndArrangeId("2", "1");
        System.out.println(t3.getAddress());
        System.out.println("------------------------");
        System.out.println("根据座位号和实验id：");
        Table t4 = t.findTableByTableIdAndArrangeId(1, "3");
        System.out.println(t4.getAddress());
        System.out.println("------------------------");
        System.out.println("测试插入：");
        Table t5 = new Table(9, "2", "g", "d", "g");
        t.InsertTable(t5);
    }
}
