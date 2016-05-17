package com.liangxunwang.unimanager.mvc.member;

import com.liangxunwang.unimanager.dao.WorkDao;
import com.liangxunwang.unimanager.model.PKWork;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.mvc.vo.CommentVO;
import com.liangxunwang.unimanager.mvc.vo.PkCommentVO;
import com.liangxunwang.unimanager.mvc.vo.PkWorkVO;
import com.liangxunwang.unimanager.query.CommentQuery;
import com.liangxunwang.unimanager.query.PkCommentQuery;
import com.liangxunwang.unimanager.query.PkWorkQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.Page;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.net.URL;
import java.util.List;

/**
 * Created by liuzwei on 15-4-4.
 */
@Controller
public class PkWorkController extends ControllerConstants {

    @Autowired
    @Qualifier("pkWorkService")
    private SaveService saveWorkService;

    @Autowired
    @Qualifier("pkWorkService")
    private ListService listWorkService;

    @Autowired
    @Qualifier("appPkWorkService")
    private ListService appPkWorkService;

    @Autowired
    @Qualifier("pkWorkService")
    private DeleteService deleteWorkService;

    @Autowired
    @Qualifier("pkWorkService")
    private FindService findWorkService;

    @Autowired
    @Qualifier("pkWorkService")
    private ExecuteService executeWorkService;

    /**
     * 添加参赛作品
     * @param work
     * @return
     * localhost:8080/savePkWork.do?ztId=019ff5a907be4bf0be0e2460fc6138ac&empId=74cd364700ac4c5e9b97302d4ce50c9b&schoolId=8041&type=0&title=aaasdsds
     */
    @RequestMapping("/savePkWork")
    @ResponseBody
    public String saveWork(PKWork work, HttpSession session){
        try {
//            if (work.getType().equals("2")) {
//                String videoPath = init(session);
//                processImg(videoPath+work.getVideoUrl(), Constants.FFMPEG);
//                String videoPic = work.getVideoUrl().substring(0, work.getVideoUrl().lastIndexOf("."))+".jpg";
//                work.setPicUrl(videoPic);
//            }
            saveWorkService.save(work);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            if (e.getMessage().equals("HAS_ZP")){
                return toJSONString(ERROR_1);
            }else {
                return toJSONString(ERROR_2);
            }
        }
//        atch (ServletException e) {
//            e.printStackTrace();
//            return toJSONString(ERROR_2);
//        }
    }

