package com.pp.controller;

import com.pp.controller.util.R;
import com.pp.service.IExperimentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/experiment")
public class ExperimentController {
    final IExperimentService experimentService;
    public ExperimentController(IExperimentService experimentService) {
        this.experimentService = experimentService;
    }
    /**
     * 获取当前所有的实验表格，无参数
     * @return 返回所有的实验表格所有记录
     */
    @GetMapping("/getAll")
    public R getAllExperiment(){
        return experimentService.getAllExperiment();
    }

    /**
     * 添加实验，传入文件、标题、摘要、相关算法
     * @param file 传输的文件
     * @param request 以form-data形式传参，除了file之外， 还需要传递summary,title,algorithm-id
     * */
    @PostMapping("/add")
    public R addExperiment(MultipartFile file, HttpServletRequest request){
        return experimentService.addExperiment(file,request);
    }

    /**
     * 修改实验
     * @param file 新文件的文档
     * @param request 包含新实验文件的id,title等信息
     * @return 描述是否修改成功，并将新的实验记录返回
     */
    @PostMapping ("modify")
    public R modify(MultipartFile file,HttpServletRequest request){
        return experimentService.modifyExperiment(file,request);
    }

    /**
     * 删除实验
     * @param id 需要删除的实验的id
     */
    @PostMapping("delete")
    public R deleteE(String id){
        return experimentService.deleteExperiment(id);
    }

    /**
     * 根据实验id查找实验
     */
    @PostMapping("selectByID")
    public R selectById(String id){
        return experimentService.selectByID(id);
    }
}