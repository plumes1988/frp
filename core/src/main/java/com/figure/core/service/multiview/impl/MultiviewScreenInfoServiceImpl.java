package com.figure.core.service.multiview.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.multiview.MultiviewScreenInfo;
import com.figure.core.model.multiview.MultiviewServiceInfo;
import com.figure.core.repository.multiview.MultiviewScreenInfoMapper;
import com.figure.core.service.multiview.IMultiviewScreenInfoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 多画面服务输出屏幕信息 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-06-30
 */
@Service
public class MultiviewScreenInfoServiceImpl extends ServiceImpl<MultiviewScreenInfoMapper, MultiviewScreenInfo> implements IMultiviewScreenInfoService {


     public void initMultiviewScreenInfoServices(MultiviewServiceInfo multiviewServiceInfo){

          QueryWrapper<MultiviewScreenInfo> multiviewScreenInfoQueryWrapper = new QueryWrapper();
          multiviewScreenInfoQueryWrapper.eq("multiviewServiceId",multiviewServiceInfo.getMultiviewServiceId());
          remove(multiviewScreenInfoQueryWrapper);

          int[] multiviewScreenPos = null;
          if(multiviewServiceInfo.getScreenType()==1){
              multiviewScreenPos = new int[]{1};
          }
         if(multiviewServiceInfo.getScreenType()==2){
             multiviewScreenPos = new int[]{2,3,4,5};
         }
         if(multiviewServiceInfo.getScreenType()==4){
             multiviewScreenPos = new int[]{6,7,8,9};
         }
         List<MultiviewScreenInfo> multiviewServiceInfos = new ArrayList<MultiviewScreenInfo>();
         for(int multiviewScreenPo:multiviewScreenPos){
             MultiviewScreenInfo multiviewScreenInfo = new MultiviewScreenInfo();
             multiviewScreenInfo.setMultiviewScreenPos(multiviewScreenPo);
             multiviewScreenInfo.setMultiviewServiceId(multiviewServiceInfo.getMultiviewServiceId());
             multiviewServiceInfos.add(multiviewScreenInfo);
         }
         saveBatch(multiviewServiceInfos);
     }
}
