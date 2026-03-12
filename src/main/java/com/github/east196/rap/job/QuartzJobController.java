package com.github.east196.rap.job;

import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.east196.core.api.Result;
import com.github.east196.core.api.ResultUtil;
import com.github.east196.core.vo.PageVo;
import com.github.east196.ezsb.core.BootException;
import com.github.east196.ezsb.core.CommonConstant;
import com.github.east196.ezsb.mybatis.PageUtil;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

/**
 * @author Exrick
 */
@RestController
@Tag(name = "定时任务管理接口")
@RequestMapping("/xboot/quartzJob")
@Transactional
public class QuartzJobController {
    
    private static final Logger log = LoggerFactory.getLogger(QuartzJobController.class);

    @Autowired
    private QuartzJobService quartzJobService;

    @Autowired
    private Scheduler scheduler;

    @RequestMapping(value = "/getAllByPage",method = RequestMethod.GET)
    @Operation(summary = "获取所有定时任务")
    public Result<Page<QuartzJob>> getAll(@ModelAttribute PageVo page){
        Page<QuartzJob> data = quartzJobService.findAll(PageUtil.initPage(page));
        return new ResultUtil<Page<QuartzJob>>().setData(data);
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @Operation(summary = "添加定时任务")
    public Result<Object> addJob(@ModelAttribute QuartzJob job){
        List<QuartzJob> list = quartzJobService.findByJobClassName(job.getJobClassName());
        if(list!=null&&list.size()>0){
            return new ResultUtil<Object>().setErrorMsg("该定时任务类名已存在");
        }
        add(job.getJobClassName(),job.getCronExpression(),job.getParameter());
        quartzJobService.save(job);
        return new ResultUtil<Object>().setSuccessMsg("创建定时任务成功");
    }

    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @Operation(summary = "更新定时任务")
    public Result<Object> editJob(@ModelAttribute QuartzJob job){
        delete(job.getJobClassName());
        add(job.getJobClassName(),job.getCronExpression(),job.getParameter());
        job.setStatus(CommonConstant.STATUS_NORMAL);
        quartzJobService.update(job);
        return new ResultUtil<Object>().setSuccessMsg("更新定时任务成功");
    }

    @RequestMapping(value = "/pause",method = RequestMethod.POST)
    @Operation(summary = "暂停定时任务")
    public Result<Object> pauseJob(@ModelAttribute QuartzJob job){
        try {
            scheduler.pauseJob(JobKey.jobKey(job.getJobClassName()));
        } catch (SchedulerException e) {
            throw new BootException("暂停定时任务失败");
        }
        job.setStatus(CommonConstant.STATUS_DISABLE);
        quartzJobService.update(job);
        return new ResultUtil<Object>().setSuccessMsg("暂停定时任务成功");
    }

    @RequestMapping(value = "/resume",method = RequestMethod.POST)
    @Operation(summary = "恢复定时任务")
    public Result<Object> resumeJob(@ModelAttribute QuartzJob job){
        try {
            scheduler.resumeJob(JobKey.jobKey(job.getJobClassName()));
        } catch (SchedulerException e) {
            throw new BootException("恢复定时任务失败");
        }
        job.setStatus(CommonConstant.STATUS_NORMAL);
        quartzJobService.update(job);
        return new ResultUtil<Object>().setSuccessMsg("恢复定时任务成功");
    }

    @RequestMapping(value = "/delByIds/{ids}",method = RequestMethod.DELETE)
    @Operation(summary = "删除定时任务")
    public Result<Object> deleteJob(@PathVariable String[] ids){
        for(String id:ids){
            QuartzJob job = quartzJobService.get(id);
            delete(job.getJobClassName());
            quartzJobService.delete(job);
        }
        return new ResultUtil<Object>().setSuccessMsg("删除定时任务成功");
    }

    /**
     * 添加定时任务
     */
    public void add(String jobClassName, String cronExpression, String parameter){
        try {
            scheduler.start();
            JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass())
                    .withIdentity(jobClassName)
                    .usingJobData("parameter",parameter)
                    .build();
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName)
                    .withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error(e.toString());
            throw new BootException("创建定时任务失败");
        } catch (Exception e){
            throw new BootException("后台找不到该类名任务");
        }
    }

    /**
     * 删除定时任务
     */
    public void delete(String jobClassName){
        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName));
            scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName));
            scheduler.deleteJob(JobKey.jobKey(jobClassName));
        } catch (Exception e) {
            throw new BootException("删除定时任务失败");
        }
    }

    public static Job getClass(String classname) throws Exception {
        Class<?> class1 = Class.forName(classname);
        return (Job)class1.newInstance();
    }
}
