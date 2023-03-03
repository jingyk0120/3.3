package cn.itcase.service.impl;

import cn.itcase.dao.UserDao;
import cn.itcase.dao.impl.UserDaoImpl;
import cn.itcase.domain.PageBean;
import cn.itcase.domain.User;
import cn.itcase.service.UserService;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {

    private UserDao dao= new UserDaoImpl();
    @Override
    public List<User> findAll() {
        /*
        * 调用dao完成查询
        * */
        return dao.findAll();
    }

    @Override
    public void addUser(User user) {
        dao.add(user);
    }

    @Override
    public void deletUser(String id) {
        dao.delete(Integer.parseInt(id));
    }

    @Override
    public User findUserById(String id) {
        return dao.findById(Integer.parseInt(id));
    }

    @Override
    public void updateUser(User user) {
        dao.update(user);
    }





    @Override
    public void delSelectedUser(String[] ids) {
        //1.遍历数组
        for (String id : ids) {
            //调用dao删除
            dao.delete(Integer.parseInt(id));
        }
    }

    @Override
    public PageBean<User> findUserByPage(String _currentPage, String _rows, Map<String, String[]> condition) {

        int currentPage=Integer.parseInt(_currentPage);
        int rows=Integer.parseInt(_rows);
        //判断是否是第一个选框的上一个
        if (currentPage <=1){
            currentPage=1;
        }

        //1.创建一个空的PageBean对象
        PageBean<User> pb =new PageBean<User>();
        //2.设置参数
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);

        //3.调用dao查询总记录数
        int totalCount =dao.findTotalCount(condition);
        pb.setTotalCount(totalCount);
        //4.调用dao查询List集合
        //计算开始的记录索引
        int start=(currentPage-1)*rows;
        List<User> list=dao.findByPage(start,rows,condition);
        pb.setList(list);

        //5.计算总页码
        int totalPage = totalCount % rows == 0 ? totalCount/rows : totalCount/rows +1;

        pb.setTotalPage(totalPage);


        //判断是否为最后一个框的下一个
        /*if (currentPage >=totalPage){
            currentPage=totalPage;
        }*/
        return pb;
    }

//    @Override
//    public User login(User user) {
//        return dao.;
//    }

}
