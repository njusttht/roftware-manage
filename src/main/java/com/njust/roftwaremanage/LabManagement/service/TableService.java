package com.njust.roftwaremanage.LabManagement.service;

import com.njust.roftwaremanage.LabManagement.dao.ArrangeDAO;
import com.njust.roftwaremanage.LabManagement.dao.TableDAO;
import com.njust.roftwaremanage.LabManagement.entity.Arrange;
import com.njust.roftwaremanage.LabManagement.entity.Classroom;
import com.njust.roftwaremanage.LabManagement.entity.Table;

import java.util.List;

public class TableService {

    /** 为学生分配选课时的座位
     *  输入:StudentId(String),arrangeId(String),教室名classroom(String),当前已选人数(int)
     *  输出:Table
     * */
    //FIXME:已知bug
    //FIXME:当教室中存在开放性实验和普通实验时，座位分配将出现问题
    public Table assignSeats(String studentId, String arrangeId, Classroom classroom, int selectedNumber){
        //由于系统为顺序分配，为了提供效率，可以从当前已选人数开始分配位置
        int num = selectedNumber + 1;
        int seat = assignSeats(num,classroom.getNumber_total(),classroom,arrangeId);
        if(seat == -1){
            //当前面有学生退选时，可能会出现获取不到座位的情况，从第一个位置继续开始，以之前的遍历位置结束
            seat = assignSeats(1,num,classroom,arrangeId);
        }
        if(seat != -1){
            //获取到了座位
            Table table = new Table(seat, classroom.getAddress(), "正常",arrangeId,studentId);
            return table;
        }
        return null;
    }

    /** 为学生分配选课时的座位（子函数）
     *  输入:起始位置(int),结束位置(int),教室对象(Classroom),arrangeId(String)
     *  返回:int(应该给学生分配的位置)
     * */
    //FIXME:已知bug
    //FIXME:当教室中存在开放性实验和普通实验时，座位分配将出现问题
    public int assignSeats(int pos, int end, Classroom classroom, String arrangeId){
        boolean flag = false;       //是否成功分配座位
        while(!flag && pos <= end){      //避免超出范围
            //判断该机器是否有人
            Table table = TableDAO.findTableByTableIdAndArrangeId(pos,arrangeId);
            if(table == null){
                //如果该实验的座位号返回为空，则说明该位置没有人
                //判断机器是否损坏
                if(MachineService.checkCondition(classroom.getAddress(), String.valueOf(pos))){
                    //没有损坏
                    flag = true;
                    return pos;
                }
            }
            pos++;
        }
        return -1;
    }

    /** 根据学生id找到学生的实验安排
     *  输入:studentId(String)
     *  返回:List<Table>
     * */
    public static List<Table> findTableByStudentId(String studentId){
        return TableDAO.findTableByStudentId(studentId);
    }

    /**
     * 根据学生id和实验id找到座位
     * 输入:studentId,arrangeId
     * 输出:Table对象
     * */
    public static Table findTableByStudentIdAndArrangeId(String studentId,String arrangeId){
        return TableDAO.findTableByStudentIdAndArrangeId(studentId, arrangeId);
    }

    /**
     * 删除座位信息
     * 输入:Table
     * */
    public static void cancelTable(Table table){
        TableDAO.dropTable(table.getTable_id(), table.getArrange_id());
        //同时更改对应的arrange中的已选人数信息
        Arrange arrange = ArrangeService.getArrangesById(table.getArrange_id());
        arrange.setNumber_selected(arrange.getNumber_selected() - 1);
        //更新arrange
        ArrangeDAO.updateArrange(arrange);
    }
}
