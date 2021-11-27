package com.njust.roftwaremanage.LabManagement.service;

import com.njust.roftwaremanage.LabManagement.dao.ArrangeDAO;
import com.njust.roftwaremanage.LabManagement.dao.StudentDAO;
import com.njust.roftwaremanage.LabManagement.dao.TableDAO;
import com.njust.roftwaremanage.LabManagement.entity.Arrange;
import com.njust.roftwaremanage.LabManagement.entity.Student;
import com.njust.roftwaremanage.LabManagement.entity.Table;
import com.njust.roftwaremanage.LabManagement.tools.Message;

import java.util.HashMap;
import java.util.List;

public class StudentService {
    private final static StudentDAO studentDAO = new StudentDAO();

    /**
     * 用户登录
     * 输入：id,password
     * 输出: 成功：Student对象
     *      失败：null
     * */
    public static Student login(String id,String password){
        Student student = studentDAO.findById(id);
        if(student != null){
            if(!password.equals(student.getPassword())){
                return null;
            }
        }
        return student;
    }

    /**
     * 获取该学生的实验列表
     * 传入参数：学生id
     * 传出参数：HashMap<String,Boolean>对象
     *         String为实验的名字
     *         Boolean对该实验学生是否已经选择，true为已经选择
     * */
    public HashMap<String,Boolean> getArranges(String id){
        HashMap<String,Boolean> names = new HashMap<>();
        //获取所有的实验列表
        List<String> arrangeNames = ArrangeService.getArrangeNames();
        //初始化HashMap
        if (arrangeNames != null) {
            for(String s: arrangeNames){
                names.put(s,false);
            }
        }
        //遍历选课信息，查看学生选了哪些实验
        List<Table> tableList = TableDAO.findTableByStudentId(id);
        if (tableList != null) {
            for(Table t:tableList){
                //查找对应的实验
                String arrangeId = t.getArrange_id();
                Arrange arrange = ArrangeDAO.findArrangeById(arrangeId);
                if (arrange != null) {
                    names.put(arrange.getName_exp(),true);
                }
            }
        }
        return names;
    }

    /**
     * 获取该学生的某一个实验的状态
     * 传入参数：学生id,实验名name
     * 传出参数：HashMap<String,Message>对象
     *          String为该实验的id
     *          Message对象的data属性为Arrange对象，包含该实验的具体信息
     *                      code属性为该实验的状态，1为可选，0为已经选择，-1为人数已满，-2为已过时间，-3为已选其它实验
     *                      mag中存储了实验状态对应的具体错误信息
     * */
    //FIXME:该函数逻辑复杂且未经测试
    public HashMap<String, Message> getArrangeDetails(String id,String name){
        HashMap<String,Message> arrangeDetails = new HashMap<>();
        //根据实验名字获取对应的实验
        List<Arrange> arrangeList = ArrangeDAO.findArrangeByName(name);
        boolean isSelect = false;
        String selectedArrangeId = null;
        if (arrangeList != null) {
            for(Arrange a:arrangeList){
                Table table = TableDAO.findTableByStudentIdAndArrangeId(id,a.getArrange_id());
                if(table != null){
                    //table不为null则表示该生已经选择该实验
                    Message message = new Message(0,"已选择",a);
                    arrangeDetails.put(a.getArrange_id(),message);
                    selectedArrangeId = a.getArrange_id();
                    isSelect = true;
                }
                else{
                    Message message = new Message(1,"可选",a);
                    arrangeDetails.put(a.getArrange_id(),message);
                }
            }
        }
        //如果学生已经选课，则将其它时段的实验状态设为-3
        if(isSelect){
            for (Arrange a : arrangeList) {
                if (!a.getArrange_id().equals(selectedArrangeId)) {
                    Message message = new Message(-3, "已选其它时段", a);
                    arrangeDetails.put(a.getArrange_id(), message);
                }
            }
        }
        else{
            //判断是否还有其它限制条件
            if (arrangeList != null) {
                for(Arrange a:arrangeList){
                    //TODO:判断是否已过实验开始时间
                    //TODO:判断是否实验人数已满
                }
            }
        }
        return arrangeDetails;
    }
}