    /**
     * 分页查询所有的参赛作品
     * @param query
     * @param page
     * @return
     */
    @RequestMapping(value = "listWorkApp", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    public String listWork(PkWorkQuery query, Page page){
        query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
        query.setSize(query.getSize()==0?page.getDefaultSize():query.getSize());
        try {
            if(query.getThemeId() != null && !query.getThemeId().equals("")){
                //说明有主题
                List<PkWorkVO> list = (List<PkWorkVO>) listWorkService.list(query);
                DataTip tip = new DataTip();
                tip.setData(list);
                return toJSONString(tip);
            }else {
                List<PkWorkVO> list = (List<PkWorkVO>) appPkWorkService.list(query);
                DataTip tip = new DataTip();
                tip.setData(list);
                return toJSONString(tip);
            }


        }catch (ServiceException e) {
            return toJSONString(ERROR_1);
        }
    }

    /**
     * 根据作品ID删除作品
     * @param zpId
     * @return
     */
    @RequestMapping("/deleteWorkById")
    @ResponseBody
    public String deleteWorkById(String zpId){
        try {
            deleteWorkService.delete(zpId);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    /**
     * 通过上下文来取工程路径
     *
     * @return
     * @throws Exception
     */
    private String getAbsolutePathByContext(HttpSession session) throws Exception {

        String webPath = session.getServletContext().getRealPath("/");

        webPath = webPath.replaceAll("[\\\\\\/]WEB-INF[\\\\\\/]classes[\\\\\\/]?", "/");
        webPath = webPath.replaceAll("[\\\\\\/]+", "/");
        webPath = webPath.replaceAll("%20", " ");

        if (webPath.matches("^[a-zA-Z]:.*?$")) {

        } else {
            webPath = "/" + webPath;
        }

        webPath += "/";
        webPath = webPath.replaceAll("[\\\\\\/]+", "/");
        return webPath;

    }

    /**
     * 通过类路径来取工程路径
     *
     * @return
     * @throws Exception
     */
    private String getAbsolutePathByClass(HttpSession session) throws Exception {
        String webPath = this.getClass().getResource("/").getPath().replaceAll("^\\/", "");
        webPath = webPath.replaceAll("[\\\\\\/]WEB-INF[\\\\\\/]classes[\\\\\\/]?", "/");
        webPath = webPath.replaceAll("[\\\\\\/]+", "/");
        webPath = webPath.replaceAll("%20", " ");

        if (webPath.matches("^[a-zA-Z]:.*?$")) {

        } else {
            webPath = "/" + webPath;
        }

        webPath += "/";
        webPath = webPath.replaceAll("[\\\\\\/]+", "/");

        return webPath;
    }
    private String getAbsolutePathByResource(HttpSession session) throws Exception {
        URL url = session.getServletContext().getResource("/");
        String path = new File(url.toURI()).getAbsolutePath();
        if (!path.endsWith("\\") && !path.endsWith("/")) {
            path += File.separator;
        }
        return path;
    }

    public String init(HttpSession session) throws ServletException {
        String webPath = null;
        try {
            webPath = getAbsolutePathByContext(session);
        } catch (Exception e) {
        }

        // 在weblogic 11g 上可能无法从上下文取到工程物理路径，所以改为下面的
        if (webPath == null) {
            try {
                webPath = getAbsolutePathByClass(session);
            } catch (Exception e) {
            }
        }

        if (webPath == null) {
            try {
                webPath = getAbsolutePathByResource(session);
            } catch (Exception e) {
            }
        }
        return webPath;
    }

    public boolean processImg(String veido_path,String ffmpeg_path) {
        File file = new File(veido_path);
        if (!file.exists()) {
            System.err.println("路径[" + veido_path + "]对应的视频文件不存在!");
            return false;
        }
        List<String> commands = new java.util.ArrayList<String>();
        commands.add(ffmpeg_path);
        commands.add("-i");
        commands.add(veido_path);
        commands.add("-y");
        commands.add("-f");
        commands.add("image2");
        commands.add("-ss");
        commands.add("1");//这个参数是设置截取视频多少秒时的画面
        //commands.add("-t");
        //commands.add("0.001");
        commands.add("-s");
        commands.add("700x525");
        commands.add(veido_path.substring(0, veido_path.lastIndexOf(".")).replaceFirst("vedio", "file") + ".jpg");
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            builder.start();
            System.out.println("截取成功");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据作品ID查找作品
     * @param zpId
     * @return
     */
    @RequestMapping(value = "/findWorkById", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    public String findWorkById(String zpId){
        try {
            PkWorkVO vo = (PkWorkVO) findWorkService.findById(zpId);
            DataTip tip = new DataTip();
            tip.setData(vo);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }


    @Autowired
    @Qualifier("pkCommentService")
    private ListService pkCommentListService;

    /**
     * 分享页
     * @param zpId
     * @param map
     * @return
     */
    @RequestMapping("/viewWork")
    public String viewWork(String zpId, ModelMap map){
        Object[] params = (Object[]) executeWorkService.execute(zpId);
        PkWorkVO vo = (PkWorkVO) params[0];
        List<PkCommentVO> commentVOs = (List<PkCommentVO>) params[1];
        map.put("vo", vo);
        map.put("commentVOs", commentVOs);
        if (!StringUtil.isNullOrEmpty(vo.getPicUrl())){
            String[] pics = vo.getPicUrl().split(",");
            if(pics != null && pics.length>0){
                for (int i=0;i<pics.length;i++){
                    if (pics[i].startsWith("upload")) {
                        pics[i] = Constants.URL + pics[i];
                    }else {
                        pics[i] = Constants.QINIU_URL + pics[i];
                    }
                }
            }
            map.put("pics", pics);
        }

        //查询该动态的评论
        PkCommentQuery query = new PkCommentQuery();
        query.setIndex(1);
        query.setSize(10);
        query.setZpId(zpId);

        List<PkCommentVO> list = (List<PkCommentVO>) pkCommentListService.list(query);

        map.put("list", list);
        return "/theme/work";
    }
}
